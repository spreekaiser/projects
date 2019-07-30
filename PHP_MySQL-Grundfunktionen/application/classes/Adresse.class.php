<?php
#####   C:\xampp\htdocs\php\php_Projekt_Barth\application\classes\Adresse.class.php   #####


class Adresse {
	
	private $id = 0;
	private $vorname = '';
	private $nachname = '';
	private $strasse = '';
	private $plz = '';
	private $ort = '';
	private $email = '';
	private $geburtstag = '';
	
	private static $PDO = null;
	
	public function __construct($values = null){
		
		if($values != null){
			$this -> exchangeArray($values);
		}
		
		
	}
	
	private function exchangeArray($values){
		
		$this -> setId($values['id']);
		$this -> setVorname($values['vorname']);
		$this -> setNachname($values['nachname']);
		$this -> setStrasse($values['strasse']);
		$this -> setPlz($values['plz']);
		$this -> setOrt($values['ort']);
		$this -> setEmail($values['email']);
		$this -> setGeburtstag($values['geburtstag']);
		
	}
	
	
	private function getArrayCopy($withID = true){
		$values = [];
		$values['id'] = $this -> getId();
		$values['vorname'] = $this -> getVorname();
		$values['nachname'] = $this -> getNachname();
		$values['strasse'] = $this -> getStrasse();
		$values['plz'] = $this -> getPlz();
		$values['ort'] = $this -> getOrt();
		$values['email'] = $this -> getEmail();
		$values['geburtstag'] = $this -> getGeburtstag();
		
		// echo '<pre>getArrayCopy - $values: '; print_r($values); echo '</pre>';
		
		if($withID == false){
			unset($values['id']);
		}
		return $values;
	}
	
	################   Datenbank   ################
	
	private static function connectDB(){
		
		if(self::$PDO == null){
			self::$PDO = new PDO(sprintf('mysql:hostname=%s;dbname=%s;charset=utf8', HOST, DB), USER, PASS);
			
		}
		// echo '<pre>Adresse: connectDB - $PDO: '; print_r(self::$PDO); echo '</pre>';
	}
	
	public static function getList(){
		$adressen = [];
		
		## Datenverbindung herstellen
		self::connectDB();
		
		## Anfrage formulieren
		$query =
		'SELECT id, vorname, nachname, strasse, plz, ort, email, geburtstag
		FROM Adresse';
		
		## Anfrage an Datenbank senden
		$PDOStatement = self::$PDO -> query($query);
		// var_dump($PDOStatement);
		
		## FetchMode sezten
		$PDOStatement -> setFetchMode(PDO::FETCH_CLASS, __CLASS__);
		
		## Ergebnisse fetchen
		$adressen = $PDOStatement -> fetchAll();
		
		
		return $adressen;
		
	}
	
	public static function getOrderListName(){
		$adressen = [];
		
		self::connectDB();
		
		$query =
		'SELECT id, vorname, nachname, strasse, plz, ort, email, geburtstag
		FROM Adresse
		ORDER BY nachname';
		
		$PDOStatement = self::$PDO -> query($query);
		$PDOStatement -> setFetchMode(PDO::FETCH_CLASS, __CLASS__);
		$adressen = $PDOStatement -> fetchAll();

		return $adressen;
	}
	
	public static function getOrderListPLZ(){
		$adressen = [];
		
		self::connectDB();
		
		$query =
		'SELECT id, vorname, nachname, strasse, plz, ort, email, geburtstag
		FROM Adresse
		ORDER BY plz';
		
		$PDOStatement = self::$PDO -> query($query);
		$PDOStatement -> setFetchMode(PDO::FETCH_CLASS, __CLASS__);
		$adressen = $PDOStatement -> fetchAll();
		
		return $adressen;
	}
	
	public static function getOrderListDay(){
		$adressen = [];
		
		self::connectDB();
		
		$query =
		'SELECT id, vorname, nachname, strasse, plz, ort, email, geburtstag
		FROM Adresse
		ORDER BY geburtstag';
		
		$PDOStatement = self::$PDO -> query($query);
		$PDOStatement -> setFetchMode(PDO::FETCH_CLASS, __CLASS__);
		$adressen = $PDOStatement -> fetchAll();
		
		return $adressen;
		
	}
	
	public static function getByName($name){
		$adressen = [];
		$name .= '%';
		// echo '<pre>Adresse- getByName() - $name: '; print_r($name); echo '</pre>';
		self::connectDB();
		
		$query =
		'SELECT id, vorname, nachname, strasse, plz, ort, email, geburtstag
		FROM Adresse
		WHERE nachname LIKE :name
		ORDER BY nachname';
		
		$PDOStatement = self::$PDO -> prepare($query);
		$PDOStatement -> bindValue(':name', $name, PDO::PARAM_STR);
		$PDOStatement -> execute();
		
		$PDOStatement -> setFetchMode(PDO::FETCH_CLASS, __CLASS__);
		$adressen = $PDOStatement -> fetchAll();
		// echo '<pre>Adresse- getByName() - $adressen: '; print_r($adressen); echo '</pre>';
		return $adressen;
	}
	
	public static function getById($id){
		
		## Datenverbindung
		self::connectDB();
		
		## Anfrage formulieren
		$query =
		'SELECT id, vorname, nachname, strasse, plz, ort, email, geburtstag
		FROM Adresse
		WHERE id = :id';
		
		## Anfrage vorbereiten
		$PDOStatement = self::$PDO -> prepare($query);
		
		## Platzhalter befüllen
		$PDOStatement -> bindValue(':id', $id, PDO::PARAM_INT);
		
		## Anfrage ausführen
		$PDOStatement -> execute();
		
		## FetchMode setzen
		$PDOStatement -> setFetchMode(PDO::FETCH_CLASS, __CLASS__);
		
		## Ergenisse entgegen nehmen
		return $PDOStatement -> fetch();
		
	}
	
	public function save(){
		
		if($this -> getId() == 0){ //falls id 0 ist
			return $this -> insert();
		} else {
			return $this -> update();
		}
	}
	
	public function insert(){
		// echo 'hier wird neu geschrieben';
		
		if( ! empty($this -> getVorname() ) && ! empty($this -> getNachname() ) ){
			## Datenbankverbindung
			self::connectDB();
			
			## Anfrage formulieren
			$query =
			'INSERT INTO Adresse
			(vorname, nachname, strasse, plz, ort, email, geburtstag)
			VALUES
			(:vorname, :nachname, :strasse, :plz, :ort, :email, :geburtstag)';
			
			## Anfrage vorbereiten
			$PDOStatement = self::$PDO -> prepare($query);
			
			## Platzhalter befüllen
			$values = $this -> getArrayCopy(false);
			
			// echo '<pre>Adresse - insert() - $values: '; print_r($values); echo '</pre>';
			## Anfrage ausführen
			$PDOStatement -> execute($values);
			
			$this -> redirect(1);
		}
		
	}
	
	public function update(){
		// echo 'es wird geupdatet';
		
		## Datenbankverbindung
		self::connectDB();
		
		## Anfrage formulieren
		$query =
		'UPDATE Adresse
		SET vorname = :vorname,
		nachname = :nachname,
		strasse = :strasse,
		plz = :plz,
		ort = :ort,
		email = :email,
		geburtstag = :geburtstag
		WHERE id = :id
		LIMIT 1';
		
		## Anfrage vorbereiten
		$PDOStatement = self::$PDO -> prepare($query);
		
		## Platzhaler befüllen
		$values = $this -> getArrayCopy();
		
		## Anfrage ausführen
		$PDOStatement -> execute($values);
		
		$this -> redirect(2);
	}
	
	public function clear(){
		
		## Datenbankverbindung
		self::connectDB();
		
		## Anfrage formulieren
		$query =
		'Delete From Adresse
		WHERE id = :id
		LIMIT 1';
		
		## Anfrage vorbereiten
		$PDOStatement = self::$PDO -> prepare($query);
		
		## Platzhalter befüllen
		$PDOStatement -> bindValue(':id', $this -> getId(), PDO::PARAM_INT);
		
		## Angrafe ausführen
		$PDOStatement -> execute();
		
		$this -> redirect(3);
	}
	
	private function redirect($case){
		
		$url = 'Location: http://';
		$url .= $_SERVER['SERVER_NAME'];
		$url .= PHP_SELF;
		
		if($case == 1){
			$url .= '?action=insert';
			//echo $url;
			header($url);	
		}
		if($case == 2){
			$url .= '?action=update';
			header($url);
		}
		if($case == 3){
			$url .= '?action=deleted';
			header($url);
		}
		
	}
	
	################   Setter   #################
	
	public function setId($id){
		$this -> id = $id;
	}
	
	public function setVorname($vorname){
		$this -> vorname = $vorname;
	}
	
	public function setNachname($nachname){
		$this -> nachname = $nachname;
	}
	
	public function setStrasse($strasse){
		$this -> strasse = $strasse;
	}
	
	public function setPlz($plz){
		$this -> plz = $plz;
	}
	
	public function setOrt($ort){
		$this -> ort = $ort;
	}
	
	public function setEmail($email){
		$this -> email = $email;
	}
	
	public function setGeburtstag($geburtstag){
		$this -> geburtstag = $geburtstag;
	}
	
		
	################   Getter   #################
	
	public function getId(){
		return $this -> id;
	}
	
	public function getVorname(){
		return $this -> vorname;
	}
	
	public function getNachname(){
		return $this -> nachname;
	}
	
	public function getStrasse(){
		return $this -> strasse;
	}
	
	public function getPlz(){
		return $this -> plz;
	}
	
	public function getOrt(){
		return $this -> ort;
	}
	
	public function getEmail(){
		return $this -> email;
	}
	
	public function getGeburtstag(){
		return $this -> geburtstag;
	}
	
	
	
	#################   Filter   #################
	
	public static function getFilter(){
		$filter = [];
		
		// eventuellen Quelltext rausfiltern
		$filter['id'] = FILTER_SANITIZE_STRING;
		$filter['vorname'] = FILTER_SANITIZE_STRING;
		$filter['nachname'] = FILTER_SANITIZE_STRING;
		$filter['strasse'] = FILTER_SANITIZE_STRING;
		$filter['plz'] = FILTER_SANITIZE_STRING;
		$filter['ort'] = FILTER_SANITIZE_STRING;
		$filter['email'] = FILTER_SANITIZE_STRING;
		$filter['geburtstag'] = FILTER_SANITIZE_STRING;
		
		return $filter;
		
	}
	
	
}





