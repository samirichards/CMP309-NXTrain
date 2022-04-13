package uk.ac.abertay.s1902765.nexttrain.stationActivityGroup;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;

import uk.ac.abertay.s1902765.nexttrain.R;
import uk.ac.abertay.s1902765.nexttrain.databinding.ActivityStationBinding;

public class StationActivity extends AppCompatActivity {

    private StationActivity_Model model;
    private ActivityStationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(StationActivity_Model.class);
        binding = ActivityStationBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        binding.setStationActivityModel(model);
        setContentView(binding.getRoot());
        getViewBundledInfo();
        setSupportActionBar(binding.stationActivityMaterialToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(model.StationName.get());

        new Thread(new Runnable() {
            @Override
            public void run() {
                GetStationURL getStationURL = new GetStationURL(model.StationName.get());
                try {
                    GetStationImageNew stationImageNew = new GetStationImageNew(getStationURL.call());
                    stationImageNew.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void getViewBundledInfo() {
        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            model.SetStation(extras.getString("stationName"), extras.getString("stationCode"));
            model.notifyChange();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.stationactivity_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private class GetStationURL implements Callable<String>{
        final String stationName;
        public GetStationURL(String _stationName){
            stationName = _stationName;
        }
        @Override
        public String call() throws Exception {
            try{
                URL url = new URL("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + stationName.replace(' ', '+') + "+Railway+Station");
                URLConnection connection = url.openConnection();

                String line;
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                JSONObject json = new JSONObject(builder.toString());
                String imageUrl = json.getJSONObject("responseData").getJSONArray("results").getJSONObject(0).getString("url");
                return imageUrl;
            }
            catch (Exception e){
                return null;
            }
        }
    }

    private class GetStationImageNew implements Callable{
        final String stationUrl;

        public GetStationImageNew(String _stationUrl){
            stationUrl = _stationUrl;
        }

        @Override
        public Object call() throws Exception {
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(stationUrl).getContent());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            binding.stationActivityHeaderImage.setImageBitmap(bitmap);
            return null;
        }
    }
}