/*
    Objektberechnungen der Währungen
 */
package de.figd.tb.money;


import java.util.Arrays;

public class Valuta {
    
    private final double[] rate = {1, 0.89, 0.0081, 0.047};
    private String[] valutas = {"Euro", "Dollar", "Yen", "MX-Peso"};
    
    private double euro;
    
    
    // Setter
    public void setEuroAmount(double euro) {
        this.euro = euro;
    }
    public void setDollarAmount(double dollar) {
        this.euro = dollar * rate[1];
    }
    public void setYenAmount(double yen) {
        this.euro = yen * rate[2];
    }
    public void setMXPeso(double mxPeso) {
        this.euro = mxPeso * rate[3];
    }
    
    
    // Getter
    public double getEuroAmount() {
        return this.euro;
    }
    public double getDollarAmount() {
        return this.euro / rate[1];
    }
    public double getYenAmount() {
        return this.euro / rate[2];
    }
    public double getMXPeso() {
        return this.euro / rate[3];
    }
    
    

    
    public double covertIntoAmount(double sourceSum, String sourceValuta, String endValuta) {
        double endSum;
        
        int sourceIndex = Arrays.asList(valutas).indexOf(sourceValuta);
        // System.out.println(sourceIndex);
        int endIndex = Arrays.asList(valutas).indexOf(endValuta);
        
        endSum = (sourceSum * this.rate[sourceIndex]) / this.rate[endIndex];
        
        return endSum;
    }
    
    
    
}
