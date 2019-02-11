package android.pursuit.org.giphy.network;

import android.support.annotation.NonNull;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Notices the changes made here. By having this factory class return the requested api services instead of a retrofit object, we can make this more flexible. If we want to introduce another API to our app it can be done easily by just creating another method that returns a different API service class.
 */
public class ApiServiceFactory {
    private static GiphyService giphyApi;

    private static Retrofit getRetrofitInstance(String baseUrl) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    @NonNull
    public static GiphyService getGiphyApi() {
        if (giphyApi == null) {
            giphyApi = getRetrofitInstance("https://api.giphy.com").create(GiphyService.class);
        }
        return giphyApi;
    }
}