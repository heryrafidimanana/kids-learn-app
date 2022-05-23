package com.main.kids;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.main.kids.model.Course;

public class DisplayVideoActivity extends YouTubeBaseActivity {

    String api_key = "AIzaSyAJ1Jnb_Qs880gbIiXAOxij_xNUBVxlGWA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_video);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Course data = (Course) getIntent().getSerializableExtra("course");

            Log.d("my course description :",data.getDescription());

            // Get reference to the view of Video player
            YouTubePlayerView ytPlayer = findViewById(R.id.youtube_player);
            TextView course_description = findViewById(R.id.course_description);

            course_description.setText(data.getDescription());

            ytPlayer.initialize(
                    api_key,
                    new YouTubePlayer.OnInitializedListener() {
                // Implement two methods by clicking on red
                // error bulb inside onInitializationSuccess
                // method add the video link or the playlist
                // link that you want to play In here we
                // also handle the play and pause
                // functionality
                @Override
                public void onInitializationSuccess(
                        YouTubePlayer.Provider provider,
                        YouTubePlayer youTubePlayer, boolean b)
                {
                    youTubePlayer.loadVideo(data.getVd());
                    youTubePlayer.play();
                }

                // Inside onInitializationFailure
                // implement the failure functionality
                // Here we will show toast
                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider,
                        YouTubeInitializationResult
                youTubeInitializationResult)
                {
                    Toast.makeText(getApplicationContext(), "Video player Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}