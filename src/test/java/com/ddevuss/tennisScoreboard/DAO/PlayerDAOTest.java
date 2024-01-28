package com.ddevuss.tennisScoreboard.DAO;

import com.ddevuss.tennisScoreboard.model.Player;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("DAOImplementedClass")
class PlayerDAOTest {

    private static Player player1;
    private static Player player2;
    private static PlayerDAO playerDAO;

    @BeforeAll
    static void createDB() {
        player1 = Player.of().name("Alex").build();
        player2 = Player.of().name("Max").build();
        playerDAO = new PlayerDAO();
        }

    @Test
    @Order(1)
    @DisplayName("will be true if it returns same player with ID")
    void saveMethodReturnsSamePlayerWithId() {
        var newPlayer = playerDAO.save(player1);
        playerDAO.save(player2);

        assertThat(newPlayer.getId()).isNotZero();
        assertThat(newPlayer).isEqualTo(player1);
    }

    @Nested
    @Order(2)
    class FindByIdTest {

        @Test
        @DisplayName("will be true if player exists with this ID")
        void returnPlayerClassWithCorrectId() {
            Integer correctId = 1;
            var player = playerDAO.findById(correctId);
            assertThat(player).isInstanceOf(Player.class);
        }

        @Test
        @DisplayName("will be false if player doesn't exist with this ID")
        void returnNullWithIncorrectId() {
            Integer incorrectId = 100;
            var mustBeNull = playerDAO.findById(incorrectId);
            assertThat(mustBeNull).isNull();
        }

    }

    @Test
    @Order(3)
    @DisplayName("Compare size of players list with number of added players by previous test")
    void getPlayersListWithSizeEqualedOne() {
        var players = playerDAO.findAll();
        assertThat(players).size().isEqualTo(2);
    }

    @Test
    @Order(4)
    @DisplayName("will be true if method returns updated name")
    void updatedPlayerHasSameName() {
        String newName = "Sergey";
        player1.setName(newName);

        boolean isUpdate = playerDAO.update(player1);
        assertThat(isUpdate).isTrue();

        var updatedPlayer = playerDAO.findById(player1.getId());

        assertThat(updatedPlayer.getName()).isEqualTo(newName);
    }

    @Nested
    @Order(5)
    class DeleteTest {

        @Test
        @DisplayName("will be false if player with this ID doesn't exist in the DB")
        void deleteByNonExistedIdPlayerReturnFalse() {
            Integer unrealId = 1000;
            boolean isDeleted = playerDAO.delete(unrealId);
            assertThat(isDeleted).isFalse();
        }

        @Test
        @DisplayName("will be true if player with this ID exists in the DB")
        void deleteExistedPlayerByIdReturnTrue() {
            boolean isDeleted = playerDAO.delete(player2.getId());
            assertThat(isDeleted).isTrue();
        }

    }

}