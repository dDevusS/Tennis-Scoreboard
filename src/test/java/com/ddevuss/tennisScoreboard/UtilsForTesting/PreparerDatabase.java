package com.ddevuss.tennisScoreboard.UtilsForTesting;

import com.ddevuss.tennisScoreboard.utils.DatabaseConnector;

public class PreparerDatabase {

    private static final DatabaseConnector databaseConnector = DatabaseConnector.getINSTANCE();
    private static final String NAME1 = "Alex";
    private static final String NAME2 = "Max";
    private static final String NAME3 = "Yuriy";
    private static final String NAME4 = "Evgeniy";
    private static final String INSERT_PLAYERS = "INSERT INTO PLAYERS (Name) VALUES (:playerName1), (:playerName2)," +
            "(:playerName3), (:playerName4)";
    private static final String DELETE_PLAYERS = "DELETE FROM PLAYERS WHERE ID IN (1, 2, 3, 4)";

    public static void insertPlayersIntoDBBeforeTesting() {
        try (var session = databaseConnector.getSession()) {
            session.beginTransaction();
            var query = session.createNativeMutationQuery(INSERT_PLAYERS);
            query.setParameter("playerName1", NAME1);
            query.setParameter("playerName2", NAME2);
            query.setParameter("playerName3", NAME3);
            query.setParameter("playerName4", NAME4);
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }

    public static void clearTablePlayersAfterTesting() {
        try (var session = databaseConnector.getSession()) {
            session.beginTransaction();
            session.createNativeMutationQuery(DELETE_PLAYERS).executeUpdate();
            session.getTransaction().commit();
        }
    }
}
