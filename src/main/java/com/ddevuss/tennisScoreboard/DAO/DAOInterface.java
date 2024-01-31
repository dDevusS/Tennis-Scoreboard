package com.ddevuss.tennisScoreboard.DAO;

import java.util.List;

public interface DAOInterface<Entity> {

    Entity save(Entity entity);

    Entity findById(Integer id);

    List<Entity> findAll();

    Entity update(Entity entity);

    boolean delete(Integer id);

}
