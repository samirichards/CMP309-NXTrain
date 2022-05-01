package uk.ac.abertay.s1902765.nexttrain.stationActivityGroup;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import uk.ac.abertay.s1902765.nexttrain.R;
import uk.ac.abertay.s1902765.nexttrain.RttApi.TrainService;
import uk.ac.abertay.s1902765.nexttrain.databinding.FragmentStationActivityListServicesBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_StationActivity_ListServices#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_StationActivity_ListServices extends Fragment {
    private static final String ARG_STATION_CODE = "mStationCode";
    private static final String ARG_IS_ARRIVAL = "mIsArrival";
    private static final String ARG_TIMEOFFSET = "mTimeOffset";
    private static final String ARG_TITLE = "mTitle";
    private Fragment_StationActivity_ListServices_ViewModel model;
    private FragmentStationActivityListServicesBinding binding;

    private String mStationCode;
    private Boolean mIsArrival;
    private int mTimeOffset;
    private String mTitle;

    public Fragment_StationActivity_ListServices() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param stationCode The Unique code of the station for which services are to be detailed.
     * @param isArrival True if the fragment is to report only arrivals to the station, false if only departures.
     * @param minuteOffset Time offset from current local time in minutes (+ or -)
     * @return A new instance of fragment fragment_stationActivity_listServices.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_StationActivity_ListServices newInstance(String stationCode, Boolean isArrival, int minuteOffset) {
        Fragment_StationActivity_ListServices fragment = new Fragment_StationActivity_ListServices();
        Bundle args = new Bundle();
        args.putString(ARG_STATION_CODE, stationCode);
        args.putBoolean(ARG_IS_ARRIVAL, isArrival);
        args.putInt(ARG_TIMEOFFSET, minuteOffset);
        if (isArrival){
            args.putString(ARG_TITLE, "Arrivals");
        }
        else{
            args.putString(ARG_TITLE, "Departures");
        }
        fragment.setArguments(args);
        return fragment;
    }

    public String GetTitle(){
        return mTitle;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStationCode = getArguments().getString(ARG_STATION_CODE);
            mIsArrival = getArguments().getBoolean(ARG_IS_ARRIVAL);
            mTimeOffset = getArguments().getInt(ARG_TIMEOFFSET);
            mTitle = getArguments().getString(ARG_TITLE);
        }
        model = new ViewModelProvider(this).get(Fragment_StationActivity_ListServices_ViewModel.class);
        model.setModelParameters(mStationCode, mIsArrival);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStationActivityListServicesBinding.inflate(inflater, container, false);
        binding.setViewmodel(model);
        binding.fragmentStationActivityListServicesRecyclerView.setAdapter(new StationActivity_ListServicesRecyclerAdapter(mIsArrival));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.fragmentStationActivityListServicesRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        binding.fragmentStationActivityListServicesRecyclerView.addItemDecoration(dividerItemDecoration);
        View view = binding.getRoot();
        return view;
    }

    @BindingAdapter("data")
    public static void setListServicesAdapterProperties(RecyclerView view, List<TrainService> items){
        ((StationActivity_ListServicesRecyclerAdapter)view.getAdapter()).setData(items);
    }
}

class StationActivity_ListServicesRecyclerAdapter extends RecyclerView.Adapter<StationActivity_ListServicesRecyclerAdapter.ViewHolder> {

    private List<TrainService> localDataset;
    private boolean mIsArrival = false;

    public StationActivity_ListServicesRecyclerAdapter(Boolean isArrival){
        localDataset = new ArrayList<TrainService>();
        mIsArrival = isArrival;
    }

    public void setData(List<TrainService> items){
        localDataset = items;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView serviceDestination;
        public final TextView serviceHeadcode;
        public final TextView serviceStatusIndicator;
        public final TextView serviceTime;
        public final TextView servicePlatformIndicator;
        public final TextView serviceVia;
        private final Context context;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            serviceDestination = (TextView) view.findViewById(R.id.serviceDestination);
            serviceHeadcode = (TextView) view.findViewById(R.id.serviceHeadcode);
            serviceStatusIndicator = (TextView) view.findViewById(R.id.serviceStatusIndicator);
            serviceTime = (TextView) view.findViewById(R.id.serviceTimeIndicator);
            servicePlatformIndicator = (TextView) view.findViewById(R.id.servicePlatformIndicator);
            serviceVia = (TextView) view.findViewById(R.id.serviceVia);
            context = itemView.getContext();
        }

        public TextView getServiceDestination() {
            return serviceDestination;
        }
        public TextView getServiceHeadcode(){
            return serviceHeadcode;
        }
        public TextView getServiceStatusIndicator(){return serviceStatusIndicator;}
        public TextView getServiceTime(){return serviceTime;}
        public TextView getServicePlatformIndicator(){return servicePlatformIndicator;}
        public TextView getServiceVia(){return serviceVia;}
    }

    @NonNull
    @Override
    public StationActivity_ListServicesRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.station_activity_servicedetail_item, parent, false);
        return new StationActivity_ListServicesRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StationActivity_ListServicesRecyclerAdapter.ViewHolder viewHolder, int position) {
        if (mIsArrival){
            viewHolder.getServiceDestination().setText(localDataset.get(position).locationDetail.origin.get(0).description);
            viewHolder.getServiceHeadcode().setText(localDataset.get(position).trainIdentity);
            if(localDataset.get(position).serviceType == "bus"){
                viewHolder.getServicePlatformIndicator().setText("Bus");
            }
            else{
                viewHolder.getServicePlatformIndicator().setText("Platform " + localDataset.get(position).locationDetail.platform);
            }
            viewHolder.getServiceTime().setText(localDataset.get(position).locationDetail.gbttBookedArrival);
            if(localDataset.get(position).locationDetail.gbttBookedArrival == localDataset.get(position).locationDetail.gbttBookedArrival){
                viewHolder.getServiceStatusIndicator().setText("On Time");
            }
            else{
                viewHolder.getServiceStatusIndicator().setText("TBC");
            }
            //TODO figure out how to display if a service is running Via a particular station
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Intent openStationActivity = new Intent(view.getContext(), StationActivity.class);
                    //openStationActivity.putExtra("stationCode", localDataset.get(viewHolder.getAdapterPosition()).CrsCode);
                    //openStationActivity.putExtra("stationName", localDataset.get(viewHolder.getAdapterPosition()).Name);
                    //view.getContext().startActivity(openStationActivity);
                    Toast.makeText(view.getContext(), localDataset.get(viewHolder.getAdapterPosition()).locationDetail.origin.get(0).description, Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            viewHolder.getServiceDestination().setText(localDataset.get(position).locationDetail.destination.get((localDataset.get(position).locationDetail.destination.size()-1)).description);
            //TODO, figure out how to deal with trains like on Merseyrail which go around a loop and come back to their orign
            viewHolder.getServiceHeadcode().setText(localDataset.get(position).trainIdentity);
            if(localDataset.get(position).serviceType == "bus"){
                viewHolder.getServicePlatformIndicator().setText("Bus");
            }
            else{
                viewHolder.getServicePlatformIndicator().setText("Platform " + localDataset.get(position).locationDetail.platform);
            }
            viewHolder.getServiceTime().setText(localDataset.get(position).locationDetail.gbttBookedDeparture);
            if(localDataset.get(position).locationDetail.gbttBookedDeparture == localDataset.get(position).locationDetail.realtimeDeparture){
                viewHolder.getServiceStatusIndicator().setText("On Time");
            }
            else{
                viewHolder.getServiceStatusIndicator().setText("TBC");
            }
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Intent openStationActivity = new Intent(view.getContext(), StationActivity.class);
                    //openStationActivity.putExtra("stationCode", localDataset.get(viewHolder.getAdapterPosition()).CrsCode);
                    //openStationActivity.putExtra("stationName", localDataset.get(viewHolder.getAdapterPosition()).Name);
                    //view.getContext().startActivity(openStationActivity);
                    Toast.makeText(view.getContext(), localDataset.get(viewHolder.getAdapterPosition()).locationDetail.destination.get(0).description, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (localDataset != null){
            return this.localDataset.size();
        }
        else {
            return 0;
        }
    }
}