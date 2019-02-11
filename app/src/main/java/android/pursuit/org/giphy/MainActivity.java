package android.pursuit.org.giphy;

import android.pursuit.org.giphy.controller.GifAdapter;
import android.pursuit.org.giphy.model.GifData;
import android.pursuit.org.giphy.model.GiphyResponse;
import android.pursuit.org.giphy.network.GiphyService;
import android.pursuit.org.giphy.network.ApiServiceFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView gifRecyclerView;
    /**
     * We don't ever wanna push API keys. This is one way to hide your key. See [app/build.gradle] for more comments
     */
    private final String apiKey = BuildConfig.API_KEY_GIPHY;
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gifRecyclerView = findViewById(R.id.recyclerview);

        query = getIntent().getStringExtra("query");

        getGifData(ApiServiceFactory.getGiphyApi(), query);
    }

    private void getGifData(GiphyService service, final String query) {
        Call<GiphyResponse> call = service.getGifData(query, apiKey);

        call.enqueue(new Callback<GiphyResponse>() {
            @Override
            public void onResponse(@NonNull Call<GiphyResponse> call, @NonNull Response<GiphyResponse> response) {
                if (response.body().getData().size() < 1) {
                    Log.d("danny", Integer.toString(response.body().getData().size()));
                    setResult(123);
                    finishActivity(456);
                    finish();

                } else {
                    gifRecyclerView.setAdapter(new GifAdapter(response.body().getData()));
                    gifRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<GiphyResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}