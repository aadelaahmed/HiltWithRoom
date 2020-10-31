package com.example.hiltwithroom.db;

import com.example.hiltwithroom.model.Pokemon;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface PokemonDao {

    @Insert
    public void insertPokemon(Pokemon pokemon);
    @Query("delete from fav_table where name =:pokemonName")
    public void deletePokemon(String pokemonName);
    @Query("select * from fav_table")
    public LiveData<List<Pokemon>> getLocalPokemons();
}
