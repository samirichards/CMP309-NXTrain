package uk.ac.abertay.s1902765.nexttrain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import uk.ac.abertay.s1902765.nexttrain.databinding.ActivityStationBinding;

public class StationActivity extends AppCompatActivity {

    private StationActivity_Model model;
    private ActivityStationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStationBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());
        model = new ViewModelProvider(this).get(StationActivity_Model.class);
        getViewBundledInfo();

        binding.stationActivityTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Bundle extras = getIntent().getExtras();
                model.SetStation(extras.getString("stationName"), extras.getString("stationCode"));
                model.notifyChange();
                binding.invalidateAll();
            }
        });
        Toast.makeText(this, "Model Station value: " + model.StationName.get(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Model CRS value: " + model.StationCode.get(), Toast.LENGTH_SHORT).show();
    }

    private void getViewBundledInfo() {
        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            model.SetStation(extras.getString("stationName"), extras.getString("stationCode"));
            model.notifyChange();
        }
    }
}