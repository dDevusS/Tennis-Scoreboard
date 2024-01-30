package com.ddevuss.tennisScoreboard.Services;

import com.ddevuss.tennisScoreboard.DTO.CurrentMatch;
import com.ddevuss.tennisScoreboard.model.Match;
import com.ddevuss.tennisScoreboard.model.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IMainService {

    void startNewMatch(CurrentMatch match);
    Map<UUID, CurrentMatch> getCurrentMatches();
    void plusPoint(CurrentMatch currentMatch);
    List<Match> getAllEndedMatches();
    List<Match> getEndedMatches(String playerName);

}
