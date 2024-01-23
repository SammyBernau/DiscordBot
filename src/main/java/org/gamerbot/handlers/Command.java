package org.gamerbot.handlers;

import com.google.common.reflect.ClassPath;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.gamerbot.Discord;
import org.gamerbot.support.CommonSupport;
import org.gamerbot.support.botimer.BotTimer;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public interface Command extends CommonSupport {

    void handleCommand(SlashCommandInteractionEvent event);

    static void handleInteraction(String command, SlashCommandInteractionEvent event) {
        BotTimer.getBotInstance().updateTime();
        Discord.jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.playing("with da boys"));
        commandHandlerInstances.get(command.toUpperCase()).handleCommand(event);
    }

    HashMap<String, Command> commandHandlerInstances = getCommandHandlerInstances();

    private static HashMap<String, Command> getCommandHandlerInstances() {
        try {
            return ClassPath.from(ClassLoader.getSystemClassLoader()).getTopLevelClasses("org.gamerbot.handlers.commands").stream().map(ClassPath.ClassInfo::load)
                    .filter(aClass -> !aClass.isInterface()).map(clazz -> {
                        try {
                            return Map.entry(clazz.getSimpleName().split("Command")[0].toUpperCase(), (Command)clazz.getConstructor().newInstance());
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            System.out.println(e.getMessage());
                        }
                        return null;
                    }).filter(Objects::nonNull).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (next, prev) -> prev, HashMap::new));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
