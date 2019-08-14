package de.figd.tb.mysqldatabase;

public class Currency {

    private String name;
    private double rate;


    // Konstruktor
    public Currency(String name, String rate) {
        this.name = name;
        this.rate = Double.parseDouble(rate);
    }


    // toString()-Methode überschreiben
    @Override
    public String toString() {
        return String.format("%s : %f %n", this.name, this.rate);
    }


    // Setter
    public void setName(String name) {
        this.name = name;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }


    // Getter
    public String getName() {
        return this.name;
    }

    public double getRate() {
        return this.rate;
    }



}
