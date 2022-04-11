package uk.ac.abertay.s1902765.nexttrain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;
import java.util.Observable;

public class StationSearchActivity extends AppCompatActivity {

    ObservableField<List<StationItem>> stationResults = new ObservableField<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_search);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //doMySearch(query);
        }
    }
}