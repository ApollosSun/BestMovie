package com.example.apollo.bestmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private String mItemStr;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_detail);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
            mItemStr = intent.getStringExtra(Intent.EXTRA_TEXT);
            ((TextView) findViewById(R.id.text_detail_item)).setText(mItemStr);
        }

        imageView = findViewById(R.id.picture_detail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
