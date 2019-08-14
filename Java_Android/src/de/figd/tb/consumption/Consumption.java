/*
    Berechnenungen für den durchschnittlichen Kraftstoffverbauch eines Fahrzeuges
 */
package de.figd.tb.consumption;


public class Consumption {
    
    // Instanzen
    private double fuel, distance;
    
    // Konstruktoren
    public Consumption() {}
    
    public Consumption(double fuel) {
        this.fuel = fuel;
    }
    
    
    // Setter
    public void setFuel(double fuel) {
        this.fuel = fuel;
    }
    
    public void setDistance(double distance) {
        this.distance = distance;
    }
    
    
    // Getter
    public double getFuel() {
        return this.fuel;
    }
    
    public double getDistance() {
        return this.distance;
    }
    
    public double getConsumption() {
        return (this.fuel / this.distance); 
    }
    
    public double getConsumptionPer100() {
        return (this.fuel * 100) / this.distance;
    }
    
}
