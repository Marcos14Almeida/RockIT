package com.example.rockit.Cadastro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.rockit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Pag0_Cadastro extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText editName,editEmail,editAge,editPassword;
    ProgressBar progressBar;
    String name,email,age,password,sex;
    RadioGroup radioGroup;
    RadioButton male,female;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag0_cadastro);

        editName = findViewById(R.id.editTextName);
        editAge = findViewById(R.id.editTextMembers);
        editPassword = findViewById(R.id.editTextPassword);
        editEmail = findViewById(R.id.autoCompleteTextViewCity);


        //radiogroup
        radioGroup = findViewById(R.id.radioGroup);
        male = findViewById(R.id.radioButton);
        female = findViewById(R.id.radioButton2);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioButton:
                        sex="M";
                        break;
                    case R.id.radioButton2:
                        sex="F";
                        break;
                    case R.id.radioButton3:
                        sex="X";
                        break;
                    default:
                        sex="Null";
                }
            }
        });

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
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            String userID = firebaseUser.getUid();
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userID);

                            // Sign in success, update UI with the signed-in user's information
                            HashMap<String, String> map = new HashMap<>();
                            map.put("generos"," ");
                            map.put("instrumentos"," ");
                            map.put("bandas"," ");
                            map.put("id",userID);
                            map.put("name",name);
                            map.put("age",age);
                            map.put("sex",sex);
                            map.put("email",email);
                            map.put("description"," ");
                            map.put("imageURL","default");
                            map.put("searching_bands","0");
                            map.put("status","offline");
                            map.put("stars","0");
                            map.put("myband","0");
                            map.put("first_login","false");
                            map.put("instagram"," ");
                            map.put("contact","00 900000000");
                            // Write a message to the database
                            reference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>(){
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "Authentication Success.",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), Pag1_bandas_preferidas.class));
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Authentication Fail.",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(getApplicationContext(), "Conexão de internet ruim :(\nNo connection to Firebase.",Toast.LENGTH_SHORT).show();
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


        if(password.equals("") || email.equals("") || age.equals("")){
            Toast.makeText(this,"Por favor preencha os campos",Toast.LENGTH_SHORT).show();
        }else {
            if(password.length()<6){
                Toast.makeText(this,"A Senha deve ter pelo menos 6 caracteres",Toast.LENGTH_SHORT).show();
            }
            if(!email.contains("@")){
                Toast.makeText(this,"O email deve ser válido com @...",Toast.LENGTH_SHORT).show();
            }
            if(!email.contains(".com")){
                Toast.makeText(this,"O email deve ser válido com \".com\"",Toast.LENGTH_SHORT).show();
            }
            if(Integer.parseInt(age)<0 || Integer.parseInt(age)>100){
                Toast.makeText(this,"A idade deve ser real",Toast.LENGTH_SHORT).show();
            }
            if(email.contains("@") && email.contains(".com") && password.length()>=6 && Integer.parseInt(age)>0 && Integer.parseInt(age)<100){
                progressBar = findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
                RegisterUser();
            }
        }

    }
}
