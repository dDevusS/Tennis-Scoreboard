package com.ddevuss.tennisScoreboard.DTO;

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
    private ActivePlayer player1;
    private ActivePlayer player2;
    private boolean isTieBreak;
}
