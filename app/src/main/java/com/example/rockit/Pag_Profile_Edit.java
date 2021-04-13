package com.example.rockit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rockit.Cadastro.Pag1_bandas_preferidas;
import com.example.rockit.Cadastro.Pag1_genero_musical;
import com.example.rockit.Cadastro.Pag1_qual_intrumento_toca;
import com.example.rockit.Classes.Classe_Geral;
import com.example.rockit.Classes.Usuario;
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
import java.util.regex.Pattern;

public class Pag_Profile_Edit extends AppCompatActivity {

    Classe_Geral classe_geral = new Classe_Geral();

    FirebaseUser firebaseUser;
    DatabaseReference reference;
    ListView list;
    TextView textView;
    Switch aSwitch;
    ArrayList<String> arrayText = new ArrayList<>();
    ArrayList<String> arrayText2 = new ArrayList<>();
    ArrayList<String> arrayText3 = new ArrayList<>();
    EditText editDescription,editContact,editContact2,editInstagram;

    //FOTO data
    StorageReference storageReference;
    private static final int IMAGE_REQUEST=1;
    private Uri imageUri;
    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag_profile_edit);

        //FIREBASE
        fireBaseDataUser();

        //FOTO
        storageReference = FirebaseStorage.getInstance().getReference("uploads");


    }


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

            //FOTO DO PERFIL
            ImageView fotoUsuario = findViewById(R.id.im_user);
            if(user.getImageURL().equals("default")){
                fotoUsuario.setImageResource(R.drawable.foto_pessoa);
            }else{
                Glide.with(getApplicationContext()).load(user.getImageURL()).into(fotoUsuario);
            }

            //LOCALIZAÇÃO
            String cidade="";
            if(snapshot.hasChild("latitude")){//se o usuario permitiu registrar latitude e longitude
                cidade = classe_geral.cidade_user(getApplicationContext(),user.getLatitude(),user.getLongitude());
            }
            TextView texto = findViewById(R.id.textViewCidade);
            texto.setText(cidade);

        //Name User
        textView = findViewById(R.id.nome_usuario);
        textView.setText(user.getName());
        //Age User
        textView = findViewById(R.id.textViewAge);
        textView.setText(user.getAge());
        //Sex User
            textView = findViewById(R.id.textViewSex);
            textView.setText(user.getSex());
        //Description
        editDescription=findViewById(R.id.textDescription);
        editDescription.setText(user.getDescription());

            //Contact
            editContact=findViewById(R.id.textPhone);
            editContact2=findViewById(R.id.textPhone2);

                if(user.getContact().length()>1){
                    String[] words = user.getContact().split(" ");
                    editContact.setText(words[0]);
                    editContact2.setText(words[1]);
                }


            //Instagram
            editInstagram=findViewById(R.id.textInsta);
            editInstagram.setText(user.getInstagram());

            //Lista Fav Bands
            list = findViewById(R.id.listFavBands);

            arrayText.clear();
            arrayText.add(classe_geral.filterBands(snapshot));
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayText);
            list.setAdapter(arrayAdapter);

            //Lista Instruments
            ListView list2 = findViewById(R.id.listInstruments);
            arrayText2.clear();
            arrayText2.add(user.getInstrumentos());
            ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayText2);
            list2.setAdapter(arrayAdapter2);

            //Lista Fav Genres
            String auxiliarString = classe_geral.filterGeneros(snapshot);

            ListView list3 = findViewById(R.id.listFavGenres);
            arrayText3.clear();
            arrayText3.add(auxiliarString);
            ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayText3);
            list3.setAdapter(arrayAdapter3);


            //Switch Search Band
            aSwitch = findViewById(R.id.switch1);
            if(user.getSearching_bands().equals("1")){
                aSwitch.setChecked(true);
            }
            aSwitch.setOnClickListener(v -> {
                if(aSwitch.isChecked()){
                    classe_geral.updateFieldUsers("searching_bands","1");
                }else{
                    classe_geral.updateFieldUsers("searching_bands","0");
                }
            });


    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
    }
});
    }

    /////////////////////////////////////////////////////////////
    //                     F U N Ç Õ E S                       //
    /////////////////////////////////////////////////////////////
    //ANTES DE SAIR DA PÁGINA SALVA A DESCRIÇÃO
    public void onPause() {
        super.onPause();
        //atualiza campos
        String selecionado = editDescription.getEditableText().toString();
        classe_geral.updateFieldUsers("description",selecionado);
        selecionado = editInstagram.getEditableText().toString();
        classe_geral.updateFieldUsers("instagram",selecionado);
        selecionado = editContact.getEditableText().toString();
        String selecionado2 = editContact2.getEditableText().toString();
        if((Pattern.compile("[0-9][0-9]").matcher(selecionado).matches() || Pattern.compile("[0-9][0-9][0-9]").matcher(selecionado).matches() )
        && selecionado2.length()>0){
            classe_geral.updateFieldUsers("contact",selecionado+" "+selecionado2);
            Toast.makeText(this,"Informações Salvas",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Número não salvo/Incorreto",Toast.LENGTH_SHORT).show();
        }



    }

    public void Editar_Fav_Genres(View view){
        Intent intent = new Intent(this, Pag1_genero_musical.class);
        startActivity(intent);
    }
    public void Editar_Fav_Bandas(View view){
        Intent intent = new Intent(this, Pag1_bandas_preferidas.class);
        startActivity(intent);
    }
    public void Editar_Instruments(View view){
        Intent intent = new Intent(this, Pag1_qual_intrumento_toca.class);
        startActivity(intent);
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

                        classe_geral.updateFieldUsers("imageURL", aUri);
                        pd.dismiss();

                        Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();
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
