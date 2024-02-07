<%@ page import="java.util.ArrayList" %>
<%@ page import="com.ddevuss.tennisScoreboard.model.Match" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: dDevusS
  Date: 2024-02-06
  Time: 9:44 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tennis Scoreboard</title>
</head>
<body>
    <%
        List<Match> matches = (ArrayList<Match>) request.getAttribute("matches");
        int rowCounter = 1;
    %>
    <% for (Match match : matches) { %>
<table border=<%= rowCounter %>>
    <tr>
        <th rowspan="2"><%= rowCounter++ %></th><th>First player</th><th>Second player</th><th>Winner</th>
    </tr>
    <tr>
        <td><%= match.getPlayer1().getName() %></td>
        <td><%= match.getPlayer2().getName() %></td>
        <td><%= match.getWinner().getName() %></td>
    </tr>
</table>
<% } %>
</body>
</html>
