package com.ddevuss.tennisScoreboard.Services;

import com.ddevuss.tennisScoreboard.DTO.CurrentMatch;
import org.junit.jupiter.api.*;

import java.util.UUID;

import static com.ddevuss.tennisScoreboard.UtilsForTesting.MatchScoreAssertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("CalculationScoreTest")
public class CalculationScoreServiceTest {

    private static final CalculationScoreService CALCULATION_SCORE_SERVICE = CalculationScoreService.getINSTANCE();
    private static final MainMatchesService MAIN_MATCHES_SERVICE = MainMatchesService.getINSTANCE();

    @BeforeAll
    static void createAndPutNewMatchesIntoMap() {
        MAIN_MATCHES_SERVICE.createNewMatch("Oleg", "Vladimir");
        MAIN_MATCHES_SERVICE.createNewMatch("Dmitriy", "Leonid");
    }

    private static void makeTieBreakSituation(CurrentMatch currentMatch) {
        currentMatch.getPlayer1().setGame(6);
        currentMatch.getPlayer2().setGame(6);
        currentMatch.setTieBreak(true);
    }

    private static void makeDeuceSituation(CurrentMatch currentMatch) {
        currentMatch.getPlayer1().setScore(3);
        currentMatch.getPlayer2().setScore(3);
        currentMatch.setDeuce(true);
    }

    @BeforeEach
    void resetCurrentMatch() {
        var matchUUID = MAIN_MATCHES_SERVICE.getUuidList().get(0);
        var currentMatch = MAIN_MATCHES_SERVICE.getCurrentMatches().get(matchUUID);

        currentMatch.getPlayer1().setScore(0);
        currentMatch.getPlayer1().setGame(0);
        currentMatch.getPlayer1().setSet(0);
        currentMatch.getPlayer1().setAdvantage(false);

        currentMatch.getPlayer2().setScore(0);
        currentMatch.getPlayer2().setGame(0);
        currentMatch.getPlayer2().setSet(0);
        currentMatch.getPlayer2().setAdvantage(false);

        currentMatch.setDeuce(false);
        currentMatch.setTieBreak(false);
    }

    @Test
    @DisplayName("will increase games score for first player and reset score for both")
    void playerGainsOneGamePoint_AfterFourConsecutivePoints() {
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
    void deuceIsTrue_AfterStartingDeuceSituation() {
        UUID matchUUID = MAIN_MATCHES_SERVICE.getUuidList().get(0);
        CurrentMatch currentMatch = MAIN_MATCHES_SERVICE.getCurrentMatches().get(matchUUID);
        int servingNumber = 3;

        for (int playCounter = 0; playCounter < servingNumber; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(currentMatch.getUuid(), currentMatch.getPlayer1().getId());
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(currentMatch.getUuid(), currentMatch.getPlayer2().getId());
        }

        assertThat(currentMatch.isDeuce())
                .describedAs("CurrentMatch.isDeuce must be true.")
                .isTrue();
    }

    @Test
    @DisplayName("First player must have advantage when he win boll in starting deuce situation.")
    void playerHaveAdvantage_AfterWinningBollInDeuceSituation() {
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
    void playersHaveThreePointsEachInDeuceSituation_AfterWinningOneBallEach() {
        UUID matchUUID = MAIN_MATCHES_SERVICE.getUuidList().get(0);
        CurrentMatch currentMatch = MAIN_MATCHES_SERVICE.getCurrentMatches().get(matchUUID);

        makeDeuceSituation(currentMatch);
        CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer1().getId());
        CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer2().getId());

        assertScoreForPlayers(currentMatch, 3);
    }

    @Test
    @DisplayName("First player must get 1 game when he win boll twice after starting deuce situation.")
    void gainGamesPoint_AfterWinningTwoBollsConsecutive() {
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
    void zeroPlayersScore_AfterDecidingDeuce() {
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
    void playerGainsOneSetsPoint_AfterWinningSevenGamesFirst() {
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
    void trueTiebreak_WhenPlayersGainSixGamesEach() {
        UUID matchUUID = MAIN_MATCHES_SERVICE.getUuidList().get(0);
        CurrentMatch currentMatch = MAIN_MATCHES_SERVICE.getCurrentMatches().get(matchUUID);
        currentMatch.getPlayer1().setGame(5);
        currentMatch.getPlayer2().setGame(5);
        int servingNumber = 4;

        for (int playCounter = 0; playCounter < servingNumber; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(currentMatch.getUuid(), currentMatch.getPlayer1().getId());
        }

        for (int playCounter = 0; playCounter < servingNumber; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(currentMatch.getUuid(), currentMatch.getPlayer2().getId());
        }

        assertThat(currentMatch.isTieBreak())
                .describedAs("currentMatch.isTieBreak must be true.")
                .isTrue();
    }

    @Test
    @DisplayName("First player must get 1 set point after deciding tie break situation. " +
            "Players scores and games must reset to zero.")
    void playerGainsOneSetPointInTiebreak_AfterWinningSevenBallsFirst() {
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
    void falseTieBreak_AfterDecidingTieBreak() {
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
    void removingMatchFromCurrentMatches_WhenPlayerWinMatch() {
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
}
