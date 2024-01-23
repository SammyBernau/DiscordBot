package org.gamerbot.handlers.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.gamerbot.handlers.Command;
import org.gamerbot.support.database.DatabaseSupport;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LeaderboardsCommand implements Command, DatabaseSupport {

    @Override
    public void handleCommand(SlashCommandInteractionEvent event) {
        String type = event.getOption("type", OptionMapping::getAsString);
        ResultSet result = executeQuery("SELECT * FROM messages_leaderboard ORDER BY " + type + " DESC LIMIT 8");
        String column = Character.toUpperCase(type.charAt(0)) + type.substring(1);
        try {
            int index = 0;
            EmbedBuilder builder = new EmbedBuilder();
            while (result.next()) {
                int amount = result.getInt(type);
                // String nickname = event.getGuild().getMemberById(result.getString("user_id")).getNickname();
                String name = event.getJDA().retrieveUserById(result.getString("user_id")).complete().getName();
                builder.addField("Name", ++index + ". " + name, true);
                builder.addField(column, String.valueOf(amount), true);
                builder.addBlankField(true);
            }
            event.replyEmbeds(builder.build()).queue();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
