package org.gamerbot.support.gpt;

public class GPTAPI {

    public static String GPT_API_KEY = "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";

    public static GPTResponse postToGPT(String prompt, GPTModel model) {
        if (model == GPTModel.GPT_IMAGES) {
            return GPTImageResponse.post(model, prompt);
        }
        return GPTChatResponse.post(model, prompt);
    }
}
