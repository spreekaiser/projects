/* 
 *  führt einen XMLHttpRequest aus 
 * 
 *  Constructor-Parameter
 *  output - HTML-Element, function callback
 *  script - optional, default response.php
 *  data   - optional, default Plain Objekt
 *  method - GET oder POST, default GET
 *  
 */


class Ajax {
    
    constructor(output, script = "response.php", data = null, method = "GET") {
        // console.log("hier ist die Klasse");
        
        this.output = output;
        this.script = script;
        this.data = data;
        this.method = method;
        
        this.request = new XMLHttpRequest();
        // console.log(this.request);
        
        this.main();
        
    }
    
    
    main() {
        // console.log("der main, der main");
        
        var that = this;
        
        this.request.onreadystatechange = function(){
           // console.log(this.readyState); 
           
           if (this.readyState == 4) {
               // console.log(this.readyState);
               // console.log(this.responseText);
               
                // Wenn Inhalt in HTML-Element dargestellt werden soll
                if (that.output instanceof HTMLElement) {
                    that.output.innerHTML = this.responseText;
                    that.output.style.display = "block";
                } else if (typeof (that.output) == "function") {
                    var responseData = JSON.parse(this.responseText);
                    that.output(responseData); // hier wird function processResponse aus script.js aufgerufen
                }
                
            }
            
        };
        
        var url = this.script;
        var query = "";
        if(this.data != null) {
            query = this.getQuery();
        }
        
        // Versenden von GET-Data:
        // request.open("GET", "message.php?name=fritz");
        // request.send();
        if (this.method == "GET") {
            if (query.length > 0) {
                url += "?" + query;
                // console.log(url);
            }
            this.request.open(this.method, url);
            this.request.send();
        }
        
        // Versenden von POST-Data:
        // request.open("POST", "message.php");
        // request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded);
        // request.send("name=fritz");
        
        if (this.method == "POST" && query.length > 0) {
            this.request.open(this.method, url);
            this.request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            this.request.send(query);
        }
        
          
        
    }
    
    
    getQuery() {
        var query = "";
        for (let property in this.data) {
           // console.log(property);
           
           let value = this.data[property];
           // console.log(value);
           
           query += property;
           query += "=";
           query += encodeURIComponent(value); // Umlaute, Sonderzeichen und Leerzeichen umformen
           query += "&";
        }
        query = query.slice(0, query.length - 1); // das letzte & entfernen
        // console.log(query);
        return query;
    }
    
    
    
    
    
}



