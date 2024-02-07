<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tennis Scoreboard</title>
    <script>
        function validateForm() {
            var player1Name = document.forms["start"]["player1Name"].value;
            var player2Name = document.forms["start"]["player2Name"].value;

            if (player1Name === player2Name) {
                document.getElementById("errorMsg").innerText = "Names of players must be different.";
                return false;
            } else {
                document.getElementById("errorMsg").innerText = "";
                return true;
            }
        }

        function submitForm() {
            if (validateForm()) {
                document.forms["start"].submit();
            }
        }
    </script>
</head>
<body>
    <form action="new-match" method="post" name="start" onsubmit="validateForm()">
        <table>
            <tr>
                <th>Name of first player</th>
                <th>Name of second player</th>
            </tr>
            <tr>
                <td>
                    <label>
                        <input type="text" name="player1Name">
                    </label>
                </td>
                <td>
                    <label>
                        <input type="text" name="player2Name">
                    </label>
                </td>
            </tr>
        </table>
        <div id="errorMsg" style="color: red;"></div>
        <br>
        <br>
        <button type="button" onclick="submitForm()">Start match</button>
    </form>
</body>
</html>
