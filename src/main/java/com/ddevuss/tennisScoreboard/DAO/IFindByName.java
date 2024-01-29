package com.ddevuss.tennisScoreboard.DAO;

public interface IFindByName<Entity> {

    Entity findByName(String name);
}
