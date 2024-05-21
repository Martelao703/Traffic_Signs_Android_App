package OBUSDK.JsonController;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface APIService {
    /*@GET("getivim/json/{id}")
    Call<IVIM> doGetIvim(@Path("id") int id);

    @GET("getivimnotc/json/{id}")
    Call<IVIM> doGetIvimNoTc(@Path("id") int id);*/

    @GET("api/getrsu/{id}")
    Call<ResponseBody> doGetRsu(@Path("id") int id);

    /*@POST("getivimsbydistance")
    Call<List<IVIM>> doGetIvimsByDistance(@Body Map<String, Object> requestBody);

    @POST("getnearestrsu")
    Call<RSU> doGetNearestRsu(@Body Map<String, Object> requestBody);

    @POST("getrsubydistance")
    Call<List<IVIM>> doGetRsuByDistance(@Body Map<String, Object> requestBody);*/
}
