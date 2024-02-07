package com.ddevuss.tennisScoreboard.Servlets;

import com.ddevuss.tennisScoreboard.DTO.CurrentMatch;
import com.ddevuss.tennisScoreboard.Services.MainMatchesService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/new-match")
public class NewMatch extends HttpServlet {

    private final MainMatchesService mainMatchesService = MainMatchesService.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/new-match.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String playerName1 = req.getParameter("player1Name");
        String playerName2 = req.getParameter("player2Name");

        var matchUUID = mainMatchesService.createNewMatch(playerName1, playerName2);
        String redirectURL = "/Tennis_Scoreboard/match-score?uuid=" + matchUUID.toString();

        resp.sendRedirect(redirectURL);
    }
}
