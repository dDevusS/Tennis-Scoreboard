<%@ page import="com.ddevuss.tennisScoreboard.DTO.CurrentMatch" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tennis Scoreboard</title>
</head>
<body>
<table border="1">
  <caption>Current matches</caption>
  <tr>
    <th colspan="4">First player</th><th colspan="4">Second player</th>
  </tr>
  <% var match = (CurrentMatch) request.getAttribute("match"); %>
  <tr>
    <th colspan="4"><%= match.getPlayer1().getName() %></th>
    <th colspan="4"><%= match.getPlayer2().getName() %></th>
  </tr>
    <tr>
        <th><%= match.getPlayer1().getSet() %></th>
        <th><%= match.getPlayer1().getGame() %></th>
        <th><%= match.getPlayer1().getTennisScore(match.isTieBreak()) %></th>
        <% if (match.getPlayer1().isAdvantage()) { %>
        <td>Advantage</td>
        <% } else if (match.isDeuce()) { %>
        <td>---------</td>
        <% } else { %>
        <th></th>
        <% } %>
        <th><%= match.getPlayer2().getSet() %></th>
        <th><%= match.getPlayer2().getGame() %></th>
        <th><%= match.getPlayer2().getTennisScore(match.isTieBreak()) %></th>
        <% if (match.getPlayer2().isAdvantage()) { %>
        <td>Advantage</td>
        <% } else if (match.isDeuce()) { %>
        <td>---------</td>
        <% } else { %>
        <th></th>
        <% } %>
    </tr>
    <tr>
        <th colspan="4">
            <form method="post">
            <button type="submit" name="playerId" value=<%= match.getPlayer1().getId() %>>First player won a point</button>
            </form>
        </th>
        <th colspan="4">
            <form method="post">
            <button type="submit" name="playerId" value=<%= match.getPlayer2().getId() %>>Second player won a point</button>
            </form>
        </th>
    </tr>
</table>
</body>
</html>
