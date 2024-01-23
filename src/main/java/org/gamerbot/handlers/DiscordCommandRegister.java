package org.gamerbot.handlers;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.gamerbot.support.CommonSupport;

public interface DiscordCommandRegister extends CommonSupport {
    CommandData registerCommands();
}
