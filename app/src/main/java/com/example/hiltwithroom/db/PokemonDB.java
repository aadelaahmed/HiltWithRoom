package com.example.hiltwithroom.db;

import com.example.hiltwithroom.model.Pokemon;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Pokemon.class,version = 1,exportSchema = false)
public abstract class PokemonDB extends RoomDatabase {
    public abstract PokemonDao getPokemonDao();
}
