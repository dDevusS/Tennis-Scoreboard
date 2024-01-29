package com.ddevuss.tennisScoreboard.DAO;

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
class PlayerDAOTest {

    private static Player player1;
    private static Player player2;
    private final static PlayerDAO playerDAO = new PlayerDAO();

    @BeforeAll
    static void createFields() {
        player1 = Player.of().name("Alex").build();
        player2 = Player.of().name("Max").build();
    }

    static Stream<Player> providePlayers() {
        return Stream.of(player1, player2);
    }

    @ParameterizedTest
    @Order(1)
    @MethodSource("providePlayers")
    @DisplayName("will be true if it returns same player with ID")
    void saveMethodReturnsSamePlayerWithId(Player player) {
        var savedPlayer = playerDAO.save(player);
        assertThat(savedPlayer.getId()).isNotZero();
        assertThat(savedPlayer).isEqualTo(player);
    }

    @ParameterizedTest
    @Order(2)
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
    @Order(2)
    @DisplayName("compare size of players list with number of added players by previous test")
    void getPlayersListWithSizeEqualedOne() {
        var players = playerDAO.findAll();
        assertThat(players).size().isEqualTo(2);
    }

    @ParameterizedTest
    @Order(3)
    @MethodSource("getArgumentsForUpdateTest")
    @DisplayName("will be true if method updated existed player's name")
    void updatedExistedPlayerHasSameFields(String name, Player player) {
        player.setName(name);
        if (!"NonExisted".equalsIgnoreCase(name)) {
            assertThat(playerDAO.update(player)).usingRecursiveComparison().isEqualTo(player);
        }
        else {
            assertThat(playerDAO.update(player)).isNull();
        }
    }

    static Stream<Arguments> getArgumentsForUpdateTest() {
        return Stream.of(
                Arguments.of("Sergey", player1),
                Arguments.of("Ivan", player2),
                Arguments.of("NonExisted", Player.of().id(1000).name("Unknown").build())
        );
    }

    @ParameterizedTest
    @Order(4)
    @ValueSource(ints = {1, 1000})
    @DisplayName("will be true if player with this ID exists in the DB")
    void deleteByExistedIdPlayerReturnTrue(int id) {
        if (id == 1) {
            assertThat(playerDAO.delete(id)).isTrue();
        }
        else {
            assertThat(playerDAO.delete(id)).isFalse();
        }
    }
}