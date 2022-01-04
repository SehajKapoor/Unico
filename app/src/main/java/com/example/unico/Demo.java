package com.example.unico;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class Demo extends AppCompatActivity {

    String y;
    ArrayList<String> roll;
    ArrayList<String> marks;
    DatabaseReference dr;
    static int total=0;
    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
      //  View v=inflater.inflate(R.layout.performance_graph_fragment, container, false);

      ///  String Year=StudentSignIn.Syear;

        dr= FirebaseDatabase.getInstance().getReference();
        roll=new ArrayList<>();
        marks=new ArrayList<>();
        t=findViewById(R.id.textSetValues);

        //Toast.makeText(this, "year ="+Year, Toast.LENGTH_SHORT).show();

        Query q=dr.child("Year4Students");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Map m= (Map) ds.getValue();

                     String rollNoS=(String) m.get("roll_no");

                    String mar= (String) m.get("marks");

               t.setText(t.getText()+"  "+rollNoS+"  "+mar);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


}
