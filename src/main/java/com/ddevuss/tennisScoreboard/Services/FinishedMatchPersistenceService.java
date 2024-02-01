package com.ddevuss.tennisScoreboard.Services;

import com.ddevuss.tennisScoreboard.DAO.MatchDAO;
import com.ddevuss.tennisScoreboard.DTO.CurrentMatch;
import com.ddevuss.tennisScoreboard.model.Match;
import com.ddevuss.tennisScoreboard.model.Player;
import lombok.Getter;

public class FinishedMatchPersistenceService {

    private static final MatchDAO MATCH_DAO = MatchDAO.getInstance();
    private static final MainMatchesService MAIN_SERVICE = MainMatchesService.getINSTANCE();
    @Getter
    private static final FinishedMatchPersistenceService INSTANCE = new FinishedMatchPersistenceService();

    private FinishedMatchPersistenceService() {
    }

    public void finishCurrentMatch(CurrentMatch currentMatch) {
        Match endedMatch = generateMatchModel(currentMatch);
        MATCH_DAO.save(endedMatch);
        //TODO: возвращает обьект из мапы. можно переделать
        MAIN_SERVICE.getCurrentMatches().remove(currentMatch.getUuid());
    }

    private Match generateMatchModel(CurrentMatch currentMatch) {
        Player player1 = Player.of()
                .id(currentMatch.getPlayer1().getId())
                .name(currentMatch.getPlayer1().getName())
                .build();

        Player player2 = Player.of()
                .id(currentMatch.getPlayer2().getId())
                .name(currentMatch.getPlayer2().getName())
                .build();

        Player winner;

        if (currentMatch.getPlayer1().getSet() > currentMatch.getPlayer2().getSet()) {
            winner = player1;
        }
        else {
            winner = player2;
        }

        return Match.of().player1(player1).player2(player2).winner(winner).build();
    }
}
