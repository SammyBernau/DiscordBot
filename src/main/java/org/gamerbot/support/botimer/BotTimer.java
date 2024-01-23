package org.gamerbot.support.botimer;


//Example of singleton pattern (for future sam to look at)
public class BotTimer {

    private static BotTimer botInstance = null;
    private long time;

    private BotTimer() {
        this.time = System.currentTimeMillis();
    }

    public void updateTime() {
        time = System.currentTimeMillis();
    }

    public long getLastSetTime() {
        return time;
    }

    public static BotTimer getBotInstance() {
        if(botInstance == null) {
            botInstance = new BotTimer();
        }
        return botInstance;
    }




}
