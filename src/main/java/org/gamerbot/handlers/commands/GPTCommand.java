package org.gamerbot.handlers.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.gamerbot.handlers.Command;
import org.gamerbot.support.gpt.GPTAPI;
import org.gamerbot.support.gpt.GPTModel;
import org.gamerbot.support.gpt.GPTResponse;

public class GPTCommand implements Command {

    @Override
    public void handleCommand(SlashCommandInteractionEvent event) {
        GPTModel model = GPTModel.valueOf(event.getOption("model", OptionMapping::getAsString));
        String prompt = event.getOption("prompt", OptionMapping::getAsString);

        event.deferReply().queue();
        GPTResponse response = GPTAPI.postToGPT(prompt, model);
        event.getHook().editOriginalEmbeds(response.getBuilder().build()).queue();
    }
}
