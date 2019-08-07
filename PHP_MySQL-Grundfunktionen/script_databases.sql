
DROP DATABASE IF EXISTS benutzer;
CREATE DATABASE benutzer; 

USE benutzer;

DROP TABLE IF EXISTS Adresse;
CREATE TABLE Adresse (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	vorname VARCHAR(80),
	nachname VARCHAR(80),
	strasse VARCHAR(120),
	plz VARCHAR(5),
	ort VARCHAR(80),
	email VARCHAR(80),
	geburtstag DATE
);

DESCRIBE Adresse;


INSERT INTO Adresse
(vorname, nachname, strasse, plz, ort, email, geburtstag)
VALUES
("Hubert", "Schimczek", "An der Biege 37", 10476, "Berlin", "hubi@mail.com", "1965-03-26"),
("Petra", "Schubert", "Brakegasse 23", 12473, "Berlin", "petschu@online.de", "1983-10-18"),
("Sandra", "Bahlert", "Triftweg 11", 15385, "Mahlow", "drinchen94@web.de", "1994-02-08"),
("Klaus", "Volmert", "Kieferallee 57", 16389, "Senftenberg", "vollekanne@gmx.net", "1973-09-23"),
("Luise", "Pankert", "Heldenplatz 6", 11739, "Berlin", "lieschen@post.com", "1954-06-27");


SELECT id, vorname, nachname, strasse, plz, ort, email, geburtstag
FROM Adresse
ORDER BY nachname;
