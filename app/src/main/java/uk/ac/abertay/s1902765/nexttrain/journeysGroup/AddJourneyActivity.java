package uk.ac.abertay.s1902765.nexttrain.journeysGroup;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import uk.ac.abertay.s1902765.nexttrain.databinding.ActivityAddJourneyBinding;

public class AddJourneyActivity extends AppCompatActivity {

    ActivityAddJourneyBinding binding;
    AddJourneyActivityViewmodel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(AddJourneyActivityViewmodel.class);
        binding = ActivityAddJourneyBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        binding.setViewmodel(model);

        setContentView(binding.getRoot());
    }
}