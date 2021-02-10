package com.example.rockit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Pag_0login_inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag_0login_inicio);
    }

    public void abrirPag_Cadastro(View view){
        Intent intent = new Intent(this, Pag_0login_Cadastro.class);
        startActivity(intent);
    }
    public void abrirMainActivity(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
