package uk.ac.abertay.s1902765.nexttrain;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.abertay.s1902765.nexttrain.databinding.FragmentLiveTrainsBinding;
import uk.ac.abertay.s1902765.nexttrain.stationActivityGroup.StationActivity;


public class LiveTrains extends Fragment implements View.OnClickListener {

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
        //Forgot to add this one line, viewmodels now work correctly
        binding.setLiveTrainsModel(liveTrainsModel);
        View view = binding.getRoot();

        binding.liveTrainsSearchField.setOnQueryTextListener(searchListener);
        binding.liveTrainsSearchRecyclerView.setAdapter(new LiveTrainsTestRecyclerViewAdapter());
        binding.testButtonRefreshLocation.setOnClickListener(this);
        binding.liveTrainsNearestStationButton.setOnClickListener(this);
        return view;
    }

    public void performStationSearch(String query){

    }

    androidx.appcompat.widget.SearchView.OnQueryTextListener searchListener =  new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            if (query.equals("")){
                liveTrainsModel.noStations();
                binding.liveTrainsSearchRecyclerView.setVisibility(View.GONE);
                binding.liveTrainsContentScrollView.setVisibility(View.VISIBLE);
                return false;
            }
            binding.liveTrainsSearchRecyclerView.setVisibility(View.VISIBLE);
            binding.liveTrainsContentScrollView.setVisibility(View.GONE);
            liveTrainsModel.stationSearchTerm.set(query);
            liveTrainsModel.executeSearch();
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if (newText.equals("")){
                liveTrainsModel.noStations();
                binding.liveTrainsSearchRecyclerView.setVisibility(View.GONE);
                binding.liveTrainsContentScrollView.setVisibility(View.VISIBLE);
                return false;
            }
            liveTrainsModel.stationSearchTerm.set(newText);
            binding.liveTrainsSearchRecyclerView.setVisibility(View.VISIBLE);
            binding.liveTrainsContentScrollView.setVisibility(View.GONE);
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.livetrains_fragment_menu, menu);
        //super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //TODO fix this, app is not registering the refresh menu item click event
        switch(item.getItemId()){
            case R.id.menu_refresh:{
                Toast.makeText(getActivity().getApplicationContext(), "Refreshing...", Toast.LENGTH_SHORT).show();
                liveTrainsModel.setNearestStation();
                return true;
            }
            default:{
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    public void onClick(View view) {
        //TODO should probably just make use of the binding stuff set up earlier, this is a bit archaic
        switch (view.getId()){
            case R.id.test_buttonRefreshLocation:{
                Toast.makeText(getActivity().getApplicationContext(), "Refreshing...", Toast.LENGTH_SHORT).show();
                return;
            }
            case R.id.liveTrains_nearestStationButton:{
                if (liveTrainsModel.nearestStation != null){
                    Intent openStationActivity = new Intent(view.getContext(), StationActivity.class);
                    openStationActivity.putExtra("stationCode", liveTrainsModel.nearestStation.get().CrsCode);
                    openStationActivity.putExtra("stationName", liveTrainsModel.nearestStation.get().Name);
                    view.getContext().startActivity(openStationActivity);
                }
                return;
            }
            default:
                return;
        }
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
        private final Context context;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            stationName = (TextView) view.findViewById(R.id.stationName);
            crsCode = (TextView) view.findViewById(R.id.stationCrs);
            context = itemView.getContext();
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
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openStationActivity = new Intent(view.getContext(), StationActivity.class);
                openStationActivity.putExtra("stationCode", localDataset.get(viewHolder.getAdapterPosition()).CrsCode);
                openStationActivity.putExtra("stationName", localDataset.get(viewHolder.getAdapterPosition()).Name);
                view.getContext().startActivity(openStationActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return localDataset.size();
    }
}