package br.com.progvisual2.games;

import android.provider.CallLog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceInterface {

    public static final String api_base = "https://api-mobile-test.herokuapp.com/api/";
    @GET("banners")
    Call<ArrayList<Listas>> listCatalog();

}
