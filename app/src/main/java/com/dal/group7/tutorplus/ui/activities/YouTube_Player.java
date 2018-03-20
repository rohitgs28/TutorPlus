package com.dal.group7.tutorplus.ui.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.dal.group7.tutorplus.R;
import com.dal.group7.tutorplus.ui.adapters.TutorDetailsFragment;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YouTube_Player extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    static private final String DEVELOPER_KEY="AIzaSyAG-b3HlpNHhlZaj36HxbWOuxcpgjB2q3o";
    TutorDetailsFragment video = new TutorDetailsFragment();
    String url = video.video_url;
    private final String VIDEO =url;
    int lastOrientation = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_player);

        YouTubePlayerView youTubeView = (YouTubePlayerView)findViewById(R.id.videoView1);
        youTubeView.initialize(DEVELOPER_KEY, this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (lastOrientation != newConfig.orientation) {
            lastOrientation = newConfig.orientation;
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        player.loadVideo(VIDEO);
        player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
        Toast.makeText(this, "Oh no! "+error.toString(),
                Toast.LENGTH_LONG).show();
    }
}