package com.example.rockit.Post;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rockit.Classes.Comment;
import com.example.rockit.Classes.Usuario;
import com.example.rockit.MainActivity;
import com.example.rockit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

//https://www.youtube.com/watch?v=V2lai8cJIkk&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=11
public class CommentAdapter extends  RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    private List<Comment> mComment;
    private Context mcontext;
    private  FirebaseUser firebaseUser;

    public void onBindViewHolder(ViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Comment comment = mComment.get(position);

        holder.comment.setText(comment.getComment());
        getUserInfo(holder.image_profile,holder.username, comment.getPublisher());

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //**VERIFICAR
                //10min
                //https://www.youtube.com/watch?v=V2lai8cJIkk&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=11
                Intent intent = new Intent(mcontext, MainActivity.class);
                intent.putExtra("publisherid", comment.getPublisher());
                mcontext.startActivity(intent);
            }
        });

        holder.image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //**VERIFICAR
                Intent intent = new Intent(mcontext,MainActivity.class);
                intent.putExtra("publisherid", comment.getPublisher());
                mcontext.startActivity(intent);
            }
        });
    }
    public int getItemCount(){
        return mComment.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image_profile;
        public TextView username;
        public TextView comment;
        public ViewHolder(View itemView) {
            super(itemView);
            image_profile = itemView.findViewById(R.id.image_profile);
            username = itemView.findViewById(R.id.username);
            comment = itemView.findViewById(R.id.comment);
        }
    }
    public CommentAdapter(Context context, List<Comment> mComment) {
        this.mComment = mComment;
        this.mcontext = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.comment_item,viewGroup,false);
        return new CommentAdapter.ViewHolder(view);
    }

    public void getUserInfo(ImageView imageView, TextView username, String publisherid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(publisherid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario user = snapshot.getValue(Usuario.class);
                if(user.getImageURL().equals("default")){
                    imageView.setImageResource(R.drawable.foto_pessoa);
                }else {
                    Glide.with(mcontext).load(user.getImageURL()).into(imageView);
                }
                username.setText(user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}
