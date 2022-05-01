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
import uk.ac.abertay.s1902765.nexttrain.R;
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
    public ObservableBoolean noServices = new ObservableBoolean();
    public ObservableInt errorState = new ObservableInt();
    public ObservableField<String> noServicesText = new ObservableField<>();

    private Boolean isArrivals = false;
    private String CrsCode = "DEE";
    //TODO Change this, set to Dundee as a temporary measure

    public Fragment_StationActivity_ListServices_ViewModel(@NonNull Application application) {
        super(application);
        httpClient = new OkHttpClient().newBuilder().addInterceptor(new BasicAuthInterceptor(AuthUsername, AuthPassword)).build();
        retrofitClient = new Retrofit.Builder().baseUrl(API_Endpoint).addConverterFactory(GsonConverterFactory.create()).client(httpClient).build();
        api = retrofitClient.create(RTTInterface.class);
        noServices.set(false);
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

    @Bindable
    public int getNoServicesVisibility(){
        return noServices.get() ? View.VISIBLE : View.GONE;
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
                        Predicate<TrainService> isPublic = trainService -> trainService.locationDetail.isPublicCall == true;
                        if(response.body().services == null){
                            noServices.set(true);
                            isLoading.set(false);
                            if(isArrivals){
                                noServicesText.set(getApplication().getString(R.string.fragment_stationActivity_listServices_noServiceArrivals));
                            }
                            else{
                                noServicesText.set(getApplication().getString(R.string.fragment_stationActivity_listServices_noServiceDepartures));
                            }
                            notifyChange();
                            return;
                        }
                        if(isArrivals){
                            Predicate<TrainService> removeOrigin = service -> service.locationDetail.crs != CrsCode && service.locationDetail.displayAs != "ORIGIN";
                            serviceList.set(response.body().services.stream().filter(isPublic).collect(Collectors.toList()));
                            //TODO This works, however there are edge cases still, On Merseyrail the arrival is the same as the origin for servies on the wirral line
                            //Due to going around the loop, need to figure out how to take account of this, as well as display the destination as the correct one
                            //For example Destination as Liverpool Central instead of West Kirby for West Kirby wirral line trains
                            //For now I'm just going to accept this, there is now a new check for if the service is public which *should* remove all the technical moves around dundee for example
                        }
                        else{
                            serviceList.set(response.body().services.stream().filter(isPublic).collect(Collectors.toList()));
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
                        noServices.set(true);
                        noServicesText.set(getApplication().getString(R.string.fragment_stationActivity_listServices_unableToFetch));
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
