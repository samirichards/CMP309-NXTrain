package uk.ac.abertay.s1902765.nexttrain;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import uk.ac.abertay.s1902765.nexttrain.databinding.FragmentLiveTrainsBinding;


public class LiveTrains extends Fragment {

    private LiveTrains_Model liveTrainsModel;
    private FragmentLiveTrainsBinding binding;
    private SearchView liveTrainsSearchView;

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
        //Forgot to add this one line, viewmodels now work correctly
        binding.setLiveTrainsModel(liveTrainsModel);
        View view = binding.getRoot();

        final CustomTestAdapter adapter = new CustomTestAdapter(this, liveTrainsModel.stations.getValue());
        binding.testListView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

