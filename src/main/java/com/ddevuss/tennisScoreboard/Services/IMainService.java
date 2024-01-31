package com.ddevuss.tennisScoreboard.Services;

import com.ddevuss.tennisScoreboard.DTO.CurrentMatch;
import com.ddevuss.tennisScoreboard.model.Match;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IMainService {

    void startNewMatch(CurrentMatch match);

    Map<UUID, CurrentMatch> getCurrentMatches();

    List<Match> getAllEndedMatches();

    List<Match> getEndedMatches(String playerName);

}
