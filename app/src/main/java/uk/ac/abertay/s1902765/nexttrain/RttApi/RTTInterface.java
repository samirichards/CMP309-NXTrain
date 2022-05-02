package uk.ac.abertay.s1902765.nexttrain.RttApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RTTInterface {
    String API_Endpoint = "api.rtt.io/api/v1/";

    @GET("json/search/{CRS}")
    Call<StationSearchResult> getLiveDepartures(@Path("CRS") String CrsCode);

    @GET("json/search/{CRS1}/to/{CRS2}")
    Call<StationSearchResult> getLiveDeparturesToStation(@Path("CRS1") String CrsCode1, @Path("CRS2") String CrsCode2);

    @GET("json/search/{CRS}/{YEAR}/{MONTH}/{DAY}")
    Call<StationSearchResult> getDeparturesByDate(@Path("CRS") String CrsCode, @Path("YEAR") Integer Year, @Path("MONTH") Integer Month, @Path("DAY") Integer Day);

    @GET("json/search/{CRS}/{YEAR}/{MONTH}/{DAY}/{TIME}")
    Call<StationSearchResult> getDeparturesByDateAndTime(@Path("CRS") String CrsCode, @Path("YEAR") Integer Year, @Path("MONTH") Integer Month, @Path("DAY") Integer Day, @Path("TIME") Integer Time);

    @GET("json/search/{CRS}/arrivals")
    Call<StationSearchResult> getLiveArrivals(@Path("CRS") String CrsCode);

    @GET("json/search/{CRS1}/to/{CRS2}/arrivals")
    Call<StationSearchResult> getLiveArrivalsToStation(@Path("CRS1") String CrsCode1, @Path("CRS2") String CrsCode2);

    @GET("json/search/{CRS}/{YEAR}/{MONTH}/{DAY}/arrivals")
    Call<StationSearchResult> getArrivalsByDate(@Path("CRS") String CrsCode, @Path("YEAR") Integer Year, @Path("MONTH") Integer Month, @Path("DAY") Integer Day);

    @GET("json/search/{CRS}/{YEAR}/{MONTH}/{DAY}/{TIME}/arrivals")
    Call<StationSearchResult> getArrivalsByDateAndTime(@Path("CRS") String CrsCode, @Path("YEAR") Integer Year, @Path("MONTH") Integer Month, @Path("DAY") Integer Day, @Path("TIME") Integer Time);

    @GET("json/service/{UID}/{YEAR}/{MONTH}/{DAY}")
    Call<ServiceSearchResult> getServiceDetail(@Path("UID") String UID, @Path("YEAR") String Year, @Path("MONTH") String Month, @Path("DAY") String Day);

}
