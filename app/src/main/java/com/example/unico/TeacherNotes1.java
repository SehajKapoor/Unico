package com.example.unico;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class TeacherNotes1 extends Fragment {
    ImageView plus;
    ListView lv;
    ArrayList<String> notesList,datesList,nn;
    DatabaseReference dr;
    String user=TeacherSignIn.username;
    String rr;
    int img=R.drawable.notethumb;

    private TeacherNotes1ViewModel mViewModel;

    public static TeacherNotes1 newInstance() {
        return new TeacherNotes1();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.teacher_notes1_fragment, container, false);

        dr= FirebaseDatabase.getInstance().getReference();
        lv=v.findViewById(R.id.notes);
        notesList=new ArrayList<>();
        datesList=new ArrayList<>();
        nn=new ArrayList<>();
        retrieve_data();
        plus=v.findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_host_fragment,new TeacherNotes2());
                fr.addToBackStack(null);
                fr.commit();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TeacherNotes3 f=new TeacherNotes3();
                Bundle arg=new Bundle();
                String a= nn.get(position);
                arg.putString("heading",a);
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
        Query q=dr.child(user+"Notes");

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Map m= (Map) ds.getValue();

                    String t=(String) m.get("heading");
                    String d=(String) m.get("date");
                    rr=t;
                    //rolll=roll_no;
                    notesList.add(t);
                    datesList.add(d);
                    String[] n = new String[notesList.size()];
                    String[] r = new String[datesList.size()];
                    for (int i = 0; i < n.length; i++) {
                        n[i] = notesList.get(i);
                    }
                    for (int i = 0; i < r.length; i++) {
                        r[i] = datesList.get(i);
                    }
                    nn.add(rr);
                    MyAdapter4 adapter = new MyAdapter4(getContext(),n,r,img);
                    lv.setAdapter(adapter);
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
        mViewModel = ViewModelProviders.of(this).get(TeacherNotes1ViewModel.class);
        // TODO: Use the ViewModel
    }

}
