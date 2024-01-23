package org.gamerbot.commands;

import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.gamerbot.handlers.DiscordCommandRegister;
import org.gamerbot.support.trivia.TriviaCategory;

import java.util.Arrays;
import java.util.stream.Collectors;

import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;

public class TriviaCommandRegister implements DiscordCommandRegister {


    @Override
    public CommandData registerCommands() {
        OptionData type = new OptionData(STRING, "type",
                "Choose the type of question you want to display.", false, false)
                .addChoices(Arrays.stream(TriviaCategory.values())
                        .map(category -> new Command.Choice(category.getCategoryName(), category.name()))
                        .collect(Collectors.toList()));
        return Commands.slash("trivia", "Post a random trivia question.")
                .addOptions(type)
                .setGuildOnly(true);
    }
}
