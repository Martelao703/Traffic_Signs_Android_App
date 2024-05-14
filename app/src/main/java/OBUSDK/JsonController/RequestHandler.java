package OBUSDK.JsonController;

import java.io.IOException;

import OBUSDK.PerEncDec.RSU;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class RequestHandler {
    private static final String BASE_URL = "http://its-server.pt/api/";

    private RSU rsu;

    /*
    public RSU doGetRSU(int id){
        String url = BASE_URL + "getrsu/" + id;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService = retrofit.create(APIService.class);

        Call<RSU> call = apiService.doGetRsu(id);

        call.enqueue(new Callback<RSU>() {
            @Override
            public void onResponse(Call<RSU> call, Response<RSU> response) {
                if (response.isSuccessful()) {

                    rsu = response.body();

                } else {
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RSU> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return rsu;
    }

     */

    public RSU doGetRSU(int id) {
        String url = BASE_URL + "getrsu/" + id;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService = retrofit.create(APIService.class);

        Call<RSU> call = apiService.doGetRsu(id);

        try {
            Response<RSU> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                // Handle unsuccessful response
                System.out.println(response.errorBody().string());
            }
        } catch (IOException e) {
            // Handle IOException
            e.printStackTrace();
        }

        return null;
    }


}
