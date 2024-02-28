<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Tennis Scoreboard</title>
    <link rel="stylesheet" href="resources/css/reset.css">
    <link rel="stylesheet" href="resources/css/main-style.css">
    <link rel="stylesheet" href="resources/css/new-match.css">

    <script>
        function validateForm() {
            var player1Name = document.forms["start"]["player1Name"].value.trim();
            var player2Name = document.forms["start"]["player2Name"].value.trim();

            if (player1Name === player2Name) {
                document.getElementById("errorMsg").innerText = "Names of players must be different.";
                return false;
            } else if (!player1Name || !player2Name) {
                document.getElementById("errorMsg").innerText = "Names of players must not be empty.";
                return false;
            } else if (player1Name.length > 16 || player2Name.length > 16) {
                document.getElementById("errorMsg").innerText = "Names of players must contain no more than 16 symbols.";
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

<header>
    <nav>
        <button class="head-nav-button" onclick="window.location.href='/Tennis-Scoreboard/matches?page=1'">
            Show ended matches
        </button>
    </nav>
</header>

<div class="main-container">

    <div class="new-match-div">
        <form action="new-match" method="post" name="start" onsubmit="validateForm()">

            <div class="input-fields-div">
                <div id="first-player" class="input-field-div">
                    <h4>FIRST PLAYER</h4>
                    <label>
                        <input class="input-field" type="text" name="player1Name">
                    </label>
                </div>

                <div id="second-player" class="input-field-div">
                    <h4>SECOND PLAYER</h4>
                    <label>
                        <input class="input-field" type="text" name="player2Name">
                    </label>
                </div>
            </div>

            <div id="errorMsg"></div>

            <button class="start-button" type="button" onclick="submitForm()">Start match</button>

        </form>
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
