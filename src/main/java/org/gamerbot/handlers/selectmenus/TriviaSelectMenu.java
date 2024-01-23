package org.gamerbot.handlers.selectmenus;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import org.gamerbot.handlers.SelectMenu;
import org.gamerbot.support.CommonSupport;
import org.gamerbot.support.database.DatabaseSupport;
import org.gamerbot.support.leaderboards.LeaderboardsHelper;
import org.gamerbot.support.trivia.TriviaController;

import java.util.stream.Collectors;

public class TriviaSelectMenu implements SelectMenu, DatabaseSupport {

    @Override
    public void handleSelectMenu(SelectMenuInteractionEvent event) {
        String[] args = event.getComponentId().split(SPLIT_ID);
        String answer = args[1];
        int id = Integer.parseInt(args[2]);
        if (!TriviaController.getInstance().getIdToUsersAnswered().containsKey(id))
            event.reply("You already answered").setEphemeral(true).queue();
        else if (TriviaController.getInstance().getIdToUsersAnswered().get(id).getAlreadyAnswered().contains(event.getMember().getId()))
            event.reply("You may only answer once!").setEphemeral(true).queue();
        else if (event.getSelectedOptions().get(0).getValue().equalsIgnoreCase(answer)) {
            MessageEmbed original = event.getMessage().getEmbeds().get(0);
            event.getMessage().getActionRows().get(0).getActionComponents().stream().map(comp -> comp.withDisabled(true)).collect(Collectors.toList());

            event.deferEdit().setEmbeds(new EmbedBuilder(original)
                    .addField("Answer", answer, false)
                    .addField("Answered By", event.getMember().getEffectiveName(), false)
                    .addField("In", (System.currentTimeMillis() - TriviaController.getInstance().getIdToUsersAnswered().get(id).getStartTime()) + " ms", false).build())
                    .setActionRow(event.getMessage().getActionRows().get(0).getActionComponents().stream().map(comp -> comp.withDisabled(true)).collect(Collectors.toList()))
                    .queue();
            TriviaController.getInstance().getIdToUsersAnswered().remove(id);
            LeaderboardsHelper.getInstance().updateLeaderboards(event.getUser(), "trivia", 1);
        } else {
            TriviaController.getInstance().getIdToUsersAnswered().get(id).getAlreadyAnswered().add(event.getMember().getId());
            event.replyEmbeds(new EmbedBuilder().setTitle("Incorrect Answer")
                    .addField("Correct Answer", answer, true)
                    .addField("In", (System.currentTimeMillis() - TriviaController.getInstance().getIdToUsersAnswered().get(id).getStartTime()) + " ms", true).build()).setEphemeral(true).queue();
        }
    }
}
