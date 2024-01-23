package org.gamerbot.support.gpt;

import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import org.gamerbot.support.api.APIRequest;
import org.springframework.http.MediaType;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Getter
public class GPTImageResponse implements GPTResponse {

    private String prompt;
    private String model;
    private String url;

    public GPTImageResponse(String prompt, String model, String url) {
        this.prompt = prompt;
        this.model = model;
        this.url = url;
    }

    @Override
    public EmbedBuilder getBuilder() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(getModel(), "https://chat.openai.com/auth/login", ICON_URL);
        if (getPrompt().length() > 220) builder.setTitle(getPrompt().substring(0, 219) + "...");
        else builder.setTitle(getPrompt());
        double price = .02;
        builder.addField("Credit Cost", 1000 + " (" + price + "¢)", true);
        builder.setColor(Color.GREEN);
        builder.setImage(url);
        return builder;
    }

    public static GPTImageResponse post(GPTModel gptModel, String prompt) {
        try {
            String GPT_API_URL = "https://api.openai.com/v1/images/generations";
            Map<String, Object> map = new APIRequest.Builder(GPT_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bearerAuth(GPTAPI.GPT_API_KEY)
                    .body(GPTResponse.setGPTBody(gptModel.getJson(), prompt))
                    .postAsJson();
            java.util.List<Map<String, Object>> data = (List<Map<String, Object>>) map.get("data");
            String url = data.get(0).get("url").toString();
            return new GPTImageResponse(prompt, "DALL·E", url);
        } catch (IOException e) {
            e.printStackTrace();
            return new GPTImageResponse(prompt,"L Bozo", ICON_URL);
        }
    }
}
