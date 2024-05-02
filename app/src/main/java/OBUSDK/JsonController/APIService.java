package OBUSDK.JsonController;

import java.util.List;
import retrofit2.http.*;

public interface APIService {
    @GET("getivim/json/{id}")
    Call<YourResponseClass> doGetIvimData(@Path("id") int id);
}
