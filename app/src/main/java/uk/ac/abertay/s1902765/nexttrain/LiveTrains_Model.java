package uk.ac.abertay.s1902765.nexttrain;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.room.RoomDatabase;

import java.util.List;

public class LiveTrains_Model extends AndroidViewModel implements Observable {
    private PropertyChangeRegistry callbacks = new PropertyChangeRegistry();
    LiveData<List<StationItem>> stations;
    public ObservableField<List<String>> stationNames = new ObservableField<>();
    AppDatabase db;
    public ObservableField<String> stationSearchTerm = new ObservableField<>();

    private String testString = "This is a test";


    public LiveTrains_Model(@NonNull Application application) {
        super(application);
        new Thread(new Runnable() {
            @Override
            public void run() {
                db = AppDatabase.getDatabase(application);
                stations = db.stationsDao().searchForStationsLive(stationSearchTerm.get());
                stationNames.set(db.stationsDao().getAllStationNames());
            }
        });
    }

    public LiveData<List<StationItem>> getStations(String searchTerm){
        return db.stationsDao().searchForStationsLive(searchTerm);
    }

    public String getTestString() {
        return testString;
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
}