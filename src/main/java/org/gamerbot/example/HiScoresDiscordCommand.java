//package org.gamerbot.example;
//
//import io.ruin.model.stat.StatType;
//import io.ruin.services.exylediscord.commands.handlers.support.HiScoresPageSupport;
//import net.dv8tion.jda.api.hooks.ListenerAdapter;
//import net.dv8tion.jda.api.interactions.commands.Command;
//import net.dv8tion.jda.api.interactions.commands.build.Commands;
//import net.dv8tion.jda.api.interactions.commands.build.OptionData;
//import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
//import org.apache.commons.collections4.Trie;
//import org.apache.commons.collections4.trie.PatriciaTrie;
//import org.gamerbot.handlers.DiscordCommand;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.LinkedHashSet;
//import java.util.stream.Collectors;
//
//import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;
//
//public class HiScoresDiscordCommand extends ListenerAdapter implements DiscordCommand, HiScoresPageSupport {
//
//    public static Trie<String, String> usernames = new PatriciaTrie<>();
//
//    @Override
//    public void registerCommands(CommandListUpdateAction action) {
//        updateUsernames();
//        Collection<Command.Choice> skills = Arrays.stream(StatType.values()).map(Enum::name).map(name -> new Command.Choice(name, name)).collect(Collectors.toCollection(HashSet::new));
//        Collection<Command.Choice> xprates = new LinkedHashSet<>() {{
//            add(new Command.Choice("5x", "5"));
//            add(new Command.Choice("10x", "10"));
//            add(new Command.Choice("25x", "25"));
//            add(new Command.Choice("50x", "50"));
//        }};
//
//        //OptionData usernameData = new OptionData(STRING, "username", "Checks the highscores for a username.", true, true);
//        OptionData skilldata = new OptionData(STRING, "skill", "Checks hi-scores for specific skill, empty will default to overall", false, false)
//                .addChoices(skills);
//        OptionData xpRateData = new OptionData(STRING, "xprate", "Checks hi-scores for specific xp rate", false, false)
//                .addChoices(xprates);
//        OptionData gamemodeData = new OptionData(STRING, "gamemode", "Chooses the gamemode to sort by", false, false)
//                .addChoices();
//        action.addCommands(
//                Commands.slash("hiscores", "View hiscores for Exyle!")
//                        //.addOptions(usernameData)
//                        .addOptions(skilldata, xpRateData)
//                        .addOption(STRING, "player", "Look for a specific player in the hiscores.", false, true)
//                        //.addOptions(gamemodeData)
//                        .setGuildOnly(true)
//        ).queue();
//    }
//
//    public void updateUsernames() {
//        // HashMap<String, HashMap<String, Integer>> usernameToXps = new HashMap<>();
//        Trie<String, String> usernames = new PatriciaTrie<>();
//
//        ResultSet rs = executeQuery("SELECT username, mode, total_level, overall_xp FROM hs_users ORDER BY overall_xp DESC, overall_xp DESC LIMIT 50");
//        System.out.println("Loading...");
//        try {
//            while (rs.next()) {
//                String username = rs.getString(1);
//                usernames.put(username.toLowerCase(), username.toLowerCase());
//            }
//        } catch(SQLException e) {
//            e.printStackTrace();
//        }
//        HiScoresDiscordCommand.usernames = usernames;
//    }
//}
