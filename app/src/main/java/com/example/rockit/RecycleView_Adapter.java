package com.example.rockit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleView_Adapter extends RecyclerView.Adapter<RecycleView_Adapter.ExampleViewHolder> {
    private ArrayList<Usuario> mExampleList;
    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;
        public ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            mTextView3 = itemView.findViewById(R.id.textView3);
        }
    }
    public RecycleView_Adapter(ArrayList<Usuario> exampleList) {
        mExampleList = exampleList;
    }
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Usuario currentItem = mExampleList.get(position);
        holder.mImageView.setImageResource(currentItem.getPhoto());
        holder.mTextView1.setText(currentItem.getName());
        holder.mTextView2.setText(currentItem.getAge());
        holder.mTextView3.setText(currentItem.getDescription());
    }
    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
