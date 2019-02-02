package android.pursuit.org.giphy.view;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.pursuit.org.giphy.MainActivity;
import android.pursuit.org.giphy.NotificationActivity;
import android.pursuit.org.giphy.PreActivity;
import android.pursuit.org.giphy.R;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class GifViewHolder extends RecyclerView.ViewHolder {
    private final ImageView gifView;
    private final TextView gifTitleView;
    private Intent intent;
    private SharedPreferences sharedPreferences;

    public GifViewHolder(@NonNull View itemView) {
        super(itemView);
        gifView = itemView.findViewById(R.id.gif_view);
        gifTitleView = itemView.findViewById(R.id.gif_title);
    }

    public void onBind(final String gifUrl, final String gifTitle) {

        Log.d("gifitem get url", "returns" + gifUrl);

        Glide.with(gifView.getContext())
                .load(gifUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new GlideDrawableImageViewTarget(gifView) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        //check isRefreshing
                    }
                });

        gifTitleView.setText(gifTitle);

        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                intent = new Intent(itemView.getContext(), NotificationActivity.class);

                intent.putExtra("gifUrl", gifUrl);
                intent.putExtra("gifTitle", gifTitle);
                itemView.getContext().startActivity(intent);
            }
        });
//        gifView.setContentDescription(gifItem.getUrl());
    }
}
