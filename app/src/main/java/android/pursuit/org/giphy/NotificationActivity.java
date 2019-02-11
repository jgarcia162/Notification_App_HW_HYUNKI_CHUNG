package android.pursuit.org.giphy;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class NotificationActivity extends AppCompatActivity {
    private NotificationManager mNotifyManager;
    private SharedPreferences sharedPreferences;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final String DISMISS = "dismiss";
    private static final String TAG = "notification_call";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        TextView notificationTextView = findViewById(R.id.textView);
        ImageView notificationImageView = findViewById(R.id.imageView);
        final Intent intent = getIntent();
        notificationTextView.setText(intent.getStringExtra("gifTitle"));
        Glide.with(notificationImageView.getContext())
                .load(intent.getStringExtra("gifUrl"))
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(new GlideDrawableImageViewTarget(notificationImageView) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        //check isRefreshing
                    }
                });

        Button notificationButton = findViewById(R.id.button);
        createNotificationChannel();
        sharedPreferences = getApplicationContext().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String gifId = intent.getStringExtra("gifTitle");


                if (sharedPreferences.contains(gifId)) {
                    toast();
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(gifId, intent.getStringExtra("gifUrl"));
                    editor.commit();
                    sendNotifications();
                }
            }
        });
    }

    private void createNotificationChannel() {
        mNotifyManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        //the minSdk is set to 28 so no need to check for API version
            // Create a NotificationChannel
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Giphy App Notification", NotificationManager
                    .IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Giphy Application");
            mNotifyManager.createNotificationChannel(notificationChannel);
    }

    private void sendNotifications() {
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }

    private NotificationCompat.Builder getNotificationBuilder() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent dismissIntent = new Intent(DISMISS);

        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, dismissIntent, PendingIntent.FLAG_ONE_SHOT);

        Intent intent = getIntent();
        return new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("Notification from Giphy App")
                .setContentText("You have sent a notification from the gif titled: " + intent.getStringExtra("gifTitle"))
                .setSmallIcon(R.drawable.ic_gif)
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setDeleteIntent(dismissPendingIntent);
    }

    private void toast() {
        Toast.makeText(this, R.string.notification_already_sent, Toast.LENGTH_SHORT).show();
    }

}

