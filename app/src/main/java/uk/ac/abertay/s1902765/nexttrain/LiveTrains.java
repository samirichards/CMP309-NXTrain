package uk.ac.abertay.s1902765.nexttrain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

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

        //This doesn't work
        //TODO Make sure you figure out how to do list adapters properly
        //Maybe check old code from your mobile dev in the studio, there you had a recyclerview (in C# but still)
        //CustomTestAdapter testAdapter = new CustomTestAdapter(this, liveTrainsModel.stations.getValue());
        //binding.testListView.setAdapter(testAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLiveTrainsBinding.inflate(inflater, container, false);
        //Forgot to add this one line, viewmodels now work correctly
        binding.setLiveTrainsModel(liveTrainsModel);
        View view = binding.getRoot();

        binding.liveTrainsSearchField.setOnQueryTextListener(searchListener);
        binding.liveTrainsTestRecyclerView.setAdapter(new LiveTrainsTestRecyclerViewAdapter());
        return view;
    }

    public void performStationSearch(String query){

    }

    androidx.appcompat.widget.SearchView.OnQueryTextListener searchListener =  new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            if (query.equals("")){
                liveTrainsModel.allStations();
                return false;
            }
            liveTrainsModel.stationSearchTerm.set(query);
            liveTrainsModel.executeSearch();
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if (newText.equals("")){
                liveTrainsModel.allStations();
                return false;
            }
            liveTrainsModel.stationSearchTerm.set(newText);
            liveTrainsModel.executeSearch();
            return false;
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @BindingAdapter("data")
    public static void setTestRecyclerProperties(RecyclerView view, List<StationItem> items){
        ((LiveTrainsTestRecyclerViewAdapter)view.getAdapter()).setData(items);
    }
}

class LiveTrainsTestRecyclerViewAdapter extends RecyclerView.Adapter<LiveTrainsTestRecyclerViewAdapter.ViewHolder> {

    private List<StationItem> localDataset;

    public LiveTrainsTestRecyclerViewAdapter(){
        //localDataset = stationItems;
    }

    public void setData(List<StationItem> items){
        localDataset = items;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView stationName;
        public final TextView crsCode;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            stationName = (TextView) view.findViewById(R.id.stationName);
            crsCode = (TextView) view.findViewById(R.id.stationCrs);
        }

        public TextView getStationName() {
            return stationName;
        }

        public TextView getCrsCode(){
            return crsCode;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.station_searchitem_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.getStationName().setText(localDataset.get(position).Name);
        viewHolder.getCrsCode().setText(localDataset.get(position).CrsCode);
    }

    @Override
    public int getItemCount() {
        return localDataset.size();
    }
}