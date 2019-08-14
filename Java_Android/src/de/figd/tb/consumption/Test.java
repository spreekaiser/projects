/*
 Datei zum Testen der Berechnungen
 */
package de.figd.tb.consumption;


public class Test {
    
    private double usage, usage100;
   
    public static void main(String[] args) {
        
        Test t = new Test();
        t.one();
        t.two();
        t.three();
        t.four();
    }
    
    public void one() {
        System.out.println("Einfacher Konstruktor:");
        Consumption c = new Consumption();
        c.setFuel(23);
        c.setDistance(250);
        this.usage = c.getConsumption();
        System.out.println("Durchschnittlicher Verbrauch in Liter: " + this.usage);
    }
    
    public void two() {
        System.out.println("Konstruktor mit double für fuel:");
        Consumption c = new Consumption(23);
        Double way = (c.getFuel() / this.usage);
        System.out.println("Erreichbare Kilometer mit Tankfüllung: " + way);
    }
    
    public void three() {
        System.out.println("Einfacher Konstruktor für Verbrauch je 100km:");
        Consumption c = new Consumption();
        c.setFuel(34);
        c.setDistance(400);
        this.usage100 = c.getConsumptionPer100();
        System.out.println("Durchschnitt je 100 km: " + c.getConsumptionPer100());
    }
    
    public void four() {
        System.out.println("Konstruktor mit double fuel je 100 km:");
        Consumption c = new Consumption(34);
        Double way = (c.getFuel() * 100) / this.usage100;
        System.out.println("Erreichbare Kilometer mit Tankfüllung bei Durchschnittsverbrauch: "
            + way);
    }
    
}
