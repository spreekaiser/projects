/* API key = 66782772b13f02aff31162e76d9c9775 
 * Link-Beispiel Berlin: 
 * "http://api.openweathermap.org/data/2.5/weather?q=Berlin,de&appid=66782772b13f02aff31162e76d9c9775" 
 * 
 * Situation Berlin:
 * Breitengrad: 52, Längengrad 13
 * Berlin id: 2950159
 * 
 * London id: 2643743
 * New York id: 5128581
 * 
*/

import {MyLocation} from "./weather.js";
import {MyWeather} from "./weather.js";


(function() {
"use strict";



/************************ Objekte festlegen  *********************************/



/* Was tun, wenn neue Funktionalitäten implementiert werden?
 * 
 *  1. Eingabe-Möglichkeit
 *     (HTML - Elemente erzeugen)
 *  2. Elemente aus dem DOM-Baum besorgen
 *  3. EventListener hinzufügen
 *  4. Methode schreiben -> Lösung der gewünschten Funktionalität
 *  5. Ausgabe
 */



/******************************************************************************/

// Globale Werte
var weatherURL = "http://api.openweathermap.org/data/2.5/weather";
var groupURL = "http://api.openweathermap.org/data/2.5/group";


var appData = {
    appid: "66782772b13f02aff31162e76d9c9775",
    units: "metric",
    lang: "de"
    // q: "Berlin,de"
};

// var watchList = [2950159, 2643743, 5128581];
var watchList = [];


// HTML Elemente
var body, wrapper, mainContent,currentWeatherTemplate, locationWrapper, locationContainer;

// Links
var currentLink, updateLink, addLocationLink, 
    addPositionLink, clearLink;

// für Navigation
var hamMenu, navList, navContainer;

// JSON - Abfrage für Location-Wahl
var citySearch = {continent: "", country: "", city: ""};
var question = {continent: "", country: "", city: ""};
var select = {continent: "", country: "", city: ""};


/******************************************************************************/

document.addEventListener("DOMContentLoaded", function() {  
    // console.log("document geladen");
    
    initElements();
    addListener();
    
    refreshAll();
    
    
/***********  Ende der INIT-Methode  **********/
});


function initElements() {
    // currentLink = document.querySelector("#current"); // Link zum Testen
    // console.log(currentLink);
    updateLink = document.querySelector("#update");
    // console.log(updateLink);
    addLocationLink = document.querySelector("#add_location");
    // console.log(addLocationLink);
    addPositionLink = document.querySelector("#add_position");
    // console.log(addPositionLink);
    clearLink = document.querySelector("#clear");
    // console.log(clearLink);
    
    // Navigation
    hamMenu = document.querySelector("#hamMenu");
    navList = document.querySelector("#main_nav ul");
    navContainer = document.querySelector("#main_nav");
    // console.log(navContainer);
    
    wrapper = document.querySelector("#wrapper");
    wrapper.style.display = "block";
    mainContent = document.querySelector("main");
    
    currentWeatherTemplate = document.querySelector("#templates > .current_weather");
    // console.log(currentWeatherDiv);
}

function addListener() {
    
    // console.log(localStorage.watchList);
    if (localStorage.watchList) {
        watchList = JSON.parse(localStorage.watchList);
    }
    
    // currentLink.addEventListener("click", getCurrentWeather); // Link zum Testen
    updateLink.addEventListener("click", refreshAll);
    addLocationLink.addEventListener("click", showLocationContainer);
    addPositionLink.addEventListener("click", addCurrentPosition);
    clearLink.addEventListener("click", clearWatchList);
    
    // Navigation
    hamMenu.addEventListener("click", toggleMenu);
    
    
}

function addCurrentPosition() {
    // console.log("Geolocation");
    
    if (navigator.geolocation) {
        // console.log("okay, vorhanden");
        navigator.geolocation.getCurrentPosition(function(position) {
            // console.log(position);
            var latitude = position.coords.latitude;
            var longitude = position.coords.longitude;
            // console.log(latitude, longitude);
            appData.lat = latitude;
            appData.lon = longitude;
            
            new Ajax(function(weather) {
                // console.log(weather);
                if (weather.id && watchList.indexOf(weather.id) < 0) {
                    watchList.unshift(weather.id);
                    localStorage.watchList = JSON.stringify(watchList);
                    showCurrentWeather(weather);
                    // refreshAll();
                    // console.log(watchList);
                }
                
            }, weatherURL, appData);
            
        });
    } else {
        alert("Keine Lokalisierung möglich");
    }
    delete appData.lat;
    delete appData.lon;
}

/* Fragt Benutzer nach Stadtnamen, um ihn in die watchList einzufügen
 * 
 */


function showLocationContainer() {
    
    body = document.querySelector("body");
    // console.log();
    locationWrapper = document.querySelector("#locationWrapper");
    // console.log(locationWrapper);
    locationContainer = document.querySelector("#locationContainer");
        
    locationWrapper.appendChild(locationContainer);
    locationWrapper.style.display = "block";
    body.appendChild(locationWrapper);
    
    question.continent = document.querySelector("#continentQuestion");
    question.country = document.querySelector("#countryQuestion");
    question.country.style.display = "none";
    question.city = document.querySelector("#cityQuestion");
    question.city.style.display = "none";
    
    select.continent = document.querySelector("#continentSelect");
    select.continent.innerHTML = "";
    select.country = document.querySelector("#countrySelect");
    select.country.style.display = "none";
    select.city = document.querySelector("#citySelect");
    select.city.style.display = "none";
    
    new Ajax(addContinents, "json/continent.json");
}

function addContinents(continents) {
    // console.log(continents);
    question.continent.style.display = "block";
    select.continent.style.display = "block";
        // console.log(continentSelect);
    
    var option = document.createElement("OPTION");
    option.value = "-- Bitte auswählen --";
    option.innerHTML = option.value;
    select.continent.appendChild(option);
    
    continents.forEach(function(continent) {
        
        // console.log(continent);
        
        var option = document.createElement("OPTION");
        option.value = continent;
        option.innerHTML = continent;
        // console.log(option.value);
        select.continent.appendChild(option);
        // continentSelect.innerHTML = option.value;
    });
    
    // EventListener für Location-Wahl Kontinent (Ort+)
    select.continent.addEventListener("change", getContinent);
}

function getContinent() {
    citySearch.continent = this.value;
    // console.log(choosedContinent);
    new Ajax(addCountries, "json/country.json");
}

function addCountries(countries) {
    // console.log(countries);
    
    question.country.style.display = "block";
    question.city.style.display = "none";
    select.country.style.display = "block";
    select.country.innerHTML = "";
    select.city.style.display = "none";
    
    
    var option = document.createElement("OPTION");
    option.value = "-- Bitte auswählen --";
    option.innerHTML = option.value;
    select.country.appendChild(option);
    
    countries.forEach(function(country) {
        // console.log(country.continent);
        
        if (country.continent == citySearch.continent) {
            var option = document.createElement("OPTION");
            option.value = country.name;
            option.innerHTML = country.name;
            countrySelect.appendChild(option);
        }
        
    });
    
    // EventListener für Location-Wahl Country (Ort+)
    select.country.addEventListener("change", getCountry);
}

function getCountry() {
    citySearch.country = this.value;
    // console.log(citySearch.country);
    new Ajax(addCities, "json/cityshort.json");
}

function addCities(cities) {
    // console.log(cities);
    
    question.city.style.display = "block";
    select.city.style.display = "block";
    select.city.innerHTML = "";
    
    var option = document.createElement("OPTION");
    option.value = "-- Bitte auswählen --";
    option.innerHTML = option.value;
    select.city.appendChild(option);
    
    cities.forEach(function(city) {
        if (city.country == citySearch.country) {
        // console.log(city.country + ": " +citySearch.country);
            
        var option = document.createElement("OPTION");
        option.value = city.name;
        option.innerHTML = city.name;
        select.city.appendChild(option);
    }
    });
    
    select.city.addEventListener("change", addLocation);
}


function addLocation() {
    
    locationWrapper.style.display = "none";
    
    appData.q = this.value;
    new Ajax(function(weather){
        
        if (weather.id && watchList.indexOf(weather.id) < 0) {
            watchList.push(weather.id);
            // console.log(watchList);
            
            localStorage.watchList = JSON.stringify(watchList);
            showCurrentWeather(weather);
        }
        
    }, weatherURL, appData);
    delete appData.q;
}

function refreshAll() {
    
    if (watchList.length > 0) {
        appData.id = watchList.join(",");
        // console.log(appData);

        new Ajax(processWatchList, groupURL, appData);
        delete appData.id; // Vorsicht beim Überschreiben von Globalen Variablen!
    }
}

function processWatchList(weatherList) {
    // console.log(weatherList);
    weatherList.list.forEach(function(weather) {
        showCurrentWeather(weather);
    });
}

function getCurrentWeather() {
    // console.log("aktuelles Wetter: Berlin");
    
    // speziell für ausgewählte Städte
    appData.q = "Berlin,de";
    // appData.lon = 13; - Längengrad
    // appData.lat = 52; - Breitengrad
    
    // appData.id = 2950159;
    
    new Ajax(showCurrentWeather, weatherURL, appData);
    
    appData.q = "London,uk";
    new Ajax(showCurrentWeather, weatherURL, appData);
    
    appData.q = "New York,us";
    new Ajax(showCurrentWeather, weatherURL, appData);
}

/* Erzeugt einen Div für die wetterausgabe, wenn er nicht existiert und
 * aktualisiert die Wetter Daten
 */
function showCurrentWeather(current) {
// console.log(current);
//    
//    var temperature = current.main.temp;
//    console.log(temperature);
    
    var weather = new MyWeather(current);
    // showConsole(weather);
    var location = new MyLocation(current);
    // console.log(location.getName());
    
    // dem currentWeatherDiv eine ID verpassen
    // console.log(location.getName() + ": " + location.getCityId());
    var idAttribute = "city-id" + location.getCityId();
    
    var currentWeatherDiv = document.getElementById(idAttribute);
    
    if (! currentWeatherDiv) {
        currentWeatherDiv = currentWeatherTemplate.cloneNode(true);
        currentWeatherDiv.id = idAttribute;
        currentWeatherDiv.addEventListener("click", removeLocation);
        updateWeatherData(currentWeatherDiv, weather, location);

        mainContent.appendChild(currentWeatherDiv);
        currentWeatherDiv.style.display = "block";
    } else {
        updateWeatherData(currentWeatherDiv, weather, location);
    }
}

function updateWeatherData(currentWeatherDiv, weather, location) {
    
    // weather.getIcon();
    location.getSunrise();
    
    var cityNameHeader = currentWeatherDiv.querySelector(".city_name");
    var descriptionParagraf = currentWeatherDiv.querySelector(".description");
    var temperatureSpan = currentWeatherDiv.querySelector(".temperature");
    var windspeedSpan = currentWeatherDiv.querySelector(".windspeed");
    var cloudySpan = currentWeatherDiv.querySelector(".cloudness");
    var sunriseSpan = currentWeatherDiv.querySelector(".sunrise");
    var sunsetSpan = currentWeatherDiv.querySelector(".sunset");
    // var iconImg = currentWeatherDiv.querySelector(".current_weather img");
    
    var weatherIcon = currentWeatherDiv.querySelector(".weatherIcon");
    weatherIcon.style.display = "inline-block";
    var owfInit = "owf";
    var owfZoom = "owf-5x";
    var owfId = "owf-"+ weather.getWeatherId();
    weatherIcon.classList.add(owfInit);
    weatherIcon.classList.add(owfZoom);
    weatherIcon.classList.add(owfId);
    
    cityNameHeader.innerHTML = location.getName();
    descriptionParagraf.innerHTML = weather.getDescription();
    temperatureSpan.innerHTML = weather.getTemperature() + "°&nbsp;C";
    windspeedSpan.innerHTML = weather.getWindSpeed() + "&nbsp;m/s";
    cloudySpan.innerHTML = weather.getClouds() + "&nbsp;Prozent";
    sunriseSpan.innerHTML = location.getSunrise() + "&nbsp;Uhr";
    sunsetSpan.innerHTML = location.getSunset() + "&nbsp;Uhr";
    // iconImg.src = weather.getIcon(); // Pfad für originale Icons von openWeatherMap
    
    /*** Aufruf der Methode, jeweilige Teilausschnitte eines Bildes anzuzeigen ***/
    // iconImg.style.clipPath = weather.getIcon();
    
}

function clearWatchList() {
    // console.log("alles muss raus");
    
    watchList = [];
    localStorage.watchList = [];
    // mainContent.innerHTML = ""; // - Schweine-Methode: quick and dirty
    while (mainContent.firstChild) {
        mainContent.removeChild(mainContent.firstChild);
    }
    
    
}

function removeLocation() {
    if (confirm("Entfernen?")) {
        var id = this.id;
        id = id.replace("city-id", "");
        id = parseInt(id);
        // console.log(id);
    }
    var garbage = watchList.indexOf(id);
    if (garbage > -1) {
        watchList.splice(garbage, 1);
        localStorage.watchList = JSON.stringify(watchList);
        mainContent.removeChild(this);
    }
}


/******************************  MENU  ****************************************/

function toggleMenu() {
    // console.log("toggle für alle");
    
    navList.classList.toggle("visible"); // für diesen Befehl muss Selector sehr speziell gesetzt sein
    // navList.style.display = "block";
    
    navContainer.classList.toggle("visible");
    
    
}

// Menü ausblenden, wenn window-Größe verändert wird
window.addEventListener("resize", function() {
    navList.classList.remove("visible");
    navContainer.classList.remove("visible");
});

// Menü ausblenden, wenn auf einen Menü-Punkt geklickt wurde
window.addEventListener("click", function() {
    // console.log(navList);
        // navList.classList.remove("visible"); // nimmt Menü sofort weg, weg es ausgeklappt werden soll
        // console.log("auf window geklickt");
    
});

/******************************************************************************/
function showConsole(weather) {
    console.log("aktuelle Temperatur: " + weather.getTemperature());
    console.log("minimale Temperatur: " + weather.getMinTemperature());
    console.log("maximale Temperatur: " + weather.getMaxTemperature());
    console.log("Windrichtung: " + weather.getWindDirection());
    console.log("Windgeschwindigkeit: " + weather.getWindSpeed());
    console.log("Bewölkung: " + weather.getClouds());
    console.log("Beschreibung: " + weather.getDescription());
}




/*********************  ENDE der anonymen FUNKTION  ***************************/
})();
