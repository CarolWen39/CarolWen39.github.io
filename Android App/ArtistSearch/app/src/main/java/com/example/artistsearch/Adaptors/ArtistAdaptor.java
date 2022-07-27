package com.example.artistsearch.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.artistsearch.Models.Artist;
import com.example.artistsearch.R;
import com.example.artistsearch.RecyclerViewInterface;

import java.util.ArrayList;

public class ArtistAdaptor extends RecyclerView.Adapter<ArtistAdaptor.ArtistHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<Artist> artistList;

    public ArtistAdaptor(Context context, ArrayList<Artist> artistList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.artistList = artistList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ArtistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // This is where to inflate the layout (giving a look to the rows)
        View view = LayoutInflater.from(context).inflate(R.layout.eachartist, parent, false);
        return new ArtistHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistHolder holder, int position) {
        // assigning values to the views we created in the recyclerview row layout file
        // based on the position of the recycler view
        Artist artist = artistList.get(position);
        holder.setImg(artist.getImage());
        holder.setName(artist.getName());

    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }


    public class ArtistHolder extends RecyclerView.ViewHolder {
        /*
        grabbing the views from our recyclerview row layout file
        like in the onCreate method
        * */
        ImageView artistImg;
        TextView artistName;
        CardView artistCard;
        View view;
        public ArtistHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            artistCard = view.findViewById(R.id.artistCard);
            artistImg = view.findViewById(R.id.artistImage);
            artistImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAbsoluteAdapterPosition();
                    if(recyclerViewInterface != null) {
                        if(pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(recyclerViewInterface != null) {
//                        int pos = getAbsoluteAdapterPosition();
//                        if(pos != RecyclerView.NO_POSITION) {
//                            recyclerViewInterface.onItemClick(pos);
//                        }
//                    }
//                }
//            });
        }
        public void setImg(String url) {
            artistImg = view.findViewById(R.id.artistImage);
            if(!url.equals("/assets/shared/missing_image.png")) {
              Glide.with(context).load(url).into(artistImg);
            }
        }

        public void setName(String name) {
            artistName = view.findViewById(R.id.artistName);
            artistName.setText(name);
        }
    }
}
