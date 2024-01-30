package com.ddevuss.tennisScoreboard.DAO;

import com.ddevuss.tennisScoreboard.model.Player;
import com.ddevuss.tennisScoreboard.UtilsForTesting.PreparerDatabase;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("DAOImplementedClass")
class PlayerDAOTest {

    private static Player player1;
    private static Player player2;
    private final static PlayerDAO playerDAO = PlayerDAO.getInstance();

    @BeforeAll
    static void createFieldsAndPrepareDB() {
        player1 = Player.of().name("Roman").build();
        player2 = Player.of().name("Egor").build();
        PreparerDatabase.insertPlayersIntoDBBeforeTesting();
    }

    static Stream<Player> providePlayers() {
        return Stream.of(player1, player2);
    }

    @ParameterizedTest
    @MethodSource("providePlayers")
    @DisplayName("will be true if it returns same player with ID")
    void saveMethodReturnsSamePlayerWithId(Player player) {
        var savedPlayer = playerDAO.save(player);
        assertThat(savedPlayer.getId()).isNotZero();
        assertThat(savedPlayer).isEqualTo(player);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 1000})
    @DisplayName("will be true if player exists with this ID")
    void findPlayerClassWithCorrectId(int id) {
        if (id == 1) {
            var player = playerDAO.findById(id);
            assertThat(player).isInstanceOf(Player.class);
        }
        else {
            var mustBeNull = playerDAO.findById(id);
            assertThat(mustBeNull).isNull();
        }
    }

    @Test
    @DisplayName("compare size of players list with number of added players by previous test")
    void getPlayersListWithSizeEqualedOne() {
        var players = playerDAO.findAll();
        assertThat(players).isNotEmpty();
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForUpdateTest")
    @DisplayName("will be true if method updated existed player's name")
    void updatedExistedPlayerHasSameFields(Player player) {
        if (!"NonExisted".equalsIgnoreCase(player.getName())) {
            assertThat(playerDAO.update(player)).usingRecursiveComparison().isEqualTo(player);
        }
        else {
            assertThat(playerDAO.update(player)).isNull();
        }
    }

    static Stream<Arguments> getArgumentsForUpdateTest() {
        return Stream.of(
                Arguments.of(Player.of().id(1).name("Sergey").build()),
                Arguments.of(Player.of().id(2).name("Ivan").build()),
                Arguments.of(Player.of().id(1000).name("NonExisted").build())
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 1000})
    @DisplayName("will be true if player with this ID exists in the DB")
    void deleteByExistedIdPlayerReturnTrue(int id) {
        if (id == 3) {
            assertThat(playerDAO.delete(id)).isTrue();
        }
        else {
            assertThat(playerDAO.delete(id)).isFalse();
        }
    }

    @Test
    @DisplayName("will be true if method returns existed player from the DB")
    void registerPlayerReturnExistedPlayer() {
        var player = playerDAO.registerPlayerByName(player1.getName());
        assertThat(player.getId()).isNotZero();
        assertThat(player.getName()).isEqualTo(player1.getName());
    }

    @Test
    @DisplayName("if player doesn't exist method creates and returns new player with this name")
    void registerPlayerReturnCreatedPlayerIfNotExist() {
        String newPlayerName = "NewPlayer";
        var newPlayer = playerDAO.registerPlayerByName(newPlayerName);
        assertThat(newPlayer.getId()).isNotZero();
        assertThat(newPlayer.getName()).isEqualTo(newPlayerName);
    }

    @AfterAll
    static void clearDB() {
        PreparerDatabase.clearTablePlayersAfterTesting();
    }
}