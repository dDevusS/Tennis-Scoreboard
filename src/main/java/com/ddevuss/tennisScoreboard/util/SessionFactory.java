package com.ddevuss.tennisScoreboard.util;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;

public class SessionFactory {

    @Getter
    private final SessionFactory INSTANCE = new SessionFactory();
    private final Configuration configuration = new Configuration();
    private final String CREATE_PLAYERS_TABLE = """
                                                CREATE TABLE IF NOT EXISTS Players (
                                                ID INT PRIMARY KEY AUTO_INCREMENT,
                                                Name VARCHAR(128) UNIQUE NOT NULL);
                                                """;
    private final String CREATE_MATCHES_TABLE = """
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

    public Session getSessionFactory() {
        var sessionFactory = configuration.buildSessionFactory();
        return sessionFactory.openSession();
    }

}
