<%@ page import="java.util.ArrayList" %>
<%@ page import="com.ddevuss.tennisScoreboard.model.Match" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tennis Scoreboard</title>
    <link rel="stylesheet" href="resources/css/main-style.css">
    <link rel="stylesheet" href="resources/css/matches.css">
    <%
        List<Match> matches = (List<Match>) request.getAttribute("matches");
        int lastPageNumber = (Integer) request.getAttribute("lastPage");
        int pageNumber = Integer.parseInt(request.getParameter("page"));
        if (pageNumber == 0) {
            pageNumber = 1;
        }

        int rowCounter = (pageNumber - 1) * 5;
    %>
    <script>
        function clearPlayerName() {
            document.getElementById('playerNameInput').value = ''; // Очищаем значение поля ввода
        }
    </script>
</head>
<body>

<header>
    <nav>
        <button class="head-nav-button" onclick="window.location.href='/Tennis_Scoreboard/new-match'">
            Start new match
        </button>
    </nav>
</header>

<div class="ended-matches-div">

    <div class="header-table-div">
        <h4 style="right: 62%">FIRST PLAYER</h4>
        <h4 style="right: 35%">SECOND PLAYER</h4>
        <h4 style="right: 10%">WINNER</h4>
    </div>

    <div class="table-body-div">
        <table class="matches-table">
            <% for (Match match : matches) { %>
            <tr>
                <td style="width: 5%"><%= ++rowCounter%>
                </td>
                <td style="width: 30%; border: 1px solid rgb(0, 0, 0);"><%= match.getPlayer1().getName() %>
                </td>
                <td style="width: 30%; border: 1px solid rgb(0, 0, 0);"><%= match.getPlayer2().getName() %>
                </td>
                <td style="width: 30%; border: 1px solid rgb(0, 0, 0);"><%= match.getWinner().getName() %>
                </td>
                <td style="width: 5%">
                    <form method="post" action="/Tennis_Scoreboard/matches">
                        <input type="hidden" name="matchId" value="<%= match.getId() %>">
                        <button class="delete-button" type="submit">X</button>
                    </form>
                </td>
            </tr>
            <% } %>
        </table>
    </div>

    <div class="page-search-div">
        <form method="get" action="/Tennis_Scoreboard/matches?page=1">
            <div class="page-search-cell" style="width: 53%; left: 6%">
                <%
                    String playerName = request.getParameter("playerName");
                    String playerNameParam = "&playerName=";
                    if (playerName == null || playerName.isBlank()) {
                        playerName = "";
                        playerNameParam = "";
                    }
                    else {
                        playerNameParam += playerName;
                    }
                %>
                <input id="playerNameInput" class="input-field" type="text" name="playerName" placeholder="Player name"
                       style="width: 100%" value="<%= playerName %>">
            </div>
            <div class="page-search-cell" style="left: 60%">
                <button type="submit" onclick="clearPlayerName()">X</button>
            </div>
            <div class="page-search-cell" style="left: 65%">
                <button type="submit">?</button>
            </div>
        </form>
        <div class="page-search-cell" style="right: 19%">
            <% if (pageNumber > 1) { %>
            <button class="back-page-button" type="button"
                    onclick="window.location.href='/Tennis_Scoreboard/matches?page=<%= pageNumber - 1 %><%= playerNameParam %>'"><
            </button>
            <% } %>
        </div>
        <div class="page-search-cell" style="right: 15%">
            <span><%= pageNumber %></span>
        </div>
        <div class="page-search-cell" style="right: 7%">
            <% if (pageNumber != lastPageNumber && lastPageNumber != 0) { %>
            <button class="forward-page-button" type="button"
                    onclick="window.location.href='/Tennis_Scoreboard/matches?page=<%= pageNumber + 1 %><%= playerNameParam %>'">>
            </button>
            <% } %>
        </div>
    </div>

</div>

<footer>
    <p>
        It is practice project created by <a target="_blank" href="https://github.com/dDevusS">dDevusS.</a>
        <br>
        You can see the technical specifications on
        <a target="_blank" href="https://zhukovsd.github.io/java-backend-learning-course/Projects/TennisScoreboard/">
            the training course
        </a> page made by Sergey Zhukov.
    </p>
</footer>

</body>
</html>
