package com.example.rockit.PostImage;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rockit.Classes.Post;
import com.example.rockit.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

//INSTAGRAM App with Firebase
//https://www.youtube.com/watch?v=mk2CrU-awkM&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=8
//https://www.youtube.com/watch?v=GV1qbi59rgc&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=6
public class Fragment_posts extends Fragment {

    private RecyclerView recyclerView;
    private Fragment_PostsAdapter fragmentPostAdapter;
    private List<Post> postList;

    private List<String> followingList;

    public Fragment_posts() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        //Barra de Carregar Página
        ProgressDialog progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        postList = new ArrayList<>();
        fragmentPostAdapter = new Fragment_PostsAdapter(getContext(), postList);
        recyclerView.setAdapter(fragmentPostAdapter);

        checkFollowing();

        ///////////////////////////////////////////////////////////
        //POST COMMENT
        //////////////////////////////////////////////////////////
        TextView post = view.findViewById(R.id.textPost);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = view.findViewById(R.id.editPost);

                //POST MESSAGE
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
                String postid = reference.push().getKey();
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("postid",postid);
                hashMap.put("postimage","");
                hashMap.put("description",editText.getText().toString());
                hashMap.put("publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());

                //HORARIO MENSAGEM
                @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("HH:mm;dd/MM/yyyy");
                String date = df.format(Calendar.getInstance().getTime());
                hashMap.put("timestamp", date);

                reference.child(postid).setValue(hashMap);

                //Limpa o campo
                editText.setText("");
            }
        });

        /////////////////////////////////////////////////////////////////////////
        //POST IMAGE
        ////////////////////////////////////////////////////////////////////////
        ImageView imageView = view.findViewById(R.id.im_post_photo);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), ActivityPostImage.class);
                startActivity(intent);
            }
        });

        progressDialog.dismiss();

        return view;
    }

    private void checkFollowing(){
        followingList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Follow")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("following");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                followingList.clear();
            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                followingList.add(dataSnapshot.getKey());
            }
            readPosts();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readPosts(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                    Post post = dataSnapshot.getValue(Post.class);
                    //for(String id: followingList){
                        assert post != null;
                        //if(post.getPublisher().equals(id)){
                        postList.add(post);
                        //}
                    //}
                }
                fragmentPostAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
