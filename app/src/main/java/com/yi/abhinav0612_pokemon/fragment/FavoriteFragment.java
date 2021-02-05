package com.yi.abhinav0612_pokemon.fragment;

import android.os.Bundle;
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
import com.yi.abhinav0612_pokemon.databinding.FragmentFavoriteBinding;
import com.yi.abhinav0612_pokemon.db.PokeDao;
import com.yi.abhinav0612_pokemon.db.PokemonDB;
import com.yi.abhinav0612_pokemon.model.Pokemon;
import com.yi.abhinav0612_pokemon.network.PokeApiService;
import com.yi.abhinav0612_pokemon.network.PokemonApiService;
import com.yi.abhinav0612_pokemon.repository.Repository;
import com.yi.abhinav0612_pokemon.viewmodel.PokemonViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends BaseFragment<FragmentFavoriteBinding> {

    private PokemonViewModel viewModel;
    private PokemonAdapter adapter;
    private ArrayList<Pokemon> pokemons;
    private Repository repository;
    private PokeDao pokeDao;
    private PokeApiService pokeApiService;
    private ViewModelProviderFactory viewModelProviderFactory;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_favorite;
    }

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
        setUpItemTouchHelper();
        observeData();
    }

    private void observeData() {
        viewModel.getFavoritePokemonList().observe(getViewLifecycleOwner(), new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {

                if(pokemons == null || pokemons.size() == 0) {
                    binding.noFavoritesText.setVisibility(View.VISIBLE);
                } else{
                    ArrayList<Pokemon> list = new ArrayList<>();
                    list.addAll(pokemons);
                    adapter.updateList(list);
                }
            }
        });
    }

    private void setUpItemTouchHelper() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int swipedPokemonPosition = viewHolder.getAdapterPosition();
                Pokemon pokemon = adapter.getPokemonAt(swipedPokemonPosition);
                viewModel.deletePokemon(pokemon.getName());
                Toast.makeText(getContext(),"Pokemon removed from favorites.",Toast.LENGTH_SHORT).show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.favoritesRecyclerView);
    }


    private void initRecyclerView() {
        binding.favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PokemonAdapter(getContext(),pokemons);
        binding.favoritesRecyclerView.setAdapter(adapter);
    }
}
