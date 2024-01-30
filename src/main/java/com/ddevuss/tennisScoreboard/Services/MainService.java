package com.ddevuss.tennisScoreboard.Services;

import com.ddevuss.tennisScoreboard.DAO.MatchDAO;
import com.ddevuss.tennisScoreboard.DAO.PlayerDAO;
import com.ddevuss.tennisScoreboard.DTO.CurrentMatch;
import com.ddevuss.tennisScoreboard.model.Match;
import com.ddevuss.tennisScoreboard.model.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainService implements IMainService {

    private static Map<UUID, CurrentMatch> currentMatches = new HashMap<>();
    private final static PlayerDAO PLAYER_DAO = PlayerDAO.getInstance();
    private final static MatchDAO MATCH_DAO = MatchDAO.getInstance();

    @Override
    public void startNewMatch(CurrentMatch currentMatch) {
        Player player1 = PLAYER_DAO.registerPlayerByName(currentMatch.getPlayer1().getName());
        Player player2 = PLAYER_DAO.registerPlayerByName(currentMatch.getPlayer2().getName());
        var randomUUID = UUID.randomUUID();

        currentMatch = CurrentMatch.of().uuid(randomUUID).player1(player1).player2(player2).build();

        currentMatches.put(randomUUID, currentMatch);
    }

    @Override
    public Map<UUID, CurrentMatch> getCurrentMatches() {
        return currentMatches;
    }

    @Override
    public void plusPoint(CurrentMatch currentMatch) {
        //TODO: Move this method to another service
    }

    @Override
    public List<Match> getAllEndedMatches() {
        return MATCH_DAO.findAll();
    }

    @Override
    public List<Match> getEndedMatches(Player player) {
        return MATCH_DAO.findAllByPlayer(player);
    }
}
