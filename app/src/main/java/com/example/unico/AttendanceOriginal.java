package com.example.unico;

import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
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

public class AttendanceOriginal extends Fragment {
    ListView lv;
    ArrayList<String> nameList, rollNoList,attendance,aa;// subjectList;
    DatabaseReference dr;
    String year, rolll;
    Spinner spin,period;
    String s1,s2,s3,s4,s5;
    String s[]={"--SELECT--","Mark All Present","Mark All Absent","Holiday"};
    //String p[]={"--SELECT PERIOD--","9:00-10:00","10:00-11:00","11:00-12:00","12:00-1:00","2:00-5:00"};
    Button submission;
    String DateToStr,m;
    TextView ddText;
    String[] n={};
    String[] r={};
   // int flag=0;
   // int i;
    private AttendanceOriginalViewModel mViewModel;

    public static AttendanceOriginal newInstance() {
        return new AttendanceOriginal();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.attendance_original_fragment, container, false);



        getDateFunction();


        dr = FirebaseDatabase.getInstance().getReference();

        year = getArguments().getString("year");

        lv = v.findViewById(R.id.studentsList);
        period=v.findViewById(R.id.period);
        nameList = new ArrayList<>();
        rollNoList = new ArrayList<>();
      //  subjectList = new ArrayList<>();
        attendance=new ArrayList<>();
        spin=v.findViewById(R.id.valid_option);
        ddText=v.findViewById(R.id.ddText);
        getPeriods();
        ddText.setText(DateToStr);
        ArrayAdapter choose=new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line,s);
        spin.setAdapter(choose);
        retrieve_data();





        // Toast.makeText(getActivity(), ""+attendance.size(), Toast.LENGTH_SHORT).show();



//        String ss=spin.getSelectedItem().toString();
//        if (ss.equals("Mark All Present"))
//        {
//
//        }
//        else if (ss.equals("Mark All Absent"))
//        {
//
//        }
//        else if (ss.equals("Holiday"))
//        {
//            Dialog d=new Dialog(getActivity());
//            d.setContentView(R.layout.dialog_attendance);
//            d.setCanceledOnTouchOutside(false);
//            final EditText ans=d.findViewById(R.id.ans);
//            Button button=d.findViewById(R.id.button);
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String a=ans.getText().toString();
//
//                }
//            });
//
//        }
        submission=v.findViewById(R.id.submission);
        submission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ss=period.getSelectedItem().toString();
                for (int i = 0; i < MyAdapter3.totalLength; i++) {
                    m = MyAdapter3.markedAttendence.get(i);
                    attendance.add(m);
                }
              //  Query q = dr.child(year+ "Attendance"+"/"+ss+"/"+DateToStr);
                for (int i=0;i<MyAdapter3.totalLength;i++)
                {
                    String username=nameList.get(i)+rollNoList.get(i);
                    String attend=attendance.get(i);
                    addAttendanceS obj=  new addAttendanceS(username,attend);
                    dr.child(year+ "Attendance"+"/"+ss+"/"+DateToStr).push().setValue(obj);

                }

              //  addAttendanceDATE obj=  new addAttendanceDATE(DateToStr);

               // dr.child(year+ "Attendance"+"/"+ss).push().setValue(obj);

                Toast.makeText(getActivity(), "Attendance Updated Successfully", Toast.LENGTH_SHORT).show();
             //   pp=ss;
            }
        });



        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AttendanceOriginalViewModel.class);
        // TODO: Use the ViewModel
    }



    public void getDateFunction()
    {
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
         DateToStr = format.format(curDate);
       // System.out.println(DateToStr);
    }

    public void getPeriods()
    {
        Query q = dr.child(year + "TimeTable");

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
                    String subjects[]={s1,s2,s3,s4,s5};

                    ArrayAdapter sub=new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line,subjects);
                    period.setAdapter(sub);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






//        for( i=0;i<subjects.length;i++)
//        {
//
//            Query q1 = dr.child(year + "Attendance/"+subjects[i]);
//            {
//
//                q1.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                        for (DataSnapshot ds1 : dataSnapshot.getChildren()) {
//                            Map m1 = (Map) ds1.getValue();
//
//                            String d=(String) m1.get("dateValue");
//                            if(d.equals(DateToStr))
//                            {
//                                flag=1;
//                            }
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//            }
//
//
//            if(flag==0)
//            {
//                subjectList.add(subjects[i]);
//            }
//            else if(flag==1)
//            {
//                flag=0;
//            }
//
//
//
//        }
//



    }






    public void retrieve_data() {
        Query q = dr.child(year + "Students");

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Map m = (Map) ds.getValue();

                    String name = (String) m.get("name");
                    String roll_no = (String) m.get("roll_no");
                   // rolll = roll_no;
                    nameList.add(name);
                    rollNoList.add(roll_no);


                     n = new String[nameList.size()];
                     r = new String[rollNoList.size()];
                    for (int i = 0; i < n.length; i++) {
                        n[i] = nameList.get(i);
                    }

                    for (int i = 0; i < r.length; i++) {
                        r[i] = rollNoList.get(i);
                    }
                    MyAdapter3 adapter = new MyAdapter3(getContext(), n, r);
                    lv.setAdapter(adapter);


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
class MyAdapter3 extends ArrayAdapter<String>
{
    Context context;
    String nameT[];
    String rollT[];
    public static ArrayList<String> markedAttendence;
    static int totalLength=0;
   // private Object v;


    MyAdapter3(Context c,String namee[],String roll[]){
        super(c,R.layout.students_list,R.id.mainTitleS,namee);
        this.context=c;
        this.nameT=namee;
        this.rollT=roll;

       markedAttendence = new ArrayList<>();

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater= (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=layoutInflater.inflate(R.layout.attendance_custom,parent,false);


        TextView title=row.findViewById(R.id.mainTitleA);
        TextView description=row.findViewById(R.id.subTitleA);
         final RadioButton absent=row.findViewById(R.id.absent);
        final RadioButton present=row.findViewById(R.id.present);

        title.setText(nameT[position]);
        description.setText("Roll No- "+rollT[position]);

//            String attend="";
//            boolean checked=((RadioButton)convertView).isChecked();
//
//            switch (convertView.getId())
//            {
//                case R.id.absent:
//                    if (checked)
//                        attend="Absent";
//                        break;
//                case R.id.present:
//                    if(checked)
//                        attend="Present";
//                        break;
//            }
//            markedAttendence.add(attend);


//              if (absent.isChecked())
//              {
//                 markedAttendence.add("Absent");
//              }
//              else if (present.isChecked())
//              {
//                  markedAttendence.add("Present");
//              }
        present.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

              //  markedAttendence.add("Present");
                present.setTag(position);
                markedAttendence.add("Present");
            }
        });
        absent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //  markedAttendence.add("Present");
                absent.setTag(position);
                markedAttendence.add("Absent");
            }
        });


        totalLength++;
        return row;
    }
}


class addAttendanceS
{

    public String username,attendence;
    public addAttendanceS(String username,String attendence)
    {


        this.username=username;
        this.attendence=attendence;


    }
}

//
//class addAttendanceDATE
//{
//
//    public String dateValue;
//    public addAttendanceDATE(String dateValue)
//    {
//
//
//        this.dateValue=dateValue;
//
//
//
//    }
//}
