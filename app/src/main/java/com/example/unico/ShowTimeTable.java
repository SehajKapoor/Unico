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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class ShowTimeTable extends Fragment {

    private ShowTimeTableViewModel mViewModel;

    public static ShowTimeTable newInstance() {
        return new ShowTimeTable();
    }

    ArrayList subjects,periods;
    TableLayout tableLayout;
    DatabaseReference dr;
    TextView t15;
    TextView ddText;
    Spinner year;
    String DateToStr;

    String p1,p2,p3,p4,p5;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.show_time_table_fragment, container, false);
        t15=v.findViewById(R.id.textView15);


        getDateFunction1();

        tableLayout=v.findViewById(R.id.tableLayoutTime);
        dr= FirebaseDatabase.getInstance().getReference();
        year=v.findViewById(R.id.year);
        ddText=v.findViewById(R.id.dateT);
        ddText.setText(DateToStr);

        String Year[]={"Select Year","Year1","Year2","Year3","Year4"};
        ArrayAdapter chooseY=new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line,Year);
        year.setAdapter(chooseY);


        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                retreiveData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return v;
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



        for( i=0;i<subjects.size();i++)
        {
            TableRow tableRow=new TableRow(getActivity());
            TextView textView=new TextView(getActivity());
            TextView textView1=new TextView(getActivity());


            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
            tableRow.setBackgroundColor(Color.WHITE);



            String p= periods.get(i).toString();
            textView.setText(p);
            String s=subjects.get(i).toString();
            textView1.setText(s);



            textView.setTextColor(Color.RED);
            textView.setTextSize(20);
            textView.setWidth(300);
            textView.setHeight(75);
            textView1.setTextColor(Color.GRAY);
            textView1.setTextSize(20);
            textView1.setWidth(600);
            textView1.setHeight(75);

            tableRow.addView(textView);
            tableRow.addView(textView1);


            tableLayout.addView(tableRow);

        }


        i=0;
    }



    public void getDateFunction1()
    {
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy ");
        DateToStr = format.format(curDate);
    }


    public void retreiveData()
    {

        subjects=new ArrayList();

        periods=new ArrayList();
        String y=year.getSelectedItem().toString();

        Query q= dr.child(y+"TimeTable");

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                    Map m = (Map) ds.getValue();

                    p1 = (String) m.get("p1");
                    p2 = (String) m.get("p2");
                    p3 = (String) m.get("p3");
                    p4 = (String) m.get("p4");
                    p5 = (String) m.get("p5");

                    subjects.add(p1);
                    subjects.add(p2);
                    subjects.add(p3);
                    subjects.add(p4);
                    subjects.add(p5);

                    periods.add("Period 1");
                    periods.add("Period 2");
                    periods.add("Period 3");
                    periods.add("Period 4");
                    periods.add("Period 5");

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
        mViewModel = ViewModelProviders.of(this).get(ShowTimeTableViewModel.class);
        // TODO: Use the ViewModel
    }

}
