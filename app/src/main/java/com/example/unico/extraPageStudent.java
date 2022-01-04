package com.example.unico;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class extraPageStudent extends AppCompatActivity {
TextView t;
ArrayList<String> rollNo, name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_extra_page_student);

        rollNo=new ArrayList<>();
        name=new ArrayList<>();

        t=findViewById(R.id.textView3);
        DatabaseClass dc=new DatabaseClass(this);
        Cursor c1=dc.showRegisteredDataStudent();
        Toast.makeText(this, "registered111", Toast.LENGTH_SHORT).show();

        if(c1.moveToFirst())
        {
            Toast.makeText(this, "registered", Toast.LENGTH_SHORT).show();
            do {


                String a=c1.getString(c1.getColumnIndex("name"));
                String b=c1.getString(c1.getColumnIndex("email"));
                String c=c1.getString(c1.getColumnIndex("phone"));
                String d=c1.getString(c1.getColumnIndex("gender"));
                String e=c1.getString(c1.getColumnIndex("roll_no"));
                String f=c1.getString(c1.getColumnIndex("class_name"));
                String g=c1.getString(c1.getColumnIndex("username"));
                String h=c1.getString(c1.getColumnIndex("password"));
                String i=c1.getString(c1.getColumnIndex("security"));
                String j=c1.getString(c1.getColumnIndex("answer"));

                rollNo.add(e);
                name.add(a);
                t.setText(t.getText().toString()+"\n"+a+""+b+""+c+""+d+""+e+""+f+""+g+""+h+""+i+""+j+"");
            }
            while (c1.moveToNext());
        }

    }
}
