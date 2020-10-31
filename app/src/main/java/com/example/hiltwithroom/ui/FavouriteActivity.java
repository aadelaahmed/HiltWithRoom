package com.example.hiltwithroom.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.hilt.android.AndroidEntryPoint;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hiltwithroom.R;
import com.example.hiltwithroom.adapters.PokemonAdapter;
import com.example.hiltwithroom.databinding.ActivityFavouriteBinding;
import com.example.hiltwithroom.databinding.ActivityMainBinding;
import com.example.hiltwithroom.model.Pokemon;

import java.util.List;

@AndroidEntryPoint
public class FavouriteActivity extends AppCompatActivity {
    RecyclerView pokemonRecyclerView;
    PokemonAdapter adapter;
    ActivityFavouriteBinding binding;
    MainViewModel viewModel;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavouriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        pokemonRecyclerView = binding.pokemonRv;
        progressBar = binding.progressBar;
        adapter = new PokemonAdapter(this);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        iniRecyclerView();
        setSwipeView();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Saved");
        actionBar.setDisplayHomeAsUpEnabled(true);
//        binding.navigationBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(FavouriteActivity.this, MainActivity.class));
//            }
//        });

    }

    private void setSwipeView() {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int swipedPosition = viewHolder.getAdapterPosition();
                Pokemon pokemon = adapter.getPokemonModel(swipedPosition);
                viewModel.deletePokemon(pokemon.getName());
                Toast.makeText(FavouriteActivity.this, "pokemon deleted from database", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(pokemonRecyclerView);
    }

    private void iniRecyclerView() {
        pokemonRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        pokemonRecyclerView.setHasFixedSize(true);
        pokemonRecyclerView.setAdapter(adapter);
        viewModel.getLiveFavPokemons().observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                adapter.setNewList(pokemons);
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
        return true;
    }
}