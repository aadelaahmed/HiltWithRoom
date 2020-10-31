package com.example.hiltwithroom.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.hiltwithroom.R;
import com.example.hiltwithroom.databinding.PokemonItemBinding;
import com.example.hiltwithroom.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {
    Context mContext;
    List<Pokemon> list = new ArrayList<>();
    public PokemonAdapter(Context mContext)
    {
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PokemonItemBinding binding = PokemonItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PokemonViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        Pokemon pokemonModel = list.get(position);
        holder.bind(pokemonModel);
    }

    public Pokemon getPokemonModel(int position)
    {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setNewList(List<Pokemon> newList)
    {
        this.list = newList;
        notifyDataSetChanged();
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder{
        PokemonItemBinding binding;
        public PokemonViewHolder(PokemonItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Pokemon pokemon)
        {
            binding.pokemonTv.setText(pokemon.getName());
            Glide.with(mContext)
                    .load(pokemon.getUrl())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(binding.pokemonImage);
        }
    }
}
