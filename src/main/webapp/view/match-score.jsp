<%@ page import="com.ddevuss.tennisScoreboard.DTO.CurrentMatch" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tennis Scoreboard</title>
    <link rel="stylesheet" href="resources/css/main-style.css">
    <link rel="stylesheet" href="resources/css/match-score.css">
    <% var match = (CurrentMatch) request.getAttribute("match"); %>
</head>
<body>

<header>
    <nav>
        <button class="head-nav-button" onclick="window.location.href='/Tennis-Scoreboard/new-match'">
            Start new match
        </button>
        <button class="head-nav-button" onclick="window.location.href='/Tennis-Scoreboard/matches'">
            Show ended matches
        </button>
    </nav>
</header>

<div class="main-container">

    <div class="match-special-status-div">
        <% if (match.isDeuce()) { %>
        <span class="status-massage">DEUCE</span>
        <% }
        else if (match.isTieBreak()) { %>
        <span class="status-massage">TIE BREAK</span>
        <% } %>
    </div>

    <div class="scoreboard-div">

        <div class="player-scoreboard-div" id="first-player-scoreboard">

            <div class="player-score-cell-div">
                <h4>SET</h4>
                <h2><%= match.getPlayer1().getSet() %>
                </h2>
            </div>

            <div class="player-score-cell-div">
                <h4>GAME</h4>
                <h2><%= match.getPlayer1().getGame() %>
                </h2>
            </div>

            <div class="player-score-cell-div">
                <h4>POINT</h4>
                <h2><%= match.getPlayer1().getTennisScore(match.isTieBreak()) %>
                </h2>
            </div>

            <div class="player-name-div">
                <h4><%= match.getPlayer1().getName() %>
                </h4>
                <div class="plus-point-button-div">
                    <form method="post">
                        <button type="submit" name="playerId" value=<%= match.getPlayer1().getId() %>>PLUS POINT
                        </button>
                    </form>
                </div>
                <div class="notice-advantage-div">
                    <% if (match.getPlayer1().isAdvantage()) { %>
                    <span style="color: red; font-size: 70%">ADVANTAGE</span>
                    <% } %>
                </div>
            </div>

        </div>

        <div class="player-scoreboard-div" id="second-player-scoreboard">

            <div class="player-name-div">
                <h4><%= match.getPlayer2().getName() %>
                </h4>
                <div class="plus-point-button-div">
                    <form method="post">
                        <button type="submit" name="playerId" value=<%= match.getPlayer2().getId() %>>PLUS POINT
                        </button>
                    </form>
                </div>
                <div class="notice-advantage-div">
                    <% if (match.getPlayer2().isAdvantage()) { %>
                    <span style="color: red; font-size: 70%">ADVANTAGE</span>
                    <% } %>
                </div>
            </div>

            <div class="player-score-cell-div">
                <h4>POINT</h4>
                <h2><%= match.getPlayer2().getTennisScore(match.isTieBreak()) %>
                </h2>
            </div>

            <div class="player-score-cell-div">
                <h4>GAME</h4>
                <h2><%= match.getPlayer2().getGame() %>
                </h2>
            </div>

            <div class="player-score-cell-div">
                <h4>SET</h4>
                <h2><%= match.getPlayer2().getSet() %>
                </h2>
            </div>

        </div>

    </div>

</div>

<footer>
    It is practice project created by <a target="_blank" href="https://github.com/dDevusS">dDevusS.</a>
    <br>
    You can see the technical specifications on
    <a target="_blank" href="https://zhukovsd.github.io/java-backend-learning-course/Projects/TennisScoreboard/">
        the training course
    </a> page made by Sergey Zhukov.
</footer>

</body>
</html>
