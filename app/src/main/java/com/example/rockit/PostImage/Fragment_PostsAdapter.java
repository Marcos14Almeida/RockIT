package com.example.rockit.PostImage;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rockit.Classes.Classe_Geral;
import com.example.rockit.Classes.Post;
import com.example.rockit.Classes.Usuario;
import com.example.rockit.Pag_Profile_Show;
import com.example.rockit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

//INSTAGRAM App with Firebase
//https://www.youtube.com/watch?v=mk2CrU-awkM&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=8
public class Fragment_PostsAdapter extends RecyclerView.Adapter<Fragment_PostsAdapter.ViewHolder>{

    public Context mContext;
    public List<Post> mPost;

    private FirebaseUser firebaseUser;


    public Fragment_PostsAdapter(Context mContext, List<Post> mPost) {
        this.mContext = mContext;
        this.mPost = mPost;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item,viewGroup,false);
        return new Fragment_PostsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Post post = mPost.get(i);

        //IMAGE
        getImage(post,viewHolder);

        //DESCRIPTION
        if(post.getDescription().equals("")){
            viewHolder.description.setVisibility(View.GONE);
        }else{
            viewHolder.description.setVisibility(View.VISIBLE);
            viewHolder.description.setText(post.getDescription());
        }

        //GET TIME FROM PUBLICATION
        Classe_Geral classe_geral = new Classe_Geral();
        String horario = classe_geral.Data(post.getTimestamp());
        viewHolder.publisher.setText(horario);

        //FUNCTIONS
        publisherInfo(viewHolder.image_profile, viewHolder.username, viewHolder.publisher, post.getPublisher());

        isLikes(post.getPostid(), viewHolder.like);

        nrLikes(viewHolder.likes, post.getPostid());

        getComments(post.getPostid(), viewHolder.comments);

        /////////////////////////////////////////////////////////////
        //                    CLICK  ON  ITEMS                     //
        /////////////////////////////////////////////////////////////

        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.like.getTag().equals("like")){
                    FirebaseDatabase.getInstance().getReference()
                            .child("Likes").child(post.getPostid())
                    .child(firebaseUser.getUid()).setValue(true);
                }else{
                    FirebaseDatabase.getInstance().getReference()
                            .child("Likes").child(post.getPostid())
                            .child(firebaseUser.getUid()).removeValue();
                }
            }
        });

        viewHolder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CommentsActivity.class);
                intent.putExtra("postid",post.getPostid());
                intent.putExtra("publisherid",post.getPublisher());
                mContext.startActivity(intent);
            }
        });

        viewHolder.image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Pag_Profile_Show.class);
                intent.putExtra("userID",post.getPublisher());
                mContext.startActivity(intent);
            }
        });
        //                    COPY TEXT                     //
        //https://stackoverflow.com/questions/19177231/android-copy-paste-from-clipboard-manager
        viewHolder.description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copy",post.getDescription());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(mContext,"Texto Copiado",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount(){
        return mPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView image_profile,post_image,like,comment,save;
        public TextView username,likes,publisher, description, comments;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image_profile = itemView.findViewById(R.id.image_profile);
            post_image = itemView.findViewById(R.id.post_image);
            like = itemView.findViewById(R.id.like);
            comment = itemView.findViewById(R.id.comment);
            save = itemView.findViewById(R.id.save);

            username = itemView.findViewById(R.id.username);
            likes = itemView.findViewById(R.id.likes);
            publisher = itemView.findViewById(R.id.publisher);
            description = itemView.findViewById(R.id.description);
            comments = itemView.findViewById(R.id.comments);
        }
    }
    /////////////////////////////////////////////////////////////
    //                    SHOW ITEMS                           //
    /////////////////////////////////////////////////////////////
    private void getImage(Post post, ViewHolder viewHolder){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("postimage").child(post.getPostid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(post.getPostimage().length()>2){
                Glide.with(mContext).load(post.getPostimage()).into(viewHolder.post_image);}
                else{
                    viewHolder.post_image.getLayoutParams().height=0;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getComments(String postid, TextView comments){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Comments").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                comments.setText("View All "+ snapshot.getChildrenCount()+" Comments");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void isLikes(String postid, final ImageView imageView){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(firebaseUser.getUid()).exists()) {
                    imageView.setImageResource(R.drawable.ic_rock_hand_fire);
                    imageView.setTag("liked");
                }else{
                    imageView.setImageResource(R.drawable.ic_rock_hand);
                    imageView.setTag("like");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void nrLikes(final TextView likes, String postid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                likes.setText(snapshot.getChildrenCount()+" Likes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void publisherInfo(ImageView image_profile, TextView username, TextView publisher, String userid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario user = snapshot.getValue(Usuario.class);

                    if (user.getImageURL().equals("default")) {
                        image_profile.setImageResource(R.drawable.foto_pessoa);
                    } else {
                        Glide.with(mContext).load(user.getImageURL()).into(image_profile);
                    }
                    username.setText(user.getName());

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}
