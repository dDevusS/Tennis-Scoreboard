package com.ddevuss.tennisScoreboard.Servlets;

import com.ddevuss.tennisScoreboard.DTO.CurrentMatch;
import com.ddevuss.tennisScoreboard.Services.CalculationScoreService;
import com.ddevuss.tennisScoreboard.Services.MainMatchesService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScore extends HttpServlet {

    private final MainMatchesService mainMatchesService = MainMatchesService.getINSTANCE();
    private final Map<UUID, CurrentMatch> currentMatches = mainMatchesService.getCurrentMatches();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuidMatch = UUID.fromString(req.getParameter("uuid"));

        var currentMatch = currentMatches.get(uuidMatch);
        req.setAttribute("match", currentMatch);

        req.getRequestDispatcher("/view/match-score.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuidMatch = UUID.fromString(req.getParameter("uuid"));
        int playerId = Integer.parseInt(req.getParameter("playerId"));

        CalculationScoreService.plusPointToPlayer(uuidMatch, playerId);
        var currentMatch = currentMatches.get(uuidMatch);

        if (currentMatch == null) {
            var allEndedMatches = mainMatchesService.getAllEndedMatches();
            resp.sendRedirect("/Tennis-Scoreboard/matches?page="
                    + Matches.getLastPageNumber(allEndedMatches.size()));
        }
        else {
            req.setAttribute("match", currentMatch);
            req.getRequestDispatcher("/view/match-score.jsp").forward(req, resp);
        }
    }
}
