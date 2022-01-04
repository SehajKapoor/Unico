package com.example.unico;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class PerformanceGraph extends Fragment {
HorizontalBarChart barChart;

String y;
ArrayList<String> roll;
static  ArrayList<String> marks;
DatabaseReference dr;
static int total=0;
static int a[];
ArrayList<Long> arr=new ArrayList();
TextView t;
int i=0,j=0;
    long mar;

    private PerformanceGraphViewModel mViewModel;

    public static PerformanceGraph newInstance() {
        return new PerformanceGraph();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.performance_graph_fragment, container, false);

        String Year=StudentSignIn.Syear;

    //    t=v.findViewById(R.id.texT);
         barChart=(HorizontalBarChart) v.findViewById(R.id.barGraph);
         dr= FirebaseDatabase.getInstance().getReference();
         roll=new ArrayList<>();




         if (Year.equals("Y1"))
         {
             y="Year1";
         }
         else if (Year.equals("Y2"))
         {
             y="Year2";
         }
         else if (Year.equals("Y3"))
         {
             y="Year3";
         }
         else if (Year.equals("Y4"))
         {
             y="Year4";
         }


        Query q=dr.child(y+"Students");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Map m= (Map) ds.getValue();

                    String rollNoS=(String) m.get("roll_no");

                    mar= (long) m.get("marks");


                    arr.add(mar);
                    roll.add(rollNoS);
                    total++;
               //     i++;
//
//                    ArrayList<BarEntry>yVals=new ArrayList<>();
//                    int BarWidth=5;
//                    int SpaceForBar=10;
//                       int x=((arr.get(i)).intValue())*10;
//
//                        //    int x=Integer.parseInt(arr.get(i));
//                    //    Toast.makeText(getActivity(), ""+arr.get(i), Toast.LENGTH_SHORT).show();
//                        yVals.add(new BarEntry(SpaceForBar*i, x));
//                    i++;
//
//                    BarDataSet set1;
//                    set1=new BarDataSet(yVals,"Data Set1");
//
//                    BarData data=new BarData(set1);
//                    data.setBarWidth(BarWidth);
//                    barChart.setData(data);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


          setData(12,10);

        return v;
    }


    private void setData(int count,int range)
    {


                    ArrayList<BarEntry>yVals=new ArrayList<>();
                    float BarWidth=9f;
                    float SpaceForBar=10f;
                    for(i=0;i<count;i++)
                    {
                        //int x=((arr.get(i)).intValue())*range;
                        float x=(float)(Math.random()*range);
                        yVals.add(new BarEntry(SpaceForBar*i, x));

                    }
//                    i++;

                    BarDataSet set1;
                    set1=new BarDataSet(yVals,"Data Set1");

                    BarData data=new BarData(set1);
                    data.setBarWidth(BarWidth);
                    barChart.setData(data);
    }

//    public void retrieve_data()
//    {
//        Query q=dr.child(y+"Students");
//        q.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds: dataSnapshot.getChildren())
//                {
//                    Map m= (Map) ds.getValue();
//
//                    String rollNoS=(String) m.get("roll_no");
//
//                    Long mar= (Long) m.get("marks");
//
//                    float mark=mar.floatValue();
//                    roll.add(rollNoS);
//                    marks.add(mar);
//                    total++;
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PerformanceGraphViewModel.class);
        // TODO: Use the ViewModel
    }

}
