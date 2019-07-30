-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 28. Sep 2018 um 13:05
-- Server-Version: 10.1.34-MariaDB
-- PHP-Version: 7.1.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `benutzer`
--
DROP DATABASE IF EXISTS `benutzer`;
CREATE DATABASE IF NOT EXISTS `benutzer` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `benutzer`;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Adresse`
--

DROP TABLE IF EXISTS `Adresse`;
CREATE TABLE `Adresse` (
  `id` int(11) NOT NULL,
  `vorname` varchar(80) COLLATE utf8_unicode_ci DEFAULT NULL,
  `nachname` varchar(80) COLLATE utf8_unicode_ci DEFAULT NULL,
  `strasse` varchar(120) COLLATE utf8_unicode_ci DEFAULT NULL,
  `plz` varchar(5) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ort` varchar(80) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(80) COLLATE utf8_unicode_ci DEFAULT NULL,
  `geburtstag` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `Adresse`
--

INSERT INTO `Adresse` (`id`, `vorname`, `nachname`, `strasse`, `plz`, `ort`, `email`, `geburtstag`) VALUES
(1, 'Hubert', 'Schimczek', 'An der Biege 37', '10476', 'Berlin', 'hubi@mail.com', '1965-03-26'),
(2, 'Petra', 'Schubert', 'Brakegasse 25', '12473', 'Berlin', 'petschu@online.de', '1983-10-18'),
(3, 'Sandra', 'Bahlert', 'Triftweg 12', '15385', 'Mahlow', 'drinchen94@web.de', '1994-02-08'),
(4, 'Klaus', 'Volmert', 'Kieferallee 57', '16389', 'Senftenberg', 'vollekanne@gmx.net', '1973-09-23'),
(5, 'Luise', 'Pankert', 'Heldenplatz 6', '11739', 'Berlin', 'lieschen@post.com', '1954-06-27'),
(6, 'Werner', 'Lehmke', 'Ahornweg 38', '12947', 'Berlin', 'werner@hotmail.com', '1952-07-12'),
(33, 'Johanna', 'Gelles', 'An der Tanne 73', '02734', 'Gümmelsbach', 'gelle@gmx.de', '1962-10-31'),
(36, 'Albrecht', 'Nordes', 'Plantanenallee 37', '63521', 'Herbstau', 'alnor32@mail.com', '1978-04-25');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `Adresse`
--
ALTER TABLE `Adresse`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `Adresse`
--
ALTER TABLE `Adresse`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
