DROP DATABASE IF EXISTS checkersDB;
CREATE DATABASE checkersDB;

USE checkersDB;

CREATE TABLE Player(
    Id       INTEGER        NOT NULL,
    PName    VARCHAR(32)    NOT NULL,
    Pass     VARCHAR(32)            ,
    PRIMARY KEY(Id)
);