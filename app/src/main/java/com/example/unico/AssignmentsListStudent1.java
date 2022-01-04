package com.example.unico;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AssignmentsListStudent1 extends Fragment {
    List<uploadPDF> uploadPDFS;
    ListView assignmentsList;
    DatabaseReference databaseReference;
    String year,user,y;
    Spinner s7;
    String DateToStr;
   // ArrayList<String> dd=new ArrayList<>();
    int images = R.drawable.pdf;
    private AssignmentsListStudent1ViewModel mViewModel;

    public static AssignmentsListStudent1 newInstance() {
        return new AssignmentsListStudent1();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.assignments_list_student1_fragment, container, false);
        s7=v.findViewById(R.id.spinner7);
        year =StudentSignIn.Syear;
        user=StudentSignIn.username;

        assignmentsList=v.findViewById(R.id.assignments_list);
        uploadPDFS=new ArrayList<>();
         getDateFunction1();

        String List[]={"--Select--","View Uploads From Teacher","View Your Uploads"};
        ArrayAdapter list=new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line,List);
        s7.setAdapter(list);
        s7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(s7.getSelectedItem().equals("View Uploads From Teacher"))
                {
                    if (year.equals("Y1"))
                    {
                        y="Year1";
                    }
                    else if (year.equals("Y2"))
                    {
                        y="Year2";
                    }
                    else if (year.equals("Y2"))
                    {
                        y="Year2";
                    }
                    else if (year.equals("Y2"))
                    {
                        y="Year2";
                    }
                    databaseReference= FirebaseDatabase.getInstance().getReference(y+"Assignments");
                    viewAllFiles();
                    assignmentsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            uploadPDF uploadPDF=uploadPDFS.get(i);

                            Intent in=new Intent();
                            in.setData(Uri.parse(uploadPDF.getUrl()));
                            startActivity(in);
                        }
                    });


                }
                else if (s7.getSelectedItem().equals("View Your Uploads"))
                {

                    databaseReference=FirebaseDatabase.getInstance().getReference(year+"StudentsAssignments"+"/"+user+"/");
                    viewAllFiles();
                    assignmentsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            uploadPDF uploadPDF=uploadPDFS.get(i);
                            Intent in=new Intent();
                            in.setData(Uri.parse(uploadPDF.getUrl()));
                            startActivity(in);
                        }
                    });

                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return v;
    }

    private void viewAllFiles() {
        final File file=new File("com.example.unico.uploadPDF.class");


        if(uploadPDFS.size()>0)
        {
            uploadPDFS.clear();
        }

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren())
                {
                    uploadPDF uploadPDF=postSnapshot.getValue(com.example.unico.uploadPDF.class);
              //      Date d=new Date(file.lastModified());

                    uploadPDFS.add(uploadPDF);
               //     dd.add(d.toString());

                }

                String[] uploads=new String[uploadPDFS.size()];

                for(int i=0;i<uploads.length;i++)
                {
                    uploads[i]=uploadPDFS.get(i).getName11();
                }
//                String date[]=new String[dd.size()];
//
//                for (int i = 0; i < date.length; i++) {
//                    date[i] = dd.get(i);
//                }

                MyAdapter adapter = new MyAdapter(getContext(), uploads, images,DateToStr);
                assignmentsList.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
        mViewModel = ViewModelProviders.of(this).get(AssignmentsListStudent1ViewModel.class);
        // TODO: Use the ViewModel
    }
}
