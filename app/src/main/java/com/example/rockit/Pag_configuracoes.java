package com.example.rockit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rockit.Classes.Classe_Geral;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Pag_configuracoes extends AppCompatActivity {

    Classe_Geral classe_geral = new Classe_Geral();
    SeekBar seekBar;
    TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag_configuracoes);

        //Toolbar / barra superior
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.configuracoes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Se clicar no botao de retornar
        toolbar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        });


        //Distância padrão
        texto = findViewById(R.id.text_number_filter);
        texto.setText("100KM");
        seekBar=findViewById(R.id.seekBar);
        seekBar.setProgress(100);

        //Switches configuration
        fireBaseDataUser();

    }
    /////////////////////////////////////////////////////////////
    //                    F I R E B A S E                      //
    /////////////////////////////////////////////////////////////
    public void fireBaseDataUser(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //Switch Permission Location
                Switch aSwitch = findViewById(R.id.switch1);
                if(snapshot.hasChild("permission_location")) {
                    if (Objects.equals(snapshot.child("permission_location").getValue(), "1")) {
                        aSwitch.setChecked(true);
                    }
                }

                    aSwitch.setOnClickListener(v -> {
                        if (aSwitch.isChecked()) {
                            classe_geral.updateFieldUsers("permission_location", "1");
                            getlocation();
                            Toast.makeText(getApplicationContext(),"Acesso Permitido",Toast.LENGTH_SHORT).show();
                        } else {
                            classe_geral.updateFieldUsers("permission_location", "0");
                            classe_geral.deleteFieldUsers("latitude");
                            classe_geral.deleteFieldUsers("longitude");
                            Toast.makeText(getApplicationContext(),"Informação deletada",Toast.LENGTH_SHORT).show();
                        }
                    });



                //Switch Permission_notification
                Switch aSwitch2 = findViewById(R.id.switch2);
                if(snapshot.hasChild("permission_notification")) {
                    if (Objects.equals(snapshot.child("permission_notification").getValue(), "1")) {
                        aSwitch2.setChecked(true);
                    }
                    aSwitch2.setOnClickListener(v -> {
                        if (aSwitch2.isChecked()) {
                            classe_geral.updateFieldUsers("permission_notification", "1");
                        } else {
                            classe_geral.updateFieldUsers("permission_notification", "0");
                        }
                    });
                }


                //CONFIGURAÇÃO DE DISTÂNCIA
                if(snapshot.hasChild("filter")) {
                    if (snapshot.child("filter").hasChild("filter_distance")) {
                        int valor = Integer.parseInt(snapshot.child("filter").child("filter_distance").getValue().toString());
                        seekBar.setProgress(valor);
                        texto = findViewById(R.id.text_number_filter);
                        texto.setText(valor + "KM");
                    }
                }

                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    int seekBarValue;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        seekBarValue = seekBar.getProgress();
                        texto.setText(seekBarValue +"KM");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid()).child("filter");
                        classe_geral.updateFieldUsersReference("filter_distance", String.valueOf(seekBarValue),reference2);
                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    /////////////////////////////////////////////////////////////
    //                    DELETAR CONTA                        //
    /////////////////////////////////////////////////////////////
    //https://www.youtube.com/watch?v=Gp4SsHpzDdg
    public void deleteAccount(View view){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(true);
        dialog.setTitle("Você tem certeza?");
        dialog.setMessage("Atenção!!! Deletar a conta é uma operação permanente");
        dialog.setPositiveButton("Deletar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                firebaseUser.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Conta Deletada", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(getApplicationContext(), "Erro na operação", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alerta = dialog.create();
        alerta.show();
    }

    ////////                GPS                  /////////
    private void getlocation(){
        //LATITUDE AND LONGITUDE LOCATION
        //REQUEST PERMISSION
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);

        //GPS location
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        if(ActivityCompat.checkSelfPermission(Pag_configuracoes.this, ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            return;
        }

        client.getLastLocation().addOnSuccessListener(Pag_configuracoes.this, location -> {

                double lat = location.getLatitude();
                double lon = location.getLongitude();
                classe_geral.updateFieldUsers("latitude",Double.toString(lat));
                classe_geral.updateFieldUsers("longitude",Double.toString(lon));
        });
    }
}
