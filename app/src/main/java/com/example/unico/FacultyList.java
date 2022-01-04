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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class FacultyList extends Fragment {
    ArrayList Tnames,Tcontact,Tdesignation;
    TableLayout tableLayout;
    DatabaseReference dr;
    TextView textView15;
    String s1,s2,s3;
    private FacultyListViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.faculty_list_fragment, container, false);

        textView15=v.findViewById(R.id.textView15);
        tableLayout=v.findViewById(R.id.tableLayoutFaculty);
        dr= FirebaseDatabase.getInstance().getReference();
        Tnames= new ArrayList<>();
        Tcontact=new ArrayList();
        Tdesignation=new ArrayList();

        retreiveData();
        addRow();
        return v;
    }



    public void addRow()
    {

        int a=1;

        //if table already contains values...removing all values first
        if(tableLayout.getChildCount()>0)
        {
            int count = tableLayout.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = tableLayout.getChildAt(i);
                if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
            }
        }

        TableRow tableRow11=new TableRow(getActivity());
        TextView textView11=new TextView(getActivity());
        TextView textView111=new TextView(getActivity());
        TextView textView211=new TextView(getActivity());
        TextView textView311=new TextView(getActivity());

        tableRow11.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
        tableRow11.setBackgroundColor(Color.LTGRAY);

        textView11.setText("S.No.");

        textView111.setText("NAME");
        textView211.setText("DESIGNATION");
        textView311.setText("CONTACT NO.");

        textView11.setTextColor(Color.parseColor("#C51111"));

        textView11.setTextSize(20);
        textView11.setWidth(150);
        textView11.setHeight(75);

        textView111.setTextColor(Color.parseColor("#C51111"));
        textView111.setTextSize(20);
        textView111.setWidth(400);
        textView111.setHeight(75);

        textView211.setTextColor(Color.parseColor("#C51111"));
        textView211.setTextSize(20);
        textView211.setWidth(500);
        textView211.setHeight(75);

        textView311.setTextColor(Color.parseColor("#C51111"));
        textView311.setTextSize(20);
        textView311.setWidth(300);
        textView311.setHeight(75);


        tableRow11.addView(textView11);
        tableRow11.addView(textView111);
        tableRow11.addView(textView211);
        tableRow11.addView(textView311);


        tableLayout.addView(tableRow11);



        for(int i=0;i<Tnames.size();i++)
        {
            TableRow tableRow=new TableRow(getActivity());
            TextView textView=new TextView(getActivity());
            TextView textView1=new TextView(getActivity());
            TextView textView2=new TextView(getActivity());
            TextView textView3=new TextView(getActivity());


            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
            tableRow.setBackgroundColor(Color.WHITE);



            textView.setText(a+"");
            String name=Tnames.get(i).toString();
            textView1.setText(name);
            String designation=Tdesignation.get(i).toString();
            textView2.setText(designation);
            String contact=Tcontact.get(i).toString();
            textView3.setText(contact);

            textView.setTextColor(Color.GRAY);
            textView.setTextSize(20);
            textView.setWidth(150);
            textView.setHeight(75);

            textView1.setTextColor(Color.GRAY);
            textView1.setTextSize(20);
            textView1.setWidth(400);
            textView1.setHeight(75);

            textView2.setTextColor(Color.GRAY);
            textView2.setTextSize(20);
            textView2.setWidth(500);
            textView2.setHeight(75);

            textView3.setTextColor(Color.GRAY);
            textView3.setTextSize(20);
            textView3.setWidth(300);
            textView3.setHeight(75);

            tableRow.addView(textView);
            tableRow.addView(textView1);
            tableRow.addView(textView2);
            tableRow.addView(textView3);


            tableLayout.addView(tableRow);

            a++;

        }
    }





    public void retreiveData()
    {



        Query q= dr.child("Teacher");

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                    Map m = (Map) ds.getValue();

                    s1= (String) m.get("name");
                    s2 = (String) m.get("designation");
                    s3= (String) m.get("contact");
                    Tnames.add(s1);
                    Tdesignation.add(s2);
                    Tcontact.add(s3);

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
        mViewModel = ViewModelProviders.of(this).get(FacultyListViewModel.class);
        // TODO: Use the ViewModel
    }

}
