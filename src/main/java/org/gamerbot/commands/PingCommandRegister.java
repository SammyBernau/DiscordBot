package org.gamerbot.commands;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.gamerbot.handlers.DiscordCommandRegister;

public class PingCommandRegister extends ListenerAdapter implements DiscordCommandRegister {
    @Override
    public CommandData registerCommands() {
        return Commands.slash("ping", "Says pong!").setGuildOnly(true);
    }
}
