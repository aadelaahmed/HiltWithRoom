package com.example.hiltwithroom.di;

import android.app.Application;

import com.example.hiltwithroom.db.PokemonDB;
import com.example.hiltwithroom.db.PokemonDao;
import com.example.hiltwithroom.utils.Constants;

import javax.inject.Singleton;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class DBModule {

    @Provides
    @Singleton
    public static PokemonDB getRoomDB(Application application)
    {
        return new Room().databaseBuilder(application, PokemonDB.class, Constants.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    public static PokemonDao getPokemonDao(PokemonDB pokemonDB)
    {
        return pokemonDB.getPokemonDao();
    }


}
