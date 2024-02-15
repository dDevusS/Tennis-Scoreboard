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

    private final static int PAGE_SIZE = 5;
    private final MainMatchesService mainMatchesService = MainMatchesService.getINSTANCE();

    public static int getLastPageNumber(int numberAllEndedMatches) {
        if (numberAllEndedMatches % PAGE_SIZE != 0) {
            return numberAllEndedMatches / PAGE_SIZE + 1;
        }
        else {
            return numberAllEndedMatches / PAGE_SIZE;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doRequestWithPagination(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var matchId = Integer.parseInt(req.getParameter("matchId"));
        mainMatchesService.deleteMatchById(matchId);

        doRequestWithPagination(req, resp);
    }

    private void doRequestWithPagination(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Match> requestedMatches = getRequestedMatches(req);

        int lastPageNumber = getLastPageNumber(requestedMatches.size());
        int pageNumber = getPageNumber(req, requestedMatches.size());
        var listMatchForPage = getListMatchForPage(pageNumber, requestedMatches);

        req.setAttribute("lastPage", lastPageNumber);
        req.setAttribute("matches", listMatchForPage);
        req.getRequestDispatcher("/view/matches.jsp?page=" + pageNumber).forward(req, resp);
    }

    private List<Match> getRequestedMatches(HttpServletRequest req) {
        String playerName = req.getParameter("playerName");
        List<Match> requestedMatches;

        if (playerName == null || playerName.isBlank()) {
            requestedMatches = mainMatchesService.getAllEndedMatches();
        }
        else {
            requestedMatches = mainMatchesService.getEndedMatchesByPlayerName(playerName);
        }

        return requestedMatches;
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
}
