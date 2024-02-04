package com.ddevuss.tennisScoreboard.UtilsForTesting;

import com.ddevuss.tennisScoreboard.DTO.CurrentMatch;
import org.junit.jupiter.api.Assertions;

import static org.assertj.core.api.Assertions.assertThat;

public class MatchScoreAssertions {

    public static void assertScoreForPlayers(CurrentMatch currentMatch, int scoreForBothPlayers) {
        Assertions.assertAll(
                "Expected to get " + scoreForBothPlayers + " score point from both players.",
                () -> {
                    assertThat(currentMatch.getPlayer1().getScore())
                            .describedAs("Expected to get " + scoreForBothPlayers + " score point from first players.")
                            .isEqualTo(scoreForBothPlayers);
                    assertThat(currentMatch.getPlayer2().getScore())
                            .describedAs("Expected to get " + scoreForBothPlayers + " score point from second players.")
                            .isEqualTo(scoreForBothPlayers);
                });
    }

    public static void assertScoreForPlayers(CurrentMatch currentMatch, int player1Score, int player2Score) {
        Assertions.assertAll(
                () -> {
                    assertThat(currentMatch.getPlayer1().getScore())
                            .describedAs("Expected to get " + player1Score + " score points from first player.")
                            .isEqualTo(player1Score);
                    assertThat(currentMatch.getPlayer2().getScore())
                            .describedAs("Expected to get " + player2Score + " score points from second player.")
                            .isEqualTo(player2Score);
                });
    }

    public static void assertGamesForPlayers(CurrentMatch currentMatch, int gamesFromBothPlayers) {
        Assertions.assertAll(
                "Expected to get " + gamesFromBothPlayers + " games points from both players.",
                () -> {
                    assertThat(currentMatch.getPlayer1().getGame())
                            .describedAs("Expected to get " + gamesFromBothPlayers + " games point from first players.")
                            .isEqualTo(gamesFromBothPlayers);
                    assertThat(currentMatch.getPlayer2().getGame())
                            .describedAs("Expected to get " + gamesFromBothPlayers + " games point from second players.")
                            .isEqualTo(gamesFromBothPlayers);
                });
    }

    public static void assertGamesForPlayers(CurrentMatch currentMatch, int player1Games, int player2Games) {
        Assertions.assertAll(
                () -> {
                    assertThat(currentMatch.getPlayer1().getGame())
                            .describedAs("Expected to get " + player1Games + " games points from first player.")
                            .isEqualTo(player1Games);
                    assertThat(currentMatch.getPlayer2().getGame())
                            .describedAs("Expected to get " + player2Games + " games points from second player.")
                            .isEqualTo(player2Games);
                });
    }

    public static void assertSetsForPlayers(CurrentMatch currentMatch, int setsFromBothPlayers) {
        Assertions.assertAll(
                "Expected to get " + setsFromBothPlayers + " sets points from both players.",
                () -> {
                    assertThat(currentMatch.getPlayer1().getSet())
                            .describedAs("Expected to get " + setsFromBothPlayers + " sets point from first players.")
                            .isEqualTo(setsFromBothPlayers);
                    assertThat(currentMatch.getPlayer2().getSet())
                            .describedAs("Expected to get " + setsFromBothPlayers + " sets point from first players.")
                            .isEqualTo(setsFromBothPlayers);
                });
    }

    public static void assertSetsForPlayers(CurrentMatch currentMatch, int player1Sets, int player2Sets) {
        Assertions.assertAll(
                () -> {
                    assertThat(currentMatch.getPlayer1().getSet())
                            .describedAs("Expected to get " + player1Sets + " sets points from first player.")
                            .isEqualTo(player1Sets);
                    assertThat(currentMatch.getPlayer2().getSet())
                            .describedAs("Expected to get " + player2Sets + " sets points from second player.")
                            .isEqualTo(player2Sets);
                });
    }
}
