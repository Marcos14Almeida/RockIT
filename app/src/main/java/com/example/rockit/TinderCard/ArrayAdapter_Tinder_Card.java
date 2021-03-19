package com.example.rockit.TinderCard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rockit.Classes.Cards_Tinder;
import com.example.rockit.R;

import java.util.List;

//https://www.youtube.com/watch?v=L5M596DWETg&list=PLxabZQCAe5fio9dm1Vd0peIY6HLfo5MCf&index=8
public class ArrayAdapter_Tinder_Card extends ArrayAdapter<Cards_Tinder> {

    public ArrayAdapter_Tinder_Card(Context context, int resourceID, List<Cards_Tinder> items){
        super(context,resourceID, items);
    }
    public View getView (int position, View convertView, ViewGroup parent) {
        Cards_Tinder cards_tinder = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_tinder, parent, false);
        }

        //TEXTOS PADRÃO FIXO
        TextView generico = convertView.findViewById(R.id.textInstruments);
        generico.setText(R.string.instrumentos_praticados);
        generico = convertView.findViewById(R.id.textAge);
        generico.setText(R.string.idade);
        generico = convertView.findViewById(R.id.textGenres);
        generico.setText(R.string.generos);
        generico = convertView.findViewById(R.id.textCity);
        generico.setText(R.string.cidade);

        //Dados usuario
        TextView name = convertView.findViewById(R.id.helloText);
        name.setText(cards_tinder.getName());

        //Procura banda SWITCH
        Switch aSwitch = convertView.findViewById(R.id.switch1);
        if(cards_tinder.getSearching_bands().equals("1")){
            aSwitch.setChecked(true);
        }
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cards_tinder.getSearching_bands().equals("1")){
                    aSwitch.setChecked(true);
                }
                else {
                    aSwitch.setChecked(false);
                }
            }
        });

        TextView instruments = convertView.findViewById(R.id.textInstruments2);
        instruments.setText(cards_tinder.getInstruments());

        TextView age = convertView.findViewById(R.id.textAge2);
        age.setText(cards_tinder.getAge());

        TextView genres = convertView.findViewById(R.id.textGenres2);
        genres.setText(cards_tinder.getGenres());

        TextView city = convertView.findViewById(R.id.textCity2);
        city.setText(cards_tinder.getCity());

        //imagem do usuario
        ImageView image = convertView.findViewById(R.id.imageViewFoto);
        if(cards_tinder.getImageURL().equals("default")){
            image.setImageResource(R.drawable.foto_pessoa);
        }else{
            Glide.with(convertView).load(cards_tinder.getImageURL()).into(image);
        }

        return  convertView;
    }
}
