package uk.ac.abertay.s1902765.nexttrain.serviceDetailGroup;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import uk.ac.abertay.s1902765.nexttrain.R;
import uk.ac.abertay.s1902765.nexttrain.RttApi.Location_Service;
import uk.ac.abertay.s1902765.nexttrain.databinding.ActivityServiceDetailBinding;
import uk.ac.abertay.s1902765.nexttrain.databinding.ActivityServiceDetailStationlistHeaderBinding;
import uk.ac.abertay.s1902765.nexttrain.databinding.ActivityServiceDetailStationlistItemBinding;

public class ServiceDetailActivity extends AppCompatActivity {

    ServiceDetailViewModel model;
    ActivityServiceDetailBinding binding;
    ActivityServiceDetailStationlistHeaderBinding headerBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(ServiceDetailViewModel.class);
        binding = ActivityServiceDetailBinding.inflate(getLayoutInflater());
        binding.setServiceDetailViewModel(model);
        headerBinding = ActivityServiceDetailStationlistHeaderBinding.inflate(getLayoutInflater());
        binding.serviceDetailStationList.addHeaderView(headerBinding.getRoot());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.serviceDetailToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getViewBundledInfo();
    }

    private void getViewBundledInfo() {
        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            model.setServiceParams(extras.getString("serviceUid"), extras.getString("runDate"));
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.service_detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @BindingAdapter("data")
    public static void setStationListAdapterProperties(ListView view, List<Location_Service> items){
        ((ServiceDetailActivity_StationListAdapter)view.getAdapter()).setData(items);
    }

    class ServiceDetailActivity_StationListAdapter extends BaseAdapter {

        private List<Location_Service> mLocalData;

        public void setData(List<Location_Service> items){
            mLocalData = items;
        }

        @Override
        public int getCount() {
            return mLocalData.size();
        }

        @Override
        public Location_Service getItem(int i) {
            return mLocalData.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            View result = view;
            ActivityServiceDetailStationlistItemBinding binding;
            if(result == null){
                binding = ActivityServiceDetailStationlistItemBinding.inflate(getLayoutInflater(), viewGroup, false);
                result = binding.getRoot();
                result.setTag(binding);
            }
            else {
                binding = (ActivityServiceDetailStationlistItemBinding) result.getTag();
            }
            binding.serviceDetailStationName.setText(mLocalData.get(position).description);
            binding.serviceDetailStationPlatform.setText(mLocalData.get(position).platform);
            binding.serviceDetailStationPlatform.setVisibility(View.VISIBLE);
            binding.serviceDetailMainTimeIndicator.setText(mLocalData.get(position).gbttBookedDeparture);
            binding.serviceDetailExpectedTimeIndicator.setText(mLocalData.get(position).realtimeDeparture);
            binding.serviceDetailExpectedTimeIndicator.setVisibility(View.VISIBLE);
            //TODO there's some missing stuff here, add the journey node things as well as checks for if it's arrival or departure
            //Or just do both with arrival being faint or something??
            return result;
        }
    }
}