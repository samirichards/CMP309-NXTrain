package uk.ac.abertay.s1902765.nexttrain.RttApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RTTInterface {
    String API_Endpoint = "api.rtt.io/api/v1/";

    @GET("json/search/{CRS}")
    Call<StationSearchResult> getDeparturesFromStation(@Path("CRS") String CrsCode);
}
