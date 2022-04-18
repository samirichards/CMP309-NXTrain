package uk.ac.abertay.s1902765.nexttrain;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Service;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.AndroidViewModel;

import org.jetbrains.annotations.NotNull;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class LiveTrains_Model extends AndroidViewModel implements Observable {
    private PropertyChangeRegistry callbacks = new PropertyChangeRegistry();
    public ObservableField<List<StationItem>> stations = new ObservableField<>();
    public ObservableField<List<String>> stationNames = new ObservableField<>();
    public ObservableField<Boolean> nearestStationButtonEnabled = new ObservableField<>();
    public ObservableField<String> nearestButtonDisplayText = new ObservableField<>();
    public ObservableField<StationItem> nearestStation = new ObservableField<>();
    private double nearestStationDistance = 0.0;
    private final DecimalFormat df = new DecimalFormat("0.0");
    Handler handler = new Handler(Looper.getMainLooper());

    @NotNull
    AppDatabase db;
    @NotNull
    StationsDao stationsDao;
    public ObservableField<String> stationSearchTerm = new ObservableField<>();

    public LiveTrains_Model(@NonNull Application application) {
        super(application);
        new Thread(new Runnable() {
            @Override
            public void run() {
                db = AppDatabase.getDatabase(application);
                stationsDao = db.stationsDao();
                stations.set(stationsDao.getNoStations());
                stationNames.set(stationsDao.getAllStationNames());
                nearestStationButtonEnabled.set(false);
                setNearestStation();
                notifyChange();
            }
        }).start();
    }

    @Override
    public void addOnPropertyChangedCallback(
            Observable.OnPropertyChangedCallback callback) {
        callbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(
            Observable.OnPropertyChangedCallback callback) {
        callbacks.remove(callback);
    }

    void notifyChange() {
        callbacks.notifyCallbacks(this, 0, null);
    }

    void notifyPropertyChanged(int fieldId) {
        callbacks.notifyCallbacks(this, fieldId, null);
    }

    public void allStations(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                stations.set(stationsDao.getAllStations());
                notifyChange();
            }
        }).start();
    }

    public void noStations(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                stations.set(stationsDao.getNoStations());
                notifyChange();
            }
        }).start();
    }

    public void executeSearch(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                stations.set(stationsDao.searchForStations(stationSearchTerm.get()));
                notifyChange();
            }
        }).start();
    }

    public String getStationDistance(){
        return df.format(nearestStationDistance) + " Miles";
    }

    public double metresToMiles(double length){
        return length / 1609.344;
    }

    public double metresToKM(double length){
        return length / 1000;
    }

    public void setNearestStation(){
        if (ActivityCompat.checkSelfPermission(getApplication().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getApplication().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            nearestStationButtonEnabled.set(false);
            nearestButtonDisplayText.set("Permission not granted");
            notifyChange();
            return;
        }

        nearestStationButtonEnabled.set(false);
        nearestButtonDisplayText.set("Loading...");
        notifyChange();

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        //Do expensive operation here
                        LocationManager manager = (LocationManager) getApplication().getBaseContext().getSystemService(Service.LOCATION_SERVICE);
                        Criteria locationCriteria = new Criteria();
                        locationCriteria.setAccuracy(Criteria.ACCURACY_FINE);
                        String bestProvider = manager.getBestProvider(locationCriteria, true);
                        @SuppressLint("MissingPermission") Location lastKnown = manager.getLastKnownLocation(bestProvider);

                        StationItem currentClosestStation = null;
                        double currentClosestStationDistance = -1;
                        for (StationItem item: stationsDao.getAllStations()) {
                            Location currentLocation = new Location("");
                            currentLocation.setLongitude(item.Longitude);
                            currentLocation.setLatitude(item.Latitude);

                            if(currentClosestStationDistance > lastKnown.distanceTo(currentLocation) || currentClosestStationDistance == -1){
                                currentClosestStation = item;
                                currentClosestStationDistance = lastKnown.distanceTo(currentLocation);
                            }
                        }
                        StationItem finalCurrentClosestStation = currentClosestStation;
                        double finalCurrentClosestStationDistance = currentClosestStationDistance;
                        DecimalFormat df = new DecimalFormat("0.0");
                        df.setRoundingMode(RoundingMode.CEILING);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                nearestStation.set(finalCurrentClosestStation);
                                nearestStationDistance = finalCurrentClosestStationDistance;
                                nearestButtonDisplayText.set(nearestStation.get().Name + "\n" + df.format(metresToMiles(nearestStationDistance)).toString() + " Miles");
                                nearestStationButtonEnabled.set(true);
                                notifyChange();
                            }
                        });
                    }
                }
        ).start();

    }
}