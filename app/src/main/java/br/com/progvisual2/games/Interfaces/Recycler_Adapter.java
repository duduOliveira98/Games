package br.com.progvisual2.games.Interfaces;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.progvisual2.games.Models.Spotlight;
import br.com.progvisual2.games.R;

public class Recycler_Adapter extends RecyclerView.Adapter<Recycler_Adapter.MyviewHolder> {

    public ArrayList<Spotlight> spotlights;
    private Context context;

    public Recycler_Adapter(ArrayList<Spotlight> spotlights, Context context) {
        this.spotlights = spotlights;
        this.context = context;

    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycler__adapter, parent, false);
        return  new MyviewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int i) {
        Spotlight spotlight = spotlights.get(i);
        Picasso.get().load(spotlight.image).into(holder.imageView1);
        holder.titulo1.setText(spotlight.title);
        holder.plataforma1.setText(spotlight.publisher);
        holder.desconto1.setText("R$ " +  spotlight.discount);
        holder.preco1.setText("R$ " +  spotlight.price);

    }

    @Override
    public int getItemCount() {
        return spotlights.size();
    }


    public class MyviewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView1;
        public TextView plataforma1;
        public TextView titulo1;
        public TextView desconto1;
        public TextView preco1;
        public ImageView imageView2;
        public TextView plataforma2;
        public TextView titulo2;
        public TextView desconto2;
        public TextView preco2;

        public MyviewHolder(View itemView) {
            super(itemView);

            //LOTE 01
            imageView1 = itemView.findViewById(R.id.imageView);
            plataforma1 = itemView.findViewById(R.id.plataforma1);
            titulo1 = itemView.findViewById(R.id.titulo1);
            desconto1 = itemView.findViewById(R.id.desconto1);
            preco1 = itemView.findViewById(R.id.preco1);


        }
    }
}