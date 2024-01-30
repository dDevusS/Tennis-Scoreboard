package com.ddevuss.tennisScoreboard.DAO;

import com.ddevuss.tennisScoreboard.model.Match;
import com.ddevuss.tennisScoreboard.model.Player;
import com.ddevuss.tennisScoreboard.utils.DatabaseConnector;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class MatchDAO implements DAOInterface <Match>, IFindAllByPlayer {

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

    //TODO: Create test
    @Override
    public List<Match> findAllByPlayer(Player player) {
        try (var session = databaseConnector.getSession()) {
            String sqlRequest = "SELECT Matches.ID, Player1, Player2, Winner FROM Matches\n" +
                    "JOIN Players P1 ON Matches.Player1 = P1.ID\n" +
                    "JOIN Players P2 ON Matches.Player2 = P2.ID\n" +
                    "WHERE P1.Name = :name OR P2.Name = :name;";

            session.beginTransaction();
            var query = session.createQuery(sqlRequest, Match.class);
            query.setParameter("name", player.getName());

            var resultList = query.getResultList();
            session.getTransaction().commit();

            return resultList;
        }
    }
}
