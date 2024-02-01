package com.ddevuss.tennisScoreboard.DTO;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@Builder(builderMethodName = "of")
public class CurrentMatch {

    private UUID uuid;
    private ActivePlayer player1;
    private ActivePlayer player2;
    private boolean isTieBreak;
    private boolean isDeuce;

}
