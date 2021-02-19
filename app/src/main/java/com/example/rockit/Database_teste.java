package com.example.rockit;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Database_teste extends AppCompatActivity {

    Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_teste);


        DatabaseHelper db=new DatabaseHelper(this);
        //If there is no data in databaseHelp then create a new one
       try{
           db.getItem(1,1);
       }catch (Exception e){
           db.addData("Joaquim", "M", "Eu sou Legal", "21", "4", "230",
                   "23.56", "741.2", "0", "Rock", "Green Day", "Bateria", "pedrinho@gmail.com", "0");
           //db.addName("Jonas");
       }

        id = 3;
        db.updateName(id,  "Boris");

        id = 4;
        db.deleteName(id); //deleta os dados da tabela, mas não a tabela

        id = 1;
        String name = db.getItem(1,1);

        String Resultado=db.printData();
        TextView texto = findViewById(R.id.textoDatabase);
        texto.setText(Resultado+id+": "+name);


        /////////////////////////////////////////////////////////////
        //                    F I R E B A S E                      //
        /////////////////////////////////////////////////////////////

        /////////
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User");

        myRef.setValue("Hello, World!");

        //Objeto usuario com dados generiocos
        Usuario user = new Usuario("Mickey", "18","teste@gma",0);
        myRef = database.getReference().child("User");
        myRef.setValue(user);
        ////////////
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);

                texto.setText(Resultado+"\nValue is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                texto.setText("Failed to read value.");
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }//OnCreate

}