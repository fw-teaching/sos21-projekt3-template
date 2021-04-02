package fi.arcada.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /* en arrayList i vilken växelkursvärdena sparas */
    ArrayList<Double> currencyValues;

    String from = "2020-03-11";
    String to = "2021-03-11";
    String currency = "SEK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Använd currencyValues för att bygga upp grafen   */
        currencyValues = getCurrencyValues();

        System.out.println(currencyValues.toString());
    }


    /* Färdig metod som hämtar växelkurserna från CurrencyApi klassen */
    public ArrayList<Double> getCurrencyValues() {

        CurrencyApi api = new CurrencyApi();
        ArrayList<Double> currencyData = null;

        // Funkar inte längre utan inloggning: String urlString = "https://api.exchangeratesapi.io/history?start_at=%s&end_at=%s&symbols=%s";
        String urlString = String.format("https://api.exchangerate.host/timeseries?start_date=%s&end_date=%s&symbols=%s",
                from.trim(),
                to.trim(),
                currency.trim());

        try {
            String jsonData = api.execute(urlString).get();

            if (jsonData != null) {
                currencyData = api.getCurrencyData(jsonData, currency.trim());
                Toast.makeText(getApplicationContext(), String.format("Hämtade %s valutakursvärden från servern", currencyData.size()), Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Kunde inte hämta växelkursdata från servern: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return currencyData;
    }
}