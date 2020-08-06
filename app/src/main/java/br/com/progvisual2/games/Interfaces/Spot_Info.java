package br.com.progvisual2.games.Interfaces;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.progvisual2.games.Models.Spotlight;
import br.com.progvisual2.games.R;

public class Spot_Info extends AppCompatActivity {

    private ImageView imageCard;
    private TextView txtitlo;
    private Spotlight spots;
    Spotlight a;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot__info);
        incializarComponentes();
        ratingbarExibe();

        ArrayList<Spotlight> p = new ArrayList<>();

        p = (ArrayList<Spotlight>) getIntent().getSerializableExtra("spots");

        if(p != null){

           a = p.get(0);
           Picasso.get().load(a.image).into(imageCard);
           txtitlo.setText(a.title);


        }else{
            Toast.makeText(this, "Não foi possivel visualizar esse Jogo", Toast.LENGTH_SHORT).show();
        }

    }

    public void incializarComponentes(){
        ratingBar = findViewById(R.id.rating);
        imageCard = findViewById(R.id.imCardView);
        txtitlo = findViewById(R.id.txTitulo);
    }

    public void ratingbarExibe(){
        ratingBar.setRating(5);
        ratingBar.invalidate();
        ratingBar.setIsIndicator(true);
    }
}