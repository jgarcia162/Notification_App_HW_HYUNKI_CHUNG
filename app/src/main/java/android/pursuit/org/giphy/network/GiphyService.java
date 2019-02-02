package android.pursuit.org.giphy.network;

import android.pursuit.org.giphy.model.GiphyResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GiphyService {

    @GET("/v1/gifs/search")
    Call<GiphyResponse> getGifData(@Query("q") String q,
                                   @Query("api_key") String apiKey);

}
