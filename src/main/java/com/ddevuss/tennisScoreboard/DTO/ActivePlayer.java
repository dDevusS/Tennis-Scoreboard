package com.ddevuss.tennisScoreboard.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder(builderMethodName = "of")
public class ActivePlayer {

    private int id;
    private String name;
    private int score;
    private int game;
    private int set;
    private boolean isAdvantage;

    public void plusScore() {
        score++;
    }

    public void plusGame() {
        game++;
    }

    public void plusSet() {
        set++;
    }

    public int getTennisScore(boolean isTieBreak) {
        if (isTieBreak) {
            return score;
        }

        switch (score) {
            case 1 -> {
                return 15;
            }
            case 2 -> {
                return 30;
            }
            case 3 -> {
                return 40;
            }
            default -> {
                return 0;
            }
        }
    }
}
