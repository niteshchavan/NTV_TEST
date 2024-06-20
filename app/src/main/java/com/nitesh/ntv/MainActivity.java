package com.nitesh.ntv;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.hls.HlsMediaSource;
import androidx.media3.ui.PlayerView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private PlayerView playerView;
    private ExoPlayer player;

    @OptIn(markerClass = UnstableApi.class)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize player view
        playerView = findViewById(R.id.video_view);

        // Initialize ExoPlayer
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        // Prepare media source
        try {
            // Create a data source factory.
            DataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory();
            HlsMediaSource hlsMediaSource =
                    new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri("https://denver1769.pages.dev/Discovery/discovery_hd_hindi.m3u8"));
            player.setMediaSource(hlsMediaSource);
            player.prepare();
            player.play();
        } catch (Exception e) {
            Log.e(TAG, "Error initializing ExoPlayer: ", e);
        }

        // Handle edge-to-edge content
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player != null) {
            player.release();
        }
    }
}
