package org.gamerbot.support.trivia;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class TriviaInstance {

    private List<String> alreadyAnswered;
    private long startTime;

    public TriviaInstance() {
        this.alreadyAnswered = new ArrayList<>();
        this.startTime = System.currentTimeMillis();
    }
}
