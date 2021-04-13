package com.example.rockit.FragmentBands;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rockit.Bandas.Pag_Band_Edit;
import com.example.rockit.Bandas.Pag_Band_Show;
import com.example.rockit.Classes.Banda;
import com.example.rockit.R;

import java.util.ArrayList;

public class RecycleView_bandas extends RecyclerView.Adapter<RecycleView_bandas.ExampleViewHolder>{
    private ArrayList<Banda> mExampleList;
    private Context mcontext;
    private String mshowOrEditBandPage;

    static class ExampleViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView1;
        TextView mTextView2;
        TextView mTextView3;
        ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.im_banda);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            mTextView3 = itemView.findViewById(R.id.textView3);
        }
    }
    public RecycleView_bandas(Context context, ArrayList<Banda> exampleList, String showOrEditBandPage) {
        this.mExampleList = exampleList;
        this.mcontext = context;
        this.mshowOrEditBandPage = showOrEditBandPage;
    }

    public RecycleView_bandas.ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_bands, parent, false);
        RecycleView_bandas.ExampleViewHolder evh = new RecycleView_bandas.ExampleViewHolder(v);
        return evh;
    }

    public void onBindViewHolder(RecycleView_bandas.ExampleViewHolder holder, int position) {
        Banda currentItem = mExampleList.get(position);
        //IMAGES FROM CHAT USERS
        if(currentItem.getImageURL().equals("default")){
            holder.mImageView.setImageResource(R.drawable.foto_banda_generica);
        }else{
            Glide.with(mcontext).load(currentItem.getImageURL()).into(holder.mImageView);
        }
        holder.mTextView1.setText(currentItem.getName());
        holder.mTextView2.setText(currentItem.getCity());
        holder.mTextView3.setText(currentItem.getDescription());

        //Se clicar pra editar banda no Pag_list_Your_Band
        if(mshowOrEditBandPage.equals("edit")){
            //SE CLICA NA IMAGEM ABRE A PAG DA BANDA
            holder.mImageView.setOnClickListener(v -> {
                Intent intent = new Intent(mcontext, Pag_Band_Edit.class);
                intent.putExtra("userID", currentItem.getName());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);
                //PRECISA FECHAR A ATIVIDADE
            });
            holder.mTextView1.setOnClickListener(v -> {
                Intent intent = new Intent(mcontext, Pag_Band_Edit.class);
                intent.putExtra("userID", currentItem.getName());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);
                //PRECISA FECHAR A ATIVIDADE
            });

        }else{//Fragmento_menu
            //SE CLICA NA IMAGEM ABRE A PAG DA BANDA
            holder.mImageView.setOnClickListener(v -> {
                Intent intent = new Intent(mcontext, Pag_Band_Show.class);
                intent.putExtra("userID", currentItem.getName());
                mcontext.startActivity(intent);
            });
            holder.mTextView1.setOnClickListener(v -> {
                Intent intent = new Intent(mcontext, Pag_Band_Show.class);
                intent.putExtra("userID", currentItem.getName());
                mcontext.startActivity(intent);
            });
        }
    }

    public int getItemCount() {
        return mExampleList.size();
    }

}