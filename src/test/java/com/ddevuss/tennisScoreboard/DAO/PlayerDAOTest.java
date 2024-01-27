package com.ddevuss.tennisScoreboard.DAO;

import com.ddevuss.tennisScoreboard.model.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PlayerDAOTest {

    private static Player player1;
    private static Player player2;

    @BeforeAll
    static void createDB() {
        player1 = Player.of().name("Alex").build();
        player1 = Player.of().name("Timur").build();
        }

    @Test
    void saveMethodReturnsPlayerWithId() {
        PlayerDAO playerDAO = new PlayerDAO();
        var newPlayer = playerDAO.save(player1);

        assertThat(newPlayer).isNotNull();
        assertThat(player1.getName()).as(newPlayer.getName());
        assertThat(newPlayer.getId()).isNotZero();
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}