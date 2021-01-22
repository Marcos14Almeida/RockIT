package com.example.rockit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Pag_cadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag_cadastro);
    }

    public void abrirPag_teste_musical(View view){
        Intent intent = new Intent(this, Pag_teste_musical.class);
        startActivity(intent);
    }
}
