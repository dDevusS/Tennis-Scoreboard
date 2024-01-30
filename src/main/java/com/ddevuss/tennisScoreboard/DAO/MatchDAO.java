package com.ddevuss.tennisScoreboard.DAO;

import com.ddevuss.tennisScoreboard.model.Match;
import com.ddevuss.tennisScoreboard.model.Player;
import com.ddevuss.tennisScoreboard.utils.DatabaseConnector;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class MatchDAO implements DAOInterface <Match>, IFindAllByPlayerName {

    private final DatabaseConnector databaseConnector = DatabaseConnector.getINSTANCE();
    private static final MatchDAO INSTANCE = new MatchDAO();

    private MatchDAO() {}

    public static MatchDAO getInstance() {
        return INSTANCE;
    }

    @Override
    public Match save(Match match) {
        try (var session = databaseConnector.getSession()) {
            session.beginTransaction();
            addPlayersIfNotExist(session, match);
            session.persist(match);
            session.getTransaction().commit();

            return match;
        }
    }

    private void addPlayersIfNotExist(Session session, Match match) {
        List<Player> players = new ArrayList<>();
        players.add(match.getPlayer1());
        players.add(match.getPlayer2());

        for (Player player: players) {
            var query
                    = session.createQuery("from Player where name = :name", Player.class);
            query.setParameter("name", player.getName());

            try {
                query.getSingleResult();
            }
            catch (NoResultException exception) {
                session.persist(player);
            }
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
            var query = session.createQuery("from Match", Match.class);
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

            addPlayersIfNotExist(session, match);
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

    @Override
    public List<Match> findAllByPlayerName(String playerName) {
        try (var session = databaseConnector.getSession()) {
            String sqlRequest1 = "FROM Player WHERE name = :name";
            String sqlRequest2 = "FROM Match WHERE player1 = :player OR player2 = :player";

            session.beginTransaction();
            var query = session.createQuery(sqlRequest1, Player.class);
            query.setParameter("name", playerName);

            List<Match> matches = new ArrayList<>();
            try {
                var player = query.getSingleResult();
                var query1 = session.createQuery(sqlRequest2, Match.class);
                query1.setParameter("player", player);
                matches = query1.getResultList();

                return matches;
            }
            catch (NoResultException exception) {
                return matches;
            }
            finally {
                session.getTransaction().commit();
            }
        }
    }
}
