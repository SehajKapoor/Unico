package com.example.unico;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class StudentFragment extends Fragment {

    ListView lv;
    ArrayList<String> nameList,rollNoList,rollArr;
    DatabaseReference dr;
    String year, rolll;
    int img=R.drawable.studenticon;
    private StudentViewModel mViewModel;

    public static StudentFragment newInstance() {
        return new StudentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.student_fragment, container, false);


        dr= FirebaseDatabase.getInstance().getReference();

        year = getArguments().getString("year");

        lv = v.findViewById(R.id.list);

        nameList = new ArrayList<>();
        rollNoList = new ArrayList<>();
        rollArr=new ArrayList<>();

        retrieve_data();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FormFragement f=new FormFragement();
                Bundle arg=new Bundle();
                arg.putString("year",year);
                String a= rollArr.get(position);
                arg.putString("rollNo",a);
                f.setArguments(arg);
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment,f);
                fr.addToBackStack(null);
                fr.commit();
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

                    //  lv.setAdapter(ad);
                    //lvRoll.setAdapter(ad1);
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
        mViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        // TODO: Use the ViewModel
    }

} class MyAdapter2 extends ArrayAdapter<String>
{
    Context context;
    String nameT[];
    int rImage;
    String rollT[];
    MyAdapter2(Context c,String namee[],String roll[],int imgs){
        super(c,R.layout.students_list,R.id.mainTitleS,namee);
        this.context=c;
        this.nameT=namee;
        this.rImage=imgs;
        this.rollT=roll;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater= (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=layoutInflater.inflate(R.layout.students_list,parent,false);


        ImageView image=row.findViewById(R.id.Sicon);
        TextView title=row.findViewById(R.id.mainTitleS);
        TextView description=row.findViewById(R.id.subTitleS);
        image.setImageResource(rImage);
        title.setText(nameT[position]);
        description.setText("Roll No- "+rollT[position]);
        return row;
    }
}