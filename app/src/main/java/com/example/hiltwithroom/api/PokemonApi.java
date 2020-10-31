package com.example.hiltwithroom.api;

import com.example.hiltwithroom.model.PokemonResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokemonApi {
    public static final String BASE_URL = "https://pokeapi.co/api/v2/";

    @GET("pokemon")
    Observable<PokemonResponse> fetchPoekmons();
}
