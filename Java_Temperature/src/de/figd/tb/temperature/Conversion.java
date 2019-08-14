package de.figd.tb.temperature;


public class Conversion {
    
    //Der absolute Nullpunkt in Celsius
    public static final double ZERO_CELSIUS = -273.15;
    public static final double ZERO_FAHRENHEIT = -459.67;
    
    // der Betrag der Temperatur
    private double kelvin;
    
    //Ersetzen des deafult-Konstruktors
    public Conversion(){}
    
    //Konstruktor für Celsius
    public Conversion(double celsius){
        this.setCelsius(celsius);

    }
    
    
    
    

    //  Setter  ****************
    public void setKelvin(double kelvin){
        if (kelvin >= 0.0) {
            this.kelvin=kelvin;
        } else {
            this.kelvin = 0.0;
        }
    }
    
    public void setCelsius(double celsius){
        if (celsius >= ZERO_CELSIUS) { 
            double kelvin = celsius + (Conversion.ZERO_CELSIUS * -1);
            this.setKelvin(kelvin);
        } else {
            this.kelvin = 0.0;
        }
    }
   
    public void setFahrenheit(double fahrenheit){
        if (fahrenheit >= ZERO_FAHRENHEIT) {
            double kelvin = (fahrenheit + 459.67) * 5 / 9;
            this.setKelvin(kelvin);
        } else {
            this.kelvin = 0.0;
        }
    }
    
    
    //  Getter  ****************
    public double getKelvin(){
        return this.kelvin;
    }
    
    public double getCelsius(){
        return this.kelvin + ZERO_CELSIUS;
    }
    
    public double getFahrenheit(){
        return this.kelvin * 9/5 - 459.67;
    }
    
    /*
        Methoden von java.lang.Object überschreiben
    */
    
    @Override
    public boolean equals(Object o) {
        
        Conversion c = (Conversion) o;
        // System.out.println("THIS: " + this.getKelvin());
        // System.out.println("c: " + c.getKelvin());
        // return false;
        
        // Vergleich von Strings
        // return this.getKelvin() == c.getKelvin();
        
        // Vergleich von Doubles
        return Math.abs(this.getKelvin() - c.getKelvin()) < 0.001;
    }
    
}
