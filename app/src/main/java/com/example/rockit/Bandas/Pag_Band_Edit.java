package com.example.rockit.Bandas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.rockit.Classes.Banda;
import com.example.rockit.Classes.Usuario;
import com.example.rockit.MainActivity;
import com.example.rockit.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

//ta bugado quando sai da página -> culpa do updateFieldBand(); por algum motivo
public class Pag_Band_Edit extends AppCompatActivity {
    String userID;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    EditText editCity,editDescription,editInsta;
    //Lista de membros
    AutoCompleteTextView autoCompleteTextView;
    ArrayList<String> listMembers = new ArrayList<>();
    ArrayList<String> listMyMembers = new ArrayList<>();
    //FOTO data
    StorageReference storageReference;
    private static final int IMAGE_REQUEST=1;
    private Uri imageUri;
    private StorageTask uploadTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag_band_edit);

        //FOTO
        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        //Toolbar / barra superior
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Se clicar no botao de retornar
        toolbar.setNavigationOnClickListener(v -> finish());

        //Pega o ID da banda da Activity anterior list_bands
        userID = getIntent().getStringExtra("userID");

        readUsers(); // lista com nome de usuarios

        //Dados da página
        fireBaseDataBand();

        //SEARCH VIEW
        autoCompleteTextView=findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,listMembers);
        autoCompleteTextView.setAdapter(adapter);
    }



    public void button_add_Members(View view){
        String nameMember = autoCompleteTextView.getEditableText().toString();
        autoCompleteTextView.setText("");

        //REMOVE DUPLICATAS DO NOVO NA LISTA; POR CAUSA DE ALGUM BUG
        HashSet<String> hashSet = new HashSet<String>(listMyMembers);
        listMyMembers.clear();
        listMyMembers.addAll(hashSet);

        //If selecionado is on the list
        for(int i=0; i<listMembers.size();i++){
            if(listMembers.get(i).equals(nameMember)){
                listMyMembers.add(nameMember);
                listMembers.remove(nameMember);
            }
        }

        //NOME DOS MEMBROS DE LISTA PARA STRING
        String members = "";
        for(int i=0; i<listMyMembers.size();i++){
            members+= listMyMembers.get(i)+";";
        }

        Log.d("membros ",members);
        show_list_members();
        //SALVA NO DATABASE
        updateFieldBand("members",members);
    }

    public void show_list_members(){
        //SHOW LIST MEMBERS
        for(int i=0;i<listMyMembers.size();i++){
            listMyMembers.set(i,listMyMembers.get(i).trim());
        }

        ListView list = findViewById(R.id.list_members);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, listMyMembers);
        list.setAdapter(arrayAdapter);
        //Se clicar no membro
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) list.getItemAtPosition(position);
                //Toast.makeText(Pag1_bandas_preferidas.this,clickedItem,Toast.LENGTH_LONG).show();
                listMembers.add(clickedItem);
                listMyMembers.remove(clickedItem);
                list.setAdapter(arrayAdapter);
            }
        });


    }

    /////////////////////////////////////////////////////////////
    //                    F I R E B A S E                      //
    /////////////////////////////////////////////////////////////
    public void fireBaseDataBand(){
        //INICIA FIREBASE
        //recebe o userID do item selecionado
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Bands").child(userID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Banda user = snapshot.getValue(Banda.class);
                assert user != null;

                //Define o nome do usuario no topo da pagina
                TextView username=findViewById(R.id.nameBand);
                username.setText(user.getName());
                //Descrição
                editDescription=findViewById(R.id.texto_descricao);
                editDescription.setText(user.getDescription());
                //GENEROS
                username=findViewById(R.id.textGeneros);
                username.setText(user.getGeneros());
                //Cidade
                editCity=findViewById(R.id.textCity);
                editCity.setText(user.getCity());
                //MEMBROS
                listMyMembers.clear();
                listMyMembers.addAll(Arrays.asList(user.getMembers().split(";")   ));
                //INSTAGRAM
                editInsta=findViewById(R.id.textInsta);
                editInsta.setText(user.getInstagram());


                show_list_members();

                ///////////////////////////////////////////////////////////////
                //FOTOS
                if (user.getImageURL().equals("default")) {
                    //FOTO NO MENU PRINCIPAL
                    ImageView fotoUsuario = findViewById(R.id.im_banda);
                    fotoUsuario.setImageResource(R.drawable.foto_banda_generica);
                } else {
                    //FOTO NO MENU PRINCIPAL
                    ImageView fotoUsuario = findViewById(R.id.im_banda);
                    Glide.with(getApplicationContext()).load(user.getImageURL()).into(fotoUsuario);;
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readUsers(){
        listMembers.clear();
        listMyMembers.clear();
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Usuario oneUser = dataSnapshot.getValue(Usuario.class);
                        //garante que tem algum valor disponível
                        assert oneUser != null;
                        assert firebaseUser != null;
                        //Se não for o meu usuario mostra na lista
                        if (!oneUser.getId().equals(firebaseUser.getUid())) {
                            listMembers.add(oneUser.getName());
                        } else {
                            listMyMembers.add(oneUser.getName());
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

    }
    //troca dados no database
    public void updateFieldBand(String field, String string){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Bands").child(userID);
        HashMap<String, Object> map = new HashMap<>();
        map.put(field, string);
        reference.updateChildren(map);
    }
    /////////////////////////////////////////////////////////////
    //                     F U N Ç Õ E S                       //
    /////////////////////////////////////////////////////////////
    //ANTES DE SAIR DA PÁGINA SALVA A DESCRIÇÃO
    public void onDestroy() {
        super.onDestroy();
        String selecionado = editDescription.getEditableText().toString();
        updateFieldBand("description",selecionado);
        selecionado = editCity.getEditableText().toString();
        updateFieldBand("city",selecionado);
        selecionado = editInsta.getEditableText().toString();
        updateFieldBand("instagram",selecionado);

        Toast.makeText(this," Informações Salvas",Toast.LENGTH_SHORT).show();
    }
    /////////////////////////////////////////////////////////////
    //               T R O C A R    F O T O                    //
    /////////////////////////////////////////////////////////////
    public void changePerfilPicture(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void uploadImage(){
        final ProgressDialog pd = new ProgressDialog(getApplicationContext());
        pd.setMessage("Uploading");
        //pd.show();

        if(imageUri !=null){
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));
            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String aUri = downloadUri.toString();

                        updateFieldBand("imageURL", aUri);
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }else{
            Toast.makeText(getApplicationContext(),"No Image Selected",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
        }
        if(uploadTask != null && uploadTask.isInProgress()){
            Toast.makeText(getApplicationContext(),"Upload in Progress",Toast.LENGTH_SHORT).show();
        }else {
            uploadImage();
        }

    }


}
