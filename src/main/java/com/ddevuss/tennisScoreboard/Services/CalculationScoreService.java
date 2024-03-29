package com.ddevuss.tennisScoreboard.Services;

import com.ddevuss.tennisScoreboard.DTO.ActivePlayer;
import com.ddevuss.tennisScoreboard.DTO.CurrentMatch;

import java.util.UUID;

import static com.ddevuss.tennisScoreboard.Services.FinishedMatchPersistenceService.finishCurrentMatch;

public class CalculationScoreService {

    private static final MainMatchesService MAIN_MATCHES_SERVICE = MainMatchesService.getINSTANCE();

    public static synchronized void plusPointToPlayer(UUID uuid, int playerId) {
        CurrentMatch currentMatch = MAIN_MATCHES_SERVICE.getCurrentMatches().get(uuid);

        if (currentMatch.isDeuce()) {
            decideDeuceSituation(currentMatch, playerId);
            checkEndOfSet(currentMatch);
        }
        else if (currentMatch.isTieBreak()) {
            plusPointToActivePlayer(currentMatch, playerId);
            checkEndOfSetIfTieBreak(currentMatch);
        }
        else {
            plusPointToActivePlayer(currentMatch, playerId);
            checkEndOfGame(currentMatch, playerId);
            checkEndOfSet(currentMatch);
        }

        checkEndOfMatch(currentMatch);
    }

    private static void decideDeuceSituation(CurrentMatch currentMatch, int playerId) {
        if (currentMatch.getPlayer1().getId() == playerId && currentMatch.getPlayer1().isAdvantage()) {
            resetScoreAndPlusGame(currentMatch.getPlayer1(), currentMatch.getPlayer2());
            currentMatch.setDeuce(false);
        }
        else if (currentMatch.getPlayer1().getId() == playerId && currentMatch.getPlayer2().isAdvantage()) {
            currentMatch.getPlayer2().setAdvantage(false);
        }
        else if (currentMatch.getPlayer1().getId() == playerId) {
            currentMatch.getPlayer1().setAdvantage(true);
        }
        else if (currentMatch.getPlayer1().isAdvantage()) {
            currentMatch.getPlayer1().setAdvantage(false);
        }
        else if (currentMatch.getPlayer2().isAdvantage()) {
            resetScoreAndPlusGame(currentMatch.getPlayer2(), currentMatch.getPlayer1());
            currentMatch.setDeuce(false);
        }
        else {
            currentMatch.getPlayer2().setAdvantage(true);
        }
    }

    private static void checkEndOfSetIfTieBreak(CurrentMatch currentMatch) {
        ActivePlayer activePlayer1 = currentMatch.getPlayer1();
        ActivePlayer activePlayer2 = currentMatch.getPlayer2();
        int tieBreakBound = 7;

        if (activePlayer1.getScore() >= tieBreakBound
                && activePlayer1.getScore() - activePlayer2.getScore() > 1) {

            resetScoreAndPlusSet(activePlayer1, activePlayer2);
            currentMatch.setTieBreak(false);
        }
        else if (activePlayer2.getScore() >= tieBreakBound
                && activePlayer2.getScore() - activePlayer1.getScore() > 1) {

            resetScoreAndPlusSet(activePlayer2, activePlayer1);
            currentMatch.setTieBreak(false);
        }
    }

    private static void checkEndOfGame(CurrentMatch currentMatch, int playerId) {
        ActivePlayer activePlayer1 = currentMatch.getPlayer1();
        ActivePlayer activePlayer2 = currentMatch.getPlayer2();
        int fortyPoints = 3;
        int scoreLimitForGame = 4;

        if (activePlayer1.getScore() == scoreLimitForGame) {
            resetScoreAndPlusGame(activePlayer1, activePlayer2);
        }
        else if (activePlayer2.getScore() == scoreLimitForGame) {
            resetScoreAndPlusGame(activePlayer2, activePlayer1);
        }
        else if (activePlayer1.getScore() == fortyPoints && activePlayer2.getScore() == fortyPoints) {
            currentMatch.setDeuce(true);
        }
    }

    private static void checkEndOfSet(CurrentMatch currentMatch) {
        ActivePlayer activePlayer1 = currentMatch.getPlayer1();
        ActivePlayer activePlayer2 = currentMatch.getPlayer2();
        int gamesBound = 6;

        if (activePlayer1.getGame() >= gamesBound || activePlayer2.getGame() >= gamesBound) {

            if (Math.abs(activePlayer1.getGame() - activePlayer2.getGame()) > 1
                    && activePlayer1.getGame() > activePlayer2.getGame()) {

                resetScoreAndPlusSet(activePlayer1, activePlayer2);
            }
            else if (Math.abs(activePlayer1.getGame() - activePlayer2.getGame()) > 1
                    && activePlayer2.getGame() > activePlayer1.getGame()) {

                resetScoreAndPlusSet(activePlayer2, activePlayer1);
            }
            else if (activePlayer1.getGame() == gamesBound && activePlayer2.getGame() == gamesBound) {
                currentMatch.setTieBreak(true);
            }
        }
    }

    private static void plusPointToActivePlayer(CurrentMatch currentMatch, int playerId) {
        if (currentMatch.getPlayer1().getId() == playerId) {
            currentMatch.getPlayer1().plusScore();
        }
        else {
            currentMatch.getPlayer2().plusScore();
        }
    }

    private static void checkEndOfMatch(CurrentMatch currentMatch) {
        int requiredSetsForVictory = 2;

        if (currentMatch.getPlayer1().getSet() == requiredSetsForVictory
                || currentMatch.getPlayer2().getSet() == requiredSetsForVictory) {
            finishCurrentMatch(currentMatch);
        }
    }

    private static void resetScoreAndPlusSet(ActivePlayer wonPlayer, ActivePlayer beatenPlayer) {
        wonPlayer.setScore(0);
        wonPlayer.setGame(0);
        wonPlayer.plusSet();
        beatenPlayer.setScore(0);
        beatenPlayer.setGame(0);
    }

    private static void resetScoreAndPlusGame(ActivePlayer wonPlayer, ActivePlayer beatenPlayer) {
        wonPlayer.setScore(0);
        wonPlayer.plusGame();
        wonPlayer.setAdvantage(false);
        beatenPlayer.setScore(0);
    }

}
