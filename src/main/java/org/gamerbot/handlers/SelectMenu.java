package org.gamerbot.handlers;

import com.google.common.reflect.ClassPath;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import org.gamerbot.support.CommonSupport;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public interface SelectMenu extends CommonSupport {

    void handleSelectMenu(SelectMenuInteractionEvent event);

    static void handleInteraction(String command, SelectMenuInteractionEvent event) {
        buttonHandlerInstances.get(command.toUpperCase()).handleSelectMenu(event);
    }

    HashMap<String, SelectMenu> buttonHandlerInstances = getButtonHandlerInstances();

    private static HashMap<String, SelectMenu> getButtonHandlerInstances() {
        try {
            return ClassPath.from(ClassLoader.getSystemClassLoader()).getTopLevelClasses("org.gamerbot.handlers.selectmenus").stream().map(ClassPath.ClassInfo::load)
                    .filter(aClass -> !aClass.isInterface()).map(clazz -> {
                        try {
                            return Map.entry(clazz.getSimpleName().split("SelectMenu")[0].toUpperCase(), (SelectMenu)clazz.getConstructor().newInstance());
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }).filter(Objects::nonNull).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (next, prev) -> prev, HashMap::new));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}

