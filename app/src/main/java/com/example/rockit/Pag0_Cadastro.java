package com.example.rockit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class Pag0_Cadastro extends AppCompatActivity {
    private FirebaseAuth mAuth;
    DatabaseHelper db;
    EditText editName,editEmail,editAge,editPassword;

    String name,email,age,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag0_cadastro);

        editName = findViewById(R.id.editTextName);
        editAge = findViewById(R.id.editTextAge);
        editPassword = findViewById(R.id.editTextPassword);
        editEmail = findViewById(R.id.autoCompleteTextViewCity);

        db = new DatabaseHelper(this);

    }

    public void abrirPag_teste_musical(View view){
        name = editName.getText().toString();
        age = editAge.getText().toString();
        password = editPassword.getText().toString();
        email = editEmail.getText().toString();
        //if(name.equals("") ||  age.equals("") || password.equals("") || email.equals("")){
        //    Toast.makeText(this,"Por favor preencha os campos",Toast.LENGTH_SHORT).show();
        //}else {
            //db.addName(name);
            //db.updateAge(1,age);
            startActivity(new Intent(this, Pag1_genero_musical.class));
        //}
    }
}
