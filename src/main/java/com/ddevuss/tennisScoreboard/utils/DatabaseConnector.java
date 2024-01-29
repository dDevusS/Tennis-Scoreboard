package com.ddevuss.tennisScoreboard.utils;

import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class DatabaseConnector {

    @Getter
    private static final DatabaseConnector INSTANCE = new DatabaseConnector();
    private final Configuration configuration = new Configuration().configure();
    private static final String CREATE_PLAYERS_TABLE = "CREATE TABLE IF NOT EXISTS Players " +
                                                       "(ID INT PRIMARY KEY AUTO_INCREMENT," +
                                                       "Name VARCHAR(128) UNIQUE NOT NULL);";
    private static final String CREATE_MATCHES_TABLE = "CREATE TABLE IF NOT EXISTS Matches " +
                                                       "(ID INT PRIMARY KEY AUTO_INCREMENT," +
                                                       "Player1 INT NOT NULL," +
                                                       "Player2 INT NOT NULL," +
                                                       "Winner INT NOT NULL," +
                                                       "FOREIGN KEY (Player1) REFERENCES Players(ID) ON DELETE CASCADE," +
                                                       "FOREIGN KEY (Player2) REFERENCES Players(ID) ON DELETE CASCADE," +
                                                       "FOREIGN KEY (Winner) REFERENCES Players(ID) ON DELETE CASCADE);";
    private static final String CREATE_INDEX_FOR_PLAYERS_NAME = "CREATE INDEX ind_name ON Players (Name);";

    private DatabaseConnector() {
        createSQLRequest(CREATE_PLAYERS_TABLE);
        createSQLRequest(CREATE_INDEX_FOR_PLAYERS_NAME);
        createSQLRequest(CREATE_MATCHES_TABLE);
    }

    public Session getSession() {
        var sessionFactory = configuration.buildSessionFactory();
        return sessionFactory.openSession();
    }

    private void createSQLRequest(String sqlString) {
        try (var session = getSession()) {
            session.beginTransaction();
            session.createNativeMutationQuery(sqlString).executeUpdate();
            session.getTransaction().commit();
        }
    }

}
