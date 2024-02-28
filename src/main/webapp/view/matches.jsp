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
    <script>
        function clearPlayerName() {
            document.getElementById('playerNameInput').value = ''; // Очищаем значение поля ввода
        }
    </script>
</head>
<body>

<header>
    <nav>
        <button class="head-nav-button" onclick="window.location.href='/Tennis-Scoreboard/new-match'">
            Start new match
        </button>
        <button class="head-nav-button" onclick="window.location.href='/Tennis-Scoreboard/insertMatches'">
            Add random matches
        </button>
    </nav>
</header>

<div class="main-container">

    <div class="ended-matches-div">

        <div class="header-table-div">
            <span style="margin-left: 30px">FIRST PLAYER</span>
            <span style="margin-right: 20px">SECOND PLAYER</span>
            <span style="margin-right: 70px">WINNER</span>
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
                    <td style="width: 5%;">
                        <form method="post"
                              action="/Tennis-Scoreboard/matches?page=<%= pageNumber%><%= playerNameParam %>">
                            <input type="hidden" name="matchId" value="<%= match.getId() %>">
                            <button class="delete-button" type="submit">X</button>
                        </form>
                    </td>
                </tr>
                <% } %>
            </table>
        </div>

        <div class="page-search-div">
            <form class="search-field-form" method="get" action="/Tennis-Scoreboard/matches?page=1">
                <div class="page-search-cell" style="margin-right: 2px">
                    <input id="playerNameInput" class="input-field" type="text" name="playerName"
                           placeholder="Player name"
                           value="<%= playerName %>">
                </div>

                <div class="page-search-cell">
                    <button type="submit">?</button>
                </div>

                <div class="page-search-cell" style="margin-right: 2px">
                    <button type="submit" onclick="clearPlayerName()">X</button>
                </div>
            </form>

            <div class="page-turner-div">

                <div class="page-search-cell" style="min-width: 20px">
                    <% if (pageNumber > 1) { %>
                    <button class="back-page-button" type="button"
                            onclick="window.location.href='/Tennis-Scoreboard/matches?page=<%= pageNumber - 1 %><%= playerNameParam %>'"><
                    </button>
                    <% } %>
                </div>

                <div class="page-search-cell" style="min-width: 40px">
                    <span><%= pageNumber %></span>
                </div>

                <div class="page-search-cell" style="min-width: 20px">
                    <% if (pageNumber != lastPageNumber && lastPageNumber != 0) { %>
                    <button class="forward-page-button" type="button"
                            onclick="window.location.href='/Tennis-Scoreboard/matches?page=<%= pageNumber + 1 %><%= playerNameParam %>'">>
                    </button>
                    <% } %>
                </div>

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
