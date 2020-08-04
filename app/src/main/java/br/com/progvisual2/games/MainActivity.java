package br.com.progvisual2.games;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ImageView banner01;
    ImageView banner02;
    ImageView banner03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        banner01 = findViewById(R.id.Banner01);
        banner02 = findViewById(R.id.Banner02);
        banner03 = findViewById(R.id.Banner03);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServiceInterface.api_base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<ArrayList<Listas>> ListasServices = service.listCatalog();

        ListasServices.enqueue(new Callback<ArrayList<Listas>>() {
            @Override
            public void onResponse(Call<ArrayList<Listas>> call, Response<ArrayList<Listas>> response) {
                if(!response.isSuccessful()){
                    Log.i("eduardo","ERRO"+ response.code());
                }else{

                    ArrayList<Listas> t = response.body();

                    for(Banners a : t){

                        Log.i("eduardo",String.format("%s, %s: %s",a.id,a.image,a.url));
                        Picasso.with(MainActivity.this).load(a.image).into(banner01);

                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Listas>> call, Throwable t) {
                Log.e("eduardo","error"+ t.getMessage());
            }
        });



    }
}
