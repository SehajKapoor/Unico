package com.example.unico;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class Evaluation2 extends Fragment {
    ListView lv;
    ArrayList<String> nameList,rollNoList, rollArr;
    DatabaseReference dr;
    EditText e6,e12;
    Spinner spinner;

    String year, rolll,y;
    String marks[]={"A1 (10)","A2 (9)","B1 (8)","B2 (7)","F (0)"};
    int img=R.drawable.studenticon;
     String nameSelected, rollSelected,usernameSelected;
     int marksSelected;

    private StudentViewModel mViewModel;

    public static StudentFragment newInstance() {
        return new StudentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.evaluation2_fragment, container, false);


        dr= FirebaseDatabase.getInstance().getReference();

        year = getArguments().getString("year");
        Toast.makeText(getActivity(), "" + year, Toast.LENGTH_SHORT).show();

        lv = v.findViewById(R.id.StudentsNameList2);

        nameList = new ArrayList<>();
        rollNoList = new ArrayList<>();
        rollArr=new ArrayList<>();


        retrieve_data();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               nameSelected=nameList.get(position);
               rollSelected=rollArr.get(position);
                final Dialog d=new Dialog(getActivity());
                d.setContentView(R.layout.dialogevaluation);
                d.setCanceledOnTouchOutside(false);

                Button assignments,evaluation;

            //    d.show();
                assignments=d.findViewById(R.id.assignment);
                evaluation=d.findViewById(R.id.evaluate);
                assignments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                        ViewAssignments s=new ViewAssignments();
                        Bundle arg=new Bundle();
                        arg.putString("year",year);
                        arg.putString("rollNo",rollSelected);
                        s.setArguments(arg);
                        s.setArguments(arg);
                        FragmentTransaction fr=getFragmentManager().beginTransaction();
                        fr.replace(R.id.nav_host_fragment,s);
                        fr.addToBackStack(null);
                        fr.commit();

                        //d.show();

                    }
                });
                evaluation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        d.dismiss();
                     final   Dialog d1=new Dialog(getActivity());
                        d1.setContentView(R.layout.dialogbox);
                        d1.setCanceledOnTouchOutside(false);

                        Button b;
                        spinner=d1.findViewById(R.id.spinner);
                        e6=d1.findViewById(R.id.editText6);
                        e12=d1.findViewById(R.id.editText12);
                        ArrayAdapter grades=new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line,marks);
                        spinner.setAdapter(grades);

                        e6.setText(nameSelected);
                        e12.setText(rollSelected);
                        b=d1.findViewById(R.id.button);
                                b.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                         String grade=spinner.getSelectedItem().toString();
                                        if (grade.equals("A1 (10)"))
                                        {
                                            marksSelected=10;
                                        }
                                        else if (grade.equals("A2 (9)"))
                                        {
                                            marksSelected=9;
                                        }
                                        else if (grade.equals("B1 (8)"))
                                        {
                                            marksSelected=8;
                                        }
                                        else if (grade.equals("B2 (7)"))
                                        {
                                            marksSelected=7;
                                        }
                                        else if (grade.equals("F (0)"))
                                        {
                                            marksSelected=0;
                                        }
                                        else {
                                            Toast.makeText(getActivity(), "Choose Valid option", Toast.LENGTH_SHORT).show();
                                        }


                                        updateMarks();
                                        d1.dismiss();
                                        d.dismiss();

                                    }
                                });


                        d1.show();
                    }
                });

                d.show();

            }
        });

        return v;
    }

    public void retrieve_data()
    {
        Query q=dr.child(year+"Students");

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Map m= (Map) ds.getValue();

                    String name=(String) m.get("name");
                    String roll_no=(String) m.get("roll_no");
                    rolll=roll_no;

                    nameList.add(name);
                    rollNoList.add(roll_no);
                    String[] n = new String[nameList.size()];
                    String[] r = new String[rollNoList.size()];
                    for (int i = 0; i < n.length; i++) {
                        n[i] = nameList.get(i);
                    }
                    for (int i = 0; i < r.length; i++) {
                        r[i] = rollNoList.get(i);
                    }
                    rollArr.add(rolll);
                    MyAdapter2 adapter = new MyAdapter2(getContext(),n,r,img);
                    lv.setAdapter(adapter);

                   }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
public void updateMarks()
{
    Query q = dr.child(year+"Students").orderByChild("roll_no").equalTo(rollSelected);
    q.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot ds:dataSnapshot.getChildren())
            {

                ds.getRef().child("marks").setValue(marksSelected);
                Toast.makeText(getActivity(), "Marks Updated Successfully!!", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
}




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        // TODO: Use the ViewModel
    }



}