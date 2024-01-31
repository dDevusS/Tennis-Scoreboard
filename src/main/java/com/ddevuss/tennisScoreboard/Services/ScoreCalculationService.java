package com.ddevuss.tennisScoreboard.Services;

import com.ddevuss.tennisScoreboard.DTO.ActivePlayer;
import com.ddevuss.tennisScoreboard.DTO.CurrentMatch;
import lombok.Getter;

import java.util.UUID;

public class ScoreCalculationService implements IScoreService {

    @Getter
    private static final ScoreCalculationService INSTANCE = new ScoreCalculationService();
    private final MainService MAIN_SERVICE = MainService.getINSTANCE();
    private final FinishedMatchPersistenceService finishedMatchPersistenceService
            = FinishedMatchPersistenceService.getINSTANCE();

    private ScoreCalculationService() {
    }

    @Override
    public void plusPointToPlayer(UUID uuid, int playerId) {
        CurrentMatch currentMatch = MAIN_SERVICE.getCurrentMatches().get(uuid);
        plusPointToActivePlayer(currentMatch, playerId);
        checkEndOfSet(currentMatch);
    }

    private void checkEndOfSet(CurrentMatch currentMatch) {
        ActivePlayer activePlayer1 = currentMatch.getPlayer1();
        ActivePlayer activePlayer2 = currentMatch.getPlayer2();

        if (currentMatch.isTieBreak()
                && Math.abs(activePlayer1.getScore() - activePlayer2.getScore()) >= 8) {

            if (activePlayer1.getScore() - activePlayer2.getScore() > 0) {
                activePlayer1.setScore(0);
                activePlayer2.setScore(0);
                int set = activePlayer1.getSet() + 1;
                activePlayer1.setSet(set);
            }
            else {
                activePlayer1.setScore(0);
                activePlayer2.setScore(0);
                int set = activePlayer2.getSet() + 1;
                activePlayer2.setSet(set);
            }
        }
        else {
            if (activePlayer1.getScore() / 4 >= 6 && activePlayer1.getScore() / 4 - activePlayer2.getScore() / 4 >= 2) {
                activePlayer1.setScore(0);
                activePlayer2.setScore(0);
                int set = activePlayer1.getSet() + 1;
                activePlayer1.setSet(set);
            }
            else if (activePlayer2.getScore() / 4 >= 6 && activePlayer2.getScore() / 4 - activePlayer1.getScore() / 4 >= 2) {
                activePlayer1.setScore(0);
                activePlayer2.setScore(0);
                int set = activePlayer2.getSet() + 1;
                activePlayer2.setSet(set);
            }
            else if (activePlayer1.getScore() >= 24 || activePlayer2.getScore() >= 24) {
                currentMatch.setTieBreak(true);
            }
        }

        checkEndOfMatch(currentMatch);
    }

    private void plusPointToActivePlayer(CurrentMatch currentMatch, int playerId) {
        int victoryPoint = 1;
        if (currentMatch.isTieBreak()) {
            victoryPoint = 4;
        }

        if (currentMatch.getPlayer1().getId() == playerId) {
            int newScore = currentMatch.getPlayer1().getScore() + victoryPoint;
            currentMatch.getPlayer1().setScore(newScore);
        }
        else {
            int newScore = currentMatch.getPlayer2().getScore() + victoryPoint;
            currentMatch.getPlayer2().setScore(newScore);
        }
    }

    private void checkEndOfMatch(CurrentMatch currentMatch) {
        int countOfSets = currentMatch.getPlayer1().getSet() + currentMatch.getPlayer2().getSet();
        if (countOfSets == 3) {
            finishedMatchPersistenceService.finishCurrentMatch(currentMatch);
        }
    }
}
