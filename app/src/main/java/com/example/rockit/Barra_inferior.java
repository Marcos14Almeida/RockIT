package com.example.rockit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Barra_inferior extends AppCompatActivity {

    FrameLayout frameLayout;
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barra_inferior);
        navigationView = findViewById(R.id.bottom_nav);
        frameLayout = findViewById(R.id.frame_layout);
        navigationView.setOnNavigationItemSelectedListener(nav);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener nav=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.bottom_procurar_shows:
                    frameLayout.setBackgroundColor(Color.RED);
                    break;
                case R.id.bottom_home:
                    frameLayout.setBackgroundColor(Color.GREEN);
                    break;
                case R.id.bottom_procurar_banda:
                    frameLayout.setBackgroundColor(Color.BLUE);
                    break;
            }
            return true;
        }
    };
}