package ru.vinogor.storage;

import ru.vinogor.exception.NotExistStorageException;
import ru.vinogor.model.*;
import ru.vinogor.sql.ConnectionFactory;
import ru.vinogor.util.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }

        ConnectionFactory connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlHelper = new SqlHelper(connectionFactory);
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.doSql(""
                        + " DELETE FROM resume "
                        + "       WHERE uuid = ? "
                ,
                ps -> {
                    ps.setString(1, uuid);
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                }
        );
    }

    @Override
    public void clear() {
        sqlHelper.doSql("DELETE FROM resume", ps -> {
                    ps.execute();
                    return null;
                }
        );
    }

    @Override
    public int size() {
        return sqlHelper.doSql("SELECT count(*) FROM resume",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    return !rs.next() ? 0 : rs.getInt(1);
                }
        );
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement(""
                            + " INSERT INTO resume (uuid, full_name) "
                            + "      VALUES (?,?) "
                    )) {
                        ps.setString(1, r.getUuid());
                        ps.setString(2, r.getFullName());
                        ps.execute();
                    }

                    writeContact(r, conn);
                    writeSection(r, conn);

                    return null;
                }
        );
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement(""
                            + " UPDATE resume "
                            + "    SET full_name = ? "
                            + "  WHERE uuid = ?")) {
                        ps.setString(1, r.getFullName());
                        ps.setString(2, r.getUuid());
                        if (ps.executeUpdate() == 0) {
                            throw new NotExistStorageException(r.getUuid());
                        }
                    }

                    deleteContact(r, conn);
                    deleteSection(r, conn);

                    writeContact(r, conn);
                    writeSection(r, conn);

                    return null;
                }
        );
    }


    @Override
    public Resume get(String uuid) {
        return sqlHelper.doSql(""
                        + "  SELECT * FROM resume AS r "
                        + "LEFT JOIN contact AS c "
                        + "       ON r.uuid = c.resume_uuid "
                        + "LEFT JOIN section AS s "
                        + "       ON r.uuid = s.resume_uuid "
                        + "    WHERE r.uuid = ? "
                ,
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }

                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        readContact(rs, r);
                        readSection(rs, r);

                    } while (rs.next());
                    return r;
                }
        );
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> resumeMap = new LinkedHashMap<>();
        sqlHelper.transactionalExecute(connection -> {

                    try (PreparedStatement psResume = connection.prepareStatement("SELECT * FROM resume ORDER BY full_name,uuid")) {
                        ResultSet rsResume = psResume.executeQuery();
                        while (rsResume.next()) {
                            String uuid = rsResume.getString("uuid");
                            String full_name = rsResume.getString("full_name");
                            resumeMap.put(uuid, new Resume(uuid, full_name));
                        }
                    }

                    try (PreparedStatement psContacts = connection.prepareStatement("SELECT * FROM contact")) {
                        ResultSet rsContacts = psContacts.executeQuery();
                        while (rsContacts.next()) {
                            String resume_uuid = rsContacts.getString("resume_uuid");
                            Resume resume = resumeMap.get(resume_uuid);
                            readContact(rsContacts, resume);
                        }
                    }

                    try (PreparedStatement psSection = connection.prepareStatement("SELECT * FROM section")) {
                        ResultSet rsSection = psSection.executeQuery();
                        while (rsSection.next()) {
                            String resume_uuid = rsSection.getString("resume_uuid");
                            Resume resume = resumeMap.get(resume_uuid);
                            readSection(rsSection, resume);
                        }
                    }
                    return null;
                }
        );
        return new ArrayList<>(resumeMap.values());
    }

    private void readContact(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            ContactType type = ContactType.valueOf(rs.getString("type"));
            resume.addContact(type, value);
        }
    }

    private void deleteContact(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid=?")) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    private void writeContact(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(""
                + " INSERT INTO contact (resume_uuid, type, value)"
                + "      VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void readSection(ResultSet rs, Resume r) throws SQLException {
        String sectionValue = rs.getString("value_section");
        if (sectionValue != null) {
            SectionType sectionType = SectionType.valueOf(rs.getString("type_section"));
            switch (sectionType) {
                case OBJECTIVE:
                case PERSONAL:
                    r.addSection(sectionType, new TextSection(sectionValue));
                    break;

                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    r.addSection(sectionType, new ListOfTextSection(Arrays.asList(sectionValue.split("\n"))));
                    break;
            }
        }
    }

    private void deleteSection(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section WHERE resume_uuid=?")) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    private void writeSection(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(""
                + " INSERT INTO section (resume_uuid, type_section, value_section)"
                + "      VALUES (?, ?, ?)")) {
            for (Map.Entry<SectionType, AbstractSection> e : r.getSections().entrySet()) {

                ps.setString(1, r.getUuid());
                SectionType sectionType = e.getKey();
                ps.setString(2, sectionType.name());

                AbstractSection value = e.getValue();

                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        ps.setString(3, ((TextSection) value).getContent());
                        break;

                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ps.setString(3, String.join("\n", ((ListOfTextSection) value).getListOfItems()));
                        break;
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}