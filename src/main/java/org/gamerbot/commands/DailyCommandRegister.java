package org.gamerbot.commands;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.gamerbot.handlers.DiscordCommandRegister;

public class DailyCommandRegister implements DiscordCommandRegister {

    @Override
    public CommandData registerCommands() {
        return Commands.slash("daily", "Receives a random amount of daily coins from ◆ 1 - ◆ 5000.");
    }
}
