package de.figd.tb.mysqldatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.TextView;
import android.util.Log;
import java.util.ArrayList;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements
                    MyRequest.RequestListener, MyCurrencyRequest.RequestListener {


    private TextView answerField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initButtons();

        this.answerField = this.findViewById(R.id.answerField);

    }

    public void initButtons() {

        Button connectButton = this.findViewById(R.id.connectButton);
        connectButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // Log.i("button", "klicki, klicki");

                MainActivity.this.connectToSQL();
            }

        });

        Button currencyButton = this.findViewById(R.id.currencyButton);
        currencyButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.getCurrency();
            }
        });

    }

    public void getCurrency() {
        // Log.i("currency", "ist started");

        // 1. Version ohne Class Currency
        // MyCurrencyRequest mcr = new MyCurrencyRequest();

        MyCurrencyRequest mcr = new MyCurrencyRequest(this);
        mcr.execute();
    }

    public void connectToSQL() {
        Log.i("connect", "verbunden mit SQL");

        MyRequest mr = new MyRequest(this);

        HashMap<String, String> data = new HashMap<>();
        data.put("name", "thoralf");
        data.put("zip", "10997");
        data.put("gender", "m");

        mr.execute(data);
    }


    // 1. Version ohne Class Currency
    // Daten aus MyRequest.java rüberholen
    @Override
    public void onRequestReady(String content) {
        // Log.i("qwertz", content);

        this.answerField.setText(content);

    }


    // 2. Version mit Class Currency
    @Override
    public void onRequestReady(ArrayList<Currency> currencies) {
        // Log.i("ready_test", currencies.toString() );

        ListView currencyListView = this.findViewById(R.id.currencyListView);
        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_expandable_list_item_1,
                currencies
        );

        currencyListView.setAdapter(adapter);

    }

}
