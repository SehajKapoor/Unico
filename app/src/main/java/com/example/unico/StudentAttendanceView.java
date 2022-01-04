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
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
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

public class StudentAttendanceView extends Fragment {
  Spinner period;
  TableLayout tableLayout;
  DatabaseReference dr;
  String year=StudentSignIn.Syear;
  String name=StudentSignIn.Sname;
  String roll_no=StudentSignIn.rollNo;
  String user=name+roll_no;
  ArrayList dates,attendance;
  String s1,s2,s3,s4,s5;
  String ss,y;
  int total=0;

    private StudentAttendanceViewViewModel mViewModel;

    public static StudentAttendanceView newInstance() {
        return new StudentAttendanceView();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.student_attendance_view_fragment, container, false);
        dr = FirebaseDatabase.getInstance().getReference();

        if (year.equals("Y1"))
        {
            y="Year1";
        }
        else if (year.equals("Y2"))
        {
            y="Year2";
        }
        else if (year.equals("Y3"))
        {
            y="Year3";
        }
        else if (year.equals("Y4"))
        {
            y="Year4";
        }
        period=v.findViewById(R.id.selectP);
        getPeriods();
        tableLayout=v.findViewById(R.id.attendanceOfS);
        dates=new ArrayList();
        attendance=new ArrayList();
        period.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ss=period.getItemAtPosition(position).toString();
                retrieve_data();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return v;
    }


    public void retrieve_data() {
        Query q = dr.child(y + "Attendance"+ss).orderByChild("username").equalTo(user);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Map m = (Map) ds.getValue();

                    String dd = (String) m.get("date");
                    String aa = (String) m.get("attendence");
                    dates.add(dd);
                    attendance.add(aa);
                    addTableRow();
                    total++;
                }
                Toast.makeText(getActivity(), ""+total, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void addTableRow()
    {

        int i=0;
        //if table already contains values...removing all values first
        if(tableLayout.getChildCount()>0)
        {
            int count = tableLayout.getChildCount();
            for (int j = 0; j < count; j++) {
                View child = tableLayout.getChildAt(j);
                if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
            }
        }

        for( i=0;i<attendance.size();i++)
        {
            TableRow tableRow=new TableRow(getActivity());
            TextView textView=new TextView(getActivity());
            TextView textView1=new TextView(getActivity());

            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
            tableRow.setBackgroundColor(Color.WHITE);

            String p= dates.get(i).toString();
            textView.setText(p);
            String s=attendance.get(i).toString();
            textView1.setText(s);


            textView.setTextColor(Color.BLACK);
            textView.setTextSize(20);
            textView.setWidth(300);
            textView.setHeight(75);
            if (textView1.equals("Present"))
            {
                textView1.setTextColor(Color.GREEN);

            }
            else if (textView1.equals("Absent"))
            {
                textView1.setTextColor(Color.RED);
            }
            textView1.setTextSize(20);
            textView1.setWidth(300);
            textView1.setHeight(75);


            tableRow.addView(textView);
            tableRow.addView(textView1);
            tableLayout.addView(tableRow);


}
        i=0;
    }

    public void getPeriods() {
        Query q = dr.child(y + "TimeTable");

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
                    String subjects[] = {s1, s2, s3, s4, s5};
                    ArrayAdapter sub = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, subjects);
                    period.setAdapter(sub);


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
        mViewModel = ViewModelProviders.of(this).get(StudentAttendanceViewViewModel.class);
        // TODO: Use the ViewModel
    }

}
