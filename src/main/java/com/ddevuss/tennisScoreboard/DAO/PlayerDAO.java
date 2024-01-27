package com.ddevuss.tennisScoreboard.DAO;

import com.ddevuss.tennisScoreboard.model.Player;

import java.util.List;
import java.util.Optional;

public class PlayerDAO implements DAOInterface <Player>{

    @Override
    public Player save(Player player) {
        return null;
    }

    @Override
    public Optional<Player> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Player> findAll() {
        return null;
    }

    @Override
    public void update(Player player) {

    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}
