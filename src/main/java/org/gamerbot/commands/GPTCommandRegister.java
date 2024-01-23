package org.gamerbot.commands;

import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.gamerbot.handlers.DiscordCommandRegister;
import org.gamerbot.support.gpt.GPTModel;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;

public class GPTCommandRegister implements DiscordCommandRegister {
    @Override
    public CommandData registerCommands() {
        List<Command.Choice> subs = Arrays.stream(GPTModel.values()).map(gptModel -> new Command.Choice(gptModel.name(), gptModel.name())).collect(Collectors.toList());

        OptionData model = new OptionData(STRING, "model", "Chooses which model to use", true, false)
                .addChoices(subs);

        OptionData prompt = new OptionData(STRING, "prompt", "The prompt for chat gpt to use/answer.", true, false);

        return Commands.slash("gpt", "ChatGPT in the server... W?")
                .addOptions(model, prompt)
                .setGuildOnly(true);
    }
}
