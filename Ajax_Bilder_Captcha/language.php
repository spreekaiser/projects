<?php

session_start();
include($_SESSION['language'].'.php');

$json = json_encode($strings);
echo $json;

