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
    private final Map<UUID, CurrentMatch> currentMatches = new HashMap<>();
    @Getter
    private final List<UUID> uuidList = new ArrayList<>();

    private MainMatchesService() {
    }

    @Override
    public void createNewMatch(String playerName1, String playerName2) {

        //TODO: решить на каком этапе будут отсеиваться игроки с одинаковыми именами

        Player player1 = PLAYER_DAO.registerPlayerByName(playerName1);
        Player player2 = PLAYER_DAO.registerPlayerByName(playerName2);

        ActivePlayer activePlayer1 = getNewActivePlayer(player1);
        ActivePlayer activePlayer2 = getNewActivePlayer(player2);
        var randomUUID = UUID.randomUUID();

        getUuidList().add(randomUUID);

        CurrentMatch currentMatch = CurrentMatch.of().uuid(randomUUID)
                .player1(activePlayer1)
                .player2(activePlayer2)
                .isTieBreak(false)
                .isDeuce(false)
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
        return ActivePlayer.of()
                .id(player.getId())
                .name(player.getName())
                .score(0)
                .game(0).set(0)
                .isAdvantage(false)
                .build();
    }
}
