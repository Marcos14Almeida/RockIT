package com.example.rockit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Pag_perfil_usuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag_perfil_usuario);
    }


    public void Editar_conteudo(View view){
        Toast.makeText(this,"Editar",Toast.LENGTH_SHORT).show();
    }
}
