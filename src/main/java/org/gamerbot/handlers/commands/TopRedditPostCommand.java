package org.gamerbot.handlers.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.gamerbot.handlers.Command;
import org.gamerbot.support.reddit.RedditAPI;
import org.gamerbot.support.reddit.RedditPost;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class TopRedditPostCommand implements Command {

    @Override
    public void handleCommand(SlashCommandInteractionEvent event) {
        String subReddit = event.getOption("subreddit", OptionMapping::getAsString);
        if (subReddit.equalsIgnoreCase("*custom*")) {
            subReddit = event.getOption("custom", OptionMapping::getAsString);
        }

        List<RedditPost> topRedditPosts = RedditAPI.topRedditPosts(subReddit);
        if (topRedditPosts.size() != 0) {
            RedditPost post = topRedditPosts.get(new Random().nextInt(topRedditPosts.size()));

            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle(post.getTitle());
            builder.setAuthor(post.getSubReddit() + " (click me)", post.getUrl(), RedditAPI.REDDIT_THUMBNAIL_URL);
            builder.setColor(Color.BLUE);
            if (post.hasImg()) builder.setImage(post.getImgUrl());
            else if (post.hasLink()) builder.addField("Link", post.getUrl(), true);
            if (post.getDescription().length() > 0) builder.setDescription(post.getDescription());
            event.replyEmbeds(builder.build()).queue();
        } else event.replyEmbeds(new EmbedBuilder().setDescription("The subreddit r/" + subReddit + " doesn't exist.").build()).queue();
    }
}

