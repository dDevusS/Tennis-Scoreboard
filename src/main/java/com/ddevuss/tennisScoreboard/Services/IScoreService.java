package com.ddevuss.tennisScoreboard.Services;

import java.util.UUID;

public interface IScoreService {

    void plusPointToPlayer(UUID uuid, int playerId);
}
