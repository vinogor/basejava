package ru.vinogor.storage;

import ru.vinogor.exception.NotExistStorageException;
import ru.vinogor.model.Resume;
import ru.vinogor.sql.ConnectionFactory;
import ru.vinogor.util.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        ConnectionFactory connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlHelper = new SqlHelper(connectionFactory);
    }

//    public void clear() {
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("DELETE FROM resume")) {
//            ps.execute();
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
//    }

    @Override
    public void clear() {
        sqlHelper.doSql("DELETE FROM resume", ps -> {
                    ps.execute();
                    return null;
                }
        );
    }

//    @Override
//    public void update(Resume r) {
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
//            ps.setString(1, r.getFullName());
//            ps.setString(2, r.getUuid());
//            if (ps.executeUpdate() == 0) {
//                throw new NotExistStorageException(r.getUuid());
//            }
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
//    }

    @Override
    public void update(Resume r) {
        sqlHelper.doSql("UPDATE resume SET full_name = ? WHERE uuid = ?",
                ps -> {
                    ps.setString(1, r.getFullName());
                    ps.setString(2, r.getUuid());
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(r.getUuid());
                    }
                    return null;
                }
        );
    }

//    @Override
//    public void save(Resume r) {
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
//            ps.setString(1, r.getUuid());
//            ps.setString(2, r.getFullName());
//            ps.execute();
//        } catch (SQLException e) {
//            throw new ExistStorageException(r.getUuid());
//        }
//    }

    @Override
    public void save(Resume r) {
        sqlHelper.doSql("INSERT INTO resume (uuid, full_name) VALUES (?,?)",
                ps -> {
                    ps.setString(1, r.getUuid());
                    ps.setString(2, r.getFullName());
                    ps.execute();
                    return null;
                }
        );
    }

//    @Override
//    public void delete(String uuid) {
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("DELETE FROM resume WHERE uuid = ? ")) {
//            ps.setString(1, uuid);
//            if (ps.executeUpdate() == 0) {
//                throw new NotExistStorageException(uuid);
//            }
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
//    }

    @Override
    public void delete(String uuid) {
        sqlHelper.doSql("DELETE FROM resume WHERE uuid = ? ",
                ps -> {
                    ps.setString(1, uuid);
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                }
        );
    }

//    @Override
//    public Resume get(String uuid) {
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r WHERE r.uuid = ?")) {
//            ps.setString(1, uuid);
//            ResultSet rs = ps.executeQuery();
//            if (!rs.next()) {
//                throw new NotExistStorageException(uuid);
//            }
//            return new Resume(uuid, rs.getString("full_name"));
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
//    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.doSql("SELECT * FROM resume r WHERE r.uuid = ? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    return new Resume(uuid, rs.getString("full_name"));
                });
    }

//    @Override
//    public List<Resume> getAllSorted() {
//        List<Resume> list = new ArrayList<>();
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume")) {
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
//            }
//            return list;
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
//    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.doSql("SELECT * FROM resume ",
                ps -> {
                    List<Resume> list = new ArrayList<>();
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
                    }
                    return list;
                }
        );
    }

//    @Override
//    public int size() {
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("SELECT count(*) FROM resume")) {
//            ResultSet rs = ps.executeQuery();
//            if (!rs.next()) {
//                return 0;
//            } else {
//                return rs.getInt(1);
//            }
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
//    }

    @Override
    public int size() {
        return sqlHelper.doSql("SELECT count(*) FROM resume",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        return 0;
                    } else {
                        return rs.getInt(1);
                    }
                }
        );
    }
}