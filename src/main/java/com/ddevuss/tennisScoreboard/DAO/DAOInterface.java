package com.ddevuss.tennisScoreboard.DAO;

import com.ddevuss.tennisScoreboard.model.Player;

import java.util.List;
import java.util.Optional;

public interface DAOInterface <Entity> {

    Entity save(Entity entity);

    Player findById(Integer id);

    List<Entity> findAll();

    boolean update(Entity entity);

    boolean delete(Integer id);

}
