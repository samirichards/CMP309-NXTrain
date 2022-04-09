package uk.ac.abertay.s1902765.nexttrain;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import java.util.List;
import java.util.Locale;

import uk.ac.abertay.s1902765.nexttrain.databinding.FragmentLiveTrainsBinding;
import uk.ac.abertay.s1902765.nexttrain.databinding.StationSearchitemLayoutBinding;


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
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

class TestStationsRecyclerViewAdapter extends RecyclerView.Adapter{
    private List<String> items;

    private class StationViewHolder extends RecyclerView.ViewHolder{

        StationSearchitemLayoutBinding binding;//Name of the test_list_item.xml in camel case + "Binding"

        public StationViewHolder(StationSearchitemLayoutBinding b){
            super(b.getRoot());
            binding = b;
        }
    }

    StationSearchitemLayoutBinding stationBinding;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StationViewHolder(StationSearchitemLayoutBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String text = String.format(Locale.ENGLISH, "%s %d", items.get(position), position);

        //TODO Ugh, finish this later
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}