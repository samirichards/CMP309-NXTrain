package uk.ac.abertay.s1902765.nexttrain.serviceDetailGroup;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
        headerBinding.serviceDetailStationInfoButton.setOnClickListener(new ServiceDetailClickListener());
        binding.serviceDetailStationList.addHeaderView(headerBinding.getRoot());
        binding.serviceDetailStationList.setAdapter(new ServiceDetailActivity_StationListAdapter());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.serviceDetailToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getViewBundledInfo();
    }

    private void getViewBundledInfo() {
        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            model.setServiceParams(extras.getString("serviceUid"), extras.getString("runDate"), extras.getString("TOC"));
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        if(model.serviceDetailIsOpen.get()){
            model.setDetailIsOpen(false);
            return false;
        }
        else{
            onBackPressed();
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        if(model.serviceDetailIsOpen.get()){
            model.setDetailIsOpen(false);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.service_detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @BindingAdapter("data")
    public static void setStationListAdapterProperties(ListView view, List<Location_Service> items){
        ((ServiceDetailActivity_StationListAdapter)((HeaderViewListAdapter)view.getAdapter()).getWrappedAdapter()).setData(items);
        //((ServiceDetailActivity_StationListAdapter)view.getAdapter()).setData(items);
    }

    public class ServiceDetailClickListener implements View.OnClickListener {
        @Override
        public void onClick (View v) {
            model.setDetailIsOpen(true);
        }
    }

    class ServiceDetailActivity_StationListAdapter extends BaseAdapter {

        private List<Location_Service> mLocalData;

        public void setData(List<Location_Service> items){
            mLocalData = items;
        }

        @Override
        public int getCount() {
            if(mLocalData != null){
                return mLocalData.size();
            }
            return 0;
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
            binding.serviceDetailStationName.setText(mLocalData.get(position).description + " (" + mLocalData.get(position).crs + ")");
            if(mLocalData.get(position).platform != null){
                //TODO make this use strings resource
                binding.serviceDetailStationPlatform.setText("Platform " + mLocalData.get(position).platform);
                binding.serviceDetailStationPlatform.setVisibility(View.VISIBLE);
            }
            else{
                binding.serviceDetailStationPlatform.setVisibility(View.GONE);
            }
            binding.serviceDetailMainTimeIndicator.setText(mLocalData.get(position).gbttBookedDeparture);
            if (mLocalData.get(position).realtimeDeparture != null){
                binding.serviceDetailExpectedTimeIndicator.setText(mLocalData.get(position).realtimeDeparture);
                binding.serviceDetailExpectedTimeIndicator.setVisibility(View.VISIBLE);
            }
            else{
                binding.serviceDetailExpectedTimeIndicator.setVisibility(View.GONE);
            }

            String resName = "TOC_Colour_" + model.ServiceTOC;
            binding.serviceDetailStationIndicator.setBackground(binding.getRoot().getContext().getApplicationContext().getDrawable(binding.getRoot().getContext().getApplicationContext().getResources().getIdentifier(resName, "color", binding.getRoot().getContext().getApplicationContext().getPackageName())));
            //TODO there's some missing stuff here, add the journey node things as well as checks for if it's arrival or departure
            //Or just do both with arrival being faint or something??
            return result;
        }
    }
}