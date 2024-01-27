package com.ddevuss.tennisScoreboard.utils;

import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class SessionFactory {

    @Getter
    private static final SessionFactory INSTANCE = new SessionFactory();
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

    private SessionFactory() {
        try (var session = getSessionFactory()) {
            session.beginTransaction();

            session.createNativeMutationQuery(CREATE_PLAYERS_TABLE).executeUpdate();
            session.createNativeMutationQuery(CREATE_MATCHES_TABLE).executeUpdate();

            session.getTransaction().commit();
        }
    }

    public Session getSessionFactory() {
        var sessionFactory = configuration.buildSessionFactory();
        return sessionFactory.openSession();
    }

}
