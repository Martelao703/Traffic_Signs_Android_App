package OBUSDK.JsonController;

import java.util.List;
import java.util.Map;

import OBUSDK.PerEncDec.IVIM;
import OBUSDK.PerEncDec.RSU;
import retrofit2.Call;
import retrofit2.http.*;

public interface APIService {
    @GET("getivim/json/{id}")
    Call<IVIM> doGetIvim(@Path("id") int id);

    @GET("getivimnotc/json/{id}")
    Call<IVIM> doGetIvimNoTc(@Path("id") int id);

    @GET("getrsu/{id}")
    Call<RSU> doGetRsu(@Path("id") int id); //Response class ??

    @POST("getivimsbydistance")
    Call<List<IVIM>> doGetIvimsByDistance(@Body Map<String, Object> requestBody);

    @POST("getnearestrsu")
    Call<RSU> doGetNearestRsu(@Body Map<String, Object> requestBody);  //Response class ??

    @POST("getrsubydistance")
    Call<List<IVIM>> doGetRsuByDistance(@Body Map<String, Object> requestBody);  //Response class ??
}
