package com.yi.abhinav0612_pokemon;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.yi.abhinav0612_pokemon.db.PokeDao;
import com.yi.abhinav0612_pokemon.network.PokeApiService;
import com.yi.abhinav0612_pokemon.repository.Repository;
import com.yi.abhinav0612_pokemon.viewmodel.PokemonViewModel;

public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {

    @SuppressLint("StaticFieldLeak")
    private static ViewModelProviderFactory viewModelProviderFactory;

    private Repository repository;

    private ViewModelProviderFactory(PokeDao pokeDao, PokeApiService apiService) {
        this.repository = new Repository(pokeDao,apiService);
    }

    public static ViewModelProviderFactory getInstance(PokeDao pokeDao, PokeApiService apiService) {
        if (viewModelProviderFactory == null) {
            viewModelProviderFactory = new ViewModelProviderFactory(pokeDao,apiService);
        }
        return viewModelProviderFactory;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //Controller-MainActivity
        if (modelClass.isAssignableFrom(PokemonViewModel.class)) {
            return (T) new PokemonViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}