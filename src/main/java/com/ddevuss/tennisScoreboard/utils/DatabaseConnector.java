package com.ddevuss.tennisScoreboard.utils;

import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

public class DatabaseConnector {

    @Getter
    private static final DatabaseConnector INSTANCE = new DatabaseConnector();
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
    private static final String CREATE_INDEX_FOR_PLAYERS_NAME = "CREATE INDEX IF NOT EXISTS ind_name ON Players (Name);";
    private final SessionFactory sessionFactory;

    private DatabaseConnector() {
        this.sessionFactory = buildSessionFactory();
        createSQLRequest(CREATE_PLAYERS_TABLE);
        createSQLRequest(CREATE_MATCHES_TABLE);
        createSQLRequest(CREATE_INDEX_FOR_PLAYERS_NAME);
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

    private void createSQLRequest(String sqlString) {
        try (Session session = getSession()) {
            session.beginTransaction();
            session.createNativeMutationQuery(sqlString).executeUpdate();
            session.getTransaction().commit();
        }
    }

    private SessionFactory buildSessionFactory() {
        try {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .applySetting(Environment.JAKARTA_JDBC_DRIVER, "org.h2.Driver")
                    .applySetting(Environment.JAKARTA_JDBC_URL, "jdbc:h2:mem:TennisMatches")
                    .applySetting(Environment.JAKARTA_JDBC_USER, "username")
                    .applySetting(Environment.JAKARTA_JDBC_PASSWORD, "password")
                    .applySetting(Environment.DIALECT, "org.hibernate.dialect.H2Dialect")
                    .build();

            Metadata metadata = new MetadataSources(standardRegistry)
                    .addAnnotatedClass(com.ddevuss.tennisScoreboard.model.Player.class)
                    .addAnnotatedClass(com.ddevuss.tennisScoreboard.model.Match.class)
                    .getMetadataBuilder()
                    .applyImplicitNamingStrategy(new org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl())
                    .build();

            return metadata.getSessionFactoryBuilder().build();
        }
        catch (Exception e) {
            System.err.println("SessionFactory creation failed: " + e);
            throw new ExceptionInInitializerError(e);
        }
    }

}
