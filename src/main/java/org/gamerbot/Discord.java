package org.gamerbot;

import com.google.common.reflect.ClassPath;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;

import org.gamerbot.commands.LeaderboardsCommandRegister;
import org.gamerbot.handlers.DiscordCommandRegister;

import org.gamerbot.handlers.events.BotInitEvent;
import org.gamerbot.handlers.events.MessageLeaderboardReceived;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

public class Discord extends ListenerAdapter {

    public static JDA jda;
    // Secret Token
    private static String TOKEN = "";
    private static String TESTING_TOKEN = "";
    public static ScheduledExecutorService gamerBotThreads = Executors.newScheduledThreadPool(10);

    public static void main(String[] args) throws InterruptedException {
        new Discord().initialize();
    }

    public void initialize() throws InterruptedException {
        JDABuilder builder = JDABuilder.createDefault(TOKEN)
                 .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setAutoReconnect(true);
        System.out.println("Registering...");

        builder.addEventListeners(new DiscordInteractionHandler());
        builder.addEventListeners(this);
        builder.addEventListeners(new MessageLeaderboardReceived());
        try {
            jda = builder.build();
            //jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.playing("with the boys"));

        } catch (LoginException e) {
            e.printStackTrace();
        }
        List<DiscordCommandRegister> classes = new ArrayList<>();
        try {
            classes = ClassPath.from(ClassLoader.getSystemClassLoader()).getTopLevelClasses("org.gamerbot.commands")
                    .stream()
                    .map(ClassPath.ClassInfo::load)
                    .map(clazz -> {
                        try {
                            return (DiscordCommandRegister) clazz.newInstance();
                        } catch (InstantiationException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }).filter(Objects::nonNull).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<CommandData> commands = classes.stream().map(DiscordCommandRegister::registerCommands).collect(Collectors.toList());

        jda.awaitReady();
        // Gamer Server
        Objects.requireNonNull(jda.getGuildById(247144216898043906L)).updateCommands().addCommands(commands).queue();
        // Test Server
        Objects.requireNonNull(jda.getGuildById(938250404028899448L)).updateCommands().addCommands(commands).queue();
        new BotInitEvent().checkBotSleep();
//        Objects.requireNonNull(jda.getGuildById(938250404028899448L)).updateCommands().addCommands(commands).queue();
    }
}