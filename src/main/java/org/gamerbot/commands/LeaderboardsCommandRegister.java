package org.gamerbot.commands;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.gamerbot.handlers.DiscordCommandRegister;

import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;

public class LeaderboardsCommandRegister extends ListenerAdapter implements DiscordCommandRegister {

    @Override
    public CommandData registerCommands() {
        OptionData typeData = new OptionData(STRING, "type", "The type of leaderboard to look at", true, false)
                .addChoices(new Command.Choice("messages", "messages"),
                        new Command.Choice("reacts", "reacts"),
                        new Command.Choice("coins", "coins"),
                        new Command.Choice("trivia", "trivia"));
        return Commands.slash("leaderboards", "Server leaderboards!")
                        .addOptions(typeData)
                        .setGuildOnly(true);
    }
}
