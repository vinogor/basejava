package ru.vinogor.util;

import org.postgresql.util.PSQLException;
import ru.vinogor.exception.ExistStorageException;
import ru.vinogor.exception.StorageException;
import ru.vinogor.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T doSql(String commandSql, SqlAction<T> sqlAction) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(commandSql)) {
            return sqlAction.doSql(ps);
        } catch (SQLException e) {
            if (e instanceof PSQLException) {
                if (e.getSQLState().equals("23505")) {
                    throw new ExistStorageException(" ");
                }
            }
            throw new StorageException(e);
        }
    }

    public interface SqlAction<T> {
        T doSql(PreparedStatement ps) throws SQLException;
    }
}
