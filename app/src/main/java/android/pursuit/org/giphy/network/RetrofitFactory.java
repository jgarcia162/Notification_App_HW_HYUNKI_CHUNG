package android.pursuit.org.giphy.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    private static Retrofit INSTANCE;

    public static Retrofit getInstance(){
        if(INSTANCE == null){
            INSTANCE = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.giphy.com")
                    .build();
        }
        return INSTANCE;
    }
}