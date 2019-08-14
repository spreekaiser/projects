package de.figd.tb.mysqldatabase;

import android.os.AsyncTask;
import android.util.Log;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.net.ProtocolException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;



public class MyRequest extends AsyncTask<HashMap<String, String>, Integer, String> {

    // 1. Version mit Übergabe-Parameter
    // private MainActivity listener;


    // 2. Version mit Interface
    private RequestListener listener;

    public interface RequestListener {

        public void onRequestReady(String content);

    }


    // 1. Version
    // Konstruktor, um von MainActivity Daten zu empfangen
    // public MyRequest(MainActivity listener) {
    //    this.listener = listener;
    // }

    // 2. Version
    // Konstruktor um via Interface daten auszutauschen
    public MyRequest(RequestListener listener) {
        this.listener = listener;
    }

    @Override
    public String doInBackground(HashMap<String, String>... params) {
        // Log.i("async", "started");


        String link = "https://de.wikipedia.org/wiki/Wikipedia:Hauptseite";
        link = "http://192.168.0.28/test.php";

        String content = "";
        HashMap<String, String> data = new HashMap<>();

        if (params.length == 1) {
            data = params[0];

        }

        try {
            URL url = new URL(link);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            if (data.size() > 0) {
                this.sendPostData(urlConnection, data);
            }

            content = this.getContent(urlConnection);
            Log.i("content", content);


        } catch (MalformedURLException mfue) {
            Log.i("exception", mfue.getMessage());
        } catch (UnsupportedEncodingException usee) {
            Log.i("exception", usee.getMessage());
        } catch (ProtocolException pe) {
            Log.i("exception", pe.getMessage());
        } catch (IOException ioe) {
            Log.i("exception", ioe.getMessage());
        }

        return content;
    }

    /*
        onPreExcecute();
        onProgressUpdate();
        onPostExecute();

     */

    @Override
    public void onPostExecute(String content) {
        Log.i("ready", content);

        // Ergebnis der Daten wieder zu MainActivity zurücksenden
        this.listener.onRequestReady(content);
    }

    public void sendPostData(HttpURLConnection urlConnection, HashMap<String, String> data)
                    throws UnsupportedEncodingException, ProtocolException, IOException {
        // Log.i("post", "send");

        String value ="";
        String query = "";
        StringBuilder sb = new StringBuilder();

        int count = 1;
        byte[] postData;


        for (String key : data.keySet()) {
            // Log.i("key", key);

            value = data.get(key);
            // Log.i("value", value);

            // Umlaute in query umwandeln
            key = URLEncoder.encode(key, "UTF-8");
            value = URLEncoder.encode(value, "UTF-8");

            sb.append(key);
            sb.append("=");
            sb.append(value);

            if (count < data.size()) {
                sb.append("&");
            }
            count += 1;
        }

        query = sb.toString();
        postData = query.getBytes(StandardCharsets.UTF_8);
        Log.i("query", query);


        /*****  Die Verbindung konfigurieren  *****/

        urlConnection.setDoInput(true);
        urlConnection.setRequestMethod("POST");

        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        urlConnection.setRequestProperty("charset", "utf-8");
        urlConnection.setRequestProperty("Content-Length", Integer.toString(postData.length));

        urlConnection.setUseCaches(false);

        /*****************************************/

        DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
        dos.write(postData);

    }

    public String getContent(HttpURLConnection urlConnection) throws IOException {
        String content = "xyz";
        StringBuilder sb = new StringBuilder();
        String line = "";

        BufferedInputStream bis = new BufferedInputStream(urlConnection.getInputStream());
        InputStreamReader ips = new InputStreamReader(bis);
        BufferedReader br = new BufferedReader(ips);



        while ((line = br.readLine()) != null ) {

            sb.append(line);

        }
        content = sb.toString();
        return content;
    }

}
