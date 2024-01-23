package org.gamerbot.support.gpt;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.EmbedBuilder;
import org.gamerbot.support.api.APIRequest;
import org.springframework.http.MediaType;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Getter @Setter
public class GPTChatResponse implements GPTResponse {

    private static String GPT_API_URL = "https://api.openai.com/v1/chat/completions";

    private String prompt;
    private int totalCreditsUsed;
    private String model;
    private String finishReason;
    private String response;

    public GPTChatResponse(String prompt, int totalCreditsUsed, String model, String finishReason, String response) {
        this.prompt = prompt;
        this.totalCreditsUsed = totalCreditsUsed;
        this.model = model;
        this.finishReason = finishReason;
        this.response = response;
    }

    @Override
    public EmbedBuilder getBuilder() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(getModel(), "https://chat.openai.com/auth/login", ICON_URL);
        if (getPrompt().length() > 220) builder.setTitle(getPrompt().substring(0, 219) + "...");
        else builder.setTitle(getPrompt());
        double price = (getTotalCreditsUsed() / 1000d) * .03;
        builder.addField("Credit Cost", getTotalCreditsUsed() + " (" + price + "¢)", true);
        builder.addField("Stop Reason", getFinishReason().toUpperCase(), true);
        builder.setColor(Color.GREEN);
        builder.setDescription(getResponse());
        return builder;
    }

    public static GPTChatResponse post(GPTModel gptModel, String prompt) {
        try {
            Map<String, Object> map = new APIRequest.Builder(GPT_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bearerAuth(GPTAPI.GPT_API_KEY)
                    .body(GPTResponse.setGPTBody(gptModel.getJson(), prompt))
                    .postAsJson();
            if (map.containsKey("usage") && map.containsKey("choices")) {
                String model = map.get("model").toString();
                Map<String, Object> usageMap = (Map<String, Object>) map.get("usage");
                int totalCredits = (int) usageMap.get("total_tokens");
                java.util.List<Map<String, Object>> choicesObj = (List<Map<String, Object>>) map.get("choices");
                Map<String, Object> choice = choicesObj.get(0);
                String stopReason = choice.get("finish_reason").toString();
                Map<String, Object> message = (Map<String, Object>) choice.get("message");
                String response = message.get("content").toString();
                return new GPTChatResponse(prompt, totalCredits, model, stopReason, response);
            }
            return new GPTChatResponse(prompt, 0, "Error", "Error", "Either ran out of api credits or somethings fucked.... ask zach lmfao");

        } catch (IOException e) {
            e.printStackTrace();
            return new GPTChatResponse(prompt,0, "Error", "Error", "Either ran out of api credits or somethings fucked.... ask zach lmfao (IO Exception)");
        }
    }

}
