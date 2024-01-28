package com.ddevuss.tennisScoreboard.DAO;

import com.ddevuss.tennisScoreboard.model.Player;

import java.util.List;
import java.util.Optional;

public interface DAOInterface <Entity> {

    Entity save(Entity entity);

    Entity findById(Integer id);

    List<Entity> findAll();

    Entity update(Entity entity);

    boolean delete(Integer id);

}
