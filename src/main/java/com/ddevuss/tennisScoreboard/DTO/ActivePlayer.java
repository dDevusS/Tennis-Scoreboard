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

}
