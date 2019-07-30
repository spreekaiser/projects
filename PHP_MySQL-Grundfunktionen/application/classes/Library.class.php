<?php

#####   C:\xampp\htdocs\php\php_Projekt_Barth\application\classes\Library.class.php   #####


// stellt statische Methoden zur Verfügung
// darf nicht instanziiert werden
// abstrakte Klasse kann nicht instanziiert werden#

abstract class Library {
	
	private function __construct(){
		
	}
	
	
	public static function rand($min, $max){
		return mt_rand($min, $max);
	}
	
	public static function getRandomNumbers($min, $max, $count, $unique = true, $sort = true){
		$numbers = [];
		
		$count = min($max, $count); // Funktion min() ermittelt von übergebenen Werten den kleinsten Wert
		
		
		while (count($numbers) < $count){
			$number = Library::rand($min, $max);
			if( ! in_array($number, $numbers) OR $unique == false ) {  // Funktion in_array prüft ob eine Variable bereits 
				$numbers[] = $number;              // in einem array vorhanden ist
			}
		}
		if($sort == true){
			sort($numbers);  // Funktion sort() sortiert Werte eines arrays der Größe nach
		}
		
		return $numbers;
	}
	
	
		#############     ViewHelper       ##############
	
	public static function numFormat($value){
		
		return number_format($value, 2, ',', '.');
		
		
	}
	
		#############    Eingabe-Filter   ###############
	
	public static function inputNumber($input){
		$input = str_replace(',', '.', $input);
		$input = floatval($input);
		return $input;
	}
	
	
}






