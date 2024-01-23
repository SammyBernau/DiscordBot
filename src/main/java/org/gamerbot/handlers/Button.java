package org.gamerbot.handlers;

import com.google.common.reflect.ClassPath;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.gamerbot.support.CommonSupport;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public interface Button extends CommonSupport {

    void handleButton(ButtonInteractionEvent event);

    static void handleInteraction(String command, ButtonInteractionEvent event) {
        buttonHandlerInstances.get(command.toUpperCase()).handleButton(event);
    }

    HashMap<String, Button> buttonHandlerInstances = getButtonHandlerInstances();

    private static HashMap<String, Button> getButtonHandlerInstances() {
        try {
            return ClassPath.from(ClassLoader.getSystemClassLoader()).getTopLevelClasses("org.gamerbot.handlers.buttons").stream().map(ClassPath.ClassInfo::load)
                    .filter(aClass -> !aClass.isInterface()).map(clazz -> {
                        try {
                            return Map.entry(clazz.getSimpleName().split("Button")[0].toUpperCase(), (Button)clazz.getConstructor().newInstance());
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
