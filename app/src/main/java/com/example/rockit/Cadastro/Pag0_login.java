package com.example.rockit.Cadastro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rockit.Classes.Usuario;
import com.example.rockit.MainActivity;
import com.example.rockit.Pag0_CadastroGoogle;
import com.example.rockit.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

//BR
//#1 Login and Registration Android App Tutorial Using Firebase Authentication - Create User
//https://www.youtube.com/watch?v=Z-RE1QuUWPg
public class Pag0_login extends AppCompatActivity {
    private GoogleSignInClient googleSignInClient;
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
        AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
        passwordResetDialog.setTitle("Reset Password");
        passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.");
        EditText resetMail = new EditText(view.getContext());
        passwordResetDialog.setView(resetMail);
        passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String mail = resetMail.getText().toString();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Email sent",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Error! Reset Link Not Sent",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        passwordResetDialog.create().show();
    }
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////// LOGIN GOOGLE /////////////////////////////
//https://www.youtube.com/watch?v=E1eqRNTZqDM&t=278s
    public void loginGoogle(View view){
        Toast.makeText(this,"Google",Toast.LENGTH_SHORT).show();
        int RC_SIGN_IN = 1;
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);

        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode, data);
        int RC_SIGN_IN = 1;
        if(requestCode==1){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount acc = task.getResult(ApiException.class);
                Toast.makeText(getApplicationContext(), "Sign In Sussessfully",Toast.LENGTH_SHORT).show();
                AuthCredential authCredential = GoogleAuthProvider.getCredential(acc.getIdToken(),null);
                mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Successful",Toast.LENGTH_SHORT).show();

                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            String userID = firebaseUser.getUid();
                            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

                            // Sign in success, update UI with the signed-in user's information
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("generos", " ");
                            map.put("instrumentos", " ");
                            map.put("bandas", " ");
                            map.put("id", userID);
                            map.put("name", account.getDisplayName());
                            map.put("age", "0");
                            map.put("sex", "X");
                            map.put("email", account.getEmail());
                            map.put("description", " ");
                            map.put("imageURL", "default");
                            map.put("searching_bands", "0");
                            map.put("status", "offline");
                            map.put("stars", "0");
                            map.put("myband", "0");
                            map.put("first_login", "false");
                            map.put("instagram", " ");
                            map.put("contact", "00 900000000");
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userID);
                            reference.updateChildren(map);


                            startActivity(new Intent(getApplicationContext(), Pag0_CadastroGoogle.class));
                        }else{
                            Toast.makeText(getApplicationContext(), "Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }catch (ApiException e){
                Toast.makeText(getApplicationContext(), "Sign In Failed",Toast.LENGTH_SHORT).show();

            }
        }
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