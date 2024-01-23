package org.gamerbot.support.leaderboards;

import net.dv8tion.jda.api.entities.User;
import org.gamerbot.support.database.DatabaseSupport;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LeaderboardsHelper implements DatabaseSupport {

    private static LeaderboardsHelper instance;

    public int updateLeaderboards(User user, String column, int amount) {
        ResultSet resultSet = executeQuery("SELECT * FROM messages_leaderboard WHERE user_id = " + user.getId());
        try {
            if (resultSet.next()) {
                int newAmount = resultSet.getInt(column) + amount;
                execute("UPDATE messages_leaderboard SET " + column + " = " + newAmount + " WHERE user_id = " + user.getId());
                return newAmount;
            } else {
                execute("INSERT INTO messages_leaderboard VALUES (NULL, " + user.getId() + ", " + user.getName() + ", 1, 0, 0, NULL)");
                execute("UPDATE messages_leaderboard SET " + column + " = " + amount + " WHERE user_id = " + user.getId());
                return amount;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amount;
    }

    public int getLeaderboards(User user, String column) {
        ResultSet resultSet = executeQuery("SELECT * FROM messages_leaderboard WHERE user_id = " + user.getId());
        try {
            if (resultSet.next()) return resultSet.getInt(column);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static LeaderboardsHelper getInstance() {
        if (instance == null) instance = new LeaderboardsHelper();
        return instance;
    }
}
