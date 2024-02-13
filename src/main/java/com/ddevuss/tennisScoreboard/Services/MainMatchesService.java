package com.ddevuss.tennisScoreboard.Services;

import com.ddevuss.tennisScoreboard.DAO.MatchDAO;
import com.ddevuss.tennisScoreboard.DAO.PlayerDAO;
import com.ddevuss.tennisScoreboard.DTO.ActivePlayer;
import com.ddevuss.tennisScoreboard.DTO.CurrentMatch;
import com.ddevuss.tennisScoreboard.model.Match;
import com.ddevuss.tennisScoreboard.model.Player;
import lombok.Getter;

import java.util.*;

public class MainMatchesService implements IMainMatchesService {

    private static final PlayerDAO PLAYER_DAO = PlayerDAO.getInstance();
    private static final MatchDAO MATCH_DAO = MatchDAO.getInstance();
    @Getter
    private static final MainMatchesService INSTANCE = new MainMatchesService();
    @Getter
    private final Map<UUID, CurrentMatch> currentMatches;
    @Getter
    private final List<UUID> uuidList;

    private MainMatchesService() {
        currentMatches = new HashMap<>();
        uuidList = new ArrayList<>();
    }

    @Override
    public UUID createNewMatch(String playerName1, String playerName2) {
        Player player1 = PLAYER_DAO.registerPlayerByName(playerName1);
        Player player2 = PLAYER_DAO.registerPlayerByName(playerName2);

        ActivePlayer activePlayer1 = getNewActivePlayer(player1);
        ActivePlayer activePlayer2 = getNewActivePlayer(player2);
        UUID randomUUID = UUID.randomUUID();

        getUuidList().add(randomUUID);

        CurrentMatch currentMatch = CurrentMatch.of()
                .uuid(randomUUID)
                .player1(activePlayer1)
                .player2(activePlayer2)
                .isTieBreak(false)
                .isDeuce(false)
                .build();

        currentMatches.put(randomUUID, currentMatch);

        return randomUUID;
    }

    @Override
    public List<Match> getAllEndedMatches() {
        return MATCH_DAO.findAll();
    }

    @Override
    public List<Match> getEndedMatchesByPlayerName(String playerName) {
        return MATCH_DAO.findAllByPlayerName(playerName);
    }

    public boolean deleteMatchById (int id) {
        return MATCH_DAO.delete(id);
    }

    private ActivePlayer getNewActivePlayer (Player player) {
        return ActivePlayer.of()
                .id(player.getId())
                .name(player.getName())
                .score(0)
                .game(0).set(0)
                .isAdvantage(false)
                .build();
    }
}
