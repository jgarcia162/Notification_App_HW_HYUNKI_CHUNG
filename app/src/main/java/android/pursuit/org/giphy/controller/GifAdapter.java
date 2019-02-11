package android.pursuit.org.giphy.controller;

import android.pursuit.org.giphy.model.GifData;
import android.pursuit.org.giphy.view.GifViewHolder;
import android.pursuit.org.giphy.R;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class GifAdapter extends RecyclerView.Adapter<GifViewHolder> {

    private final List<GifData> gifList;

    public GifAdapter(List<GifData> gifList) {
        this.gifList = gifList;
    }

    @NonNull
    @Override
    public GifViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View child = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_itemview, viewGroup, false);
        return new GifViewHolder(child);
    }

    @Override
    public void onBindViewHolder(@NonNull GifViewHolder gifViewHolder, int i) {
        gifViewHolder.onBind(gifList.get(i).getDownsizedUrl(), gifList.get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        return 20;
    }
}
