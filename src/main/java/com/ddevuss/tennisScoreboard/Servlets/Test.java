package com.ddevuss.tennisScoreboard.Servlets;

import com.ddevuss.tennisScoreboard.DAO.MatchDAO;
import com.ddevuss.tennisScoreboard.DAO.PlayerDAO;
import com.ddevuss.tennisScoreboard.model.Match;
import com.ddevuss.tennisScoreboard.model.Player;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//TODO: Переделать метод с возможность взаимодействия с пользователем

@WebServlet("/test")
public class Test extends HttpServlet {

    private final static MatchDAO MATCH_DAO = MatchDAO.getInstance();
    private final static PlayerDAO PLAYER_DAO = PlayerDAO.getInstance();
    private final static Player PLAYER_1 = Player.of().name("PLAYER1").build();
    private final static Player PLAYER_2 = Player.of().name("PLAYER2").build();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        saveSixteenNewMatches();
        String redirectURL = "/Tennis_Scoreboard/matches";
        resp.sendRedirect(redirectURL);
    }

    private static void saveSixteenNewMatches() {
        PLAYER_DAO.save(PLAYER_1);
        PLAYER_DAO.save(PLAYER_2);

        for (int counter = 16; counter > 0; counter--) {
            Match match = Match.of().player1(PLAYER_1).player2(PLAYER_2).winner(PLAYER_1).build();
            MATCH_DAO.save(match);
        }
    }
}
