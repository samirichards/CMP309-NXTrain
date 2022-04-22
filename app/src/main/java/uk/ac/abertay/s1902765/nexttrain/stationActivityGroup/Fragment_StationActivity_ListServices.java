package uk.ac.abertay.s1902765.nexttrain.stationActivityGroup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.thalesgroup.rtti._2013_11_28.token.types.AccessToken;
import com.thalesgroup.rtti._2017_10_01.ldb.GetBoardRequestParams;
import com.thalesgroup.rtti._2017_10_01.ldb.Ldb;
import com.thalesgroup.rtti._2017_10_01.ldb.*;
import com.thalesgroup.rtti._2017_10_01.ldb.StationBoardResponseType;
import com.thalesgroup.rtti._2017_10_01.ldb.types.ServiceItem;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import uk.ac.abertay.s1902765.nexttrain.R;
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
    AccessToken accessToken = new AccessToken();
    Ldb soap;

    private String mStationCode;
    private Boolean mIsArrival;
    private int mTimeOffset;
    private String mTitle;

    public Fragment_StationActivity_ListServices() {
        // Required empty public constructor
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
        Toast.makeText(getActivity().getApplicationContext(), mTitle + " fragment created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStationActivityListServicesBinding.inflate(inflater, container, false);
        binding.setFragmentListServicesModel(model);
        View view = binding.getRoot();

        return view;
    }
}