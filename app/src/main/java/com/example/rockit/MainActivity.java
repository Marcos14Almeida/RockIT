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

import com.bumptech.glide.Glide;
import com.example.rockit.Bandas.Pag_List_Your_Band;
import com.example.rockit.Cadastro.Pag0_login;
import com.example.rockit.Classes.Classe_Geral;
import com.example.rockit.Classes.Usuario;
import com.example.rockit.PostImage.Fragment_posts;
import com.example.rockit.TinderCard.Fragment_procurar_banda;
import com.example.rockit.FragmentBands.Fragmento_bandas;
import com.example.rockit.Chat.Fragmento_chats;
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


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Classe_Geral classe_geral = new Classe_Geral();

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    FrameLayout frameLayout;
    BottomNavigationView navigationViewBottom;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser==null){
            Intent intent = new Intent(this, Pag0_login.class);
            startActivity(intent);
            finish();
        }else {
            //GET LOCATION
            //update locate user
            getlocation();
            //GET DATA FROM USER
            fireBaseDataUser();

        }

        //PAGINA INICIAL É O FRAGMENT MENU
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Fragmento_bandas()).commit();

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
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////                GPS                  /////////
    private void getlocation(){
        //LATITUDE AND LONGITUDE LOCATION
        //REQUEST PERMISSION
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);

        //GPS location
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        if(ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            return;
        }
        client.getLastLocation().addOnSuccessListener(MainActivity.this, location -> {
            if(location!=null){
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                classe_geral.updateFieldUsers("latitude",Double.toString(lat));
                classe_geral.updateFieldUsers("longitude",Double.toString(lon));
            }
        });
    }

////////                ITEMS BARRA LATERAL                   /////////
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.navi_alterar_perfil:
                Intent intent = new Intent(this, Pag_Profile_Edit.class);
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
            case R.id.navi_configurar_banda:
                intent = new Intent(this, Pag_List_Your_Band.class);
                startActivity(intent);
                break;
            case R.id.navi_sobre_app_info:
                intent = new Intent(this, Pag_Config_Sobre_App.class);
                startActivity(intent);
                break;
            case R.id.navi_avalie_app:
                intent = new Intent(this, AvaliacaoApp.class);
                startActivity(intent);
                break;
            case R.id.navi_fale_conosco:
                intent = new Intent(this, Database_teste.class);
                startActivity(intent);
                break;
            case R.id.navi_logout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this,"Logout",Toast.LENGTH_SHORT).show();
                intent = new Intent(this, Pag0_login.class);
                startActivity(intent);
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
                case R.id.bottom_bandas:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Fragmento_bandas()).commit();
                    break;
                case R.id.bottom_chat:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Fragmento_chats()).commit();
                    break;
                case R.id.bottom_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Fragment_posts()).commit();
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
                if (snapshot.hasChild("imageURL")) {

                        //NAVIGATION DRAWER -> VISTA DAS CONFIGURAÇÕES QUANDO PUXA O MENU DA ESQUERDA
                        NavigationView navigationView = findViewById(R.id.navigation_view);
                        View headerView = navigationView.getHeaderView(0);
                        //Link Views do navigation drawer
                        TextView navUsername = headerView.findViewById(R.id.textViewUserName);
                        navUsername.setText(user.getName());//NOME DO USUARIO


                        ///////////////////////////////////////////////////////////////
                        //FOTOS
                        if (user.getImageURL().equals("default")) {
                            //FOTO NO MENU PRINCIPAL
                            ImageView fotoUsuario = findViewById(R.id.im_user);
                            fotoUsuario.setImageResource(R.drawable.foto_pessoa);
                            //FOTO NO NAVIGATION DRAWER
                            fotoUsuario = headerView.findViewById(R.id.im_user);
                            fotoUsuario.setImageResource(R.drawable.foto_pessoa);
                        } else {
                            //FOTO NO MENU PRINCIPAL
                            ImageView fotoUsuario = findViewById(R.id.im_user);
                            Glide.with(getApplicationContext()).load(user.getImageURL()).into(fotoUsuario);
                            //FOTO NO NAVIGATION DRAWER
                            fotoUsuario = headerView.findViewById(R.id.im_user);
                            Glide.with(getApplicationContext()).load(user.getImageURL()).into(fotoUsuario);
                        }


                    }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {            }
        });
    }

////////                STATUS - Is User Online?                     /////////
    @Override
    protected void onResume(){
            super.onResume();
        classe_geral.updateFieldUsers("status","online");
    }
    @Override
    protected void onPause(){
        super.onPause();
        classe_geral.updateFieldUsers("status","offline");

        //HORARIO ULTIMA VEZ ONLINE
        DateFormat df = new SimpleDateFormat("HH:mm;dd/MM/yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        classe_geral.updateFieldUsers("last_seen",date);
    }

}
//FIREBASE FIRST CONNECTION WITH ANDROID
//https://www.youtube.com/watch?v=U5p8MAJAn_c

//Toast.makeText(this,"Agenda",Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(this, Pag0_login.class);
//                startActivity(intent);
//Classe_Geral classe_geral = new Classe_Geral();


//Branches GIT tutorial
//https://www.youtube.com/watch?v=TbRpFqjv0iM
//BRANCH INTO MASTER
//        git add -A
//        git commit -m
//        git checkout master (switches to master branch)
//        git pull origin master (pulls any new code thats been added to the master branch)
//        if there is new code then: git add -A and git commit -m again
//        git push origin master (push your code to the master branch on github)