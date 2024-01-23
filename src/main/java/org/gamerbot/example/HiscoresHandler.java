//package org.gamerbot.example;
//
//import io.ruin.model.stat.StatType;
//import io.ruin.services.exylediscord.commands.handlers.CommandHandler;
//import io.ruin.services.exylediscord.commands.handlers.support.CommonSupport;
//import io.ruin.services.exylediscord.commands.handlers.support.HiScoresPageSupport;
//import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
//import net.dv8tion.jda.api.interactions.commands.OptionMapping;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//public class HiscoresHandler implements CommandHandler, CommonSupport, HiScoresPageSupport {
//
//    private static final HashMap<String, String> skillNameToColumnName = Arrays
//            .stream(StatType.values())
//            .map(Enum::name)
//            .map(statTypeName -> Map.entry(statTypeName, statTypeName.toLowerCase() + "_xp"))
//            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (prev, next) -> next, HashMap::new));
//
//    @Override
//    public void handleCommand(SlashCommandInteractionEvent event) {
//        String skillNameOption = event.getOption("skill", () -> "<NONE>", OptionMapping::getAsString);
//        String xpRateOption = event.getOption("xprate", () -> "<NONE>", OptionMapping::getAsString);
//
//        String playerOption = event.getOption("player", () -> "<NONE>", OptionMapping::getAsString);
//        // String gamemodeOption = event.getOption("gamemode", () -> "<NONE>", OptionMapping::getAsString);
//
//        if (playerOption.equalsIgnoreCase("<NONE>")) {
//            String skillNameColumn = getOrDefault(skillNameToColumnName.getOrDefault(skillNameOption, "overall_xp"), "overall_xp");
//            int xpRateColumn = Integer.parseInt(getOrDefault(xpRateOption, "-1"));
//
//            replyWithHiScoresEmbed(skillNameColumn, skillNameOption, xpRateColumn, 1, event);
//        } else {
//            int xpRateColumn = Integer.parseInt(getOrDefault(xpRateOption, "-1"));
//            String skillNameColumn = getOrDefault(skillNameToColumnName.getOrDefault(skillNameOption, "overall_xp"), "overall_xp");
//            replyHiScoresEmbedForPlayer(skillNameColumn, skillNameOption, xpRateColumn, playerOption, event);
//        }
//    }
//}
