package com.example.rockit.Cadastro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rockit.MainActivity;
import com.example.rockit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Pag0_login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText editTextUsername,editTextPassword;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag0_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        editTextUsername = findViewById(R.id.editTextName);
        editTextPassword = findViewById(R.id.editTextPassword);
    }
    /////////////////////////////////////////////////////////////
    //                    F I R E B A S E                      //
    /////////////////////////////////////////////////////////////
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void SignInUser(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user
                            SignInUserFail();
                        }
                    }
                });
    }





    /////////////////////////////////////////////////////////////////////////
    /////////////////////////// OTHER FUNCTIONS /////////////////////////////
    public void SignInUserFail(){
        editTextUsername.setText("");
        editTextPassword.setText("");
        Toast.makeText(getApplicationContext(), "Authentication failed.",Toast.LENGTH_SHORT).show();
    }
    public void esqueceuSenha(View view){
    }

    public void LoginGoogle(View view){
        //email="a@gmail.com";
        //password="aaaaaa";
        //SignInUser(email,password);
    }
    public void abrirPag_Cadastro(View view){
        startActivity(new Intent(this, Pag0_Cadastro.class));
    }
    public void abrirMainActivity(View view){
        email=editTextUsername.getText().toString();
        password=editTextPassword.getText().toString();
        if(email.equals("") ||  password.equals("")){
            Toast.makeText(this,"Por favor preencha os campos",Toast.LENGTH_SHORT).show();
        }else {
        SignInUser(email,password);}

    }
}
//BR
//#1 Login and Registration Android App Tutorial Using Firebase Authentication - Create User
//https://www.youtube.com/watch?v=Z-RE1QuUWPg