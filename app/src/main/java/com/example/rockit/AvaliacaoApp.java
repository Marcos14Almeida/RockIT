package com.example.rockit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.AsyncPlayer;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.rockit.Classes.Banda;
import com.example.rockit.Classes.Comment;
import com.example.rockit.Classes.Stars;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class AvaliacaoApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacao_app);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Avaliação");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());


        readStars();

        playMusic();
    }

    /////////////////////////////////////////////////////////////////////////////
    //https://www.youtube.com/watch?v=DxZMDSKNG1A
    MediaPlayer mediaPlayer = new MediaPlayer();

    public void playMusic() {
        try {
            mediaPlayer.setDataSource("https://firebasestorage.googleapis.com/v0/b/davaiapp-c447a.appspot.com/o/Muse%20-%20Supermassive%20Black%20Hole.mp3?alt=media&token=fc75eb66-003c-4ac0-8523-a4902bc2aa77");
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }


/////////////////////////////////////////////////////////////////////////////
    private  void readStars(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Stars").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Stars stars = snapshot.getValue(Stars.class);
                assert stars != null;
                if (snapshot.exists()) {
                    //ratingBar
                    RatingBar ratingBar = findViewById(R.id.ratingBar);
                    ratingBar.setRating(Float.parseFloat(stars.getNota1()));

                    ratingBar = findViewById(R.id.ratingBar2);
                    ratingBar.setRating(Float.parseFloat(stars.getNota2()));

                    ratingBar = findViewById(R.id.ratingBar3);
                    ratingBar.setRating(Float.parseFloat(stars.getNota3()));

                    ratingBar = findViewById(R.id.ratingBar4);
                    ratingBar.setRating(Float.parseFloat(stars.getNota4()));

                    ratingBar = findViewById(R.id.ratingBar5);
                    ratingBar.setRating(Float.parseFloat(stars.getNota5()));

                    ratingBar = findViewById(R.id.ratingBar6);
                    ratingBar.setRating(Float.parseFloat(stars.getNota6()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void salvar(View view){
        RatingBar ratingBar=findViewById(R.id.ratingBar);
        float stars = ratingBar.getRating();
        updateFieldUsers("nota1",String.valueOf(stars));

        ratingBar=findViewById(R.id.ratingBar2);
        stars = ratingBar.getRating();
        updateFieldUsers("nota2",String.valueOf(stars));

        ratingBar=findViewById(R.id.ratingBar3);
        stars = ratingBar.getRating();
        updateFieldUsers("nota3",String.valueOf(stars));

        ratingBar=findViewById(R.id.ratingBar4);
        stars = ratingBar.getRating();
        updateFieldUsers("nota4",String.valueOf(stars));

        ratingBar=findViewById(R.id.ratingBar5);
        stars = ratingBar.getRating();
        updateFieldUsers("nota5",String.valueOf(stars));

        ratingBar=findViewById(R.id.ratingBar6);
        stars = ratingBar.getRating();
        updateFieldUsers("nota6",String.valueOf(stars));

        Toast.makeText(this,"Informações Salvas",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //Update Stars
    //troca dados no database
    public void updateFieldUsers(String field, String string){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Stars").child(firebaseUser.getUid());
        HashMap<String, Object> map = new HashMap<>();
        map.put(field, string);
        reference.updateChildren(map);
    }
}
