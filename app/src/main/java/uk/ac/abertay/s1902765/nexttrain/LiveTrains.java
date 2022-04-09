package uk.ac.abertay.s1902765.nexttrain;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.SearchView;

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

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, liveTrainsModel.stationNames);
        binding.testListView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

public class CustomTestAdapter extends BaseAdapter{

    List<StationItem> result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
    public CustomTestAdapter(Fragment fragmentActivity, List<StationItem> stationItems) {
        // TODO Auto-generated constructor stub
        result=stationItems;
        context=fragmentActivity.getContext();
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return result.size();
    }

    @Override
    public StationItem getItem(int i) {
        return result.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.station_searchitem_layout, parent, false);
            //Todo fix this please
        }
        return view;
    }
}