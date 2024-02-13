package com.ddevuss.tennisScoreboard.Servlets;

import com.ddevuss.tennisScoreboard.Services.MainMatchesService;
import com.ddevuss.tennisScoreboard.model.Match;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/matches")
public class Matches extends HttpServlet {

    private final MainMatchesService mainMatchesService = MainMatchesService.getINSTANCE();
    private final static int PAGE_SIZE = 5;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String playerName = req.getParameter("playerName");
        List<Match> allEndedMatches;

        if (playerName == null || playerName.isBlank()) {
            allEndedMatches = mainMatchesService.getAllEndedMatches();
        }
        else {
            allEndedMatches = mainMatchesService.getEndedMatchesByPlayerName(playerName);
        }

        doRequestWithPagination(req, resp, allEndedMatches);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var matchId = Integer.parseInt(req.getParameter("matchId"));
        mainMatchesService.deleteMatchById(matchId);
        var allEndedMatches = mainMatchesService.getAllEndedMatches();

        doRequestWithPagination(req, resp, allEndedMatches);
    }

    private void doRequestWithPagination(HttpServletRequest req, HttpServletResponse resp, List<Match> allEndedMatches)
            throws ServletException, IOException {
        int lastPageNumber = getLastPageNumber(allEndedMatches.size());
        int pageNumber = getPageNumber(req, allEndedMatches.size());
        var listMatchForPage = getListMatchForPage(pageNumber, allEndedMatches);

        req.setAttribute("lastPage", lastPageNumber);
        req.setAttribute("matches", listMatchForPage);
        req.getRequestDispatcher("/view/matches.jsp?page=" + pageNumber).forward(req, resp);
    }

    private List<Match> getListMatchForPage(int pageNumber, List<Match> allEndedMatches) {
        if (allEndedMatches.isEmpty()) {
            return allEndedMatches;
        }

        int firstIndex = (pageNumber - 1) * PAGE_SIZE;
        int secondIndex = firstIndex + PAGE_SIZE;

        if (secondIndex > allEndedMatches.size()) {
            secondIndex = allEndedMatches.size();
        }

        return allEndedMatches.subList(firstIndex, secondIndex);
    }

    private int getPageNumber(HttpServletRequest req, int numberAllEndedMatches) {
        if (req.getParameter("page") == null) {
            return 1;
        }
        else {
            int page = Integer.parseInt(req.getParameter("page"));
            int lastPage = getLastPageNumber(numberAllEndedMatches);

            if (page <= 0) {
                return 1;
            }

            return Math.min(page, lastPage);
        }
    }

    public static int getLastPageNumber(int numberAllEndedMatches) {
        if (numberAllEndedMatches % PAGE_SIZE != 0) {
            return numberAllEndedMatches / PAGE_SIZE + 1;
        }
        else {
            return numberAllEndedMatches / PAGE_SIZE;
        }
    }
}
