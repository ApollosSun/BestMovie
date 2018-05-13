package com.example.apollo.bestmovie;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FragmentList extends Fragment{

    private ArrayAdapter<String> mListAdapter;
    private final String LOG_TAG = FetchMovieData.class.getSimpleName();

    private FilmAdapter mFilmAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mListAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item,
                R.id.list_text_item,
                new ArrayList<String>()
        );

        mFilmAdapter = new FilmAdapter(getActivity(), new ArrayList<Film>());
        Film newFilm = new Film("5", "Titile", "/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg", "none", "13.05.18");
        mFilmAdapter.add(newFilm);

        ListView listView = rootView.findViewById(R.id.listview_item);
        listView.setAdapter(mFilmAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, mListAdapter.getItem(position));
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_update){
            UpdateData();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        UpdateData();
    }

    private void UpdateData (){
        FetchMovieData movieData = new FetchMovieData();
        movieData.execute();
    }

    public class FetchMovieData extends AsyncTask<Void, Void, String[][]>{

        private String[][] getDataFromJson (String dataJson, int numItems)
        throws JSONException{

            final String RESULTS = "results";
            final String VOTE = "vote_average";
            final String TITLE = "title";
            final String IMAGE = "poster_path";
            final String OVERVIEW = "overview";
            final String RELEASE = "release_date";

            String [][] resultMovieStrs = new String [numItems][5];
            JSONObject dataMoviesJson = new JSONObject(dataJson);
            JSONArray moviesArray = dataMoviesJson.getJSONArray(RESULTS);

            for (int i = 0; i < moviesArray.length()-10; i++){

                JSONObject selectedMovie = moviesArray.getJSONObject(i);

                resultMovieStrs[i][0] = selectedMovie.getString(VOTE);
                resultMovieStrs[i][1] = selectedMovie.getString(TITLE);
                resultMovieStrs[i][2] = selectedMovie.getString(IMAGE);
                resultMovieStrs[i][3] = selectedMovie.getString(OVERVIEW);
                resultMovieStrs[i][4] = selectedMovie.getString(RELEASE);
            }

            for (String[] s : resultMovieStrs){
                Log.v(LOG_TAG, "Vote: " + s[0]);
                Log.v(LOG_TAG, "Title: " + s[1]);
                Log.v(LOG_TAG, "Image: " + s[2]);
                Log.v(LOG_TAG, "Overview: " + s[3]);
                Log.v(LOG_TAG, "Release Date: " + s[4]);
            }

            return resultMovieStrs;
        }

        @Override
        protected String[][] doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String dataMoviesJsonStr;
            int numItems = 10;

            try{
                final String BASE_URL = "https://api.themoviedb.org/3/movie/popular?";
                final String APP_ID_PARAM = "api_key";

                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        //.appendQueryParameter(APP_ID_PARAM, new Keys().getAPI())
                        .appendQueryParameter(APP_ID_PARAM, Keys.MOVIE_KEY_API)
                        .build();

                URL url = new URL(builtUri.toString());
                Log.v(LOG_TAG, "Built Uri " + builtUri.toString() + " Url " + url);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null){
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0){
                    return null;
                }
                dataMoviesJsonStr = buffer.toString();

                //Log.v(LOG_TAG, "Data Movies Json Str: " + dataMoviesJsonStr);

            } catch (IOException e){
                Log.e(LOG_TAG, "Error message: " + e);
                return null;
            } finally {
                if (urlConnection != null){
                    urlConnection.disconnect();
                }
                if(reader != null){
                    try{
                        reader.close();
                    } catch (final IOException e){
                        Log.e(LOG_TAG, "Error closing stream ", e);
                    }
                }
            }

            try{
                return getDataFromJson(dataMoviesJsonStr, numItems);
            } catch (JSONException e){
                Log.e(LOG_TAG, e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String[][] result) {
            if(result != null){
                mListAdapter.clear();
                for(String[] s:result){
                    mListAdapter.add(s[1] + " Rating: " +s[0]);
                }

            }
        }
    }
}
