package OBUSDK.JsonController;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

import OBUSDK.JsonData.Rsu;
import OBUSDK.JsonData.VirtualRSU;
import retrofit2.Call;
import retrofit2.http.*;

public interface APIService {
    /*@GET("getivim/json/{id}")
    Call<IVIM> doGetIvim(@Path("id") int id);

    @GET("getivimnotc/json/{id}")
    Call<IVIM> doGetIvimNoTc(@Path("id") int id);*/

    @GET("api/getrsu/{id}")
    Call<Rsu> doGetRsu(@Path("id") int id);

    /*@POST("getivimsbydistance")
    Call<List<IVIM>> doGetIvimsByDistance(@Body Map<String, Object> requestBody);
    */
    @POST("api/getrsubydistance")
    Call<List<VirtualRSU>> doGetRsuByDistance(@Body Map<String, Object> requestBody);
    /*
    @POST("getnearestrsu")
    Call<List<IVIM>> doGetNearestRsu(@Body Map<String, Object> requestBody);*/
}
