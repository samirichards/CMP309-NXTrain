package uk.ac.abertay.s1902765.nexttrain.serviceDetailGroup;

import android.app.Application;
import android.text.Spanned;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.AndroidViewModel;

import java.io.IOException;
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
import uk.ac.abertay.s1902765.nexttrain.RttApi.Location_Service;
import uk.ac.abertay.s1902765.nexttrain.RttApi.RTTInterface;
import uk.ac.abertay.s1902765.nexttrain.RttApi.ServiceSearchResult;
import uk.ac.abertay.s1902765.nexttrain.RttApi.TrainService;

public class ServiceDetailViewModel extends AndroidViewModel implements Observable {

    private PropertyChangeRegistry callbacks = new PropertyChangeRegistry();
    private String serviceUid;
    private String[] dateSplit;
    public String ServiceTOC;
    private String AuthUsername = "rttapi_samirichards";
    private String AuthPassword = "dd684f86675dbc290e53f83060bd471a67cc3de3";
    private String API_Endpoint = "https://api.rtt.io/api/v1/";
    Retrofit retrofitClient;
    OkHttpClient httpClient;
    private RTTInterface api;
    public ObservableBoolean isLoading = new ObservableBoolean();
    public ObservableBoolean isError = new ObservableBoolean();
    public ObservableField<ServiceSearchResult> currentService = new ObservableField<>();
    public ObservableField<Spanned> pageTitle = new ObservableField<>();
    public ObservableField<List<Location_Service>> callingPoints = new ObservableField<>();
    public ObservableBoolean serviceDetailIsOpen = new ObservableBoolean();

    public ServiceDetailViewModel(@NonNull Application application) {
        super(application);
        httpClient = new OkHttpClient().newBuilder().addInterceptor(new uk.ac.abertay.s1902765.nexttrain.serviceDetailGroup.BasicAuthInterceptor(AuthUsername, AuthPassword)).build();
        retrofitClient = new Retrofit.Builder().baseUrl(API_Endpoint).addConverterFactory(GsonConverterFactory.create()).client(httpClient).build();
        api = retrofitClient.create(RTTInterface.class);
        serviceDetailIsOpen.set(false);
        notifyChange();
    }

    public void setServiceParams(String _serviceUid, String _serviceDate, String _serviceTOC){
        serviceUid = _serviceUid;
        dateSplit = _serviceDate.split("-");
        ServiceTOC = _serviceTOC;
        getService();
    }

    public void setDetailIsOpen(Boolean value){
        serviceDetailIsOpen.set(value);
        notifyChange();
    }

    public void getService(){
        isLoading.set(true);
        notifyChange();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<ServiceSearchResult> call = api.getServiceDetail(serviceUid, dateSplit[0], dateSplit[1], dateSplit[2]);
                call.enqueue(new Callback<ServiceSearchResult>() {
                    @Override
                    public void onResponse(Call<ServiceSearchResult> call, retrofit2.Response<ServiceSearchResult> response) {
                        if (response.body() != null){
                            currentService.set(response.body());
                            String titleText = "<b>"+currentService.get().atocName + "</b> service to <b>" + currentService.get().destination.get(0).description+"</b>";
                            Predicate<Location_Service> isPublic = callingPoint -> callingPoint.isPublicCall == true;
                            callingPoints.set(currentService.get().locations.stream().filter(isPublic).collect(Collectors.toList()));
                            pageTitle.set(HtmlCompat.fromHtml(titleText, HtmlCompat.FROM_HTML_MODE_LEGACY));
                            isLoading.set(false);
                            isError.set(false);
                        }
                        else{
                            isLoading.set(false);
                            isError.set(true);
                        }
                        notifyChange();
                    }

                    @Override
                    public void onFailure(Call<ServiceSearchResult> call, Throwable t) {
                        isLoading.set(false);
                        isError.set(true);
                        notifyChange();
                    }
                });
            }
        }).start();
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
