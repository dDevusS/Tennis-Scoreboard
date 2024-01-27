package com.ddevuss.tennisScoreboard.util;

import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class SessionFactory {

    @Getter
    private static final SessionFactory INSTANCE = new SessionFactory();
    private static final Configuration configuration = new Configuration();
    private static final String CREATE_PLAYERS_TABLE = """
                                                CREATE TABLE IF NOT EXISTS Players (
                                                ID INT PRIMARY KEY AUTO_INCREMENT,
                                                Name VARCHAR(128) UNIQUE NOT NULL);
                                                """;
    private static final String CREATE_MATCHES_TABLE = """
                                     CREATE TABLE IF NOT EXISTS Matches (
                                     ID INT PRIMARY KEY AUTO_INCREMENT,
                                     Player1 INT NOT NULL,
                                     Player2 INT NOT NULL,
                                     Winner INT NOT NULL,
                                     FOREIGN KEY (Player1) REFERENCES Players(ID) ON DELETE CASCADE,
                                     FOREIGN KEY (Player2) REFERENCES Players(ID) ON DELETE CASCADE,
                                     FOREIGN KEY (Winner) REFERENCES Players(ID) ON DELETE CASCADE);
                                     """;

    private SessionFactory() {
        configuration.configure();
        try (var session = getSessionFactory()) {
            session.beginTransaction();

            var query = session.createQuery(CREATE_PLAYERS_TABLE + CREATE_MATCHES_TABLE);
            query.executeUpdate();

            session.getTransaction().commit();
        }
    }

    public static Session getSessionFactory() {
        var sessionFactory = configuration.buildSessionFactory();
        return sessionFactory.openSession();
    }

}
