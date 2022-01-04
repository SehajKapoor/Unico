package com.example.unico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class FullImage extends AppCompatActivity {
ImageView imageview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        imageview=findViewById(R.id.imageViewFull);
        getSupportActionBar().hide();
        getSupportActionBar().setTitle("Events");
        Intent i=getIntent();
        int position=i.getExtras().getInt("id");
        EventsAdapter ed=new EventsAdapter(this);
        imageview.setImageResource(ed.imageArray[position]);
    }
}
