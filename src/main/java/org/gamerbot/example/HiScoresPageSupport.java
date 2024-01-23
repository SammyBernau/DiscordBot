//package org.gamerbot.example;
//
//import io.ruin.services.exylediscord.commands.handlers.support.database.DatabaseSupport;
//import net.dv8tion.jda.api.EmbedBuilder;
//import net.dv8tion.jda.api.entities.MessageEmbed;
//import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
//import net.dv8tion.jda.api.interactions.components.buttons.Button;
//import org.gamerbot.support.CommonSupport;
//
//import java.awt.*;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.text.NumberFormat;
//import java.util.LinkedList;
//import java.util.Queue;
//
//public interface HiScoresPageSupport extends CommonSupport, DatabaseSupport {
//
//    default void replyWithHiScoresEmbed(String skillNameColumn,
//                                               String skillNameOption,
//                                               int xpRateColumn,
//                                               int page,
//                                               IReplyCallback event) {
//        Button prevButton = page - 1 == 0 ?
//                Button.danger("ppage:" + page + ":" + skillNameColumn + ":" + skillNameOption + ":" + xpRateColumn, "Prev Page").asDisabled() :
//                Button.primary("ppage:" + page + ":" + skillNameColumn + ":" + skillNameOption + ":" + xpRateColumn, "Prev Page");
//
//        event.replyEmbeds(createHiScoresEmbed(skillNameColumn, skillNameOption, xpRateColumn, page))
//                .setEphemeral(true)
//                .addActionRow(prevButton, Button.primary("npage:" + page + ":" + skillNameColumn + ":" + skillNameOption + ":" + xpRateColumn, "Next Page"))
//                .queue();
//    }
//
//    default MessageEmbed createHiScoresEmbed(String skillNameColumn,
//                                                String skillNameOption,
//                                                    int xpRateColumn,
//                                                    int page) {
//        String skillName = getOrDefault(skillNameOption, "Overall");
//        EmbedBuilder builder = new EmbedBuilder();
//        builder.setColor(Color.BLUE).setTitle("High Scores - " + skillName).setThumbnail("https://exyle.net/logo.png");
//        int startLimit = (page * 8) - 8;
//        int index = startLimit;
//
//        String whereConditions = "";
//        if (xpRateColumn != -1) {
//            whereConditions += "WHERE mode = " + xpRateColumn;
//        }
//
//        ResultSet rs = executeQuery("SELECT username, mode, total_level, " + skillNameColumn + " FROM hs_users " + whereConditions + " ORDER BY " + skillNameColumn + " DESC, " + skillNameColumn + " DESC LIMIT " + 8 + " OFFSET " + startLimit);
//        try {
//            while (rs.next()) {
//                builder
//                        .addField("Player", ++index + ". " + rs.getString(1), true)
//                        .addField("Total Lvl", NumberFormat.getIntegerInstance().format(rs.getInt(3)), true)
//                        .addField("XP", NumberFormat.getIntegerInstance().format(rs.getInt(4)), true);
//            }
//        } catch(SQLException e) {
//            e.printStackTrace();
//        }
//        return builder.build();
//    }
//
//    default void replyHiScoresEmbedForPlayer(String skillNameColumn,
//                                        String skillNameOption,
//                                        int xpRateColumn,
//                                        String player,
//                                        IReplyCallback event) {
//        try {
//            ResultSet exists = executeQuery("SELECT username FROM hs_users WHERE username = '" + player + "'");
//            if (exists.next())
//                event.replyEmbeds(createHiScoresEmbedForPlayer(skillNameColumn, skillNameOption, xpRateColumn, player)).setEphemeral(true).queue();
//            else
//                event.reply("That user doesn't exist...").setEphemeral(true).queue();
//        } catch(Exception e) {
//            event.reply("That user doesn't exist...").setEphemeral(true).queue();
//        }
//    }
//
//    default MessageEmbed createHiScoresEmbedForPlayer(String skillNameColumn,
//                                                      String skillNameOption,
//                                                      int xpRateColumn,
//                                                      String player) {
//        String skillName = getOrDefault(skillNameOption, "Overall");
//        EmbedBuilder builder = new EmbedBuilder();
//        builder.setColor(Color.BLUE).setTitle("High Scores - " + skillName).setThumbnail("https://exyle.net/logo.png");
//
//        String whereConditions = "";
//        if (xpRateColumn != -1) {
//            whereConditions += "WHERE mode = " + xpRateColumn;
//        }
//
//        ResultSet rs = executeQuery("SELECT username, mode, total_level, " + skillNameColumn + " FROM hs_users " + whereConditions +
//                " ORDER BY " + skillNameColumn + " DESC");
//
//        Queue<PlayerData> playerData = new LinkedList<>();
//
//        int maxIndex = 0;
//        int index = 0;
//        try {
//            while (rs.next()) {
//                String username = rs.getString(1);
//                if (username.equalsIgnoreCase(player)) maxIndex = index + 4;
//                if (maxIndex != 0 && index >= maxIndex && playerData.size() > 7) break;
//                playerData.add(new PlayerData(++index, rs.getString(1), rs.getInt(3), rs.getInt(4)));
//                if (playerData.size() >= 9) playerData.poll();
//            }
//        } catch(SQLException e) {
//            e.printStackTrace();
//        }
//        for(int i = 0; i < 8; i++) {
//            PlayerData data = playerData.poll();
//            if (data != null) {
//                builder
//                        .addField("Player", data.number + ". " + data.username, true)
//                        .addField("Total Lvl", NumberFormat.getIntegerInstance().format(data.lvl), true)
//                        .addField("XP", NumberFormat.getIntegerInstance().format(data.xp), true);
//            }
//        }
//        return builder.build();
//    }
//
//    class PlayerData {
//        private int number;
//        private String username;
//        private int lvl;
//        private int xp;
//
//        public PlayerData(int number, String username, int lvl, int xp) {
//            this.number = number;
//            this.username = username;
//            this.lvl = lvl;
//            this.xp = xp;
//        }
//    }
//
//}
