package android.pursuit.org.giphy;

import android.app.Activity;
import android.content.Intent;
import android.pursuit.org.giphy.controller.GifAdapter;
import android.pursuit.org.giphy.model.GiphyResponse;
import android.pursuit.org.giphy.network.GiphyService;
import android.pursuit.org.giphy.network.RetrofitFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private RecyclerView gifRecyclerView;
    private final int itemLimiter = 20;
    private final String apiKey = "22CJKx0EJlDONSYCnktJR8WRuJKldpli";
    private String query;

    private List<String> gifs;
    private List<String> gifTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gifRecyclerView = findViewById(R.id.recyclerview);

        gifs = new ArrayList<>(itemLimiter);
        gifTitles = new ArrayList<>(itemLimiter);

        query = getIntent().getStringExtra("query");

        Retrofit retrofit = RetrofitFactory.getInstance();
        final GiphyService service = retrofit.create(GiphyService.class);
        getGifData(service, query, apiKey);


    }

    private void getGifData(GiphyService service, final String q, String apiKey) {

        Call<GiphyResponse> call = service.getGifData(q, apiKey);

        call.enqueue(new Callback<GiphyResponse>() {
            @Override
            public void onResponse(@NonNull Call<GiphyResponse> call, @NonNull Response<GiphyResponse> response) {
                if (response.body().getData().size() < 1) {
                    Log.d("danny", Integer.toString(response.body().getData().size()));
                    setResult(123);
                    finishActivity(456);
                    finish();

                }else {

                        for (int i = 0; i < itemLimiter; i++) {
                            String resUrl = response.body().getData().get(i).getImages().getDownsized().geturl();
                            String resTitle = response.body().getData().get(i).getTitle();
                            gifs.add(resUrl);
                            gifTitles.add(resTitle);

                            Log.d("Response", "response : " + resUrl);
                        }


                    gifRecyclerView.setAdapter(new GifAdapter(gifs, gifTitles));
                    gifRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<GiphyResponse> call, @NonNull Throwable t) {

//                startActivity(new Intent(MainActivity.this, PreActivity.class));
//                Toast.makeText(MainActivity.this, "your query returned no results!", Toast.LENGTH_SHORT).show();
                Log.e("FAIL", "onFailure: " + t.getMessage());
            }
        });


    }


}
