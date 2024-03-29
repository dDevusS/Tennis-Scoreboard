package com.ddevuss.tennisScoreboard.DAO;

import com.ddevuss.tennisScoreboard.model.Match;
import com.ddevuss.tennisScoreboard.model.Player;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("DAOImplementedClass")
class MatchDAOTest {

    private final static Player player1 = Player.of().name("Stanislav").build();
    private final static Player player2 = Player.of().name("Boris").build();
    private final static Player player3 = Player.of().name("Oleg").build();
    private final static Player player4 = Player.of().name("Fedor").build();
    private final static MatchDAO matchDAO = MatchDAO.getInstance();
    private final static PlayerDAO playerDAO = PlayerDAO.getInstance();
    private static Match match1;
    private static Match match2;
    private static Match match3;
    private static Match match4;

    @BeforeAll
    static void createFieldsAndAddPlayersIntoDB() {
        playerDAO.save(player1);
        playerDAO.save(player2);
        playerDAO.save(player3);
        playerDAO.save(player4);

        match1 = Match.of().player1(player1).player2(player2).winner(player1).build();
        match2 = Match.of().player1(player2).player2(player1).winner(player2).build();
        match3 = Match.of().player1(player3).player2(player1).winner(player3).build();
        match4 = Match.of().player1(player2).player2(player1).winner(player2).build();
    }

    static Stream<Match> provideMatches() {
        return Stream.of(match1, match2, match3, match4);
    }

    static Stream<Arguments> provideArgumentsForUpdateTest() {
        return Stream.of(
                Arguments.of(
                        Match.of().player1(player3)
                                .player2(player4)
                                .winner(player3)
                                .id(1).build(),
                        true
                ),
                Arguments.of(
                        Match.of().player1(player1)
                                .player2(player3)
                                .winner(player1)
                                .id(2).build(),
                        true
                ),
                Arguments.of(
                        Match.of().player1(player1)
                                .player2(player3)
                                .winner(player1)
                                .id(1000).build(),
                        false
                )
        );
    }

    @ParameterizedTest
    @Order(1)
    @MethodSource("provideMatches")
    @DisplayName("will be true if it returns same match with ID")
    void saveMethodReturnsSameMatchesWithId(Match match) {
        var savedMatch = matchDAO.save(match);
        assertThat(savedMatch.getId()).isNotZero();
        assertThat(savedMatch).usingRecursiveComparison().isEqualTo(match);
    }

    @ParameterizedTest
    @Order(2)
    @ValueSource(ints = {1, 1000})
    @DisplayName("will be true if match exists with this ID")
    void findMatchClassWithCorrectId(int id) {
        var maybeMatch = matchDAO.findById(id);

        if (id == 1) {
            assertThat(maybeMatch).isInstanceOf(Match.class);
        }
        else {
            assertThat(maybeMatch).isNull();
        }
    }

    @Test
    @Order(2)
    @DisplayName("compare size of matches list with number of added matches by previous test")
    void getMatchesListNotEmpty() {
        var matches = matchDAO.findAll();
        assertThat(matches).isNotEmpty();
    }

    @ParameterizedTest
    @Order(2)
    @MethodSource("provideArgumentsForUpdateTest")
    @DisplayName("will be true if method updated existed match")
    void updateMethodReturnsSameMatchIfExist(Match match, boolean expectedResult) {
        var updatedMatch = matchDAO.update(match);
        if (expectedResult) {
            assertThat(updatedMatch).usingRecursiveComparison().isEqualTo(match);
        }
        else {
            assertThat(updatedMatch).isNull();
        }
    }

    @ParameterizedTest
    @Order(3)
    @ValueSource(ints = {1, 1000})
    @DisplayName("will be true if player with this ID exists in the DB")
    void deleteByExistedMatchIdReturnsTrue(int id) {
        if (id == 1) {
            assertThat(matchDAO.delete(id)).isTrue();
        }
        else {
            assertThat(matchDAO.delete(id)).isFalse();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"UnrealName", "Stanislav", "Boris",})
    @DisplayName("will return list with matches there is player with this name")
    void returnMatchListWithPlayerWithThisName(String playerName) {
        if ("UnrealName".equalsIgnoreCase(playerName)) {
            assertThat(matchDAO.findAllByPlayerName(playerName)).isEmpty();
        }
        else {
            assertThat(matchDAO.findAllByPlayerName(playerName)).isNotEmpty();
        }
    }
}