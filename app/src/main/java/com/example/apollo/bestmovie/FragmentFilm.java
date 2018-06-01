package com.example.apollo.bestmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FragmentFilm extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("Film")) {

            Film newFilm = getActivity().getIntent().getParcelableExtra("Film");

            ((TextView) rootView.findViewById(R.id.tvVote)).setText(newFilm.getVote());
            ((TextView) rootView.findViewById(R.id.tvTitleDetail)).setText(newFilm.getTitle());
            ((TextView) rootView.findViewById(R.id.tvRelease)).setText(newFilm.getReleaseDate());
            ((TextView) rootView.findViewById(R.id.tvOverview)).setText("Overview: " + newFilm.getOverview());

            String urlImage = "https://image.tmdb.org/t/p/w500" + newFilm.getImage();

            Picasso.get()
                    .load(urlImage)
                    .into((ImageView) rootView.findViewById(R.id.ivImage));

        }
        return rootView;
    }
}
