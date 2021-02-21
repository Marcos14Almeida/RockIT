package com.example.rockit.Cadastro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rockit.R;
import com.example.rockit.Classes.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Pag0_Cadastro extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText editName,editEmail,editAge,editPassword;
    ProgressBar progressBar;
    String name,email,age,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag0_cadastro);

        editName = findViewById(R.id.editTextName);
        editAge = findViewById(R.id.editTextAge);
        editPassword = findViewById(R.id.editTextPassword);
        editEmail = findViewById(R.id.autoCompleteTextViewCity);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }
    /////////////////////////////////////////////////////////////
    //                    F I R E B A S E                      //
    /////////////////////////////////////////////////////////////

    public void RegisterUser(){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Pag0_Cadastro.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Usuario usuario = new Usuario("001",name,age,email,"M","Eu sou um macaco","5","1000","latitude","longitude","1","default","nenhum","nenhum","nenhum");
                            FirebaseUser user = mAuth.getCurrentUser();
                            // Write a message to the database
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>(){
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "Authentication Success.",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), Pag1_genero_musical.class));
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Authentication Fail.",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(getApplicationContext(), "No connection to Firebase.\n"+email+"\n"+password,Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }


    /////////////////////////////////////////////////////////////////////////
    /////////////////////////// OTHER FUNCTIONS /////////////////////////////
    public void abrirPag_teste_musical(View view){
        name = editName.getText().toString();
        age = editAge.getText().toString();
        password = editPassword.getText().toString();
        email = editEmail.getText().toString();


        if(password.equals("") || email.equals("")){
            Toast.makeText(this,"Por favor preencha os campos",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), Pag1_genero_musical.class));
        }else {
            if(password.length()<6){
                Toast.makeText(this,"A Senha deve ter pelo menos 6 caracteres",Toast.LENGTH_SHORT).show();
            }
            else if(!email.contains("@")){
                Toast.makeText(this,"O email deve ser válido",Toast.LENGTH_SHORT).show();
            }
            else {
                progressBar = findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
                RegisterUser();
            }
        }

    }
}
