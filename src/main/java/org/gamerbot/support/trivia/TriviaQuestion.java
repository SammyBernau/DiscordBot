package org.gamerbot.support.trivia;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
public class TriviaQuestion {

    private String topic;
    private String question;
    private String difficulty;
    private String correctAnswer;
    private Set<String> choices;

    public TriviaQuestion(String topic, String question, String difficulty, String correctAnswer, Set<String> choices) {
        this.topic = topic;
        this.question = question;
        this.difficulty = difficulty;
        this.correctAnswer = correctAnswer;
        this.choices = choices;
        this.choices.add(correctAnswer);
    }
}
