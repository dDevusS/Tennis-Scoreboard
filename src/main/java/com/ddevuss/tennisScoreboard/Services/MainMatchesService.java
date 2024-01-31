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

public class MainMatchesService implements IMainMatchesService {

    private static final PlayerDAO PLAYER_DAO = PlayerDAO.getInstance();
    private static final MatchDAO MATCH_DAO = MatchDAO.getInstance();
    @Getter
    private static final MainMatchesService INSTANCE = new MainMatchesService();
    @Getter
    private final Map<UUID, CurrentMatch> currentMatches = new HashMap<>();

    private MainMatchesService() {
    }

    @Override
    public void createNewMatch(CurrentMatch currentMatch) {
        Player player1 = PLAYER_DAO.registerPlayerByName(currentMatch.getPlayer1().getName());
        Player player2 = PLAYER_DAO.registerPlayerByName(currentMatch.getPlayer2().getName());

        ActivePlayer activePlayer1 = getNewActivePlayer(player1);
        ActivePlayer activePlayer2 = getNewActivePlayer(player2);
        var randomUUID = UUID.randomUUID();

        currentMatch = CurrentMatch.of().uuid(randomUUID)
                .player1(activePlayer1)
                .player2(activePlayer2)
                .isTieBreak(false)
                .build();

        currentMatches.put(randomUUID, currentMatch);
    }

    @Override
    public List<Match> getAllEndedMatches() {
        return MATCH_DAO.findAll();
    }

    @Override
    public List<Match> getEndedMatches(String playerName) {
        return MATCH_DAO.findAllByPlayerName(playerName);
    }

    private ActivePlayer getNewActivePlayer (Player player) {
        return ActivePlayer.of().id(player.getId()).name(player.getName()).score(0).set(0).build();
    }
}
