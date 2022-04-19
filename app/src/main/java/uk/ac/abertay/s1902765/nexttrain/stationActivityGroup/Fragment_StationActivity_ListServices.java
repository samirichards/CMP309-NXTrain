package uk.ac.abertay.s1902765.nexttrain.stationActivityGroup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import uk.ac.abertay.s1902765.nexttrain.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_StationActivity_ListServices#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_StationActivity_ListServices extends Fragment {
    private static final String ARG_STATION_CODE = "mStationCode";
    private static final String ARG_IS_ARRIVAL = "mIsArrival";
    private static final String ARG_TIMEOFFSET = "mTimeOffset";

    private String mStationCode;
    private Boolean mIsArrival;
    private int mTimeOffset;

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
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStationCode = getArguments().getString(ARG_STATION_CODE);
            mIsArrival = getArguments().getBoolean(ARG_IS_ARRIVAL);
            mTimeOffset = getArguments().getInt(ARG_TIMEOFFSET);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_station_activity_list_services, container, false);
    }
}