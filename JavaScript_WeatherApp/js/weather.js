/* 
 * 
 */


// ab ECMA-6:  DateTimeFormatter
// year, month, day, hour, minute, second
// Möglichkeiten für month: numeric, 2-digit, narrow, short, long
var timeFormat = new Intl.DateTimeFormat("de-DE", {
    hour: "2-digit",    
    minute: "2-digit"
});


export class MyLocation {
    constructor(origin) {
        this.origin = origin;
        // console.log(this.origin);
        
    }
    getName() {
        return this.origin.name;
    }
    getCityId() {
        return this.origin.id;
    }
    getSunrise() {
        var sunrise = new Date(1000 * this.origin.sys.sunrise);
        var sunriseFormat = timeFormat.format(sunrise);
        //console.log(sunriseFormat);
        return sunriseFormat;
        
        // this.dateTest();
    }
    getSunset() {
        var sunset = new Date(1000 * this.origin.sys.sunset);
        var sunsetFormat = timeFormat.format(sunset);
        return sunsetFormat;
    }
    
    
    
/******************************************************************************/

    dateTest() {
        // console.log("Wir testen Datum");
        
        console.log(timeFormat.format(now));
 
        // Datum-Objekte bis ECMA-5
        var now = new Date();
        // console.log(now);
        
        var month = now.getMonth();
        var day = now.getDay();
        var date = now.getDate();
        var hour = now.getHours();
        var minute = now.getMinutes();
        
        // console.log(month, day, date, hour, minute);  
    }

/******************************************************************************/
        
}


// Globale Werte
var iconURL = "http://openweathermap.org/img/wn/###@2x.png";




export class MyWeather {
    constructor(origin) {
        this.origin = origin;
        // console.log(origin);
    }
    
    getTemperature() {
        return parseInt(this.origin.main.temp);
    }
    getMinTemperature() {
        return parseInt(this.origin.main.temp_min);
    }
    getMaxTemperature() {
        return parseInt(this.origin.main.temp_max);
    }
    getWindDirection() {
        return this.origin.wind.deg;
    }
    getWindSpeed() {
        return this.origin.wind.speed;
    }
    getClouds() {
        return this.origin.clouds.all;
    }
    getDescription() {
        return this.origin.weather[0].description;
    }
    
    getIcon() { // Icon-Darstellung mit URL von openWeatherMap
        var iconCode = this.origin.weather[0].icon;
        var url = iconURL.replace("###", iconCode);
        // console.log(iconCode);
        return url;
    }
    getWeatherId() {
        return this.origin.weather[0].id;
    }
    
    

/******  Variante eines Bildes und jenach Wetterlage wird anderer Teilausschnitt angezeigt  ******/
//    getIcon() {
//        
//        var iconCode = this.origin.weather[0].icon;
//        var clips = {
//            _10d: "inset(120px,166px,240px,0px)"
//        };
//        // console.log(iconCode);
//        return clips._10d;
//    }
    
}




