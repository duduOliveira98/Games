package br.com.progvisual2.games.Interfaces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.progvisual2.games.Adapters.Listas;
import br.com.progvisual2.games.Adapters.ListasSpot;
import br.com.progvisual2.games.Adapters.RecyclerItemClickListener;
import br.com.progvisual2.games.Models.Banners;
import br.com.progvisual2.games.Models.Spotlight;
import br.com.progvisual2.games.R;
import br.com.progvisual2.games.Adapters.ServiceInterface;
import br.com.progvisual2.games.ViewPager.SlidePagerAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private ArrayList<Banners> urls;
    private ArrayList<Spotlight> urlsSpot;
    List<Spotlight> spots = new ArrayList<Spotlight>();
    private ViewPager vpager;
    private PagerAdapter pagerAdapter;
    private RecyclerView recyclerView;
    private Recycler_Adapter recycler_adapter;
    private ArrayList<Spotlight>spotlights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView= findViewById(R.id.recyclerView);
        //PAGER
        ViewPager viewPager = findViewById(R.id.Pager);

        ArrayList<Banners> urls = new ArrayList<>();
        ArrayList<Spotlight> urlsSpot = new ArrayList<>();


        //CHAMANDO RETROFIT
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServiceInterface.api_base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<ArrayList<Listas>> ListasServices = service.listCatalog();
        Call<ArrayList<ListasSpot>> ListasServices2 = service.listSpot();


        //CONSUMINDO A API PARA O GET BANNERS
        ListasServices.enqueue(new Callback<ArrayList<Listas>>() {
            @Override
            public void onResponse(Call<ArrayList<Listas>> call, Response<ArrayList<Listas>> response) {
                if(!response.isSuccessful()){
                    Log.i("eduardo","ERRO"+ response.code());
                }else{

                    ArrayList<Listas> t = response.body();

                    for(Banners a : t){

                        Log.i("eduardo",String.format("%s, %s: %s",a.id,a.image,a.url));

                        //ADCIONANDO AS URLS DOS BANNERS EM UM ARRAYLIST PARA DEPOIS INSERIR NOS IMAGEVIEW
                        urls.add(a);
                    }

                    //CARREGANDO OS BANNER NA ACTIVITY COM O PICASSO
                        Banners b1 = urls.get(0);
                        Banners b2 = urls.get(1);
                        Banners b3 = urls.get(2);
                        // Picasso.get().load(b1.image).into(banner01);
                        // Picasso.get().load(b2.image).into(banner02);
                        // Picasso.get().load(b3.image).into(banner03);
                        ArrayList<String> teste2 = new ArrayList<>();
                        teste2.add(b1.image);
                        teste2.add(b2.image);
                        teste2.add(b3.image);
                        SlidePagerAdapter slidePagerAdapter = new SlidePagerAdapter(MainActivity.this,teste2);
                        viewPager.setAdapter(slidePagerAdapter);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Listas>> call, Throwable t) {
                Log.e("eduardo","error"+ t.getMessage());
            }
        });


        //CONSUMINDO A API PARA O GET SPOTLIGTH
        ListasServices2.enqueue(new Callback<ArrayList<ListasSpot>>() {
            @Override
            public void onResponse(Call<ArrayList<ListasSpot>> call, Response<ArrayList<ListasSpot>> response) {
                if(!response.isSuccessful()){
                    Log.i("eduardoSpot","ERRO"+ response.code());
                }else{
                    ArrayList<ListasSpot> t = response.body();

                    for(Spotlight a : t){

                        Log.i("eduardoSpot",String.format("%s, %s: %s",a.id,a.image,a.title));

                        //ADCIONANDO AS URLS DOS BANNERS EM UM ARRAYLIST PARA DEPOIS INSERIR NOS IMAGEVIEW
                        urlsSpot.add(a);
                        spots.add(a);
                    }
                    recycler_adapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onFailure(Call<ArrayList<ListasSpot>> call, Throwable t) {

            }
        });

        //INFLANDO O ADAPTER RV
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recycler_adapter = new Recycler_Adapter(urlsSpot,this);
        recyclerView.setAdapter(recycler_adapter);

        //ADC EVENTO DE CLICK NO RV
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                if(spots != null){
                                    Spotlight spotlight = spots.get(position);
                                    //PASSANDO OS DADOS DO CARDVIEW PARA TELA ABRE CARDVIEW
                                    Intent intent = new Intent(MainActivity.this, Spot_Info.class);
                                    intent.putExtra("spots", (Serializable) spots);
                                    startActivity(intent);
                                }else{

                                    Toast.makeText(MainActivity.this, "Não foi possivel visualizar esse Jogo", Toast.LENGTH_SHORT).show();

                                }

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );



    }



}
