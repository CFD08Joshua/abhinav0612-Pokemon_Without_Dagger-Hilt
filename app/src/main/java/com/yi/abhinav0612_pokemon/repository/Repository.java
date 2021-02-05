package com.yi.abhinav0612_pokemon.repository;

import androidx.lifecycle.LiveData;

import com.yi.abhinav0612_pokemon.db.PokeDao;
import com.yi.abhinav0612_pokemon.model.Pokemon;
import com.yi.abhinav0612_pokemon.model.PokemonResponse;
import com.yi.abhinav0612_pokemon.network.PokeApiService;
import com.yi.abhinav0612_pokemon.network.PokemonApiService;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class Repository {

    private PokeDao pokeDao;
    private PokeApiService pokeApiService;

    // TODO  Inject
    public Repository(PokeDao pokeDao, PokeApiService apiService) {
        this.pokeDao = pokeDao;
        this.pokeApiService = apiService;
    }


//    public Observable<PokemonResponse> getPokemons(){
//        return pokeApi.getPokemons();
//    }

    public Observable<PokemonResponse> getPokemons(){
        return PokemonApiService.providePokemonApiService().getPokemons();
    }

    public void insertPokemon(Pokemon pokemon){
        pokeDao.insertPokemon(pokemon);
    }

    public void deletePokemon(String pokemonName){
        pokeDao.deletePokemon(pokemonName);
    }

    public void deleteAll(){
        pokeDao.deleteAll();
    }

    public LiveData<List<Pokemon>> getFavoritePokemon(){
        return pokeDao.getFavoritePokemons();
    }
}
