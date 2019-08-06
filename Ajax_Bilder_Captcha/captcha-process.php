<?php

session_start();

$isNoBot = 'false';

if($_POST['antwort'] == $_SESSION['antwort']){
	$isNoBot = 'true';
}

echo $isNoBot;


