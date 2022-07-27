package com.example.artistsearch.Adaptors;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.artistsearch.Models.Artwork;
import com.example.artistsearch.R;
import com.example.artistsearch.RecyclerViewInterface;

import java.util.ArrayList;

public class ArtworkAdaptor extends RecyclerView.Adapter<ArtworkAdaptor.ArtworkHolder>{
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<Artwork> artworkArrayList;
    Dialog cateDialog;

    public ArtworkAdaptor(Context context, ArrayList<Artwork> artworkArrayList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.artworkArrayList = artworkArrayList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ArtworkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.eachartwork, parent, false);
        return new ArtworkAdaptor.ArtworkHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtworkHolder holder, int position) {
        Artwork artwork = artworkArrayList.get(position);
        holder.setImg(artwork.getImgUrl());
        holder.setName(artwork.getArtworkName());
    }

    @Override
    public int getItemCount() {
        return artworkArrayList.size();
    }

    public class ArtworkHolder extends RecyclerView.ViewHolder {
        /*
        grabbing the views from our recyclerview row layout file
        like in the onCreate method
        * */
        ImageView artworkImg;
        TextView artworkName;
        View view;
        public ArtworkHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            view.setOnClickListener(new View.OnClickListener() {
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
        public void setImg(String url) {
            artworkImg = view.findViewById(R.id.artworkImage);
            if(!url.equals("/assets/shared/missing_image.png")) {
                    Glide.with(context).load(url).into(artworkImg);
            }

        }

        public void setName(String name) {
            artworkName = view.findViewById(R.id.artworkName);
            artworkName.setText(name);
        }
    }
}
