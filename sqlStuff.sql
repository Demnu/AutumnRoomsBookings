USE [master];
GO

DROP DATABASE [SENG2050_assign3];
GO

CREATE DATABASE [SENG2050_assign3];
USE [SENG2050_assign3];

DROP LOGIN [jdbcUser];
GO

Drop USER [jdbcUser];

CREATE LOGIN [jdbcUser]
WITH PASSWORD='mySecur3Passw0rd!'
GO

CREATE USER [jdbcUser] FOR LOGIN jdbcUser;
GO

GRANT SELECT, INSERT, UPDATE, DELETE
    TO jdbcUser;

Drop TABLE [Users];

CREATE TABLE [Users] (
    [userName] VARCHAR(80) PRIMARY KEY NOT NULL,
    [password] VARCHAR(80) NOT NULL,
    [firstName]  VARCHAR(80) NOT NULL,
    [lastName] VARCHAR(80) NOT NULL,
    [email] VARCHAR(80) NOT NULL,
    [phoneNumber] VARCHAR(14) NOT NULL
    );

DROP TABLE [Roles];

CREATE TABLE [Roles] (
    [userName] VARCHAR(80) FOREIGN KEY references [Users],
    role VARCHAR(8) NOT NULL
    );
DROP TABLE [Issue];

CREATE TABLE [Issue] (
    [issueID] INT NOT NULL PRIMARY KEY IDENTITY,
    [userName] VARCHAR(80) NOT NULL FOREIGN KEY REFERENCES [Users],
    [state] VARCHAR(64) NOT NULL,
    [category] VARCHAR(64) NOT NULL,
    [title] VARCHAR (80) NOT NULL,
    [annonymousUser] BIT NOT NULL,
    [issueDescription] VARCHAR(500) NOT NULL,
    [resolutionDetails] VARCHAR(500),
    [dateResolved] DATE,
    [dateReported] DATE NOT NULL,
    [dateLastUpdated] DATETIME
    )
DROP TABLE [Comment];

CREATE TABLE [Comment] (
    [commentID] INT NOT NULL PRIMARY KEY IDENTITY,
    [issueID] INT NOT NULL FOREIGN KEY REFERENCES [Issue],
    [userName] VARCHAR(80) NOT NULL FOREIGN KEY REFERENCES [Users],
    [comment] VARCHAR(500) NOT NULL,
    [dateMade] DATE NOT NULL,
    [internalComment] BIT NOT NULL
    );


CREATE TABLE [Maintainence] (
    [maintainenceID] INT NOT NULL PRIMARY KEY IDENTITY,
    [userName] VARCHAR(80) NOT NULL FOREIGN KEY REFERENCES [Users],
    [startDate] DATE NOT NULL,
    [endDate] DATE NOT NULL,
    [comment] VARCHAR(500) NOT NULL,
    [title] VARCHAR(80) NOT NULL,
    );

INSERT INTO Users VALUES ('admin', 'd033e22ae348aeb5660fc2140aec35850c4da997', 'admin', 'admin', 'admin@admin.com', 'adminphonelmao');
INSERT INTO Users VALUES ('user', '12dea96fec20593566ab75692c9949596833adc9', 'user', 'user', 'user@user.com', 'userphonenumXD');
INSERT INTO Roles VALUES ('admin', 'staff');
INSERT INTO Roles VALUES ('user', 'user');

INSERT INTO Comment VALUES (2, 'user','Test','02-01-2021');

SELECT * FROM Users;
SELECT * FROM Roles;
SELECT * FROM Comment;
SELECT * FROM Issue;
SELECT * FROM Roles;
SELECT * FROM Maintainence;