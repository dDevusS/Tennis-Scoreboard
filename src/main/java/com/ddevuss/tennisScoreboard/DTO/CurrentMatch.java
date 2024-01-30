package com.ddevuss.tennisScoreboard.DTO;

import com.ddevuss.tennisScoreboard.model.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder(builderMethodName = "of")
public class CurrentMatch {

    private UUID uuid;
    private Player player1;
    private Player player2;
    private int player1Score;
    private int player2Score;
}
