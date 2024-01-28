package com.ddevuss.tennisScoreboard.DAO;

import com.ddevuss.tennisScoreboard.model.Player;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PlayerDAOTest {

    private static Player player1;
    private static PlayerDAO playerDAO;

    @BeforeAll
    static void createDB() {
        player1 = Player.of().name("Alex").build();
        playerDAO = new PlayerDAO();
        }

    @Test
    @Order(1)
    void saveMethodReturnsPlayerWithId() {
        var newPlayer = playerDAO.save(player1);

        assertThat(newPlayer).isNotNull();
        assertThat(player1.getName()).as(newPlayer.getName());
        assertThat(newPlayer.getId()).isNotZero();
    }

    @Test
    @Order(2)
    void findById() {
        Integer correctId = 1;
        Integer incorrectId = 100;

        var player = playerDAO.findById(correctId);
        assertThat(player).isNotNull();

        var mustBeNull = playerDAO.findById(incorrectId);
        assertThat(mustBeNull).isNull();
    }

    @Test
    @Order(3)
    void getPlayersListWithSizeEqualedOne() {
        var players = playerDAO.findAll();
        assertThat(players).size().isEqualTo(1);
    }

    @Test
    @Order(4)
    void updatedPlayerHasSameName() {
        String newName = "Sergey";
        player1.setName(newName);

        boolean isUpdate = playerDAO.update(player1);
        assertThat(isUpdate).isTrue();

        var updatedPlayer = playerDAO.findById(player1.getId());

        assertThat(updatedPlayer.getName()).isEqualTo(newName);
    }

    @Test
    @Order(5)
    void deleteByNonExistedIdPlayerReturnFalse() {
        Integer unrealId = 1000;
        boolean isDeleted = playerDAO.delete(unrealId);
        assertThat(isDeleted).isFalse();
    }

    @Test
    @Order(6)
    void deleteExistedPlayerByIdReturnTrue() {
        boolean isDeleted = playerDAO.delete(player1.getId());
        assertThat(isDeleted).isTrue();
    }

}