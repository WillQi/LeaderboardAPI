# LeaderboardAPI - Redis Implementation
Redis DataSource implementation for the LeaderboardAPI using Jedis.
The `RedisDataSource` **only supports** string keys and double values.

## Download
You can find the latest Redis implementation in the [releases](https://github.com/WillQi/LeaderboardAPI/releases) or you can build it yourself.
- `git clone https://github.com/WillQi/LeaderboardAPI.git`
- `cd LeaderboardAPI/redis-impl`
- `mvn clean package`
- Navigate to the `target` folder and add the jar to your project

## Example Usage
```java
import io.github.willqi.leaderboardapi.core.datasources.DataSource;
import io.github.willqi.leaderboardapi.core.leaderboards.Leaderboard;
import io.github.willqi.leaderboardapi.core.leaderboards.UUIDLeaderboard;
import io.github.willqi.leaderboardapi.redis.datasource.RedisDataSource;

import java.util.UUID;

class Main {
    public static void main(String[] args) {
        DataSource<String, Double> dataSource = new RedisDataSource("key to store leaderboard under", "0.0.0.0", 6379, "username", "password");
        Leaderboard<UUID, Double, DataSource<String, Double>> leaderboard = new UUIDLeaderboard<>();
        // From here on out, you can use the leaderboard as normally!
    }
}
```