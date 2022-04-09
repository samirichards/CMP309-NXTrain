package uk.ac.abertay.s1902765.nexttrain;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.abertay.s1902765.nexttrain.databinding.FragmentLiveTrainsBinding;


public class LiveTrains extends Fragment {

    private LiveTrains_Model liveTrainsModel;
    private FragmentLiveTrainsBinding binding;

    public LiveTrains() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        liveTrainsModel = new ViewModelProvider(this).get(LiveTrains_Model.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLiveTrainsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
        //TODO make the databinding work properly
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}