package org.gamerbot.support.gpt;

import lombok.Getter;

@Getter
public enum GPTModel {
    GPT_3("""
            {
              "model": "gpt-3.5-turbo-1106",
              "messages":[
                {"role":"system","content":""},
                {"role":"assistant","content":""},
                {"role":"user","content":"<PROMPT>"}
              ],
              "temperature":0.3,
              "max_tokens":654,
              "frequency_penalty":0.4,
              "presence_penalty":0.5
            }
            """.strip()),
    GPT_4("""
            {
              "model": "gpt-4-1106-preview",
              "messages":[
                {"role":"system","content":""},
                {"role":"assistant","content":""},
                {"role":"user","content":"<PROMPT>"}
              ],
              "temperature":0.3,
              "max_tokens":654,
              "frequency_penalty":0.4,
              "presence_penalty":0.5
            }
            """.strip()),
    GPT_IMAGES("""
            {
              "model":"dall-e-3",
              "prompt":"<PROMPT>",
              "n":1,
              "size":"1792x1024",
              "quality":"hd"
            }
            """.strip());

    private final String json;

    GPTModel(String json) {
        this.json = json;
    }
}
