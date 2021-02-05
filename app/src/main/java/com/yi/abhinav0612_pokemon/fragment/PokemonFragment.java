package com.yi.abhinav0612_pokemon.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yi.abhinav0612_pokemon.R;
import com.yi.abhinav0612_pokemon.ViewModelProviderFactory;
import com.yi.abhinav0612_pokemon.adapters.PokemonAdapter;
import com.yi.abhinav0612_pokemon.databinding.FragmentPokemonBinding;
import com.yi.abhinav0612_pokemon.db.PokeDao;
import com.yi.abhinav0612_pokemon.db.PokemonDB;
import com.yi.abhinav0612_pokemon.model.Pokemon;
import com.yi.abhinav0612_pokemon.network.PokeApiService;
import com.yi.abhinav0612_pokemon.network.PokemonApiService;
import com.yi.abhinav0612_pokemon.repository.Repository;
import com.yi.abhinav0612_pokemon.viewmodel.PokemonViewModel;

import java.util.ArrayList;

public class PokemonFragment extends BaseFragment<FragmentPokemonBinding> {
    private static final String TAG = "Pokemon";
    private PokemonViewModel viewModel;
    private PokemonAdapter adapter;
    private ArrayList<Pokemon> pokemons;
    private Repository repository;
    private PokeDao pokeDao;
    private PokeApiService pokeApiService;
    private ViewModelProviderFactory viewModelProviderFactory;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        pokeDao = PokemonDB.getInstance(getContext()).pokeDao();
        pokeApiService = PokemonApiService.providePokemonApiService();
        repository = new Repository(pokeDao, pokeApiService);
        viewModel = new PokemonViewModel(repository);
        viewModelProviderFactory = ViewModelProviderFactory.getInstance(pokeDao, pokeApiService);
        viewModel = new ViewModelProvider(this,viewModelProviderFactory).get(PokemonViewModel.class);
        initRecyclerView();
        observeData();
        setUpItemTouchHelper();
        viewModel.getPokemons();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pokemon;
    }

    private void setUpItemTouchHelper() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int swipedPokemonPosition = viewHolder.getAdapterPosition();
                Pokemon pokemon = adapter.getPokemonAt(swipedPokemonPosition);
                viewModel.insertPokemon(pokemon);
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(),"Pokemon added to favorites.",Toast.LENGTH_SHORT).show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.pokemonRecyclerView);
    }


    private void observeData() {
        viewModel.getPokemonList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Pokemon>>() {
            @Override
            public void onChanged(ArrayList<Pokemon> pokemons) {
                Log.e(TAG, "onChanged: " + pokemons.size() );
                adapter.updateList(pokemons);
            }
        });
    }

    private void initRecyclerView() {
        binding.pokemonRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PokemonAdapter(getContext(),pokemons);
        binding.pokemonRecyclerView.setAdapter(adapter);
    }
}
