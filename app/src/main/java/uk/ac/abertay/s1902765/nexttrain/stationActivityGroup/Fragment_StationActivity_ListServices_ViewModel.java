package uk.ac.abertay.s1902765.nexttrain.stationActivityGroup;

import android.app.Application;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.AndroidViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.ac.abertay.s1902765.nexttrain.RttApi.RTTInterface;
import uk.ac.abertay.s1902765.nexttrain.RttApi.StationSearchResult;
import uk.ac.abertay.s1902765.nexttrain.RttApi.TrainService;

public class Fragment_StationActivity_ListServices_ViewModel extends AndroidViewModel implements Observable {
    private PropertyChangeRegistry callbacks = new PropertyChangeRegistry();

    private String AuthUsername = "rttapi_samirichards";
    private String AuthPassword = "dd684f86675dbc290e53f83060bd471a67cc3de3";
    private String API_Endpoint = "https://api.rtt.io/api/v1/";
    Retrofit retrofitClient;
    OkHttpClient httpClient;
    private RTTInterface api;

    public ObservableField<List<TrainService>> serviceList = new ObservableField<>();
    public ObservableBoolean isLoading = new ObservableBoolean();
    public ObservableInt errorState = new ObservableInt();

    private Boolean isArrivals = false;
    private String CrsCode = "DEE";
    //TODO Change this, set to Dundee as a temporary measure

    public Fragment_StationActivity_ListServices_ViewModel(@NonNull Application application) {
        super(application);
        httpClient = new OkHttpClient().newBuilder().addInterceptor(new BasicAuthInterceptor(AuthUsername, AuthPassword)).build();
        retrofitClient = new Retrofit.Builder().baseUrl(API_Endpoint).addConverterFactory(GsonConverterFactory.create()).client(httpClient).build();
        api = retrofitClient.create(RTTInterface.class);
    }

    public void setModelParameters(String _crsCode, Boolean _isArrival){
        CrsCode = _crsCode;
        isArrivals = _isArrival;
        updateServiceList();
    }

    @Bindable
    public int getLoadVisibility(){
        return isLoading.get() ? View.VISIBLE : View.GONE;
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
        isLoading.set(true);
        serviceList.set(new ArrayList<TrainService>());
        new Thread(new Runnable() {
            public void run() {
                Call<StationSearchResult> call = api.getLiveDepartures(CrsCode);
                call.enqueue(new Callback<StationSearchResult>() {
                    @Override
                    public void onResponse(Call<StationSearchResult> call, retrofit2.Response<StationSearchResult> response) {
                        Toast.makeText(getApplication().getApplicationContext(), response.body().location.name, Toast.LENGTH_SHORT).show();
                        if(isArrivals){
                            Predicate<TrainService> removeOrigin = service -> service.locationDetail.crs != CrsCode && service.locationDetail.displayAs != "ORIGIN";
                            serviceList.set(response.body().services.stream().filter(x-> !x.locationDetail.crs.equals(CrsCode)).collect(Collectors.toList()));
                            //TODO Filter out services if the origin is the same as the current station
                        }
                        else{
                            serviceList.set(response.body().services);
                        }
                        isLoading.set(false);
                        errorState.set(0);
                        notifyChange();
                    }

                    @Override
                    public void onFailure(Call<StationSearchResult> call, Throwable t) {
                        Toast.makeText(getApplication().getApplicationContext(), "Service fetch failed", Toast.LENGTH_SHORT).show();
                        isLoading.set(false);
                        errorState.set(1);
                        notifyChange();
                    }
                });
            }
        }).start();
    }
}

class BasicAuthInterceptor implements Interceptor {

    private String credentials;

    public BasicAuthInterceptor(String user, String password) {
        this.credentials = Credentials.basic(user, password);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request authenticatedRequest = request.newBuilder()
                .header("Authorization", credentials).build();
        return chain.proceed(authenticatedRequest);
    }

}
