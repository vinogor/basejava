package ru.vinogor.storage;

import ru.vinogor.exception.NotExistStorageException;
import ru.vinogor.model.ContactType;
import ru.vinogor.model.Resume;
import ru.vinogor.sql.ConnectionFactory;
import ru.vinogor.util.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
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
                        ContactType type = ContactType.valueOf(rs.getString("type"));
                        String value = rs.getString("value");
                        r.addContact(type, value);
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

                    sqlHelper.doSql("DELETE FROM contact", ps -> {
                                ps.execute();
                                return null;
                            }
                    );

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

                    Map<String, Resume> map = new HashMap<>();
                    List<Resume> resumes = new ArrayList<>();

                    while (rs.next()) {
                        String uuid = rs.getString("uuid");
                        String full_name = rs.getString("full_name");

                        ContactType type = ContactType.valueOf(rs.getString("type"));
                        String value = rs.getString("value");

                        if (map.containsKey(uuid)) { // если мапа с таким ключом уже есть, то
                            // добавить ещё контакт в последнее резюме
                            resumes.get(resumes.size() - 1).addContact(type, value);
                        } else {
                            // создать новое резюме + добавить в него контакты + добавить в map новую пару
                            Resume newResume = new Resume(uuid, full_name);
                            newResume.addContact(type, value);
                            resumes.add(newResume);
                            map.put(uuid, newResume);
                        }
                        Resume resume = new Resume();
                        map.put(uuid, resume);
                    }
                    return resumes;
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