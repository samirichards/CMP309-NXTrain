package uk.ac.abertay.s1902765.nexttrain;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import uk.ac.abertay.s1902765.nexttrain.databinding.StationSearchitemLayoutBinding;

public class StationSearchActivity extends AppCompatActivity {

    ObservableField<List<StationItem>> stationResults = new ObservableField<>();
    ObservableField<String> searchTerm = new ObservableField<>();
    StationSearchActivity_ViewModel stationSearchModel;
    StationSearchitemLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stationSearchModel = new ViewModelProvider(this).get(StationSearchActivity_ViewModel.class);
        // Inflate view and obtain an instance of the binding class.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_station_search);
        // Specify the current activity as the lifecycle owner.
        binding.setLifecycleOwner(this);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        searchTerm.set(intent.getStringExtra(SearchManager.QUERY));
        performSearch();
    }

    private void performSearch(){

    }
}

