package org.gamerbot.support.trivia;

import com.google.gson.JsonObject;
import org.apache.commons.text.StringEscapeUtils;
import org.gamerbot.support.api.APIRequest;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TriviaAPI {

    public static TriviaQuestion getRandomQuestion() {
        return getRandomQuestion(TriviaCategory.GENERAL_KNOWLEDGE.getId());
    }

    public static TriviaQuestion getRandomQuestion(int category) {
        JsonObject jsonResponse = new APIRequest.Builder("https://opentdb.com/api.php?amount=1&category=" + category).getAsJson()
                .getAsJsonArray("results").get(0).getAsJsonObject();
        return new TriviaQuestion(
                    jsonResponse.get("category").getAsString(),
                StringEscapeUtils.unescapeHtml4(jsonResponse.get("question").getAsString()),
                    jsonResponse.get("difficulty").getAsString(),
                StringEscapeUtils.unescapeHtml4(jsonResponse.get("correct_answer").getAsString()),
                    StreamSupport.stream(jsonResponse.get("incorrect_answers").getAsJsonArray().spliterator(), false)
                        .map(element -> StringEscapeUtils.unescapeHtml4(element.getAsString())).collect(Collectors.toSet())
                );
    }
}
