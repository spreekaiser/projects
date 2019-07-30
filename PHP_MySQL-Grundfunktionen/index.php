<?php  

	// echo '<pre>'; print_r($_POST); echo '</pre>';
	
	require('application/configs/config.php');
	
	function myAutoload($className){
				
		$classFile = sprintf('application/classes/%s.class.php', $className);
		require($classFile);
	}
	
	spl_autoload_register('myAutoload');
	
	new Controller();
	
	
	
	
	
		


		
