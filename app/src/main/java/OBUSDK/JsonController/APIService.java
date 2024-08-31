package OBUSDK.JsonController;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

import OBUSDK.JsonData.Rsu;
import OBUSDK.JsonData.VirtualRSU;
import retrofit2.Call;
import retrofit2.http.*;

public interface APIService {
    @GET("api/getrsu/{id}")
    Call<Rsu> doGetRsu(@Path("id") int id);

    @POST("api/getrsubydistance")
    Call<List<VirtualRSU>> doGetRsuByDistance(@Body Map<String, Object> requestBody);
}
