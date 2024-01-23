package org.gamerbot.commands;

import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.gamerbot.handlers.DiscordCommandRegister;

import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;

public class DailyRedditPostCommandRegister implements DiscordCommandRegister {
    @Override
    public CommandData registerCommands() {
        OptionData subreddit = new OptionData(STRING, "subreddit", "displays chosen subreddit top post", true, false)
                .addChoices(new Command.Choice("Showerthoughts", "Showerthoughts"),
                        new Command.Choice("news", "news"),
                        new Command.Choice("meme","meme"),
                        new Command.Choice("animegirls","AnimeGirls"),
                        new Command.Choice("custom","*custom*"));
        OptionData custom = new OptionData(STRING, "custom", "displays custom sub reddit post", false, false);
        return Commands.slash("topredditpost", "Your slice of reddit")
                .addOptions(subreddit, custom)
                .setGuildOnly(true);
    }
}
