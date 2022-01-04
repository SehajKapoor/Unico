package com.example.unico;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
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
import android.widget.ImageView;
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

public class AssignmentsListTeacher1 extends Fragment {
    List<uploadPDF> uploadPDFS;
    ListView assignmentsList;
    DatabaseReference databaseReference;
    TextView t24;
    String year;
    int images = R.drawable.pdf;
  //  ArrayList<String> dd=new ArrayList<>();
    String DateToStr;
    private AssignmentsListTeacher1ViewModel mViewModel;

    public static AssignmentsListTeacher1 newInstance() {
        return new AssignmentsListTeacher1();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.assignments_list_teacher1_fragment, container, false);
        year = getArguments().getString("year");
        t24 = v.findViewById(R.id.textView24);
        t24.setText(year + " Uploaded Assignments");
        databaseReference = FirebaseDatabase.getInstance().getReference(year + "Assignments");
        getDateFunction1();
        assignmentsList = v.findViewById(R.id.assignments_list);

        uploadPDFS = new ArrayList<>();
        viewAllFiles();

        assignmentsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                uploadPDF uploadPDF = uploadPDFS.get(i);

                Intent in = new Intent();
                in.setData(Uri.parse(uploadPDF.getUrl()));
                startActivity(in);
            }
        });

        return v;
    }

    private void viewAllFiles() {
        final File file=new File("com.example.unico.uploadPDF.class");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    uploadPDF uploadPDF = postSnapshot.getValue(com.example.unico.uploadPDF.class);

            //        Date d=new Date(file.lastModified());
                  //  long d= file.lastModified();

                    uploadPDFS.add(uploadPDF);
                //    dd.add(d.toString());
                }

                String[] uploads = new String[uploadPDFS.size()];

             //   String date[]=new String[dd.size()];
//
//                for (int i = 0; i < date.length; i++) {
//                    date[i] = dd.get(i);
//                }

                for (int i = 0; i < uploads.length; i++) {
                    uploads[i] = uploadPDFS.get(i).getName11();
                }


                MyAdapter adapter = new MyAdapter(getContext(), uploads, images,DateToStr);
                assignmentsList.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AssignmentsListTeacher1ViewModel.class);
        // TODO: Use the ViewModel
    }
    public void getDateFunction1()
    {
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy ");
        DateToStr = format.format(curDate);
    }

}

    class MyAdapter extends ArrayAdapter<String>
    {
      Context context;
      String rTitle[];
      int rImage;
      String d;
      MyAdapter(Context c,String title[],int imgs, String d){
       super(c,R.layout.row,R.id.mainTitle,title);
       this.context=c;
       this.rTitle=title;
       this.rImage=imgs;
       this.d=d;
      }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
          LayoutInflater layoutInflater= (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          View row=layoutInflater.inflate(R.layout.row,parent,false);


          ImageView image=row.findViewById(R.id.pdfImage);
          TextView title=row.findViewById(R.id.mainTitle);
          TextView description=row.findViewById(R.id.subTitle);
          image.setImageResource(rImage);
          title.setText(rTitle[position]);
          description.setText("Uploaded On-: "+d);
          return row;
        }
    }


