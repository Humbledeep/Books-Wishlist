package com.app.bookwishlist;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private TextView textViewName,textViewAuthor,textViewGenre,textViewPublication;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textViewName=findViewById(R.id.add_edittext);
        textViewAuthor=findViewById(R.id.add_author);
        textViewGenre=findViewById(R.id.add_genre);
        textViewPublication=findViewById(R.id.add_year);

        if(getIntent().hasExtra("name")){

            textViewName.setText(getIntent().getStringExtra("name"));
            textViewAuthor.setText(getIntent().getStringExtra("author"));
            textViewGenre.setText(getIntent().getStringExtra("genre"));
            textViewPublication.setText(getIntent().getStringExtra("publication"));

        }

    }
}
