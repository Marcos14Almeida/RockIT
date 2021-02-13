package com.example.rockit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Pag0_Cadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag0_cadastro);
    }

    public void abrirPag_teste_musical(View view){
        Intent intent = new Intent(this, Pag1_teste_musical.class);
        startActivity(intent);
    }
}
