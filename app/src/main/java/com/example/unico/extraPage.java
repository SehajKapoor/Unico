package com.example.unico;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class extraPage extends AppCompatActivity {
TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_page);
        t=findViewById(R.id.textView2);
        DatabaseClass dc=new DatabaseClass(this);
        Cursor c1=dc.showRegisteredData();

        if(c1.moveToFirst())
        {
            Toast.makeText(this, "registered", Toast.LENGTH_SHORT).show();
            do {
                String a=c1.getString(c1.getColumnIndex("name"));
                String b=c1.getString(c1.getColumnIndex("email"));
                String c=c1.getString(c1.getColumnIndex("phone"));
                String d=c1.getString(c1.getColumnIndex("gender"));
                String e=c1.getString(c1.getColumnIndex("incharge"));
                String f=c1.getString(c1.getColumnIndex("designation"));
                String g=c1.getString(c1.getColumnIndex("username"));
                String h=c1.getString(c1.getColumnIndex("password"));
                String i=c1.getString(c1.getColumnIndex("security"));
                String j=c1.getString(c1.getColumnIndex("answer"));
                t.setText(t.getText().toString()+"\n"+a+""+b+""+c+""+d+""+e+""+f+""+g+""+h+""+i+""+j+"");
            }while (c1.moveToNext());
        }

    }
}
