package de.figd.tb.mysqldatabase;

import android.os.AsyncTask;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import android.util.Log;


// 1. Version zum Testen ohne Class Currency
// public class MyCurrencyRequest extends AsyncTask<Void, Void, Void> {
public class MyCurrencyRequest extends AsyncTask<Void, Void, ArrayList<Currency>> {

    private RequestListener listener;


    public interface RequestListener {
        public void onRequestReady(ArrayList<Currency> currencies);
    }

    public MyCurrencyRequest(RequestListener listener) {
        this.listener = listener;

    }


    @Override
    // 1. Version ohne Class Curency
    // public Void doInBackground(Void... params) {
    public ArrayList<Currency> doInBackground(Void... params) {
        // Log.i("currency", "in the background");

        ArrayList<Currency> currencies = new ArrayList<>();
        String link =  "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";

        Document currencyXMLDoc = this.getXMLFromURL(link);

        currencies = this.xmlToRates(currencyXMLDoc);
        // Log.i("currencies", currencies.toString());

        // return null;
        return currencies;
    }


    @Override
    public void onPostExecute(ArrayList<Currency> currencies) {
        // Log.i("ready", currencies.toString());
        this.listener.onRequestReady(currencies);


    }


    // private void xmlToRates(Document xmlDoc) {
    private ArrayList<Currency> xmlToRates(Document xmlDoc) {
        // Log.i("currency", " in ToRates");

        ArrayList<Currency> currencies = new ArrayList<>();

        NodeList cubeList = xmlDoc.getElementsByTagName("Cube");
        Node cube;

        // Deklaration der Variablen der Schleife
        Element element;
        String currencyString, rateString;
        Double rate;

        Currency currency;

        for (int i = 0; i < cubeList.getLength(); i += 1) {
            cube = cubeList.item(i);
            // Log.i("currencyTest", cube.toString());

            element = (Element) cube;
            currencyString = element.getAttribute("currency");
            // Log.i("currency", currency);

            rateString = element.getAttribute("rate");

            if (! (currencyString.isEmpty() && rateString.isEmpty()) ) {
                // zum Testen der Funktionen - einfach
                // rate = Double.parseDouble(rateString);
                // Log.i("currency", currencyString + ": " + rateString);

                // zum Testen mit Class Currency
                currency = new Currency(currencyString, rateString);
                // Log.i("currency", currency.toString());

                currencies.add(currency);
                // Log.i("currencies", currencies.toString());

            }
        }

        Log.i("currency", String.valueOf(cubeList.getLength()));


        return currencies;
    }


    private Document getXMLFromURL(String link) {
        Document xmlDoc = null;

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;

        try {

            db = dbf.newDocumentBuilder();
            xmlDoc = db.parse(link);

        } catch (ParserConfigurationException pce) {
            Log.i("exception", pce.getMessage());
        } catch (SAXException saxe) {
            Log.i("exception", saxe.getMessage());
        } catch (IOException ioe) {
            Log.i("exception", ioe.getMessage());
        }

        // Log.i("xml", xmlDoc.toString());

        return xmlDoc;
    }


}
