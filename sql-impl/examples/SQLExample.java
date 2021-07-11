import io.github.willqi.leaderboardapi.core.datasources.DataSource;
import io.github.willqi.leaderboardapi.core.datasources.exceptions.DataSourceException;
import io.github.willqi.leaderboardapi.core.leaderboards.Leaderboard;
import io.github.willqi.leaderboardapi.sql.datasource.SQLDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*
Example table creation syntax
=======
CREATE TABLE sql_leaderboard (
    id INT NOT NULL,
    uuid VARCHAR(36) NOT NULL,
    score DOUBLE NOT NULL,
    PRIMARY KEY(id)
);
 */

public class SQLExample {

    private static final String GET_TOP_QUERY = "SELECT uuid, score FROM sql_leaderboard ORDER BY score DESC LIMIT ?";
    private static final String GET_SCORE_QUERY = "SELECT score FROM sql_leaderboard WHERE uuid=?";
    private static final String SET_SCORE_QUERY = "INSERT INTO sql_leaderboard (uuid, score) VALUES (?, ?) ON DUPLICATE KEY UPDATE score=?";
    private static final String ADD_SCORE_QUERY = "INSERT INTO sql_leaderboard (uuid, score) VALUES (?, ?) ON DUPLICATE KEY UPDATE score=score + ?";
    private static final String REMOVE_QUERY = "DELETE FROM sql_leaderboard WHERE uuid=?";
    private static final String RESET_QUERY = "DELETE FROM sql_leaderboard;";


    public static void main(String[] args) {
        // Create the SQLDataSource implementation
        // This example implies that the table is already created
        DataSource<String, Double> dataSource = new SQLDataSource<String, Double>("jbdcUrl", "username", "password"){
            @Override
            public List<Record<String, Double>> getTop(int placings) throws DataSourceException {
                try (Connection connection = this.getConnection()) {
                    try (PreparedStatement stmt = connection.prepareStatement(GET_TOP_QUERY)) {
                        stmt.setInt(1, placings);

                        try (ResultSet result = stmt.executeQuery()) {
                            List<Record<String, Double>> records = new ArrayList<>();
                            while (result.next()) {
                                String uuid = result.getString("uuid");
                                double score = result.getDouble("score");
                                records.add(new Record<>(uuid, score));
                            }
                            return records;
                        }
                    }
                } catch (SQLException exception) {
                    throw new DataSourceException(exception);
                }
            }

            @Override
            public Double get(String key) throws DataSourceException {
                try (Connection connection = this.getConnection()) {
                    try (PreparedStatement stmt = connection.prepareStatement(GET_SCORE_QUERY)) {
                        stmt.setString(1, key);

                        try (ResultSet result = stmt.executeQuery()) {
                            if (result.next()) {
                                return result.getDouble("score");
                            } else {
                                return 0d;
                            }
                        }
                    }
                } catch (SQLException exception) {
                    throw new DataSourceException(exception);
                }
            }

            @Override
            public void set(String key, Double value) throws DataSourceException {
                try (Connection connection = this.getConnection()) {
                    try (PreparedStatement stmt = connection.prepareStatement(SET_SCORE_QUERY)) {
                        stmt.setString(1, key);
                        stmt.setDouble(2, value);
                        stmt.setDouble(3, value);
                        stmt.execute();
                    }
                } catch (SQLException exception) {
                    throw new DataSourceException(exception);
                }
            }

            @Override
            public void add(String key, Double value) throws DataSourceException {
                try (Connection connection = this.getConnection()) {
                    try (PreparedStatement stmt = connection.prepareStatement(ADD_SCORE_QUERY)) {
                        stmt.setString(1, key);
                        stmt.setDouble(2, value);
                        stmt.setDouble(3, value);
                        stmt.execute();
                    }
                } catch (SQLException exception) {
                    throw new DataSourceException(exception);
                }
            }

            @Override
            public void remove(String key) throws DataSourceException {
                try (Connection connection = this.getConnection()) {
                    try (PreparedStatement stmt = connection.prepareStatement(REMOVE_QUERY)) {
                        stmt.setString(1, key);
                        stmt.execute();
                    }
                } catch (SQLException exception) {
                    throw new DataSourceException(exception);
                }
            }

            @Override
            public void reset() throws DataSourceException {
                try (Connection connection = this.getConnection()) {
                    try (Statement stmt = connection.createStatement()) {
                        stmt.execute(RESET_QUERY);
                    }
                } catch (SQLException exception) {
                    throw new DataSourceException(exception);
                }
            }
        };
        Leaderboard<UUID, Double, DataSource<String, Double>> leaderboard = new UUIDLeaderboard<>(dataSource);
        // From here on out, you can use the leaderboard as normally!

    }


    private static class UUIDLeaderboard<V> extends Leaderboard<UUID, V, DataSource<String, V>> {

        public UUIDLeaderboard(DataSource<String, V> dataSource) {
            super(dataSource);
        }

        @Override
        public List<Entry<UUID, V>> getTop(int placings) throws DataSourceException {
            List<Entry<UUID, V>> results = new ArrayList<>();
            this.dataSource.getTop(placings).forEach(record ->
                    results.add(new Entry<>(UUID.fromString(record.getIdentifier()), record.getValue())));
            return results;
        }

        @Override
        public V get(UUID key) throws DataSourceException {
            return this.dataSource.get(key.toString());
        }

        @Override
        public void add(UUID key, V amount) throws DataSourceException {
            this.dataSource.add(key.toString(), amount);
        }

        @Override
        public void set(UUID key, V value) throws DataSourceException {
            this.dataSource.set(key.toString(), value);
        }

        @Override
        public void remove(UUID key) throws DataSourceException {
            this.dataSource.remove(key.toString());
        }

        @Override
        public void reset() throws DataSourceException {
            this.dataSource.reset();
        }

    }


}
