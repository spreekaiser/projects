<?php

#####   C:\xampp\htdocs\php\php_Projekt_Barth\application\classes\Controller.class.php   #####

### regelt den Programmablauf


class Controller {
	
		
	## InstanzVariablen
	private $adressen = [];
	private $adresse = [];
	
	private $action = 'all';
	private $view = 'list';
	
		
	
	public function __construct(){
		
		$this -> action = $this -> getAction();
		
		$this -> main();
		$this -> view();		
		
	}
	
	private function main(){
		
		
		
		// echo 'Controller: function main - $action: ' . $this->action;
		
		switch($this -> action){
			case 'all' :
				$this -> adressen = Adresse::getList();
				break;
			case 'modify' :
				$id = intval($_GET['id']);
				$this -> adresse = Adresse::getById($id);
				$this -> view = 'form';
				break;
			case 'save' :
				$filter = Adresse::getFilter();
				$values = [];
				$values = filter_input_array(INPUT_POST, $filter);
				$this -> adresse = new Adresse($values);
				// echo '<pre>Controller Save $adresse: '; print_r($this -> adresse); echo '</pre>';
				$this -> adresse -> save();
				break;
			case 'insert' :
				$this -> view = 'insert';
				break;
			case 'new' :
				$this -> adresse = new Adresse();
				$this -> view = 'form';
				break;
			case 'clear' :
				$id = intval($_GET['id']); // id filtern
				$this -> adresse = Adresse::getById($_GET['id']);
				if( ! empty($this -> adresse) ){
					$this -> adresse -> clear();
				}
				break;
			case 'deleted' :
				$this -> view = 'clear';
				break;
			case 'update' :
				$this -> view = 'update';
				break;
			case 'orderName' :
				$this -> adressen = Adresse::getOrderListName();
				break;
			case 'orderPLZ' :
				$this -> adressen = Adresse::getOrderListPLZ();
				break;
			case 'orderDay' :
				$this -> adressen = Adresse::getOrderListDay();
				break;
			case 'search' :
				$this -> search();
				break;
		}
		
	}
	
	private function view(){
		
		#################################################
		#############       Ausgabe        ##############
		
		require('application/templates/head.tpl.phtml');
		
		switch ($this -> view){
			case 'list' :
				require('application/templates/list.tpl.phtml');
				// Adresse::getList();
				break;
			case 'form' :
				require('application/templates/form.tpl.phtml');
				break;
			case 'insert' :
				require('application/templates/insert_note.tpl.phtml');
				break;
			case 'update' :
				require('application/templates/update_note.tpl.phtml');
				break;
			case 'clear' :
				require('application/templates/clear_note.tpl.phtml');
				break;
			case 'search' :
				$this -> search();
				break;
		}
		
				
		#############       Footer         ##############
		
		require('application/templates/footer.tpl.phtml');
		
		
	}
	
	public function getURL($class, $action, $id = 0){
		$url = PHP_SELF;
		$values = [
			'class' => $class,
			'action' => $action,
			'id' => $id
		];
		
		$query = http_build_query($values);
		$url .= '?' . $query;
				
		return $url;
		
	}
	
	private function getAction(){
		$action = '';
		$action = filter_input(INPUT_GET, 'action', FILTER_SANITIZE_STRING);
		
		if(empty($action) ){
			$action = filter_input(INPUT_POST, 'action', FILTER_SANITIZE_STRING);
		}
		if(empty($action) ){
			$action = 'all';
		}
		return $action;
	}
	
	private function search(){
		
		$name = filter_input(INPUT_GET, 'name', FILTER_SANITIZE_STRING);
		
		if( ! empty($name) ){
			$this -> adressen = Adresse::getByName($name);
		} else {
			$this -> adressen = Adresse::getList();
		}
		
	}

	
}



