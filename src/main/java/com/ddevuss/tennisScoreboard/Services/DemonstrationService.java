package com.ddevuss.tennisScoreboard.Services;

import com.ddevuss.tennisScoreboard.DAO.MatchDAO;
import com.ddevuss.tennisScoreboard.DAO.PlayerDAO;
import com.ddevuss.tennisScoreboard.model.Match;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DemonstrationService {

    private static final Random RANDOM = new Random();
    @Getter
    private static final DemonstrationService INSTANCE = new DemonstrationService();
    private static final List<String> listOfName = getListOfNames();
    private final MatchDAO matchDAO = MatchDAO.getInstance();
    private final PlayerDAO playerDAO = PlayerDAO.getInstance();

    private DemonstrationService() {
    }

    private static List<String> getListOfNames() {
        String listOfNames = """
                Bobby Riggs/
                Billie Jean King/
                Mikael Pernfors/
                Serena Williams/
                Martina Navratilova/
                Hana Mandlikova/
                Stan Smith/
                Gabriela Sabatini/
                Steffi Graf/
                Rod Laver/
                Vic Seixas/
                Chris Evert/
                Arthur Ashe/
                Roger Federer/
                Maria Sharapova/
                Ivan Lendl/
                Mats Wilander/
                Charlotte Cooper/
                Pat Cash/
                Ken Rosewall""";

        return Arrays.stream(listOfNames.split("/")).map(String::trim).toList();
    }

    public void insertSevenEndedMatches() {

        for (int count = 0; count < 7; count++) {
            var playersNames = getPlayersNames();

            Match match = Match.of()
                    .player1(playerDAO.registerPlayerByName(playersNames[0]))
                    .player2(playerDAO.registerPlayerByName(playersNames[1]))
                    .winner(playerDAO.registerPlayerByName(playersNames[RANDOM.nextInt(2)]))
                    .build();

            matchDAO.save(match);
        }
    }

    private String[] getPlayersNames() {
        boolean isDifferenceNames = false;
        String[] playersNames = new String[2];

        while (!isDifferenceNames) {
            playersNames[0] = listOfName.get(RANDOM.nextInt(listOfName.size()));
            playersNames[1] = listOfName.get(RANDOM.nextInt(listOfName.size()));

            if (!playersNames[0].equals(playersNames[1])) {
                isDifferenceNames = true;
            }
        }

        return playersNames;
    }
}
