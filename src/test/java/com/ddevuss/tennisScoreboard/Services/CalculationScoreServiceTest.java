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

    @Test
    @DisplayName("will increase games score for first player and reset score for both")
    void checkingEndOfGame() {
        UUID matchUUID = MAIN_MATCHES_SERVICE.getUuidList().get(0);
        CurrentMatch currentMatch = MAIN_MATCHES_SERVICE.getCurrentMatches().get(matchUUID);

        currentMatch.getPlayer2().setScore(2);

        for (int playCounter = 0; playCounter < 4; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer1().getId());
        }

        assertScoreForPlayers(currentMatch, 0);
        assertGamesForPlayers(currentMatch, 1, 0);
        assertSetsForPlayers(currentMatch, 0);
    }

    @Test
    @DisplayName("will become deuce situation and decide this")
    void decidingDeuceSituation() {
        UUID matchUUID = MAIN_MATCHES_SERVICE.getUuidList().get(0);
        CurrentMatch currentMatch = MAIN_MATCHES_SERVICE.getCurrentMatches().get(matchUUID);

        for (int playCounter = 0; playCounter < 3; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer1().getId());
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer2().getId());
        }
        assertThat(currentMatch.isDeuce()).isTrue();
        assertThat(currentMatch.getPlayer1().isAdvantage()).isFalse();
        assertThat(currentMatch.getPlayer2().isAdvantage()).isFalse();

        CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer1().getId());
        assertThat(currentMatch.getPlayer1().isAdvantage()).isTrue();
        assertThat(currentMatch.getPlayer2().isAdvantage()).isFalse();
        assertScoreForPlayers(currentMatch, 3);

        CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer2().getId());
        assertThat(currentMatch.getPlayer2().isAdvantage()).isTrue();
        assertThat(currentMatch.getPlayer1().isAdvantage()).isFalse();
        assertScoreForPlayers(currentMatch, 3);

        CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer2().getId());
        assertThat(currentMatch.getPlayer1().isAdvantage()).isFalse();
        assertThat(currentMatch.getPlayer2().isAdvantage()).isFalse();
        assertScoreForPlayers(currentMatch, 0);
        assertGamesForPlayers(currentMatch, 0, 1);
        assertSetsForPlayers(currentMatch, 0);
    }

    @Test
    @DisplayName("will increase sets score after coming 6-4 games")
    void checkingEndOfSetWithSixFourGames() {
        UUID matchUUID = MAIN_MATCHES_SERVICE.getUuidList().get(0);
        CurrentMatch currentMatch = MAIN_MATCHES_SERVICE.getCurrentMatches().get(matchUUID);

        currentMatch.getPlayer1().setGame(5);
        currentMatch.getPlayer2().setGame(4);

        for (int playCounter = 0; playCounter < 4; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer1().getId());
        }

        assertScoreForPlayers(currentMatch, 0);
        assertGamesForPlayers(currentMatch, 0);
        assertSetsForPlayers(currentMatch, 1, 0);
    }

    @Test
    @DisplayName("will increase sets score after coming 7-5 games")
    void checkingEndOfSetWithSevenFiveGames() {
        UUID matchUUID = MAIN_MATCHES_SERVICE.getUuidList().get(0);
        CurrentMatch currentMatch = MAIN_MATCHES_SERVICE.getCurrentMatches().get(matchUUID);

        currentMatch.getPlayer1().setGame(5);
        currentMatch.getPlayer2().setGame(5);

        for (int playCounter = 0; playCounter < 8; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer1().getId());
        }

        assertScoreForPlayers(currentMatch, 0);
        assertGamesForPlayers(currentMatch, 0);
        assertSetsForPlayers(currentMatch, 1, 0);
    }

    @Test
    @DisplayName("will become tieBreak and decide this with score 7-5")
    void becomingAndDecidingTieBreakWithSevenFiveScore() {
        UUID matchUUID = MAIN_MATCHES_SERVICE.getUuidList().get(0);
        CurrentMatch currentMatch = MAIN_MATCHES_SERVICE.getCurrentMatches().get(matchUUID);

        currentMatch.getPlayer1().setGame(5);
        currentMatch.getPlayer2().setGame(5);

        for (int playCounter = 0; playCounter < 4; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer1().getId());
        }

        for (int playCounter = 0; playCounter < 4; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer2().getId());
        }

        assertThat(currentMatch.isTieBreak()).isTrue();

        for (int playCounter = 0; playCounter < 5; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer1().getId());
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer2().getId());
        }

        assertThat(currentMatch.isTieBreak()).isTrue();
        assertScoreForPlayers(currentMatch, 5);

        for (int playCounter = 0; playCounter < 2; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer1().getId());
        }

        assertThat(currentMatch.isTieBreak()).isFalse();
        assertScoreForPlayers(currentMatch, 0);
        assertGamesForPlayers(currentMatch, 0);
        assertSetsForPlayers(currentMatch, 1, 0);
    }

    @Test
    @DisplayName("will become tieBreak and decide this with score 7-5")
    void becomingAndDecidingTieBreakWithEightSixScore() {
        UUID matchUUID = MAIN_MATCHES_SERVICE.getUuidList().get(0);
        CurrentMatch currentMatch = MAIN_MATCHES_SERVICE.getCurrentMatches().get(matchUUID);

        currentMatch.getPlayer1().setGame(5);
        currentMatch.getPlayer2().setGame(5);

        for (int playCounter = 0; playCounter < 4; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer1().getId());
        }

        for (int playCounter = 0; playCounter < 4; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer2().getId());
        }

        assertThat(currentMatch.isTieBreak()).isTrue();

        for (int playCounter = 0; playCounter < 5; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer1().getId());
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer2().getId());
        }

        assertThat(currentMatch.isTieBreak()).isTrue();
        assertScoreForPlayers(currentMatch, 5);

        for (int playCounter = 0; playCounter < 2; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer1().getId());
        }

        assertThat(currentMatch.isTieBreak()).isFalse();
        assertScoreForPlayers(currentMatch, 0);
        assertGamesForPlayers(currentMatch, 0);
        assertSetsForPlayers(currentMatch, 1, 0);
    }

    @Test
    @DisplayName("will become tieBreak and decide this with score 9-7")
    void becomingAndDecidingTieBreakWithNineSevenScore() {
        UUID matchUUID = MAIN_MATCHES_SERVICE.getUuidList().get(0);
        CurrentMatch currentMatch = MAIN_MATCHES_SERVICE.getCurrentMatches().get(matchUUID);

        currentMatch.getPlayer1().setGame(5);
        currentMatch.getPlayer2().setGame(5);

        for (int playCounter = 0; playCounter < 4; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer1().getId());
        }

        for (int playCounter = 0; playCounter < 4; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer2().getId());
        }

        assertThat(currentMatch.isTieBreak()).isTrue();

        for (int playCounter = 0; playCounter < 5; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer1().getId());
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer2().getId());
        }

        assertThat(currentMatch.isTieBreak()).isTrue();
        assertScoreForPlayers(currentMatch, 5);

        for (int playCounter = 0; playCounter < 2; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer1().getId());
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer2().getId());
        }

        assertScoreForPlayers(currentMatch, 7);

        for (int playCounter = 0; playCounter < 2; playCounter++) {
            CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer1().getId());
        }

        assertThat(currentMatch.isTieBreak()).isFalse();
        assertScoreForPlayers(currentMatch, 0);
        assertGamesForPlayers(currentMatch, 0);
        assertSetsForPlayers(currentMatch, 1, 0);
    }

    @Test
    @DisplayName("Will remove the match from a HashMap")
    void checkingEndOfMatch() {
        UUID matchUUID = MAIN_MATCHES_SERVICE.getUuidList().get(1);
        CurrentMatch currentMatch = MAIN_MATCHES_SERVICE.getCurrentMatches().get(matchUUID);

        currentMatch.getPlayer1().setSet(1);
        currentMatch.getPlayer1().setGame(5);
        currentMatch.getPlayer1().setScore(3);

        CALCULATION_SCORE_SERVICE.plusPointToPlayer(matchUUID, currentMatch.getPlayer1().getId());
        CurrentMatch maybeCurrentMatch = MAIN_MATCHES_SERVICE.getCurrentMatches().get(matchUUID);

        assertThat(maybeCurrentMatch).isNull();
    }

    @AfterEach
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

    private static void assertScoreForPlayers(CurrentMatch currentMatch, int scoreForBothPlayers) {
        assertThat(currentMatch.getPlayer1().getScore()).isEqualTo(scoreForBothPlayers);
        assertThat(currentMatch.getPlayer2().getScore()).isEqualTo(scoreForBothPlayers);
    }

    private static void assertScoreForPlayers(CurrentMatch currentMatch, int player1Score, int player2Score) {
        assertThat(currentMatch.getPlayer1().getScore()).isEqualTo(player1Score);
        assertThat(currentMatch.getPlayer2().getScore()).isEqualTo(player2Score);
    }

    private static void assertGamesForPlayers(CurrentMatch currentMatch, int gamesForBothPlayers) {
        assertThat(currentMatch.getPlayer1().getScore()).isEqualTo(gamesForBothPlayers);
        assertThat(currentMatch.getPlayer2().getScore()).isEqualTo(gamesForBothPlayers);
    }

    private static void assertGamesForPlayers(CurrentMatch currentMatch, int player1Games, int player2Games) {
        assertThat(currentMatch.getPlayer1().getScore()).isEqualTo(player1Games);
        assertThat(currentMatch.getPlayer2().getScore()).isEqualTo(player2Games);
    }

    private static void assertSetsForPlayers(CurrentMatch currentMatch, int setsForBothPlayers) {
        assertThat(currentMatch.getPlayer1().getScore()).isEqualTo(setsForBothPlayers);
        assertThat(currentMatch.getPlayer2().getScore()).isEqualTo(setsForBothPlayers);
    }

    private static void assertSetsForPlayers(CurrentMatch currentMatch, int player1Sets, int player2Sets) {
        assertThat(currentMatch.getPlayer1().getScore()).isEqualTo(player1Sets);
        assertThat(currentMatch.getPlayer2().getScore()).isEqualTo(player2Sets);
    }
}
