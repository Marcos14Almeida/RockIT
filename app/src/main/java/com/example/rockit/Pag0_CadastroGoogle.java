package com.example.rockit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.rockit.Cadastro.Pag1_genero_musical;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Pag0_CadastroGoogle extends AppCompatActivity {
    String age,sex;
    EditText editAge;
    RadioGroup radioGroup;
    RadioButton male,female;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag0_cadastro_google);
        editAge = findViewById(R.id.editTextAge);

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

    }
    /////////////////////////////////////////////////////////////
    //                    F I R E B A S E                      //
    /////////////////////////////////////////////////////////////
    public void acesso(String campo,String string){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        HashMap<String, Object> map = new HashMap<>();
        map.put(campo, string);
        reference.updateChildren(map);
    }
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////// OTHER FUNCTIONS /////////////////////////////
    public void abrirPag_teste_musical(View view){
        age = editAge.getText().toString();
        if(!age.equals(null) && !sex.equals(null)){
        acesso("age",age);
        acesso("sex",sex);
        startActivity(new Intent(getApplicationContext(), Pag1_genero_musical.class));}
        else{
            Toast.makeText(this,"Empty Fields!",Toast.LENGTH_SHORT).show();
        }
    }
}
