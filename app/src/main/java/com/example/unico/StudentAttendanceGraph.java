package com.example.unico;

import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StudentAttendanceGraph extends Fragment {
String Name=StudentSignIn.Sname;
String Roll=StudentSignIn.rollNo;
String Year=StudentSignIn.Syear;
Spinner period;
ArrayList subjects;
    DatabaseReference dr;
    String s1,s2,s3,s4,s5;
    String ss,y;
    long total,present;
    String subjectY1[]= {"Mechanics","Math 1","Chemistry","C Programming","Engineering Drawing 1"};
    String subjectY2[]= {"Maths 2","Relational Database","Electronics","C++ Programming","Electronics Lab"};;
    String subjectY3[]= {"Visual Basic Programming","Data Structures","Microprocessor","Computer Networks","Computer Networks Lab"};;
    String subjectY4[]= {"Mobile Computing","Computer Architecture","Operating System","Java Programming","Project Lab"};;

    PieChart chart;

    private StudentAttendanceGraphViewModel mViewModel;

    public static StudentAttendanceGraph newInstance() {
        return new StudentAttendanceGraph();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.student_attendance_graph_fragment, container, false);
        dr = FirebaseDatabase.getInstance().getReference();

         Name=StudentSignIn.Sname;
         Roll=StudentSignIn.rollNo;
         Year=StudentSignIn.Syear;
        period=v.findViewById(R.id.selectPeriod);
        chart= v.findViewById(R.id.chartPie);
        getPeriods();
        if (Year.equals("Y1"))
        {
            y="Year1";
            ArrayAdapter sub = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, subjectY1);
            period.setAdapter(sub);

        }
        else if (Year.equals("Y2"))
        {
            y="Year2";
            ArrayAdapter sub = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, subjectY2);
            period.setAdapter(sub);

        }
        else if (Year.equals("Y3"))
        {
            y="Year3";
            ArrayAdapter sub = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, subjectY3);
            period.setAdapter(sub);

        }
        else if (Year.equals("Y4"))
        {
            y="Year4";
            ArrayAdapter sub = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, subjectY4);
            period.setAdapter(sub);

        }





        period.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ss=period.getItemAtPosition(position).toString();

            //    Toast.makeText(getActivity(), "total attend"+ss, Toast.LENGTH_SHORT).show();
                String user=Name+""+Roll;

                setUpPieChart();
                Query q;

                     q = dr.child(y+user+"Attendance"+ss);
                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                Map m = (Map) ds.getValue();

                                total = (long) m.get("total");
                                present = (long) m.get("present");



                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }








            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        return v;
    }


    private void setUpPieChart() {
        //Populating the list of pie chart

       // int x= total;
      //  int y= Integer.parseInt(present);
        int rainfall[]={(int)total,(int)present};
        String pref[]={"TOTAL","PRESENT"};

        List<PieEntry> pieEnteries=new ArrayList<>();
        final int[] MY_COLORS = {Color.rgb(47,121,114),
                Color.rgb(255,128,64)};

        for(int i=0;i<rainfall.length;i++)
        {
            pieEnteries.add(new PieEntry(rainfall[i],pref[i]));
        }

        PieDataSet dataset=new PieDataSet(pieEnteries,"Attendance Analysis");
        dataset.setColors(MY_COLORS );
        PieData data=new PieData(dataset);

        //Get the chart

        chart.setData(data);
        chart.invalidate();
    }

    public void getPeriods() {
        subjects=new ArrayList();


        Query q = dr.child(Year + "TimeTable");

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Map m = (Map) ds.getValue();

                    s1 = (String) m.get("p1");
                    s2 = (String) m.get("p2");
                    s3 = (String) m.get("p3");
                    s4 = (String) m.get("p4");
                    s5 = (String) m.get("p5");
                    subjects.add(s1);
                    subjects.add(s2);
                    subjects.add(s3);
                    subjects.add(s4);
                    subjects.add(s5);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(StudentAttendanceGraphViewModel.class);
        // TODO: Use the ViewModel
    }

}
