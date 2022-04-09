package uk.ac.abertay.s1902765.nexttrain;

import android.app.Application;
import android.database.Observable;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.room.RoomDatabase;

import java.util.List;

public class LiveTrains_Model extends AndroidViewModel {
    LiveData<List<StationItem>> stations;
    AppDatabase db;
    private String stationSearchTerm = "";

    private String testString = "This is a test";

    public LiveTrains_Model(@NonNull Application application) {
        super(application);
        db = AppDatabase.getDatabase(application);
        stations = db.stationsDao().searchForStationsLive(stationSearchTerm);
    }

    public LiveData<List<StationItem>> getStations(String searchTerm){
        return db.stationsDao().searchForStationsLive(searchTerm);
    }

    public String getTestString() {
        return testString;
    }

    public String getStationSearchTerm() {
        return stationSearchTerm;
    }

    public void setStationSearchTerm(String stationSearchTerm) {
        this.stationSearchTerm = stationSearchTerm;
    }
}