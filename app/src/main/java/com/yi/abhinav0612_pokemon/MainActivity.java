package com.yi.abhinav0612_pokemon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.yi.abhinav0612_pokemon.databinding.ActivityMainBinding;
import com.yi.abhinav0612_pokemon.fragment.FavoriteFragment;
import com.yi.abhinav0612_pokemon.fragment.PokemonFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private boolean isFavoriteListVisible = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new PokemonFragment()).commit();
        binding.FavoriteFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFavoriteListVisible){
                    isFavoriteListVisible = false;
                    getSupportActionBar().setTitle("Favorites");
                    binding.FavoriteFragment.setText("Favorites");
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new PokemonFragment()).commit();

                }
                else {
                    isFavoriteListVisible = true;
                    getSupportActionBar().setTitle("Pokemon");
                    binding.FavoriteFragment.setText("Pokemon");
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new FavoriteFragment()).commit();
                }
            }
        });

    }
}