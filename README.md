# Tennis Match Scoreboard Project

This project focuses on scoring tennis matches and storing information about completed matches. It was developed for educational purposes as part of the [Java Backend Learning Course](https://zhukovsd.github.io/java-backend-learning-course/) by Sergey Zhukov.

**You can check in this application on:**
```
http://45.95.203.87:9090/Tennis-Scoreboard/

```

## Application Features:

**Match Operations:**
  - Creating a new match
  - Viewing completed matches and searching by player names
  - Scoring points in the current match

**Tennis Match Scoring:**
  - Matches are played until two sets are won (best of 3)
  - Tie-break is played in case of a 6/6 score in a set, until one player reaches 7 points

#### Interface:

**Home Page:**
  - URL: Tennis-Scoreboard/
  - Provides links to the new match page and the list of completed matches

**New Match Page:**
  - URL: Tennis-Scoreboard/new-match
  - HTML form with fields "First player", "Second player", and a "Start match" button
  - Initiating the "Start match" button triggers a POST request to Tennis-Scoreboard/match-score

**Match Score Page:**
  - URL: Tennis-Scoreboard/match-score
  - Displays the current score of the match
  - Provides buttons for adding points to one of the players
  - After completing the match, the application redirects to the page with completed matches at Tennis-Scoreboard/matches
    
**Ended Matches Page:**
  - URL: Tennis-Scoreboard/matches
  - Displays information about all completed matches, including player names and the winner's name
  - Includes a search bar for finding matches by player name
  - The application allows adding a list of random completed matches for demonstration purposes

## Project Overview:

The project aims to provide a practical learning experience in Java backend development, focusing on:

1. **Technologies Used:**
   - Java: Collections, Object-Oriented Programming (OOP)
   - Design Pattern: MVC(S)
   - Build Tools: Gradle
   - Java Servlets
   - JavaServer Pages (JSP)
   - Databases: SQL, Hibernate, H2 (in-memory SQL database)
   - Frontend: HTML/CSS, block layout
   - Testing: Unit testing with JUnit 5
   - Deployment: Docker for containerization, with bash scripts for container management and image creation

2. **Motivation:**
   - Creating a client-server application with a web interface
   - Gaining practical experience with ORM using Hibernate
   - Designing a simple web interface without external libraries
   - Understanding the MVC(S) architectural pattern

3. **Project Limitations:**
   - The project does not involve frameworks to focus on practicing the MVC pattern; Spring Boot integration starts from project #6
   - Bootstrap is not utilized for layout to practice manual HTML/CSS; Bootstrap can be introduced in project #5
   - As the project is single-user, sessions are not implemented

## Installation and Setup Instructions:

To run this project locally, follow these steps:

1. **Download the installation script:**
   - Download the install_tennis_scoreboard.sh script from the project directory.

2. **Grant execute permissions:**
   ```bash
   chmod +x install_tennis_scoreboard.sh

3. **Run the installation script:**
   ```bash
   sh install_tennis_scoreboard.sh
   ```
   - If access to the remote repository is restricted, manually download the project from GitHub. Then, grant execute permissions to the manager_container_tennis_scoreboard.sh script and run the script: 
   ```bash
   chmod +x manager_container_tennis_scoreboard.sh
   sh manager_container_tennis_scoreboard.sh install
   ```

4. **Start the application:**
   - Execute the following command:
   ```bash
   sh manager_container_tennis_scoreboard.sh run
   ```
   - Application will be access on:
   ```
   http://localhost:8080/Tennis-Scoreboard/
   ```

### Additional Options:

For additional help use:
```bash
sh manager_container_tennis_scoreboard.sh --help
```


