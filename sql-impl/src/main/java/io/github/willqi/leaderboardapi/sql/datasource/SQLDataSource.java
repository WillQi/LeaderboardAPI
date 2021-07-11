package io.github.willqi.leaderboardapi.sql.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.willqi.leaderboardapi.core.datasources.DataSource;
import io.github.willqi.leaderboardapi.core.datasources.exceptions.DataSourceException;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class SQLDataSource<K, V> implements DataSource<K, V> {

    private final HikariDataSource source;

    public SQLDataSource(String jbdcUrl, String username, String password) {
        HikariConfig dbConfig = new HikariConfig();
        dbConfig.setJdbcUrl(jbdcUrl);
        dbConfig.setUsername(username);
        dbConfig.setPassword(password);
        this.source = new HikariDataSource(dbConfig);
    }

    protected Connection getConnection() throws SQLException {
        return this.source.getConnection();
    }

    @Override
    public void close() throws DataSourceException {
        try {
            this.source.close();
        } catch (RuntimeException exception) {
            throw new DataSourceException(exception);
        }
    }

}
