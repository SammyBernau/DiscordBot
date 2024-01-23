package org.gamerbot.support.trivia;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class TriviaController {

    private Map<Integer, TriviaInstance> idToUsersAnswered;
    private long lastQuestion;

    private static TriviaController instance;

    private TriviaController() {
        this.idToUsersAnswered = new HashMap<>();
        lastQuestion = 0;
    }

    public static TriviaController getInstance() {
        if (instance == null) instance = new TriviaController();
        return instance;
    }
}
