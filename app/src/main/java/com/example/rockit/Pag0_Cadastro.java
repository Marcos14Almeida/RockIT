package com.example.rockit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Pag0_Cadastro extends AppCompatActivity {
    DatabaseHelper db;
    EditText editName,editCity,editAge,editPassword;

    String name,city,age,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag0_cadastro);

        editName = findViewById(R.id.editTextName);
        editAge = findViewById(R.id.editTextAge);
        editPassword = findViewById(R.id.editTextPassword);
        editCity = findViewById(R.id.autoCompleteTextViewCity);

        db = new DatabaseHelper(this);

    }

    public void abrirPag_teste_musical(View view){
        name = editName.getText().toString();
        age = editAge.getText().toString();
        password = editPassword.getText().toString();
        city = editCity.getText().toString();
        //if(name.equals("") ||  age.equals("") || password.equals("") || city.equals("")){
        //    Toast.makeText(this,"Por favor preencha os campos",Toast.LENGTH_SHORT).show();
        //}else {
            //db.addName(name);
            //db.updateAge( 5,age);
            Intent intent = new Intent(this, Pag1_genero_musical.class);
            startActivity(intent);
        //}
    }
}
