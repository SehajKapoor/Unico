package com.example.unico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManasgementMain extends AppCompatActivity {
Button b1,b2,b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manasgement_main);
        b1=findViewById(R.id.register);
        b2=findViewById(R.id.payment);
        b3=findViewById(R.id.registerT);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ManasgementMain.this,StudentRegistryYear.class);
                startActivity(i);

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ManasgementMain.this,ManagementYear.class);
                startActivity(i);

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ManasgementMain.this,TeacherRegistry.class);
                startActivity(i);

            }
        });
    }
}
