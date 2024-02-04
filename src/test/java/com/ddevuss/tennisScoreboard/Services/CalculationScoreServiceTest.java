package com.ddevuss.tennisScoreboard.Services;

import com.ddevuss.tennisScoreboard.DTO.CurrentMatch;
import org.junit.jupiter.api.*;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@Tag("CalculationScoreTest")
public class CalculationScoreServiceTest {

    private static final CalculationScoreService CALCULATION_SCORE_SERVICE = CalculationScoreService.getINSTANCE();
    private static final MainMatchesService MAIN_MATCHES_SERVICE = MainMatchesService.getINSTANCE();

    @BeforeAll
    static void createAndPutNewMatchesIntoMap() {
        MAIN_MATCHES_SERVICE.createNewMatch("Oleg", "Vladimir");
        MAIN_MATCHES_SERVICE.createNewMatch("Dmitriy", "Leonid");
    }

    @BeforeEach
    void resetCurrentMatch() {
        var matchUUID = MAIN_MATCHES_SERVICE.getUuidList().get(0);
        var currentMatch = MAIN_MATCHES_SERVICE.getCurrentMatches().get(matchUUID);

        currentMatch.getPlayer1().setScore(0);
        currentMatch.getPlayer1().setGame(0);
        currentMatch.getPlayer1().setSet(0);

        currentMatch.getPlayer2().setScore(0);
        currentMatch.getPlayer2().setGame(0);
        currentMatch.getPlayer2().setSet(0);

        currentMatch.setDeuce(false);
        currentMatch.setTieBreak(false);
    }

    @Test
    @DisplayName("will increase games score for first player and reset score for both")
    void firstPlayerGainsOneGamePoint_AfterFourConsecutivePoints() {
        UUID matchUUID = MAIN_MATCHES_SERVICE.getUuidList().get(0);
        CurrentMatch currentMatch = MAIN_MATCHES_SERVICE.getCurrentMatches().get(matchUUID);
        int servingNumber = 4;

        for (int playCounter = 0; playCounter < servingNumber; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer1().getId());
        }

        Assertions.assertAll("Player1 have to have 1 games point and player2 have to have 0 games point" +
                " while score and sets must be 0 for both players.",
                () -> {
            assertScoreForPlayers(currentMatch, 0);
            assertGamesForPlayers(currentMatch, 1, 0);
            assertSetsForPlayers(currentMatch, 0);
        });
    }

    @Test
    @DisplayName("CurrentMatch.isDeuce must be true when each players get 3 score points.")
    void deuceIsTrueAfterStartingDeuceSituation() {
        UUID matchUUID = MAIN_MATCHES_SERVICE.getUuidList().get(0);
        CurrentMatch currentMatch = MAIN_MATCHES_SERVICE.getCurrentMatches().get(matchUUID);

        makeDeuceSituation(currentMatch);

        assertThat(currentMatch.isDeuce())
                .describedAs("CurrentMatch.isDeuce must be true.")
                .isTrue();
    }

    @Test
    @DisplayName("First player must have advantage when he win boll in starting deuce situation.")
    void firstPlayerHaveAdvantageAfterWinningBollWhileDeuceSituation() {
        UUID matchUUID = MAIN_MATCHES_SERVICE.getUuidList().get(0);
        CurrentMatch currentMatch = MAIN_MATCHES_SERVICE.getCurrentMatches().get(matchUUID);

        makeDeuceSituation(currentMatch);
        CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer1().getId());

        Assertions.assertAll(
                () -> {
                    assertThat(currentMatch.getPlayer1().isAdvantage())
                            .describedAs("player1.isAdvantage must be true.")
                            .isTrue();
                    assertThat(currentMatch.getPlayer2().isAdvantage())
                            .describedAs("player2.isAdvantage must be false.")
                            .isFalse();
                });
    }

    @Test
    @DisplayName("Players score mustn't change after starting deuce situation.")
    void playersScoreThreePointsEachInDeuceSituationAfterWinningOneBallEach() {
        UUID matchUUID = MAIN_MATCHES_SERVICE.getUuidList().get(0);
        CurrentMatch currentMatch = MAIN_MATCHES_SERVICE.getCurrentMatches().get(matchUUID);

        makeDeuceSituation(currentMatch);
        CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer1().getId());
        CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer2().getId());

        assertScoreForPlayers(currentMatch, 3);
    }

    @Test
    @DisplayName("First player must get 1 game when he win boll twice after starting deuce situation.")
    void gettingOneGamesPoint_AfterWinningTwoBollsConsecutiveByFirstPlayer() {
        UUID matchUUID = MAIN_MATCHES_SERVICE.getUuidList().get(0);
        CurrentMatch currentMatch = MAIN_MATCHES_SERVICE.getCurrentMatches().get(matchUUID);
        int servingNumber = 2;

        makeDeuceSituation(currentMatch);
        for (int playCounter = 0; playCounter < servingNumber; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer1().getId());
        }

        assertGamesForPlayers(currentMatch, 1, 0);
    }

    @Test
    @DisplayName("Players advantage statuses and match deuce must be false" +
            " after deciding deuce situation.")
    void falsePlayersAdvantagesAndDeuceStatus_AfterDecidingDeuce() {
        UUID matchUUID = MAIN_MATCHES_SERVICE.getUuidList().get(0);
        CurrentMatch currentMatch = MAIN_MATCHES_SERVICE.getCurrentMatches().get(matchUUID);
        int servingNumber = 2;

        makeDeuceSituation(currentMatch);

        for (int playCounter = 0; playCounter < servingNumber; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer1().getId());
        }

        Assertions.assertAll(
                () -> {
                    assertThat(currentMatch.getPlayer1().isAdvantage())
                            .describedAs("players1.isAdvantage must be false.")
                            .isFalse();
                    assertThat(currentMatch.getPlayer1().isAdvantage())
                            .describedAs("players2.isAdvantage must be false.")
                            .isFalse();
                    assertThat(currentMatch.isDeuce())
                            .describedAs("currentMatch.isDeuce must be false.")
                            .isFalse();
                }
        );
    }

    @Test
    @DisplayName("Players scores must be zero after deciding deuce situation.")
    void zeroPlayersScoreAfterDecidingDeuce() {
        UUID matchUUID = MAIN_MATCHES_SERVICE.getUuidList().get(0);
        CurrentMatch currentMatch = MAIN_MATCHES_SERVICE.getCurrentMatches().get(matchUUID);
        int servingNumber = 2;

        makeDeuceSituation(currentMatch);

        for (int playCounter = 0; playCounter < servingNumber; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer1().getId());
        }

        assertScoreForPlayers(currentMatch, 0);
    }

    @Test
    @DisplayName("First player must have 1 sets point," +
            " players scores and players games must reset to zero after coming 7-5 games")
    void firstPlayerGainsOneSetsPoint_AfterWinningSevenGamesFirst() {
        UUID matchUUID = MAIN_MATCHES_SERVICE.getUuidList().get(0);
        CurrentMatch currentMatch = MAIN_MATCHES_SERVICE.getCurrentMatches().get(matchUUID);
        int servingNumber = 8;

        currentMatch.getPlayer1().setGame(5);
        currentMatch.getPlayer2().setGame(5);

        for (int playCounter = 0; playCounter < servingNumber; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer1().getId());
        }

        Assertions.assertAll(
                () -> {
                    assertScoreForPlayers(currentMatch, 0);
                    assertGamesForPlayers(currentMatch, 0);
                    assertSetsForPlayers(currentMatch, 1, 0);
                }
        );
    }

    @Test
    @DisplayName("TieBreak must become true when players games score will be 6-6.")
    void trueTiebreakWhenPlayersGainSixGamesEach() {
        UUID matchUUID = MAIN_MATCHES_SERVICE.getUuidList().get(0);
        CurrentMatch currentMatch = MAIN_MATCHES_SERVICE.getCurrentMatches().get(matchUUID);

        makeTieBreakSituation(currentMatch);

        assertThat(currentMatch.isTieBreak())
                .describedAs("currentMatch.isTieBreak must be true.")
                .isTrue();
    }

    @Test
    @DisplayName("First player must get 1 set point after deciding tie break situation. " +
            "Players scores and games must reset to zero.")
        void firstPlayerGainsOneSetPointInTiebreakAfterWinningSevenBallsFirst() {
        UUID matchUUID = MAIN_MATCHES_SERVICE.getUuidList().get(0);
        CurrentMatch currentMatch = MAIN_MATCHES_SERVICE.getCurrentMatches().get(matchUUID);

        makeTieBreakSituation(currentMatch);

        for (int playCounter = 0; playCounter < 7; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer1().getId());
        }

        Assertions.assertAll(
                () -> {
                    assertScoreForPlayers(currentMatch, 0);
                    assertGamesForPlayers(currentMatch, 0);
                    assertSetsForPlayers(currentMatch, 1, 0);
                }
        );
    }

    @Test
    @DisplayName("tieBreak from currentMatch must be false after deciding tieBreak situation.")
    void falseTieBreakAfterDecidingTieBreak() {
        UUID matchUUID = MAIN_MATCHES_SERVICE.getUuidList().get(0);
        CurrentMatch currentMatch = MAIN_MATCHES_SERVICE.getCurrentMatches().get(matchUUID);

       makeTieBreakSituation(currentMatch);

        for (int playCounter = 0; playCounter < 7; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer1().getId());
        }

        assertThat(currentMatch.isTieBreak())
                .describedAs("currentMatch.isTieBreak must be false.")
                .isFalse();
    }

    @Test
    @DisplayName("Will remove the match from a HashMap")
    void removingMatchFromCurrentMatchesHashMapWhenFirstPlayerWinMatch() {
        UUID matchUUID = MAIN_MATCHES_SERVICE.getUuidList().get(1);
        CurrentMatch currentMatch = MAIN_MATCHES_SERVICE.getCurrentMatches().get(matchUUID);

        currentMatch.getPlayer1().setSet(1);
        currentMatch.getPlayer1().setGame(5);
        currentMatch.getPlayer1().setScore(3);

        CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer1().getId());
        CurrentMatch maybeCurrentMatch = MAIN_MATCHES_SERVICE.getCurrentMatches().get(matchUUID);

        assertThat(maybeCurrentMatch)
                .describedAs("MAIN_MATCHES_SERVICE.getCurrentMatches().get(matchUUID) must return null.")
                .isNull();
    }

    private static void assertScoreForPlayers(CurrentMatch currentMatch, int scoreForBothPlayers) {
        Assertions.assertAll(
                "Expected to get " + scoreForBothPlayers + " score point from both players.",
                () -> {
            assertThat(currentMatch.getPlayer1().getScore()).isEqualTo(scoreForBothPlayers);
            assertThat(currentMatch.getPlayer2().getScore()).isEqualTo(scoreForBothPlayers);
        });
    }

    private static void assertScoreForPlayers(CurrentMatch currentMatch, int player1Score, int player2Score) {
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

    private static void assertGamesForPlayers(CurrentMatch currentMatch, int gamesFromBothPlayers) {
        Assertions.assertAll(
                "Expected to get " + gamesFromBothPlayers + " games points from both players.",
                () -> {
                    assertThat(currentMatch.getPlayer1().getGame()).isEqualTo(gamesFromBothPlayers);
                    assertThat(currentMatch.getPlayer2().getGame()).isEqualTo(gamesFromBothPlayers);
                });
    }

    private static void assertGamesForPlayers(CurrentMatch currentMatch, int player1Games, int player2Games) {
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

    private static void assertSetsForPlayers(CurrentMatch currentMatch, int setsFromBothPlayers) {
        Assertions.assertAll(
                "Expected to get " + setsFromBothPlayers + " sets points from both players.",
                () -> {
                    assertThat(currentMatch.getPlayer1().getSet()).isEqualTo(setsFromBothPlayers);
                    assertThat(currentMatch.getPlayer2().getSet()).isEqualTo(setsFromBothPlayers);
                });
    }

    private static void assertSetsForPlayers(CurrentMatch currentMatch, int player1Sets, int player2Sets) {
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

    private static void makeTieBreakSituation(CurrentMatch currentMatch) {
        currentMatch.getPlayer1().setGame(5);
        currentMatch.getPlayer2().setGame(5);
        int servingNumber = 4;

        for (int playCounter = 0; playCounter < servingNumber; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(currentMatch.getUuid(), currentMatch.getPlayer1().getId());
        }

        for (int playCounter = 0; playCounter < servingNumber; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(currentMatch.getUuid(), currentMatch.getPlayer2().getId());
        }
    }

    private static void makeDeuceSituation(CurrentMatch currentMatch) {
        int servingNumber = 3;
        for (int playCounter = 0; playCounter < servingNumber; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(currentMatch.getUuid(), currentMatch.getPlayer1().getId());
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(currentMatch.getUuid(), currentMatch.getPlayer2().getId());
        }
    }
}
