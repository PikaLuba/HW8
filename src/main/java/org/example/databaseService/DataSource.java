package org.example.databaseService;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private static DataSource INSTANCE;
    private static final String DB_URL = "jdbc:mysql://172.16.48.174:3306/mybd";
    private static final String DB_USER = "boot";
    private static final String DB_PASSWORD = "11222121222";

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    private DataSource() {
        config.setJdbcUrl(DB_URL);
        config.setUsername(DB_USER);
        config.setPassword(DB_PASSWORD);
        ds = new HikariDataSource(config);
        Flyway flyway = Flyway.configure()
                .dataSource(ds)
                .locations("db.migration")
                .load();
        flyway.migrate();
    }

    public static DataSource getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        } else {
            INSTANCE = new DataSource();
            return INSTANCE;
        }
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}

