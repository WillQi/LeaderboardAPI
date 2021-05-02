# LeaderboardAPI - Core
The core functionality that must be included with any LeaderboardAPI implementation.
You can see examples of how to create your own `DataSource` or `Leaderboard` in the [examples](https://github.com/WillQi/LeaderboardAPI/tree/master/core/examples) directory.

## Download
You can find the latest core implementation in the [releases](https://github.com/WillQi/LeaderboardAPI/releases) or you can build it yourself.
- `git clone https://github.com/WillQi/LeaderboardAPI.git`
- `cd LeaderboardAPI/core`
- `mvn clean package`
- Navigate to the `target` folder and add the jar to your project

## Terminology
- A **Leaderboard** represents the frontend object used to retrieve scores from the `DataSource`. The key and value provided do not have to match the database types.
- A **DataSource** represents the backend object used to retrieve data from the data source. (in most cases, the database) The key and value provided must match the retrieved database types.