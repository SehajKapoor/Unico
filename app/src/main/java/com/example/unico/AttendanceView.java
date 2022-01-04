package com.example.unico;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class AttendanceView extends Fragment {

    TableLayout tableLayout;
    DatabaseReference dr;
    ArrayList list,dateList;

    TextView date;
    String name,attendence,date1;

    String formattedDate;

    String year;


    private AttendanceViewViewModel mViewModel;

    public static AttendanceView newInstance() {
        return new AttendanceView();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.attendance_view_fragment, container, false);


        year = getArguments().getString("year");
        tableLayout=(TableLayout) v.findViewById(R.id.tableLayout11);

        dr= FirebaseDatabase.getInstance().getReference();
        list=new ArrayList();
        dateList=new ArrayList();

        date=(TextView) v.findViewById(R.id.textView10);
        getDate();
        date.setText(formattedDate);
        retreiveData();

        return v;
    }

    public void getDate()
    {

        formattedDate = new SimpleDateFormat("yyyy-MMM-dd").format(new Date());    // Use formattedDate as your String filled with the date.
    }



    public void addTableRow()
    {
        for(int i=0;i<list.size();i++)
        {
            TableRow tableRow=new TableRow(getActivity());
            TextView textView=new TextView(getActivity());
            TextView textView1=new TextView(getActivity());


            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
            tableRow.setBackgroundColor(Color.WHITE);
            String a= (String) list.get(i);
            textView.setText(a);
            String aa=dateList.get(i).toString();

            if (formattedDate.equals(aa)) {

                 if (a.contains(".")) {
                    textView1.setText("|   Absent");

                } else if (a.contains("_")) {
                    textView1.setText("|   -");
                } else {
                    textView1.setText("|   Present");
                }
            }
            else
            {
                textView1.setText("|   ");
            }


            textView.setTextColor(Color.RED);
            textView.setTextSize(20);
            textView.setWidth(300);
            textView.setHeight(75);
            textView1.setTextColor(Color.GRAY);
            textView1.setTextSize(20);
            textView1.setWidth(300);
            textView1.setHeight(75);

            tableRow.addView(textView);
            tableRow.addView(textView1);


            tableLayout.addView(tableRow);

        }
    }


    public void retreiveData()
    {
        Query q= dr.child(year+"Students");

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                    Map m = (Map) ds.getValue();

                    name = (String) m.get("name");
                    attendence = (String) m.get("attendance");
                    date1 = (String) m.get("date");

                    dateList.add(date1);

                    getDate();
                    Toast.makeText(getActivity(), formattedDate, Toast.LENGTH_SHORT).show();

                    if (attendence.equals("absent")) {
                        name = name + ".";
                    } else if (attendence.equals("")) {
                        name = name + "_";
                    }


                    list.add(name);
                }
                addTableRow();


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AttendanceViewViewModel.class);
        // TODO: Use the ViewModel
    }

}
