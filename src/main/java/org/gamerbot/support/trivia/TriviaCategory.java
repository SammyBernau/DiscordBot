package org.gamerbot.support.trivia;

import lombok.Getter;

@Getter
public enum TriviaCategory {
    COMPUTER_SCIENCE(18, "Science: Computers"),
    GENERAL_KNOWLEDGE(9, "General Knowledge"),
    VIDEO_GAMES(15, "Video Games"),
    ANIME(31, "Anime/Manga"),
    HISTORY(23, "History");

    private int id;
    private String categoryName;

    TriviaCategory(int id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }
}
