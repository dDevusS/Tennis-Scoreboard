package com.ddevuss.tennisScoreboard.Services;

import com.ddevuss.tennisScoreboard.DAO.MatchDAO;
import com.ddevuss.tennisScoreboard.DAO.PlayerDAO;
import com.ddevuss.tennisScoreboard.DTO.ActivePlayer;
import com.ddevuss.tennisScoreboard.DTO.CurrentMatch;
import com.ddevuss.tennisScoreboard.model.Match;
import com.ddevuss.tennisScoreboard.model.Player;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainService implements IMainService {

    private final static PlayerDAO PLAYER_DAO = PlayerDAO.getInstance();
    private final static MatchDAO MATCH_DAO = MatchDAO.getInstance();
    @Getter
    private final static MainService INSTANCE = new MainService();
    private static Map<UUID, CurrentMatch> currentMatches = new HashMap<>();

    private MainService() {
    }

    @Override
    public void startNewMatch(CurrentMatch currentMatch) {
        Player player1 = PLAYER_DAO.registerPlayerByName(currentMatch.getPlayer1().getName());
        Player player2 = PLAYER_DAO.registerPlayerByName(currentMatch.getPlayer2().getName());
        //TODO: реализовать конвертатор
        ActivePlayer activePlayer1 = ActivePlayer.of().id(player1.getId())
                .name(player1.getName())
                .score(0)
                .set(0)
                .build();

        ActivePlayer activePlayer2 = ActivePlayer.of().id(player2.getId())
                .name(player2.getName())
                .score(0)
                .set(1)
                .build();

        var randomUUID = UUID.randomUUID();

        currentMatch = CurrentMatch.of().uuid(randomUUID)
                .player1(activePlayer1)
                .player2(activePlayer2)
                .isTieBreak(false)
                .build();

        currentMatches.put(randomUUID, currentMatch);
    }

    @Override
    public Map<UUID, CurrentMatch> getCurrentMatches() {
        return currentMatches;
    }

    @Override
    public List<Match> getAllEndedMatches() {
        return MATCH_DAO.findAll();
    }

    @Override
    public List<Match> getEndedMatches(String playerName) {
        return MATCH_DAO.findAllByPlayerName(playerName);
    }
}
