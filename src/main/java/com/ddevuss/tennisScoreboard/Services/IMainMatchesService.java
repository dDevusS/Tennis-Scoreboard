package com.ddevuss.tennisScoreboard.Services;

import com.ddevuss.tennisScoreboard.DTO.CurrentMatch;
import com.ddevuss.tennisScoreboard.model.Match;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IMainMatchesService {

    UUID createNewMatch(String playerName1, String playerName2);

    Map<UUID, CurrentMatch> getCurrentMatches();

    List<Match> getAllEndedMatches();

    List<Match> getEndedMatchesByPlayerName(String playerName);

}
