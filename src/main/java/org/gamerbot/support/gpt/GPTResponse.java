package org.gamerbot.support.gpt;

import net.dv8tion.jda.api.EmbedBuilder;

public interface GPTResponse {
    String ICON_URL = "https://assets.stickpng.com/images/63c52af590250dd34bd6a9ab.png";

    EmbedBuilder getBuilder();

    static String setGPTBody(String json, String prompt) {
        return json.replace("<PROMPT>", prompt);
    }
}
