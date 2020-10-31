package com.example.hiltwithroom.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.hiltwithroom.databinding.ActivityMainBinding;
import com.example.hiltwithroom.model.Pokemon;

import java.util.List;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    RecyclerView pokemonRecyclerView;
    PokemonAdapter adapter;
    ActivityMainBinding binding;
    MainViewModel viewModel;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        pokemonRecyclerView = binding.pokemonRv;
        progressBar = binding.progressBar;
        adapter = new PokemonAdapter(this);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        iniRecyclerView();
        setSwipeView();
        getSupportActionBar().setTitle("Home");
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
                viewModel.insertPokemon(pokemon);
                Toast.makeText(MainActivity.this, "pokemon inserted to database", Toast.LENGTH_SHORT).show();
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
        viewModel.fetchLivePokemons().observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                adapter.setNewList(pokemons);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.save_item:
                startActivity(new Intent(this, FavouriteActivity.class));
                break;
        }
        return true;
    }
}