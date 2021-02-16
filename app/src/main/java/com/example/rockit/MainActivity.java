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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import android.content.Intent;
import android.widget.Toast;


import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DatabaseHelper db=new DatabaseHelper(this);
    Integer id;

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

    }


    ////////                GPS                  /////////
    private void getlocation(){
        //LATITUDE AND LONGITUDE LOCATION
        requestPermission();
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
    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);
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
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Fragmento_shows()).commit();
                    break;
                case R.id.bottom_chat:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new Fragmento_menu()).commit();
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