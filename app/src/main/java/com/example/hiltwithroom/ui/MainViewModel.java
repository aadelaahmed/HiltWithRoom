package com.example.hiltwithroom.ui;

import android.util.Log;

import com.example.hiltwithroom.model.Pokemon;
import com.example.hiltwithroom.model.PokemonResponse;
import com.example.hiltwithroom.repositories.PokemonRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";
    @Inject
    PokemonRepository repository;
    @Inject
    CompositeDisposable compositeDisposable;
    MutableLiveData<List<Pokemon>> livePokemons;
    LiveData<List<Pokemon>> liveFavsPokemons;
    @ViewModelInject
    public MainViewModel(PokemonRepository repository, CompositeDisposable compositeDisposable) {
        this.livePokemons = new MutableLiveData<>();
        this.repository = repository;
        this.compositeDisposable = compositeDisposable;
    }

    public LiveData<List<Pokemon>> fetchLivePokemons() {
//        repository.getPokemonsData()
//                .subscribeOn(Schedulers.io())
//                .map(response -> {
//                    List<Pokemon> list = response.getPoekmons();
//                    for (Pokemon temp :
//                            list) {
//                        String[] tempArr = temp.getUrl().split("/");
//                        temp.setUrl("https://pokeres.bastionbot.org/images/pokemon/" + tempArr[tempArr.length - 1] + ".png");
//                    }
//                    return list;
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        pokemons -> livePokemons.setValue(pokemons),
//                        errorArg -> Log.d(TAG, "getLivePokemons: "+errorArg.getLocalizedMessage())
//                );
        compositeDisposable.add(
                repository.getPokemonsData()
                        .subscribeOn(Schedulers.io())
                        .map(new Function<PokemonResponse, List<Pokemon>>() {
                            @Override
                            public List<Pokemon> apply(PokemonResponse response) throws Throwable {
                                List<Pokemon> list = response.getPoekmons();
                                for (Pokemon temp :
                                        list) {
                                    String[] tempArr = temp.getUrl().split("/");
                                    temp.setUrl("https://pokeres.bastionbot.org/images/pokemon/" + tempArr[tempArr.length - 1] + ".png");
                                }
                                return list;
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<List<Pokemon>>() {
                            @Override
                            public void onNext(@NonNull List<Pokemon> pokemonList) {
                                livePokemons.postValue(pokemonList);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.d(TAG, "onError: " + e.getLocalizedMessage());
                            }

                            @Override
                            public void onComplete() {

                            }
                        })
        );
        //Log.d(TAG, "check on live pokemons -->"+livePokemons.getValue().toString());
        return livePokemons;
    }

    public LiveData<List<Pokemon>> getLivePokemons()
    {
        return livePokemons;
    }

    public void insertPokemon(Pokemon pokemon)
    {
        repository.insertPokemon(pokemon);
    }

    public void deletePokemon(String pokemonName)
    {
        repository.deletePokemon(pokemonName);
    }

    public LiveData<List<Pokemon>> getLiveFavPokemons()
    {
        liveFavsPokemons = repository.getLiveFavs();
        return liveFavsPokemons;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
