/*  Projektarbeit von Thoralf Barth am 22. Mai 2019
    
    Währungsrechner
 */

package de.figd.tb.money;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class XMoney extends JFrame implements ActionListener {
    
    private JTextField fromValueField, intoValueField;
    private JButton calcButton, clearButton, closeButton;
    private JComboBox fromValueBox, intoValueBox;
    
    
    public XMoney() {
        this.setBounds(500, 250, 400, 300);
        this.setTitle("Währungsrechner");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
    
    
    public static void main(String[] args) {
        System.out.println("Währungsrechner");
        
        XMoney xm = new XMoney();
        // xm.test();
        xm.init();
    }
    
    public void test() {
        // System.out.println("Als Tester den Test testen");
        
        Valuta v = new Valuta();
        v.setEuroAmount(17);
        
        // v.setDollarAmount(v.covertIntoAmount(v.getEuroAmount(), v.getRate()));
        System.out.printf("Euro: %.2f %n", v.getEuroAmount());
        System.out.printf("Dollar: %.2f %n", v.getDollarAmount());
        // System.out.printf("Kurs: %.2f %n", v.getRate());
    }
    
    
    public void init() {
        
        JLabel headLine = this.labelCreator("Zwischen verschiedenen Währungen umrechnen", 150);
        JPanel inputPanel = this.inputPanelCreator();
        JPanel buttonPanel = this.buttonPanelCreator();
        
        this.add(headLine, BorderLayout.PAGE_START);
        this.add(inputPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.PAGE_END);
        
        this.pack();
        this.setVisible(true);
    }
    
    
    
    public JPanel inputPanelCreator() {
        JPanel inputPanel = new JPanel(new GridLayout(2,1));
        
        JLabel fromValueLabel = labelCreator("Ausgangsbetrag: ", 100);
        this.fromValueField = textFieldCreator(100);
        this.fromValueBox = selectBoxCreator("Euro", "Dollar", "Yen", "MX-Peso");
        
        JLabel intoValueLabel = labelCreator("Zielbetrag: ", 100);
        this.intoValueField = textFieldCreator(100);
        this.intoValueBox = selectBoxCreator("Euro", "Dollar", "Yen", "MX-Peso");
        
        inputPanel.add(fromValueLabel);
        inputPanel.add(this.fromValueField);
        inputPanel.add(fromValueBox);
        
        inputPanel.add(intoValueLabel);
        inputPanel.add(this.intoValueField);
        inputPanel.add(intoValueBox);
        return inputPanel;
    }
    
    public JLabel labelCreator(String notation, int width) {
        JLabel label = new JLabel(notation);
        label.setMinimumSize(new Dimension(width, 20));
        return label;
    }
    
    public JTextField textFieldCreator(int width) {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(width, 20));
        textField.setHorizontalAlignment(JTextField.RIGHT);
        return textField;
    }
    
    public JComboBox selectBoxCreator(String... valutas) {
        JComboBox selectBox = new JComboBox(valutas);
        return selectBox;
    }
    
    public JPanel buttonPanelCreator() {
        JPanel buttonPanel = new JPanel();
        
        EmptyBorder borderLine = new EmptyBorder(5, 5, 5, 5);
        buttonPanel.setBorder(borderLine);
        
        this.calcButton = buttonCreator("Berechnen");
        this.calcButton.addActionListener(this);
        this.clearButton = buttonCreator("Leeren");
        this.clearButton.addActionListener(this);
        this.closeButton = buttonCreator("Beenden");
        this.closeButton.addActionListener(this);
        
        buttonPanel.add(this.calcButton);
        buttonPanel.add(this.clearButton);
        buttonPanel.add(this.closeButton);
        return buttonPanel;
    }
    
    public JButton buttonCreator(String notation) {
        JButton button = new JButton(notation);
        button.setActionCommand(notation);
        return button;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        switch (ae.getActionCommand()) {
            case "Berechnen" :
                this.calc();
                break;
            case "Leeren" : 
                this.clear();
                break;
            case "Beenden" :
                this.close();
                break;
        }
    }
    
    public void calc() {
        String sourceValuta = (String) this.fromValueBox.getSelectedItem();
        String endValuta = (String) this.intoValueBox.getSelectedItem();
        double sourceSum = Double.parseDouble(this.fromValueField.getText());
        
        Valuta v = new Valuta();
        double endSum = v.covertIntoAmount(sourceSum, sourceValuta, endValuta);
        
        this.showResult(sourceValuta, endValuta, sourceSum, endSum);
    }
    
    public void showResult(String sourceValuta, String endValuta, double sourceSum, double endSum) {
        System.out.println("Ausgangswährung: " + sourceValuta);
        System.out.println("Zielwährung: " + endValuta);
        System.out.println("Ausgangswert: " + sourceSum);
        System.out.printf("Zielwert: %.2f %n", endSum);
        
        String outputSum = String.format("%.2f", endSum);
        this.intoValueField.setText(outputSum);
        
    }
    
    
    public void clear() {
        this.fromValueField.setText("");
        this.intoValueField.setText("");
    }
    
    public void close() {
        System.exit(0);
    }
    
}

        