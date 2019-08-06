<?php

require('captcha-image.php');
?>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html lang = "<?php echo $_SESSION['language']; ?>" >
<head>
	<!-- die ersten beiden Buchstaben der Spracherkennung werden aus captcha-image.php initialisiert -->
	<meta content = "<?php echo $_SESSION['language']; ?>" >
	<title>Bilder-Captcha</title>
	<style>
		#wrapper{
			width: 50%;
			margin: auto;
		}
		
	</style>
	<!-- über Browserauswertung $_SESSION['language'] wird die entsprechende sprache.js Datei mit ihren Konstanten eingebunden -->
	<script src = "<?php echo $_SESSION['language'].'.js'; ?>"></script>
</head>
<body>
	<div id = "wrapper" >
		<h2>Cpatcha mit Bildern</h2>
		<form id = "email" >
			Ihre Email:
			<input type = "text" id = "adress" name = "adress" ><br>
			Betreff:
			<input type = "text" id = "subject" name = "subject" ><br>
			Nachricht:
			<textarea id = "notice" name = "notice" ></textarea>
						
		</form>
		<form id = "captcha" >
			<div id = "frage" ><?php echo $_SESSION['frage']; ?></div>
			<div id = "bild" ><img src = "<?php echo $_SESSION['bild']; ?>"></div>
			<div><input type = "number" name = "antwort" id = "antwort" ></div>
			<div><input type = "submit" name = "subCaptcha" ></div>
		</form>
	</div>

	<script>
		
		var $isNoBot = false;
		var wrapper = document.getElementById('wrapper');
		var formCaptcha = document.getElementById('captcha');
		var formEmail = document.getElementById('email');
		var textEmail = '';
		var textBetreff = '';
		var textNachricht = '';
		var json = '';
		
		// variable noBot ist aus de.js eingebunden - auf diesen Wege regelt man verschiedene Sprachen
		document.getElementsByName('subCaptcha')[0].setAttribute('value', noBot);
		
		document.getElementById('adress').value = sessionStorage.getItem('email');
		document.getElementById('subject').value = sessionStorage.getItem('betreff');
		document.getElementById('notice').value = sessionStorage.getItem('nachricht');
		
		// folgender Request regelt die Sprachangaben des Nutzers
		var xhr = new XMLHttpRequest();
		xhr.open('POST', 'language.php');
		xhr.send();
		
		xhr.onreadystatechange = function() {
			if(this.readyState == 4 && this.status == 200) {
				//alert(this.responseText);
				json = JSON.parse(this.responseText);
				document.getElementsByName('submit-captcha')[0].setAttribute('value', json.no_bot);
				//alert(json);
			}
		};
		
		formCaptcha.onsubmit = function(e){
			e.preventDefault();
			
			textEmail = document.getElementById('adress').value;
			textBetreff = document.getElementById('subject').value;
			textNachricht = document.getElementById('notice').value;
			
			var xhr = new XMLHttpRequest();
			xhr.open("POST", "captcha-process.php");
			xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xhr.send("antwort="+document.getElementById('antwort').value);
			
			xhr.onreadystatechange = function(){
				if(this.readyState == 4 && this.status == 200){
					if(this.responseText == 'true'){
						$isNoBot = true;
						wrapper.removeChild(formCaptcha);
						var submitEmail = document.createElement('input');
						submitEmail.setAttribute('type', 'submit');
						submitEmail.setAttribute('value',  json.send_mail);
						formEmail.appendChild(submitEmail);
					} else{
						sessionStorage.setItem('email', textEmail); // Name 'email' wird hier neu vergeben
						sessionStorage.setItem('betreff', textBetreff);
						sessionStorage.setItem('nachricht', textNachricht);
						
						location.reload();
					}
					
				}
			};
		};
		
		formEmail.onsubmit = function(e){
			e.preventDefault();
			if($isNoBot == true){
				var xhr = new XMLHttpRequest();
				xhr.open('POST', 'send-mail.php');
				xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
				xhr.send('email='+textEmail+'&betreff='+textBetreff+'&nachricht='+textNachricht);
				
				xhr.onreadystatechange = function(){
					if(this.readyState == 4 && this.status == 200){
						// alert(this.responseText);
						wrapper.removeChild(formEmail);
						var divSend = document.createElement('div');
						var textSend = document.createTextNode(json.feedback_sent);
						divSend.appendChild(textSend);
						wrapper.appendChild(divSend);
						
					}
				};
			}
		};
		
		
	</script>

</body>
</html>

