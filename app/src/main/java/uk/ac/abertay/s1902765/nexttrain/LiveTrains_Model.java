package uk.ac.abertay.s1902765.nexttrain;

import android.app.Application;
import android.database.Observable;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.room.RoomDatabase;

import java.util.List;

public class LiveTrains_Model extends AndroidViewModel {
    public LiveData<List<StationItem>> stations;
    public ObservableField<List<String>> stationNames = new ObservableField<>();
    AppDatabase db;
    public ObservableField<String> stationSearchTerm = new ObservableField<>();



    public LiveTrains_Model(@NonNull Application application) {
        super(application);
        db = AppDatabase.getDatabase(application);
        stations = db.stationsDao().searchForStationsLive(stationSearchTerm.get());
        stationNames.set(db.stationsDao().getAllStationNames());
    }

    public LiveData<List<StationItem>> getStations(String searchTerm){
        return db.stationsDao().searchForStationsLive(searchTerm);
    }
}