/*
    Projekt zur Probe: Temperaturumrechnung

    
    verwendete Methoden
    
    matches()
    parseDouble()
    replace()
    Math.abs()
*/

package de.figd.tb.temperature;

public class Temperature {

    //das Berechnungsobjekt
    private Conversion conversion;
    
    // Nachkommastellen
    private int scale = 0;
    
    
    public static void main(String[] args) {
        Temperature t = new Temperature();
        // t.test();
        // t.moreTest();
        t.run(args);
        
        System.out.println("\n\n****************ENDZEILE*********************");
    }

    
        public void run(String[] args){
            double input;
            boolean show = true;
            
            // die Eingabe
            if (args.length == 1) {
                // hier wird nicht sichergestellt, dass input wirklich nur eine Zahl ist
                // input = Double.parseDouble(args[0]);
                // Methode soll hier sicherstellen, dass input wirklich Zahlen enthält
                input = this.stringToDouble(args[0]);
                
                // Methode um exacte Anzahl der Nachkommastellen zu berechnen
                this.setScale(args[0]);
                
                //die Verarbeitung
                this.conversion = new Conversion(input);
            
            
                //die Ausgabe
                this.show();
            } else if (args.length == 2) { 
                input = this.stringToDouble(args[0]);
                // Nachkommastellen berechnen
                this.setScale(args[0]);
                
                String inputUnit = args[1];
                
                this.conversion = new Conversion();
                switch (inputUnit) {
                    case "K" : case "k" :
                        this.conversion.setKelvin(input);
                        break;
                    case "C" : case "c" :
                        this.conversion.setCelsius(input);
                        break;
                    case "F" : case "f" :
                        this.conversion.setFahrenheit(input);
                        break;
                    default: 
                        System.out.println("zulässige Maßeinheiten sind:");
                        System.out.println("K oder k für Kelvin");
                        System.out.println("C oder c für Celsius");
                        System.out.println("F oder f für Fahrenheit");
                        show = false;
                }
                
                // System.out.println(input);
                // System.out.println(inputUnit);
                
                if (show) {
                    this.show();
                }
                
            }
            else if (args.length == 0) {
                this.error("Bitte ein Argument angeben");
            }
            
            
    }
        
        

    public void show(){
        String format = "%." + this.scale + "f";
        
        System.out.println("Fahrenheit: " + this.conversion.getFahrenheit());
        System.out.printf("Kelvin: " + format + " %n" ,this.conversion.getKelvin());
        System.out.printf("Celsius: " + format + " %n" , this.conversion.getCelsius());
        System.out.printf("Fahrenheit: " + format + " %n",this.conversion.getFahrenheit());
    
    }
        
        
    public void error(String message) {
        System.err.println(message);
        System.exit(2);
    }
        
    public double stringToDouble(String value) {
        double result = 0.0;
        value = value.replace(",", ".");
        
        if (! value.matches("[^0-9\\.]+")){
            result = Double.parseDouble(value);
        } else {
            this.error("Bitte einen numerischen Wert eingeben");
        }
        return result;
    }
    
    /*  setzt die Anzahl der Nachkommastellen, anhand der Position des Punktes .
        im übergebenen String
    */
    public void setScale(String value) {
        
        int scale = 0;
        value = value.replace(",", ".");
        int pos = value.indexOf(".");
        if (pos >= 0) {
            scale = value.length() - pos - 1;
        }
        this.scale = scale;
    }
    
        
        
        
        
        
    
    public void test(){
        //Kostruktor mit Celsius
        Conversion c=new Conversion(32);
        // c.setKelvin(-100);
        // c.setCelsius(-300);
        c.setFahrenheit(-500);



        System.out.println("Kelvin: " +c.getKelvin());
        System.out.println("Celsius: " + c.getCelsius());
        System.out.println("Fahrenheit: " + c.getFahrenheit());


    
    }


    public void moreTest() {
                
        Conversion c = new Conversion(38);
        // Conversion d = new Conversion(38);
        
        Conversion d = new Conversion();
        d.setKelvin(311.15);
        
        System.out.println(c == d);
        System.out.println("gleich: " + c.equals(d));
        
        
        
        
    }





    
}
