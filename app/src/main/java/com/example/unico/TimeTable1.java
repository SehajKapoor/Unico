package com.example.unico;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class TimeTable1 extends Fragment {
TextView t6,t8,t9,t10,t11,t12,t13,t14;
Button b10;
Spinner chooseyear,period1,period2,period3,period4,period5;
DatabaseReference db;
ArrayAdapter<String> chooseY,subjects,labs;
    String yearSelected;


String Year[]={"Choose Year","Year1","Year2","Year3","Year4"};
String Y1[]={"Math 1","Chemistry","Mechanics","C Programming"};
String Y1Labs[]={"Engineering Drawing 1","Chemistry Lab","Mechanics Lab","C Programming Lab"};
String Y2[]={"Maths 2","C++ Programming","Electronics","Relational Database"};
String Y2Labs[]={"Engineering Drawing 2","C++ Programming Lab","Electronics Lab","Relational Database Lab"};
String Y3[]={"Data Structures","Visual Basic Programming","Computer Networks","Microprocessor"};
String Y3Labs[]={"Data Structures Lab","Visual Basic Lab","Computer Networks Lab","Microprocessor Lab"};
String Y4[]={"Java Programming","Operating System","Computer Architecture","Mobile Computing"};
String Y4Labs[]={"Java Programming Lab","Operating System Lab","Mobile Computing Lab","Project Lab"};
    private TimeTable1ViewModel mViewModel;

    public static TimeTable1 newInstance() {
        return new TimeTable1();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.time_table1_fragment, container, false);
        chooseY=new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line,Year);


        db= FirebaseDatabase.getInstance().getReference();
        t6=v.findViewById(R.id.textView6);
        t8=v.findViewById(R.id.textView8);
        t9=v.findViewById(R.id.textView9);
        t10=v.findViewById(R.id.textView10);
        t11=v.findViewById(R.id.textView11);
        t12=v.findViewById(R.id.textView12);
        t13=v.findViewById(R.id.textView13);
        t14=v.findViewById(R.id.textView14);
        b10=v.findViewById(R.id.button10);
        chooseyear=v.findViewById(R.id.choose_year);
        chooseyear.setAdapter(chooseY);
        period1=v.findViewById(R.id.period_1);
        period2=v.findViewById(R.id.period_2);
        period3=v.findViewById(R.id.period_3);
        period4=v.findViewById(R.id.period_4);
        period5=v.findViewById(R.id.period_5);

        chooseyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                 yearSelected=chooseY.getItem(position);

                if(yearSelected.equals("Year1"))
                {
                    subjects=new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line,Y1);
                    labs=new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line,Y1Labs);
                }
                else if(yearSelected.equals("Year2"))
                {
                    subjects=new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line,Y2);
                    labs=new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line,Y2Labs);
                }
                else  if(yearSelected.equals("Year3"))
                {
                    subjects=new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line,Y3);
                    labs=new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line,Y3Labs);
                }
                else if(yearSelected.equals("Year4"))
                {
                    subjects=new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line,Y4);
                    labs=new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line,Y4Labs);
                }

                period1.setAdapter(subjects);
                period2.setAdapter(subjects);
                period3.setAdapter(subjects);
                period4.setAdapter(subjects);
                period5.setAdapter(labs);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTable();
            }
        });




        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TimeTable1ViewModel.class);
        // TODO: Use the ViewModel
    }


 void updateTable()
{
    final String year = chooseyear.getSelectedItem().toString();
    final String p1 = period1.getSelectedItem().toString();
    final String p2 = period2.getSelectedItem().toString();
    final String p3 = period3.getSelectedItem().toString();
    final String p4 = period4.getSelectedItem().toString();
    final String p5 = period5.getSelectedItem().toString();
    Query q = db.child(year + "TimeTable");


    q.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                ds.getRef().child("p1").setValue(p1);
                ds.getRef().child("p2").setValue(p2);
                ds.getRef().child("p3").setValue(p3);
                ds.getRef().child("p4").setValue(p4);
                ds.getRef().child("p5").setValue(p5);

            }
          Toast.makeText(getActivity(), "Time Table of " + year + " updated successfully !!", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

}

}
class getTimeTable {
    public String year,p1,p2,p3,p4,p5;
    public getTimeTable(String year, String p1, String p2, String p3, String p4, String p5) {
        {
            this.year=year;
            this.p1=p1;
            this.p2=p2;
            this.p3=p3;
            this.p4=p4;
            this.p5=p5;
        }
    }
}
