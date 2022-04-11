package uk.ac.abertay.s1902765.nexttrain;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.AndroidViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiveTrains_Model extends AndroidViewModel implements Observable {
    private PropertyChangeRegistry callbacks = new PropertyChangeRegistry();
    public ObservableField<List<StationItem>> stations = new ObservableField<>();
    public ObservableField<List<String>> stationNames = new ObservableField<>();

    public ObservableField<String> quote = new ObservableField<>();

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
                stations.set(stationsDao.getAllStations());
                stationNames.set(stationsDao.getAllStationNames());
                notifyChange();
            }
        }).start();
        quote.set("something");
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

    public void executeSearch(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                stations.set(stationsDao.searchForStations(stationSearchTerm.get()));
                notifyChange();
            }
        }).start();
    }
}