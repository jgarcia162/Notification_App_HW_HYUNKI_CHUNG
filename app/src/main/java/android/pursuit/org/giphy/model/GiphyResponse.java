package android.pursuit.org.giphy.model;

import android.pursuit.org.giphy.model.GifData;

import java.util.List;

public class GiphyResponse {
    private List<GifData> data;

    public List<GifData> getData() {
        return data;
    }

    public void setData(List<GifData> data) {
        this.data = data;
    }
}
