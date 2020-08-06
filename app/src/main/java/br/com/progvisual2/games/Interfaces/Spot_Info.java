package br.com.progvisual2.games.Interfaces;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.progvisual2.games.Models.Spotlight;
import br.com.progvisual2.games.R;

public class Spot_Info extends AppCompatActivity {

    private ImageView imageCard;
    private TextView txtitlo;
    private Spotlight spots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot__info);
        incializarComponentes();

        spots = (Spotlight) getIntent().getSerializableExtra("spots");
        
        if(spots != null){
            Picasso.get().load(spots.image).into(imageCard);
            txtitlo.setText( spots.title);
        }

    }

    public void incializarComponentes(){
        imageCard = findViewById(R.id.imCardView);
        txtitlo = findViewById(R.id.txTitulo);
    }
}