package com.example.rockit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.Objects;
//YOUTUBE API
//https://www.youtube.com/watch?v=Up9BjrIuoXY
//        https://console.cloud.google.com/apis/api/youtube.googleapis.com/credentials?authuser=1&hl=pt-br&project=davaiapp-c447a

//https://github.com/PierfrancescoSoffritti/android-youtube-player
//https://www.youtube.com/watch?v=qzcGfN9S_QY

//https://www.youtube.com/results?search_query=android+studio+youtube
public class Youtube extends YouTubeBaseActivity {

    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    String videokey="Up9BjrIuoXY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        //Toolbar / barra superior
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        //(toolbar);
        //Objects.requireNonNull(getSupportActionBar()).setTitle("Videos");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Se clicar no botao de retornar
        toolbar.setNavigationOnClickListener(v -> finish());

        youTubePlayerView=findViewById(R.id.youtube_video);
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(videokey); //youtube URL from video
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        youTubePlayerView.initialize("AIzaSyAxeFDYpqxS4biF6oQ_2qgwWMYbh299BVM",onInitializedListener);//chave da API

    }


}
