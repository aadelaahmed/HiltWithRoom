package com.example.hiltwithroom.repositories;

import com.example.hiltwithroom.api.PokemonApi;
import com.example.hiltwithroom.db.PokemonDao;
import com.example.hiltwithroom.model.Pokemon;
import com.example.hiltwithroom.model.PokemonResponse;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PokemonRepository {
    @Inject
    PokemonApi api;
    @Inject
    PokemonDao dao;

    @Inject
    public PokemonRepository(PokemonApi api, PokemonDao dao) {
        this.api = api;
        this.dao = dao;
    }

    public Observable<PokemonResponse> getPokemonsData() {
        return api.fetchPoekmons();
    }

    public void insertPokemon(Pokemon pokemon)
    {
        dao.insertPokemon(pokemon);
    }

    public void deletePokemon(String pokemonName)
    {
        dao.deletePokemon(pokemonName);
    }

    public LiveData<List<Pokemon>> getLiveFavs()
    {
        return dao.getLocalPokemons();
    }
}
