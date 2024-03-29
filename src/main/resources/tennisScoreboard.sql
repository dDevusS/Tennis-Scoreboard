CREATE TABLE IF NOT EXISTS Players
(
    ID
    INT
    PRIMARY
    KEY
    AUTO_INCREMENT,
    Name
    VARCHAR
(
    128
) UNIQUE NOT NULL
    );

CREATE TABLE IF NOT EXISTS Matches
(
    ID
    INT
    PRIMARY
    KEY
    AUTO_INCREMENT,
    Player1
    INT
    NOT
    NULL,
    Player2
    INT
    NOT
    NULL,
    Winner
    INT
    NOT
    NULL,
    FOREIGN
    KEY
(
    Player1
) REFERENCES Players
(
    ID
) ON DELETE CASCADE,
    FOREIGN KEY
(
    Player2
) REFERENCES Players
(
    ID
)
  ON DELETE CASCADE,
    FOREIGN KEY
(
    Winner
) REFERENCES Players
(
    ID
)
  ON DELETE CASCADE
    );

CREATE INDEX IF NOT EXISTS ind_name ON Players (Name);