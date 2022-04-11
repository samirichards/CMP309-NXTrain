package uk.ac.abertay.s1902765.nexttrain;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.AndroidViewModel;

public class StationActivity_Model extends AndroidViewModel implements Observable {

    private PropertyChangeRegistry callbacks = new PropertyChangeRegistry();
    public ObservableField<String> StationName = new ObservableField<>();
    public ObservableField<String> StationCode = new ObservableField<>();
    public ObservableField<String> testString = new ObservableField<>();
    public StationActivity_Model(@NonNull Application application) {
        super(application);
        testString.set("This is a test");
        notifyChange();
    }

    public void SetStation(String name, String code){
        StationName.set(name);
        StationCode.set(code);
        notifyChange();
        Toast.makeText(getApplication().getApplicationContext(), "Set station has been run", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void addOnPropertyChangedCallback(Observable.OnPropertyChangedCallback callback) {
        callbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(Observable.OnPropertyChangedCallback callback) {
        callbacks.remove(callback);
    }

    void notifyChange() {
        callbacks.notifyCallbacks(this, 0, null);
    }

    void notifyPropertyChanged(int fieldId) {
        callbacks.notifyCallbacks(this, fieldId, null);
    }
}
