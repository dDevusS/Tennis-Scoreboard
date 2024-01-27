package com.ddevuss.tennisScoreboard.DAO;

import com.ddevuss.tennisScoreboard.model.Player;
import com.ddevuss.tennisScoreboard.utils.SessionFactory;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class PlayerDAO implements DAOInterface <Player>{

    private final SessionFactory sessionFactory = SessionFactory.getINSTANCE();

    @Override
    public Player save(Player player) {
        try (var session = sessionFactory.getSessionFactory()) {
            session.beginTransaction();
            session.persist(player);
            session.getTransaction().commit();

            return player;
        }
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
