package uk.ac.abertay.s1902765.nexttrain.StationActivityGroup;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import uk.ac.abertay.s1902765.nexttrain.R;
import uk.ac.abertay.s1902765.nexttrain.databinding.ActivityStationBinding;

public class StationActivity extends AppCompatActivity {

    private StationActivity_Model model;
    private ActivityStationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(StationActivity_Model.class);
        binding = ActivityStationBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        binding.setStationActivityModel(model);
        setContentView(binding.getRoot());
        getViewBundledInfo();
        setSupportActionBar(binding.stationActivityMaterialToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void getViewBundledInfo() {
        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            model.SetStation(extras.getString("stationName"), extras.getString("stationCode"));
            model.notifyChange();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.stationactivity_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}