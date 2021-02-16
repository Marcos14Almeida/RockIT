package com.example.rockit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.Objects;


public class Fragment_procurar_banda extends Fragment {
///////////////////////////////////////////////
    //TINDER SWAP
//https://www.youtube.com/watch?v=SJW_4UMXbu8
//https://github.com/Diolor/Swipecards
///////////////////////////////////////////////
    private ArrayList<String> al;
    private ArrayAdapter<String> arrayAdapter;
    private int i;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragmento_procurar_banda, container, false);

        al = new ArrayList<>();
        //Se o perfil está no database
        boolean flag=false; int n=1;
        while(!flag) {
            try{
                DatabaseHelper db=new DatabaseHelper(getActivity());
                al.add(db.getName(n)+"\n"+db.getAge(n));
                n++;
            }catch (Exception e){
                flag=true;
            }
        }
        al.add("Geremias\n23\n São Paulo\n H");
        al.add("Rodolfo");
        al.add("Claudio");
        al.add("Barbara");
        al.add("Natália");
        al.add("Aaron Rodger");
        al.add("Silvio Santos");
        al.add("Rony Rústico");

        arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.card_tinder, R.id.helloText, al );

        SwipeFlingAdapterView flingContainer = view.findViewById(R.id.frame);

        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                al.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                Toast.makeText(getActivity(), "Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(getActivity(), "Right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                al.add("Pessoa ".concat(String.valueOf(i)));
                arrayAdapter.notifyDataSetChanged();
                Log.d("LIST", "notified");
                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                Toast.makeText(getActivity(), "onScroll", Toast.LENGTH_SHORT).show();
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(getActivity(), "Clicked!", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}
