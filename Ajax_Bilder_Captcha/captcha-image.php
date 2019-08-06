<?php

session_start();
$sprache = substr($_SERVER['HTTP_ACCEPT_LANGUAGE'], 0, 2); // durch substr(Obj, erster Wert, zweiter Wert) 
														   // werden hier die ersten beiden Buchstaben des Sprachwertes gewonnen
$_SESSION['language'] = $sprache;
include($sprache.'.php'); // es sind nur deutsch und englisch hinterlegt, alles andere scheitert mit 404



$bilder = array(

	array("bild" => 'images/der-verlorene-sohn.jpg',
		  "frage" => FRAGE01, // Beispiel für mehrsprachige Scripte
		  "antwort" => 5),
	array("bild" => 'images/die-anatomie.jpg',
		  "frage" => 'Wieviele lebende Personen sind auf dem Bild abgebildet?',
		  "antwort" => 8),
	array("bild" => 'images/die-badenden.jpg',
		  "frage" => 'Wieviele Damen sind hier am Baden?',
		  "antwort" => 5),
	array("bild" => 'images/die-vorsteher.jpg',
		  "frage" => 'Wieviele Personen sehen Sie auf diesem Bild?',
		  "antwort" => 5),
	array("bild" => 'images/las-meninas.jpg',
		  "frage" => 'Wieviele Personen sind auf diesem Bild zu sehen?',
		  "antwort" => 8),
	array("bild" => 'images/last-supper.jpg',
		  "frage" => 'Wieviele Fenster sind hier zu sehen',
		  "antwort" => 3),	  
	array("bild" => 'images/nicolas-poussin.jpg',
		  "frage" => 'Wieviele gemalte Personen sind auf diesem Bild zu sehen?',
		  "antwort" => 1),
	array("bild" => 'images/die-judenbraut.jpg',
		  "frage" => 'Wieviele Personen sind auf diesem Bild zu sehen?',
		  "antwort" => 2),
);


$random = rand(0, count($bilder)-1);
$bild = $bilder[$random];

$_SESSION['random'] = $random;
$_SESSION['bild'] = $bild['bild'];
$_SESSION['frage'] = $bild['frage'];
$_SESSION['antwort'] = $bild['antwort'];

$json = json_encode($bild);

