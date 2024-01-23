package org.gamerbot.handlers.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.gamerbot.handlers.Command;
import org.gamerbot.support.database.DatabaseSupport;
import org.gamerbot.support.leaderboards.LeaderboardsHelper;

import java.security.SecureRandom;

public class DailyCommand implements Command, DatabaseSupport {
    @Override
    public void handleCommand(SlashCommandInteractionEvent event) {
        int coins = new SecureRandom().nextInt(0, 5000);
        int newCoins = LeaderboardsHelper.getInstance().updateLeaderboards(event.getUser(), "coins", coins);
        event.reply("You received **" + coins + "** coins.  You now have **" + newCoins + "** coins.").queue();
    }
}
