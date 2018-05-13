package com.example.apollo.bestmovie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FilmAdapter extends ArrayAdapter<Film>{

    public FilmAdapter(Context context, ArrayList<Film> films){
        super(context, 0, films);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_film, parent, false);
        }

        Film film = getItem(position);

        ImageView ivImage = convertView.findViewById(R.id.ivImage);
        TextView tvVote = convertView.findViewById(R.id.tvVote);
        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvReleaseDate = convertView.findViewById(R.id.tvRelease);

        String urlImage = "https://image.tmdb.org/t/p/w500" + film.getImage();

        Picasso.get()
                .load(urlImage)
                .into(ivImage);

        tvVote.setText(film.getVote());
        tvTitle.setText(film.getTitle());
        tvReleaseDate.setText(film.getReleaseDate());

        return convertView;
    }
}
