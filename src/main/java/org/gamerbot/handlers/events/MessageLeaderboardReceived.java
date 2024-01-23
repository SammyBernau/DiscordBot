package org.gamerbot.handlers.events;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.gamerbot.support.database.DatabaseSupport;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageLeaderboardReceived extends ListenerAdapter implements DatabaseSupport {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        ResultSet resultSet = executeQuery("SELECT * FROM messages_leaderboard WHERE user_id = " + event.getAuthor().getId());
        try {
            if (resultSet.next()) {
                execute("UPDATE messages_leaderboard SET messages = " + (resultSet.getInt("messages") + 1) + " WHERE user_id = " + event.getAuthor().getId());
            } else {
                execute("INSERT INTO messages_leaderboard VALUES (NULL, " + event.getAuthor().getId() + ", \"" + event.getAuthor().getName() + "\", 1, 0, 0, NOW())");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
