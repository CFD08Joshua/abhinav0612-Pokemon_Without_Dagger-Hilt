package com.yi.abhinav0612_pokemon.network;

import com.yi.abhinav0612_pokemon.model.PokemonResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;


public interface PokeApiService {
    @GET("pokemon")
    Observable<PokemonResponse> getPokemons();
}
