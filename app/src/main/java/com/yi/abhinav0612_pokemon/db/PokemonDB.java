package com.yi.abhinav0612_pokemon.db;

import android.app.Application;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.yi.abhinav0612_pokemon.model.Pokemon;

@Database(entities = {Pokemon.class}, version = 2, exportSchema = false)
public abstract class PokemonDB extends RoomDatabase {
    public abstract PokeDao pokeDao();
    //  TODO providePokeDao
    private static volatile PokemonDB instance;
    public static PokemonDB getInstance(Context mContext) {
        if (instance == null) {
            synchronized (PokemonDB.class) {
                if (instance == null) {
                    instance = providePokemonDB(mContext);
                }
            }
        }
        return instance;
    }

    private static PokemonDB providePokemonDB(Context mContext){
        return Room.databaseBuilder(mContext,PokemonDB.class,"Favorite Database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }
}
