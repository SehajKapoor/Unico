package com.example.unico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudentRegistryYear extends AppCompatActivity {
    Button b7,b17,b18,b19;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registry_year);
        b7=findViewById(R.id.Year1);
        b17=findViewById(R.id.Year2);
        b18=findViewById(R.id.Year3);
        b19=findViewById(R.id.Year4);
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StudentRegistryYear.this,StudentRegistry.class);
                String year="Year1";
                Bundle bundle =new Bundle();
                bundle.putString("year",year);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        b17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StudentRegistryYear.this,StudentRegistry.class);
                String year="Year2";
                Bundle bundle =new Bundle();
                bundle.putString("year",year);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        b18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StudentRegistryYear.this,StudentRegistry.class);
                String year="Year3";
                Bundle bundle =new Bundle();
                bundle.putString("year",year);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        b19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StudentRegistryYear.this,StudentRegistry.class);
                String year="Year4";
                Bundle bundle =new Bundle();
                bundle.putString("year",year);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

    }
}
