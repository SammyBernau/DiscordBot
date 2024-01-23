package org.gamerbot;

import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.gamerbot.handlers.Button;
import org.gamerbot.handlers.Command;
import org.gamerbot.handlers.SelectMenu;
import org.gamerbot.support.CommonSupport;
import org.jetbrains.annotations.NotNull;

public class DiscordInteractionHandler extends ListenerAdapter implements CommonSupport {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getGuild() == null)
            return;
        Command.handleInteraction(event.getName().toUpperCase(), event);
        System.currentTimeMillis();
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if (event.getGuild() == null)
            return;
        String button = event.getComponentId().split(SPLIT_ID)[0];
        Button.handleInteraction(button, event);
    }

    @Override
    public void onCommandAutoCompleteInteraction(@NotNull CommandAutoCompleteInteractionEvent event) {
//        if (event.getName().equalsIgnoreCase("hiscores") && event.getFocusedOption().getName().equalsIgnoreCase("player")) {
//            Collection<Command.Choice> choices = HiScoresDiscordCommand.usernames.prefixMap(event.getFocusedOption().getValue()).values().stream().map(name -> new Command.Choice(name, name)).limit(25).collect(Collectors.toCollection(HashSet::new));
//            event.replyChoices(choices).queue();
//        }
//        if (event.getName().equals("hiscores") && event.getFocusedOption().getName().equals("gamemode")) {
//            List<Command.Choice> options = Arrays.stream(StatType.values()).map(StatType::name)
//                    .filter(word -> word.toLowerCase().startsWith(event.getFocusedOption().getValue().toLowerCase())) // only display words that start with the user's current input
//                    .map(word -> new Command.Choice(word, word)) // map the words to choices
//                    .collect(Collectors.toList());
//            event.replyChoices(options).queue();
//        } else
    }

    @Override
    public void onSelectMenuInteraction(@NotNull SelectMenuInteractionEvent event) {
        if (event.getGuild() == null)
            return;
        String selectMenu = event.getComponentId().split(SPLIT_ID)[0];
        SelectMenu.handleInteraction(selectMenu, event);
    }
}
