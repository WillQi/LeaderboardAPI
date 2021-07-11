# LeaderboardAPI - SQL Implementation
DataSource implementation for the LeaderboardAPI using SQL.

## Download
You can find the latest SQL implementation in the [releases](https://github.com/WillQi/LeaderboardAPI/releases) or you can build it yourself.
- `git clone https://github.com/WillQi/LeaderboardAPI.git`
- `cd LeaderboardAPI/sql-impl`
- `mvn clean package`
- Navigate to the `target` folder and add the jar to your project

## Example Usage

```java
import io.github.willqi.leaderboardapi.core.datasources.DataSource;
import io.github.willqi.leaderboardapi.core.leaderboards.Leaderboard;
import io.github.willqi.leaderboardapi.core.leaderboards.UUIDLeaderboard;
import io.github.willqi.leaderboardapi.redis.datasource.RedisDataSource;
import io.github.willqi.leaderboardapi.sql.datasource.SQLDataSource;

import java.util.UUID;

class Main {
    public static void main(String[] args) {
        DataSource<String, Double> dataSource = new SQLDataSource<>("jbdcUrl", "username", "password"){
            // ... you will need to implement the methods yourself. (It puts a greater influence of what data you want to receive from the database into your hands!)
            // However you have access to .getConnection() which will return a SQL connection for you to use
        };
        Leaderboard<UUID, Double, DataSource<String, Double>> leaderboard = new UUIDLeaderboard<>(dataSource);
        // From here on out, you can use the leaderboard as normally!
    }
}
```