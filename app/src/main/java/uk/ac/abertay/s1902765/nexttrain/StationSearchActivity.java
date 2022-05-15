package uk.ac.abertay.s1902765.nexttrain;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.abertay.s1902765.nexttrain.databinding.ActivityStationSearchBinding;

public class StationSearchActivity extends AppCompatActivity {

    ObservableField<List<StationItem>> stationResults = new ObservableField<>();
    ObservableField<String> searchTerm = new ObservableField<>();
    StationSearchActivity_ViewModel stationSearchModel;
    ActivityStationSearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stationSearchModel = new ViewModelProvider(this).get(StationSearchActivity_ViewModel.class);
        // Inflate view and obtain an instance of the binding class.
        binding = ActivityStationSearchBinding.inflate(getLayoutInflater());
        // Specify the current activity as the lifecycle owner.
        binding.setLifecycleOwner(this);
        binding.setStationSearchModel(stationSearchModel);
        setContentView(binding.getRoot());
    }

    androidx.appcompat.widget.SearchView.OnQueryTextListener searchListener =  new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            if (query.equals("")){
                stationSearchModel.noStations();
                binding.stationSearchSearchRecyclerView.setVisibility(View.GONE);
                //binding.liveTrainsContentScrollView.setVisibility(View.VISIBLE);
                return false;
            }
            binding.stationSearchSearchRecyclerView.setVisibility(View.VISIBLE);
            //binding.liveTrainsContentScrollView.setVisibility(View.GONE);
            stationSearchModel.stationSearchTerm.set(query);
            stationSearchModel.executeSearch();
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if (newText.equals("")){
                stationSearchModel.noStations();
                binding.stationSearchSearchRecyclerView.setVisibility(View.GONE);
                //binding.liveTrainsContentScrollView.setVisibility(View.VISIBLE);
                return false;
            }
            stationSearchModel.stationSearchTerm.set(newText);
            binding.stationSearchSearchRecyclerView.setVisibility(View.VISIBLE);
            //binding.liveTrainsContentScrollView.setVisibility(View.GONE);
            stationSearchModel.executeSearch();
            return false;
        }
    };

    @BindingAdapter("data")
    public static void setTestRecyclerProperties(RecyclerView view, List<StationItem> items){
        ((LiveTrainsTestRecyclerViewAdapter)view.getAdapter()).setData(items);
    }

    private void performSearch(){

    }
}

class StationSearchResultsRecyclerViewAdapter extends RecyclerView.Adapter<StationSearchResultsRecyclerViewAdapter.ViewHolder> {

    private List<StationItem> localDataset;

    public StationSearchResultsRecyclerViewAdapter(){
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
                //Intent openStationActivity = new Intent(view.getContext(), StationActivity.class);
                //openStationActivity.putExtra("stationCode", localDataset.get(viewHolder.getAdapterPosition()).CrsCode);
                //openStationActivity.putExtra("stationName", localDataset.get(viewHolder.getAdapterPosition()).Name);
                //view.getContext().startActivity(openStationActivity);
                //TODO Make this activity return the station which was searched for and found
            }
        });
    }

    @Override
    public int getItemCount() {
        return localDataset.size();
    }
}

