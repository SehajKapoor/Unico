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
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewAssignments extends Fragment {
    List<uploadPDF> uploadPDFS;
    ListView assignmentsList;
    DatabaseReference databaseReference;
    String yearr,Yearpath,y,rollSelected;
    int images = R.drawable.pdf;
    ArrayList<String> dd=new ArrayList<>();

    private ViewAssignmentsViewModel mViewModel;

    public static ViewAssignments newInstance() {
        return new ViewAssignments();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.view_assignments_fragment, container, false);
        yearr = getArguments().getString("year");
        rollSelected=getArguments().getString("rollNo");

        if(yearr.equals("Year1"))
        {
            y="Y1";
        }
        else if (yearr.equals("Year2"))
        {
            y="Y2";
        }
        else if (yearr.equals("Year3"))
        {
            y="Y3";
        }
        else if (yearr.equals("Year4"))
        {
            y="Y4";
        }
        Yearpath=y+"StudentsAssignments";

        databaseReference=FirebaseDatabase.getInstance().getReference(""+Yearpath+"/"+rollSelected+y+"/");

        assignmentsList = v.findViewById(R.id.assignments_list);

        uploadPDFS = new ArrayList<>();
        viewFiles();

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

    private void viewFiles() {

        final File file=new File("com.example.unico.uploadPDF.class");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    uploadPDF uploadPDF = postSnapshot.getValue(com.example.unico.uploadPDF.class);

                    Date d=new Date(file.lastModified());
                    //  long d= file.lastModified();

                    uploadPDFS.add(uploadPDF);
                    dd.add(d.toString());
                }

                String[] uploads = new String[uploadPDFS.size()];

                String date[]=new String[dd.size()];

                for (int i = 0; i < date.length; i++) {
                    date[i] = dd.get(i);
                }

                for (int i = 0; i < uploads.length; i++) {
                    uploads[i] = uploadPDFS.get(i).getName11();
                }


                MyAdapter1 adapter = new MyAdapter1(getContext(), uploads, images,date);
                assignmentsList.setAdapter(adapter);
                //  ArrayAdapter<String> adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,uploads);
                // assignmentsList.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ViewAssignmentsViewModel.class);
        // TODO: Use the ViewModel
    }

}
class MyAdapter1 extends ArrayAdapter<String>
{
    Context context;
    String rTitle[];
    int rImage;
    String d[];
    MyAdapter1(Context c,String title[],int imgs, String d[]){
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
        description.setText(""+d[position]);
        return row;
    }
}