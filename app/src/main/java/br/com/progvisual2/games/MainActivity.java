package br.com.progvisual2.games;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

import br.com.progvisual2.games.fragments.PageFragment;
import br.com.progvisual2.games.fragments.PageFragment_2;
import br.com.progvisual2.games.fragments.PageFragment_3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ImageView banner01;
    ImageView banner02;
    ImageView banner03;
    ImageView banner04;
    private ArrayList<Banners> urls;
    private ArrayList<Spotlight> urlsSpot;
    private ViewPager vpager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //PAGER
        ViewPager viewPager = findViewById(R.id.Pager);


        View v = getLayoutInflater().inflate(R.layout.banner_01, null);
        View v2 = getLayoutInflater().inflate(R.layout.banner_02, null);
        View v3 = getLayoutInflater().inflate(R.layout.banner_03, null);
        //INICALIZANDO OS BANNERS
        banner01 = v.findViewById(R.id.B01);
        banner02 = v2.findViewById(R.id.B02);
        banner03 = v3.findViewById(R.id.B03);


        ArrayList<Banners> urls = new ArrayList<>();
        ArrayList<Spotlight> urlsSpot = new ArrayList<>();


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
                    }

                }
            }

            @Override
            public void onFailure(Call<ArrayList<ListasSpot>> call, Throwable t) {

            }
        });

    }
}
