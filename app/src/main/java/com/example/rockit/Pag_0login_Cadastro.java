package com.example.rockit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Pag_0login_Cadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_0login_cadastro);
    }

    public void abrirPag_teste_musical(View view){
        Intent intent = new Intent(this, Pag_0login_teste_musical.class);
        startActivity(intent);
    }
}
