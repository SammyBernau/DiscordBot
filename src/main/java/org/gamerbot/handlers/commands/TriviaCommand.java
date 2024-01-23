package org.gamerbot.handlers.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import org.gamerbot.handlers.Command;
import org.gamerbot.support.trivia.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TriviaCommand implements Command {
    @Override
    public void handleCommand(SlashCommandInteractionEvent event) {
        if (System.currentTimeMillis() - TriviaController.getInstance().getLastQuestion() < 10000) {
            event.reply("You can only get a trivia every 10 seconds.").setEphemeral(true).queue();
            return;
        }
        TriviaController.getInstance().setLastQuestion(System.currentTimeMillis());
        String type = "";
        if (event.getOption("type") != null) {
            type = event.getOption("type").getAsString();
        }
        TriviaQuestion question;
        if (type.isEmpty()) {
            question = TriviaAPI.getRandomQuestion();
        } else {
            question = TriviaAPI.getRandomQuestion(TriviaCategory.valueOf(type.toUpperCase()).getId());
        }
        MessageEmbed embed = new EmbedBuilder()
                .setTitle(question.getTopic())
                .setDescription(question.getQuestion())
                .addField("Difficulty", question.getDifficulty(), true)
                .setColor(Color.CYAN)
                .setAuthor("Trivia", null, "https://thumbs.dreamstime.com/b/brain-black-icon-circle-brain-black-icon-circle-vector-illustration-isolated-113569420.jpg")
                .build();

        ArrayList<String> answers = new ArrayList<>(question.getChoices());
        Collections.shuffle(answers);

        int id;
        Random random = new Random();
        do {
            id = random.nextInt(100);
        } while (TriviaController.getInstance().getIdToUsersAnswered().containsKey(id));
        TriviaController.getInstance().getIdToUsersAnswered().put(id, new TriviaInstance());

        SelectMenu selectMenu = SelectMenu
                .create("trivia" + SPLIT_ID + question.getCorrectAnswer() + SPLIT_ID + id)
                .addOptions(answers.stream().map(answer -> SelectOption.of(answer, answer)).collect(Collectors.toList()))
                .build();

        int finalId = id;
        event.replyEmbeds(embed)
                .addActionRow(selectMenu).queue((message) -> {
                    TriviaController.getInstance().getIdToUsersAnswered().get(finalId).setStartTime(System.currentTimeMillis());
                    message.deleteOriginal().queueAfter(90, TimeUnit.SECONDS, (x) -> {
                        TriviaController.getInstance().getIdToUsersAnswered().remove(finalId);
                    });
                });
    }
}
