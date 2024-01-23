package org.gamerbot.support.gpt;

public class GPTTester {
    public static void main(String[] args) {
        System.out.println(GPTChatResponse.post(GPTModel.GPT_3, "What is the name of the 14th president?").getResponse());
    }
}
