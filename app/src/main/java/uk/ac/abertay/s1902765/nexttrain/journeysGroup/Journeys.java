package uk.ac.abertay.s1902765.nexttrain.journeysGroup;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import uk.ac.abertay.s1902765.nexttrain.databinding.FragmentJourneysBinding;

public class Journeys extends Fragment {
    JourneysFragmentViewModel model;
    FragmentJourneysBinding binding;

    public Journeys() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(JourneysFragmentViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentJourneysBinding.inflate(inflater, container, false);
        binding.setViewmodel(model);

        binding.journeysNewJourneyFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startNewJourneysIntent = new Intent(getContext(), AddJourneyActivity.class);
                startActivity(startNewJourneysIntent);
                //TODO add reveal transition here
            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();

    }

    //TODO implement Recyclerview adapter here after adding ability for journeys to be saved
}