package com.ddevuss.tennisScoreboard.DAO;

import com.ddevuss.tennisScoreboard.model.Match;
import com.ddevuss.tennisScoreboard.utils.DatabaseConnector;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class MatchDAO implements DAOInterface <Match> {

    private final DatabaseConnector databaseConnector = DatabaseConnector.getINSTANCE();

    @Override
    public Match save(Match match) {
        try (var session = databaseConnector.getSession()) {
            session.beginTransaction();
            session.persist(match);
            session.getTransaction().commit();

            return match;
        }
    }

    @Override
    public Match findById(Integer id) {
        try (var session = databaseConnector.getSession()) {
            session.beginTransaction();
            var match = session.get(Match.class, id);
            session.getTransaction().commit();

            return match;
        }
    }

    @Override
    public List<Match> findAll() {
        try (var session = databaseConnector.getSession()) {
            session.beginTransaction();
            var query = session.createQuery("from Matches", Match.class);
            var resultList = query.getResultList();
            session.getTransaction().commit();

            return resultList;
        }
    }

    @Override
    public Match update(Match match) {
        try (var session = databaseConnector.getSession()) {
            session.beginTransaction();
            var maybeMatch = session.get(Match.class, match.getId());

            if (maybeMatch == null) {
                session.getTransaction().rollback();
                return null;
            }

            var updatedMatch = session.merge(match);
            session.getTransaction().commit();

            return updatedMatch;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (var session = databaseConnector.getSession()) {
            session.beginTransaction();
            var match = session.get(Match.class, id);

            if (match == null) {
                session.getTransaction().rollback();
                return false;
            }

            session.remove(match);
            session.getTransaction().commit();

            return true;
        }
    }
}
