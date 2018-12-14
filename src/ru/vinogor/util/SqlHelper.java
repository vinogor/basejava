package ru.vinogor.util;

import org.postgresql.util.PSQLException;
import ru.vinogor.exception.ExistStorageException;
import ru.vinogor.exception.StorageException;
import ru.vinogor.sql.ConnectionFactory;
import ru.vinogor.sql.SqlTransaction;

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

    public <T> T transactionalExecute(SqlTransaction<T> executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = executor.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
//              throw ExceptionUtil.convertException(e);
                if (e instanceof PSQLException) {
                    if (e.getSQLState().equals("23505")) {
                        throw new ExistStorageException(" ");
                    }
                }
                throw new StorageException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
