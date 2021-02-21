package com.example.rockit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.rockit.Cadastro.Pag0_login;
import com.example.rockit.Classes.Usuario;
import com.example.rockit.Fragments.Fragment_shows;
import com.example.rockit.Fragments.Fragment_procurar_banda;
import com.example.rockit.Fragments.Fragmento_menu;
import com.example.rockit.Fragments.Fragmento_chats;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DatabaseHelper db=new DatabaseHelper(this);
    Integer id;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    FrameLayout frameLayout;
    BottomNavigationView navigationViewBottom;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    //GPS location
    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = 1;//update locate user
        getlocation();

        //PAGINA INICIAL É O FRAGMENT MENU
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Fragmento_menu()).commit();

        //BARRA LATERAL
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Toolbar Superior
        toolbar.setNavigationIcon(R.drawable.ic_profile_perfil_24dp);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        //BARRA INFERIOR
        navigationViewBottom = findViewById(R.id.bottom_nav);
        frameLayout = findViewById(R.id.frame_layout);
        navigationViewBottom.setOnNavigationItemSelectedListener(nav);
        navigationViewBottom.setSelectedItemId(R.id.bottom_home);

        DatabaseHelper db=new DatabaseHelper(this);
        //If there is no data in databaseHelp then create a new one
        try{
            db.getItem(1,1);
        }catch (Exception e){
            db.addData("Joaquim", "M", "Eu sou Legal", "21", "4", "230",
                    "23.56", "741.2", "0", "Rock", "Green Day", "Bateria", "pedrinho@gmail.com", "0");
        }



    }


    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser==null){
            Intent intent = new Intent(this, Pag0_login.class);
            startActivity(intent);
            finish();
        }
        //GET DATA FROM USER
        fireBaseDataUser();
    }

    ////////                GPS                  /////////
    private void getlocation(){
        //LATITUDE AND LONGITUDE LOCATION
        //REQUEST PERMISSION
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);

        client = LocationServices.getFusedLocationProviderClient(this);
        if(ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            return;
        }
        client.getLastLocation().addOnSuccessListener(MainActivity.this, location -> {
            if(location!=null){
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                db.updateLocationLongitude(id,Double.toString(longitude));
                db.updateLocationLatitude(id,Double.toString(latitude));
            }
        });
    }

////////                ITEMS BARRA LATERAL                   /////////
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.navi_alterar_perfil:
                Intent intent = new Intent(this, Pag_perfil_usuario.class);
                startActivity(intent);
                break;
            case R.id.navi_minha_agenda:
                Toast.makeText(this,"Agenda",Toast.LENGTH_SHORT).show();
                break;
            case R.id.navi_configuracoes:
                intent = new Intent(this, Pag_configuracoes.class);
                startActivity(intent);
                break;
            case R.id.navi_trocar_usuario:
                intent = new Intent(this, Pag0_login.class);
                startActivity(intent);
                break;
            case R.id.navi_sobre_app_info:
                intent = new Intent(this, Pag_Config_Sobre_App.class);
                startActivity(intent);
                break;
            case R.id.navi_fale_conosco:
                intent = new Intent(this, Database_teste.class);
                startActivity(intent);
                break;
            case R.id.navi_deletar_conta:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this,"Deletar Conta",Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    ////////                BARRA INFERIOR                      /////////
    private BottomNavigationView.OnNavigationItemSelectedListener nav=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.bottom_procurar_shows:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Fragment_shows()).commit();
                    break;
                case R.id.bottom_chat:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Fragmento_chats()).commit();
                    break;
                case R.id.bottom_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Fragmento_menu()).commit();
                    break;
                case R.id.bottom_procurar_banda:
                    frameLayout.setBackgroundColor(Color.LTGRAY);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Fragment_procurar_banda()).commit();
                    frameLayout.setBackgroundColor(Color.WHITE);
                    break;
            }
            return true;
        }
    };
    /////////////////////////////////////////////////////////////
    //                    F I R E B A S E                      //
    /////////////////////////////////////////////////////////////
    public void fireBaseDataUser(){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario user = snapshot.getValue(Usuario.class);
                if (user != null) {
                    //NAVIGATION DRAWER -> VISTA DAS CONFIGURAÇÕES QUANDO PUXA O MENU DA ESQUERDA
                    NavigationView navigationView = findViewById(R.id.navigation_view);
                    View headerView = navigationView.getHeaderView(0);
                    //Link Views
                    TextView navUsername = headerView.findViewById(R.id.textViewUserName);
                    navUsername.setText(user.getName());//NOME DO USUARIO


                    if (user.getImageURL().equals("default")) {
                        //FOTO NO MENU PRINCIPAL
                        ImageView fotoUsuario = findViewById(R.id.im_user);
                        fotoUsuario.setImageResource(R.drawable.foto_pessoa);
                        //FOTO NO NAVIGATION DRAWER
                        fotoUsuario = headerView.findViewById(R.id.im_user);
                        fotoUsuario.setImageResource(R.drawable.foto_pessoa);
                    } else {
                        ImageView fotoUsuario = findViewById(R.id.im_user);
                        fotoUsuario.setImageResource(R.drawable.foto_usuario);
                        //FOTO NO NAVIGATION DRAWER
                        fotoUsuario = headerView.findViewById(R.id.im_user);
                        fotoUsuario.setImageResource(R.drawable.foto_usuario);
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
////////                FUNÇÕES                      /////////
    public void abrirPag_banda(View view){
        Intent intent = new Intent(this, Pag_banda.class);
        startActivity(intent);
    }
    public void abrirPag_perfil_usuario(View view){
        Intent intent = new Intent(this, Pag_perfil_usuario.class);
        startActivity(intent);
    }

}
//FIREBASE FIRST CONNECTION WITH ANDROID
//https://www.youtube.com/watch?v=U5p8MAJAn_c

//Toast.makeText(this,"Agenda",Toast.LENGTH_SHORT).show();

//Branches GIT tutorial
//https://www.youtube.com/watch?v=TbRpFqjv0iM
//BRANCH INTO MASTER
//        git add -A
//        git commit -m
//        git checkout master (switches to master branch)
//        git pull origin master (pulls any new code thats been added to the master branch)
//        if there is new code then: git add -A and git commit -m again
//        git push origin master (push your code to the master branch on github)