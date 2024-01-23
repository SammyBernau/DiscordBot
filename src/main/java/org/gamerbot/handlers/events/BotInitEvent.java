package org.gamerbot.handlers.events;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.gamerbot.Discord;
import org.gamerbot.support.botimer.BotTimer;

import java.util.concurrent.TimeUnit;


public class BotInitEvent  {

    public void checkBotSleep () {
        Discord.gamerBotThreads.scheduleAtFixedRate(() -> {
            if(System.currentTimeMillis()-BotTimer.getBotInstance().getLastSetTime() > 300000){
                Discord.jda.getPresence().setPresence(OnlineStatus.IDLE,Activity.watching("Anime"));
            }
        }, 0, 1, TimeUnit.MINUTES);
    }
}
