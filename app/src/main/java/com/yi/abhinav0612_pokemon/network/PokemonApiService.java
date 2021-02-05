package com.yi.abhinav0612_pokemon.network;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonApiService {

    /**
     * NetworkModule - providePokemonApiService
     * @return
     */
    public static PokeApiService providePokemonApiService(){
        return  new Retrofit.Builder()
                .baseUrl(" https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(PokeApiService.class);
    }
}
