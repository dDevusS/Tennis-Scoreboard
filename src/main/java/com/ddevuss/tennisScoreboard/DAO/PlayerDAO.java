package com.ddevuss.tennisScoreboard.DAO;

import com.ddevuss.tennisScoreboard.model.Player;
import com.ddevuss.tennisScoreboard.utils.DatabaseConnector;
import jakarta.persistence.NonUniqueResultException;

import java.util.List;

public class PlayerDAO implements DAOInterface <Player>, IFindByName<Player> {

    private final DatabaseConnector databaseConnector = DatabaseConnector.getINSTANCE();

    @Override
    public Player save(Player player) {
        try (var session = databaseConnector.getSession()) {
            session.beginTransaction();
            session.persist(player);
            session.getTransaction().commit();

            return player;
        }
    }

    @Override
    public Player findById(Integer id) {
        try (var session = databaseConnector.getSession()) {
            session.beginTransaction();
            var player = session.get(Player.class, id);
            session.getTransaction().commit();

            return player;
        }
    }

    @Override
    public List<Player> findAll() {
        try (var session = databaseConnector.getSession()) {
            session.beginTransaction();
            var query = session.createQuery("from Player", Player.class);
            return query.getResultList();
        }
    }

    @Override
    public Player update(Player player) {
        try (var session = databaseConnector.getSession()) {
            session.beginTransaction();
            var maybePlayer = session.get(Player.class, player.getId());

            if (maybePlayer == null) {
                session.getTransaction().rollback();
                return null;
            }

            var updatedPlayer = session.merge(player);
            session.getTransaction().commit();

            return updatedPlayer;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (var session = databaseConnector.getSession()) {
            session.beginTransaction();
            var player = session.get(Player.class, id);
            if (player == null) {
                session.getTransaction().rollback();
                return false;
            }
            else {
                session.remove(player);
                session.getTransaction().commit();
                return true;
            }
        }
    }

    @Override
    public Player findByName(String name) {
        try (var session = databaseConnector.getSession()) {
            session.beginTransaction();
            var query
                    = session.createQuery("from Player where name = :name", Player.class);
            query.setParameter("name", name);

            try {
                return query.getSingleResult();
            }
            catch (NonUniqueResultException exception) {
                return null;
            }
            finally {
                session.getTransaction().commit();
            }
        }
    }
}
