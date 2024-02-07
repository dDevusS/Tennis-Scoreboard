package com.ddevuss.tennisScoreboard.Servlets;

import com.ddevuss.tennisScoreboard.Services.MainMatchesService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/matches")
public class Matches extends HttpServlet {

    private final MainMatchesService mainMatchesService = MainMatchesService.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var allEndedMatches = mainMatchesService.getAllEndedMatches();

        req.setAttribute("matches", allEndedMatches);
        req.getRequestDispatcher("/view/matches.jsp").forward(req, resp);
    }
}
