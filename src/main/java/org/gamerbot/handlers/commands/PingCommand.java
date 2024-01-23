package org.gamerbot.handlers.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.gamerbot.handlers.Command;

public class PingCommand implements Command {
    @Override
    public void handleCommand(SlashCommandInteractionEvent event) {
        event.reply("> Pong!").setEphemeral(true).queue();
    }
}
