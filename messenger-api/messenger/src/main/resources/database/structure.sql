CREATE TABLE IF NOT EXISTS message
(
    id        INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    username  VARCHAR(32)        NOT NULL,
    message   TEXT               NOT NULL,
    timestamp DATETIME
);

CREATE TABLE IF NOT EXISTS user
(
    username VARCHAR(32) NOT NULL PRIMARY KEY,
    password VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS config
(
    enable_client BOOLEAN DEFAULT true
);

CREATE TABLE IF NOT EXISTS event
(
    id          INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name        VARCHAR(32)        NOT NULL,
    expiry_time DATETIME
);