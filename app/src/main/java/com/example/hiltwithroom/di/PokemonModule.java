package com.example.hiltwithroom.di;


import com.example.hiltwithroom.api.PokemonApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(ApplicationComponent.class)
public class PokemonModule {
    @Provides
    @Singleton
    public static Retrofit provideRetrofit()
    {
        return new Retrofit.Builder()
                .baseUrl(PokemonApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public static PokemonApi provideApi(Retrofit retrofit)
    {
        return retrofit.create(PokemonApi.class);
    }

    @Provides
    public static CompositeDisposable provideCompositeDisposable()
    {
        return new CompositeDisposable();
    }
}
