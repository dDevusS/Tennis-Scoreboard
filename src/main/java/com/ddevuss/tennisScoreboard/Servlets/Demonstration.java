package com.ddevuss.tennisScoreboard.Servlets;

import com.ddevuss.tennisScoreboard.Services.DemonstrationService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/insertMatches")
public class Demonstration extends HttpServlet {

    private final static DemonstrationService DEMONSTRATION_SERVICE = DemonstrationService.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        DEMONSTRATION_SERVICE.insertSevenEndedMatches();
        String redirectURL = "/Tennis_Scoreboard/matches";
        resp.sendRedirect(redirectURL);
    }
}
