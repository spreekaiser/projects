<?php


mail('meine@email.de', $_POST['betreff'], $_POST['nachricht'], "From: ".$_POST['email']);

echo 'Ihre Nachricht wurde versendet. Wir bedanken uns';

