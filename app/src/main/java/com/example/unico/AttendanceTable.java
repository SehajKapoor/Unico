package com.example.unico;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class AttendanceTable extends Fragment {

    ArrayList<String> nameList, rollNoList,attendance,attend,subjectList,subjects;
  //  ArrayList<String> presentRecord;
  //  String presentRecord[];
    DatabaseReference dr;
    Spinner spin,period;
    String s1,s2,s3,s4,s5;
    TableLayout tableLayout,table;
    String s[]={"--SELECT--","Mark Attendance","Mark All Present","Mark All Absent","Holiday"};
    Button submission,reset;
    String DateToStr,m;
    TextView ddText;
    String year,ss="",a;
    static String yy;
     int total=0;
     String present;
    static String sub="Subjects";
    int flag=0;
    int i=0;


    private AttendanceTableViewModel mViewModel;

    public static AttendanceTable newInstance() {
        return new AttendanceTable();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.attendance_table_fragment, container, false);


        getDateFunction1();

        dr = FirebaseDatabase.getInstance().getReference();

        year = getArguments().getString("year");
        yy=year;

        tableLayout=v.findViewById(R.id.attendanceStudents);
        table=v.findViewById(R.id.main);

        period=v.findViewById(R.id.spinner8);
        nameList = new ArrayList<>();
        rollNoList = new ArrayList<>();
        attendance=new ArrayList<>();
        subjectList = new ArrayList<>();
        attend=new ArrayList<>();

        spin=v.findViewById(R.id.spinner9);
        ddText=v.findViewById(R.id.date);
        getPeriods();
        ddText.setText(DateToStr);
        ArrayAdapter choose=new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line,s);
        spin.setAdapter(choose);
        retrieve_data();
        period.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ss=period.getItemAtPosition(position).toString();

                retrieve_subjects();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        reset=v.findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment,new YearsForAttendance());
                fr.addToBackStack(null);
                fr.commit();

            }
        });

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemValue=spin.getItemAtPosition(position).toString();

                if(itemValue.equals("Mark Attendance"))
                {
                    addTableRow();

                    spin.setEnabled(false);
                }
                else if(itemValue.equals("Mark All Present"))
                {
                    addTableRowForPresent();


                    spin.setEnabled(false);
                }
                else if(itemValue.equals("Mark All Absent"))
                {
                    addTableRowForAbsent();


                    spin.setEnabled(false);
                }
                else if (itemValue.equals("Holiday"))
                {
                    spin.setEnabled(false);
                    final   Dialog d=new Dialog(getActivity());
                    d.setContentView(R.layout.dialog_attendance);
                    d.setCanceledOnTouchOutside(false);
                    final EditText ans=d.findViewById(R.id.ans);
                    Button button=d.findViewById(R.id.button);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                             a=ans.getText().toString();

                            addTableRowHoliday();

                            d.dismiss();
                        }
                    });
                    d.show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submission=v.findViewById(R.id.submission);
        submission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameList.size() == total) {

                    for (int i = 0; i < total; i++) {
                        String username = nameList.get(i) + rollNoList.get(i);
                        final String attend1 = attend.get(i);
                        String d = DateToStr;
                        addAttendanceStudent1 obj = new addAttendanceStudent1(username, attend1, d);
                        dr.child(yy + "Attendance" + "/" + ss + "/" + DateToStr).push().setValue(obj);


                        Query q = dr.child(yy + username + "Attendance" + ss);


                        q.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                    Map m = (Map) ds.getValue();

                                    long totalDB = (long) m.get("total");
                                    long presentDB = (long) m.get("present");
                                    Toast.makeText(getActivity(), ""+totalDB+presentDB, Toast.LENGTH_SHORT).show();
                                    int tt =(int) (totalDB);

                                    tt++;
                                  //  totalDB=(long)tt;
                                   // totalDB = Integer.toString(tt);
                                    int pp =(int)(presentDB);

                                    String presentValue = attend1;

                                    ds.getRef().child("total").setValue(tt);
                                    if (presentValue.equals("Present")) {
                                        pp++;
                                       // presentDB=(long)pp;
                                       // presentDB = Integer.toString(pp);
                                        ds.getRef().child("present").setValue(pp);

                                    } else {
                                        ds.getRef().child("present").setValue(pp);
                                    }

                                }
                                //          Toast.makeText(getActivity(), "Time Table of " + year + " updated successfully !!", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }


                    //     Toast.makeText(getActivity(), "Present size "+presentRecord.size(), Toast.LENGTH_SHORT).show();

                    //        updateAttendance();
//                    addSubject obj3 = new addSubject(DateToStr,ss);
//                    dr.child(yy + "Subjects" + "/" + DateToStr).push().setValue(obj3);

                    Toast.makeText(getActivity(), "Attendance Updated Successfully", Toast.LENGTH_SHORT).show();
                    attend.clear();
                    nameList.clear();
                    rollNoList.clear();
                    total = 0;
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.nav_host_fragment, new TeacherFragment());
                    fr.addToBackStack(null);
                    fr.commit();

                }
                else {
                    Toast.makeText(getActivity(), "Please First Mark All Students Attendance", Toast.LENGTH_SHORT).show();
                }
            }

        });

        return v;


    }


    public void addTableRow()
    {



     //   presentRecord[100];
        //if table already contains values...removing all values first
        if(tableLayout.getChildCount()>0)
        {
            int count = tableLayout.getChildCount();
            for (int j = 0; j < count; j++) {
                View child = tableLayout.getChildAt(j);
                if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
            }
        }

        for( i=0;i<nameList.size();i++)
        {
            TableRow tableRow=new TableRow(getActivity());
            TextView textView=new TextView(getActivity());
            TextView textView1=new TextView(getActivity());
            final RadioButton absent=new RadioButton(getActivity());
            final RadioButton present=new RadioButton(getActivity());

            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
            tableRow.setBackgroundColor(Color.WHITE);

            String p= nameList.get(i);
            textView.setText(p);
            String s=rollNoList.get(i);
            textView1.setText(s);
            absent.setText("Absent");
            present.setText("Present");


            textView.setTextColor(Color.BLACK);
            textView.setTextSize(20);
            textView.setWidth(250);
            textView.setHeight(75);
            textView1.setTextColor(Color.BLACK);
            textView1.setTextSize(20);
            textView1.setWidth(100);
            textView1.setHeight(75);
            absent.setTextColor(Color.RED);
            absent.setTextSize(20);
            absent.setWidth(200);
            absent.setHeight(75);
            present.setTextColor(Color.GREEN);
            present.setTextSize(20);
            present.setWidth(200);
            present.setHeight(75);


            tableRow.addView(textView);
            tableRow.addView(textView1);
            tableRow.addView(absent);
            tableRow.addView(present);


            tableLayout.addView(tableRow);



                absent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        attend.add(i,"Absent");

                     //   presentRecord[i]="0";
                        if(present.isChecked())
                        {
                            present.setChecked(false);
                        }
                    }
                });

                present.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        attend.add(i,"Present");
                       // presentRecord[i]="1";
                        if(absent.isChecked())
                        {
                            absent.setChecked(false);
                        }
                    }
                });



            total++;
        }
        i=0;
    }


    public void addTableRowForPresent()
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



        for( i=0;i<nameList.size();i++)
        {
            TableRow tableRow=new TableRow(getActivity());
            TextView textView=new TextView(getActivity());
            TextView textView1=new TextView(getActivity());
            final RadioButton absent=new RadioButton(getActivity());
            RadioButton present=new RadioButton(getActivity());
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
            tableRow.setBackgroundColor(Color.WHITE);



            String p= nameList.get(i);
            textView.setText(p);
            String s=rollNoList.get(i);
            textView1.setText(s);
            absent.setText("Absent");
            present.setText("Present");


            textView.setTextColor(Color.BLACK);
            textView.setTextSize(20);
            textView.setWidth(250);
            textView.setHeight(75);
            textView1.setTextColor(Color.BLACK);
            textView1.setTextSize(20);
            textView1.setWidth(100);
            textView1.setHeight(75);
            absent.setTextColor(Color.RED);
            absent.setTextSize(20);
            absent.setWidth(200);
            absent.setHeight(75);
            present.setTextColor(Color.GREEN);
            present.setTextSize(20);
            present.setWidth(200);
            present.setHeight(75);


            tableRow.addView(textView);
            tableRow.addView(textView1);
            tableRow.addView(absent);
            tableRow.addView(present);


            tableLayout.addView(tableRow);


            present.setChecked(true);

            attend.add("Present");

         //   presentRecord[i]="1";

            absent.setEnabled(false);

            total++;
        }
        i=0;
    }


    public void addTableRowForAbsent()
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

        for( i=0;i<nameList.size();i++)
        {

            TableRow tableRow=new TableRow(getActivity());
            TextView textView=new TextView(getActivity());
            TextView textView1=new TextView(getActivity());
            final RadioButton absent=new RadioButton(getActivity());
            RadioButton present=new RadioButton(getActivity());
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
            tableRow.setBackgroundColor(Color.WHITE);



            String p= nameList.get(i);
            textView.setText(p);
            String s=rollNoList.get(i);
            textView1.setText(s);
            absent.setText("Absent");
            present.setText("Present");


            textView.setTextColor(Color.BLACK);
            textView.setTextSize(20);
            textView.setWidth(250);
            textView.setHeight(75);
            textView1.setTextColor(Color.BLACK);
            textView1.setTextSize(20);
            textView1.setWidth(100);
            textView1.setHeight(75);
            absent.setTextColor(Color.RED);
            absent.setTextSize(20);
            absent.setWidth(200);
            absent.setHeight(75);
            present.setTextColor(Color.GREEN);
            present.setTextSize(20);
            present.setWidth(200);
            present.setHeight(75);


            tableRow.addView(textView);
            tableRow.addView(textView1);
            tableRow.addView(absent);
            tableRow.addView(present);


            tableLayout.addView(tableRow);


            absent.setChecked(true);

            attend.add("Absent");

          //  presentRecord[i]="0";

            present.setEnabled(false);

            total++;
        }
        i=0;
    }

    public void addTableRowHoliday()
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

        for( i=0;i<nameList.size();i++)
        {
            TableRow tableRow=new TableRow(getActivity());
            TextView textView=new TextView(getActivity());
            TextView textView1=new TextView(getActivity());
            TextView textView2=new TextView(getActivity());


            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
            tableRow.setBackgroundColor(Color.WHITE);

            String p= nameList.get(i);
            textView.setText(p);
            String s=rollNoList.get(i);
            textView1.setText(s);
            textView2.setText(a+"(Holiday)");

            textView.setTextColor(Color.BLACK);
            textView.setTextSize(20);
            textView.setWidth(250);
            textView.setHeight(75);
            textView1.setTextColor(Color.BLACK);
            textView1.setTextSize(20);
            textView1.setWidth(200);
            textView1.setHeight(75);
            textView2.setTextColor(Color.BLACK);
            textView2.setTextSize(20);
            textView2.setWidth(300);
            textView2.setHeight(75);

            tableRow.addView(textView);
            tableRow.addView(textView1);
            tableRow.addView(textView2);

            tableLayout.addView(tableRow);


            attend.add(i,a+"(Holiday)");
            total++;
        }
        i=0;
    }


    public void getPeriods() {


        Query q = dr.child(yy + "TimeTable");

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
                   subjects =new ArrayList<>();

                    subjects.add(s1);
                    subjects.add(s2);
                    subjects.add(s3);
                    subjects.add(s4);
                    subjects.add(s5);
                    ArrayAdapter sub1 = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, subjects);
                    period.setAdapter(sub1);


//                    for (int k=0;k<5;k++)
//                    {
//                        if (sub.equals(subjects.get(k)))
//                        {
//                            subjects.remove(k);
//                        }
////                        else
////                        {
////                            period.setPrompt(subjects.get(k));
////                        }
//                    }


                    }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        }


    public void retrieve_data() {
        Query q = dr.child(yy + "Students");

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Map m = (Map) ds.getValue();

                    String name = (String) m.get("name");
                    String roll_no = (String) m.get("roll_no");
                    nameList.add(name);
                    rollNoList.add(roll_no);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void retrieve_subjects() {


        Query q = dr.child(yy + "Subjects"+"/"+DateToStr);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Map m = (Map) ds.getValue();

                    String date = (String) m.get("date");
                    String ssub = (String) m.get("subject");
                    if (DateToStr.equals(date))
                    {
                        if (ss.equals(ssub))
                        {
                            subjects.remove(ss);
                            ArrayAdapter sub1 = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, subjects);
                            period.setAdapter(sub1);

                        }
                        else
                        {
                            ArrayAdapter sub1 = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, subjects);
                            period.setAdapter(sub1);

                        }
                    }
                    else
                    {
                        ArrayAdapter sub1 = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, subjects);
                        period.setAdapter(sub1);

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void getDateFunction1()
    {
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy ");
        DateToStr = format.format(curDate);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AttendanceTableViewModel.class);
        // TODO: Use the ViewModel
    }

}




class addAttendanceDATE
{

    public String date;
    public addAttendanceDATE(String dateValue)
    {

        this.date=dateValue;

    }
}

class addAttendanceStudent1
{

    public String username,attendance,d;
    //public int total,present;
    public addAttendanceStudent1(String username,String attendance,String d)
    {

        this.username=username;
        this.attendance=attendance;
        this.d=d;

    }
}
class addSubject
{

    public String subject,date;
    //public int total,present;
    public addSubject(String d,String sub)
    {

        this.subject=sub;

        this.date=d;

    }
}
