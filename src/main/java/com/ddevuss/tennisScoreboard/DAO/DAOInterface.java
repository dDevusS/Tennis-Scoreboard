package com.ddevuss.tennisScoreboard.DAO;

import java.util.List;
import java.util.Optional;

public interface DAOInterface <Entity> {

    Entity save(Entity entity);

    Optional<Entity> findById(Integer id);

    List<Entity> findAll();

    void update(Entity entity);

    boolean delete(Integer id);

}
