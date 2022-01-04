package com.example.unico;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final String TAG=this.getClass().getName();
    TextView code;
    Button b8,b3,submit,b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Choose Account");
        b3=findViewById(R.id.button3);
        b8=findViewById(R.id.button8);
        b1=findViewById(R.id.button1);
        b3.setOnClickListener(this);
        b8.setOnClickListener(this);
        b1.setOnClickListener(this);
    }
boolean twice=false;
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Please press back again to exit", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"click");
        if (twice==true)
        {
            Intent i=new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
            System.exit(0);
        }
        twice=true;
        Log.d(TAG,"twice: "+twice);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice=false;
                Log.d(TAG,"twice: "+twice);
            }
        },3000);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button3)
        {
                     Intent i=new Intent(MainActivity.this,TeacherSignIn.class);
                    // finish();
                     startActivity(i);
            }
        if(v.getId()==R.id.button8)
        {
                        Intent i=new Intent(MainActivity.this,StudentSignin2.class);
                       // finish();
                        startActivity(i);
        }
        if (v.getId()==R.id.button1)
        {
                        Intent i=new Intent(MainActivity.this,ManasgementMain.class);
                        startActivity(i);

        }
    }
}
