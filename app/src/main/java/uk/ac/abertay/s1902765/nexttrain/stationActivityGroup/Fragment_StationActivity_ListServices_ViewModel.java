package uk.ac.abertay.s1902765.nexttrain.stationActivityGroup;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.AndroidViewModel;

import com.thalesgroup.rtti._2013_11_28.token.types.AccessToken;
import com.thalesgroup.rtti._2017_10_01.ldb.LDBServiceSoap;
import com.thalesgroup.rtti._2017_10_01.ldb.Ldb;
import com.thalesgroup.rtti._2017_10_01.ldb.types.ServiceItem;

import java.util.List;

public class Fragment_StationActivity_ListServices_ViewModel extends AndroidViewModel implements Observable {
    private PropertyChangeRegistry callbacks = new PropertyChangeRegistry();
    private static final String LDB_TOKEN = "da083564-3a3a-4ae4-b056-6116ecfd1aaf";
    public ObservableField<List<ServiceItem>> serviceItemList = new ObservableField<>();
    private Boolean isArrivals = false;
    private String CrsCode = "DEE";
    //TODO Change this, set to Dundee as a temporary measure

    public Fragment_StationActivity_ListServices_ViewModel(@NonNull Application application) {
        super(application);
        updateServiceList();
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

    void updateServiceList(){
        new Thread(new Runnable() {
            public void run() {
                AccessToken token = new AccessToken();
                token.setTokenValue(LDB_TOKEN);

                Ldb soap = new Ldb();
                try{
                    LDBServiceSoap soapService = soap.getLDBServiceSoap12();
                }
                catch (Exception e){
                    e.getCause();
                    Toast.makeText(getApplication().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                soap.getServiceName();
                soap.getWSDLDocumentLocation();

                //GetBoardRequestParams params = new GetBoardRequestParams();
                //params.setCrs(CrsCode);
                //params.setNumRows(4);

                //StationBoardResponseType departureBoard = soapService.getDepartureBoard(params, token);

                //serviceItemList.set(departureBoard.getGetStationBoardResult().getTrainServices().getService());
            }
        }).start();
    }
}
