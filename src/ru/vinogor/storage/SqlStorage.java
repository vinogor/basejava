package ru.vinogor.storage;

import ru.vinogor.exception.NotExistStorageException;
import ru.vinogor.model.ContactType;
import ru.vinogor.model.Resume;
import ru.vinogor.sql.ConnectionFactory;
import ru.vinogor.util.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
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
    public Resume get(String uuid) {
        return sqlHelper.doSql(""
                        + "   SELECT * FROM resume AS r "
                        + "LEFT JOIN contact AS c "
                        + "       ON r.uuid = c.resume_uuid "
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
                    } while (rs.next());
                    return r;
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
                    writeContact(r, conn);
                    return null;
                }
        );
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.doSql(""
                        + "   SELECT * FROM resume AS r "
                        + "LEFT JOIN contact AS c "
                        + "       ON r.uuid = c.resume_uuid "
                        + " ORDER BY full_name, uuid"
                ,
                ps -> {

                    ResultSet rs = ps.executeQuery();

                    Map<String, Resume> map = new LinkedHashMap<>();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid");
                        String full_name = rs.getString("full_name");

                        if (map.get(uuid) == null) {
                            Resume resume = new Resume(uuid, full_name);
                            readContact(rs, resume);
                            map.put(uuid, resume);
                        } else {
                            Resume resume = map.get(uuid);
                            readContact(rs, resume);
                            map.put(uuid, resume);
                        }
                    }
                    return new ArrayList<>(map.values());
                }
        );
    }

    private void readContact(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            ContactType type = ContactType.valueOf(rs.getString("type"));
            resume.addContact(type, value);
        }
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
}