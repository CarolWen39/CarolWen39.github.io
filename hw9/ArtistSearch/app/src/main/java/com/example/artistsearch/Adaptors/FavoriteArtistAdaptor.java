package com.example.artistsearch.Adaptors;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artistsearch.Models.FavoriteArtist;
import com.example.artistsearch.R;
import com.example.artistsearch.RecyclerViewInterface;

import java.util.ArrayList;

public class FavoriteArtistAdaptor extends RecyclerView.Adapter<FavoriteArtistAdaptor.FavoriteArtistHolder>{
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<FavoriteArtist> favoriteArtistArrayList;

    public FavoriteArtistAdaptor(Context context, ArrayList<FavoriteArtist> favoriteArtistArrayList, RecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.favoriteArtistArrayList = favoriteArtistArrayList;
    }

    @NonNull
    @Override
    public FavoriteArtistAdaptor.FavoriteArtistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.eachfavorite, parent, false);
        return new FavoriteArtistAdaptor.FavoriteArtistHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteArtistAdaptor.FavoriteArtistHolder holder, int position) {
        FavoriteArtist favoriteArtist = favoriteArtistArrayList.get(position);
        holder.setName(favoriteArtist.getName());
        holder.setNation(favoriteArtist.getNation());
        holder.setBirth(favoriteArtist.getBirthday());
    }

    @Override
    public int getItemCount() {
        return favoriteArtistArrayList.size();
    }

    public class FavoriteArtistHolder extends RecyclerView.ViewHolder {
        TextView artistName, artistNation, artistBirth;
        ImageView imageButton;
        View view;
        public FavoriteArtistHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            imageButton = view.findViewById(R.id.favoriteDetailBtn);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null) {
                        int pos = getAbsoluteAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }

        public void setName(String name) {
            artistName = view.findViewById(R.id.favoriteName);
            artistName.setText(name);
        }
        public void setNation(String nation) {
            artistNation = view.findViewById(R.id.favoriteNation);
            artistNation.setText(nation);
        }
        public void setBirth(String birth) {
            artistBirth = view.findViewById(R.id.favoriteBirth);
            artistBirth.setText(birth);
        }
    }
}
