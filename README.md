# LeaderboardAPI
Simple API for developers who are looking to implement their own leaderboard system without worrying too much about the database implementation.

| Module       | Description                                                      |
| ------------ | ---------------------------------------------------------------- |
| `core`       | Mandatory package to be included as it contains the core classes |
| `redis-impl` | Redis DataSource implementation                                  |
| `sql-impl`   | SQL DataSource implementation                                    |

## Download
You can find the latest jars in the [releases](https://github.com/WillQi/LeaderboardAPI/releases) or you can build it yourself.
- `git clone https://github.com/WillQi/LeaderboardAPI.git`
- `cd LeaderboardAPI`
- `mvn clean package`
- Navigate to the `target` folder in the `core` and optionally the implementation you want, and then add the jars to your project.