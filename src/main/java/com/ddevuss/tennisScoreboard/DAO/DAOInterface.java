package com.ddevuss.tennisScoreboard.DAO;

import java.util.List;

public interface DAOInterface <Entity> {

    void save(Entity entity);

    Entity findById(Integer id);

    List<Entity> findAll();

    void update(Entity entity);

    void delete(Integer id);

}
