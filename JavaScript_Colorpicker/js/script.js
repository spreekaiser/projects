"use strict";
/* 
    Deklaration von (globalen) Variablen ohne var ist mit "use strict" nicht mehr möglich. 
    Ein Falschschreiben und daher ungewollte Neudeklarieren von Variablen, wird somit umgangen
 */


// alert("jetzt wird gepickt");


var colors = ["white", "black", "red", "blue", "green", "yellow", "orange", "purple", "darkred"];
var selectContainer, previewContainer;

// window.onload = function() { - onload kann nur einmal im Programm überschrieben werden, daher besser:
// window.addEventListener("load", function(){ - wenn window fertig geladen ist, dauert aber!
document.addEventListener("DOMContentLoaded", function() {  /* nutzbar, wenn DOM-Baum fertig geladen ist,
                                                            aber vor den Medien: Bilder, Video etc.   */

    // Elemente besorgen: querySelector(css-Selektor) - querySelectorAll(css-Selektor)
    selectContainer = document.querySelector(".colorpicker > div:first-of-type");
    // console.dir(selectContainer); // .dir zeigt auch im Google-Chrome die Eigenschaften des Elementes
    
    previewContainer = document.querySelector(".colorpicker > div:last-of-type");
    // console.log(previewContainer);
            
    addColorFields();
   
    
// };
});


function addColorFields() {
    // console.log("jetzt kommen die Farbfelder");
    
    colors.forEach(function(value, index){
        // console.log(index + value);
        
        var container = document.createElement('DIV');
        container.style.backgroundColor = value;
        
        container.addEventListener("mouseover", function(){ // beim Entwickeln mit click-Event arbeiten
            // console.log(this);
            var selectColor = this.style.backgroundColor;
            
            previewContainer.style.backgroundColor = selectColor;
            
        });
        
        container.addEventListener("click", selectColor);
        container.addEventListener("mouseout", resetPreview);
        // console.log(container);
        
        selectContainer.appendChild(container);
        
    });
    
}


function selectColor() {
    // console.log(this);
    
    var selectColor = this.style.backgroundColor;
    // console.log(selectColor);
    
    deSelectColor();
    
    this.classList.add("selected");
    var parentContainer = this.parentElement;
    var colorInput = parentContainer.querySelector("input[name='color']");
    // console.log(colorInput);
    colorInput.value = selectColor;
}

function deSelectColor() {
    // console.log("weg mit dem zeugs");
    
    var colorField = document.querySelector(".colorpicker > div:first-of-type > div[class='selected']");
    // console.log(colorField);
    
    if (colorField) {
        colorField.classList.remove("selected");
    }
    
}

// setzt Preview-Container auf ausgewählte Farbe nach mouse-out
function resetPreview() {
    
    var parentContainer = this.parentElement;
    var selectedContainer = parentContainer.querySelector("div.selected");
    // console.log(selectedContainer);
    
    if (selectedContainer) {
        var color = selectedContainer.style.backgroundColor;
        previewContainer.style.backgroundColor = color;
    }
    
}





