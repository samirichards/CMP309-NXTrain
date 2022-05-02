package uk.ac.abertay.s1902765.nexttrain.serviceDetailGroup;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import uk.ac.abertay.s1902765.nexttrain.R;
import uk.ac.abertay.s1902765.nexttrain.databinding.ActivityServiceDetailBinding;

public class ServiceDetailActivity extends AppCompatActivity {

    ServiceDetailViewModel model;
    ActivityServiceDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(ServiceDetailViewModel.class);
        binding = ActivityServiceDetailBinding.inflate(getLayoutInflater());
        binding.setServiceDetailViewModel(model);
        getViewBundledInfo();
        setContentView(binding.getRoot());

        setSupportActionBar(binding.serviceDetailToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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
}