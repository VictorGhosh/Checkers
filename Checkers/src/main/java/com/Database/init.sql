DROP DATABASE IF EXISTS checkersDB;
CREATE DATABASE checkersDB;

USE checkersDB;

CREATE TABLE Player(
    id       INTEGER        AUTO_INCREMENT,
    pName    VARCHAR(255)   NOT NULL,
    pass     VARCHAR(255)   ,
    PRIMARY KEY(id)
);