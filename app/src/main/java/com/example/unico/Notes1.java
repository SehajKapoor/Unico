package com.example.unico;

import androidx.fragment.app.FragmentTransaction;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class Notes1 extends Fragment {
ImageView plus;
    ListView lv;
    ArrayList<String> notesList,datesList,nn;
    DatabaseReference dr;
    String year=StudentSignIn.Syear;
    String user=StudentSignIn.username;
    String rr;
    int img=R.drawable.notethumb;

    private Notes1ViewModel mViewModel;

    public static Notes1 newInstance() {
        return new Notes1();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.notes1_fragment, container, false);

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
                fr.replace(R.id.nav_host_fragment,new edit_notes());
                fr.addToBackStack(null);
                fr.commit();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editNotes f=new editNotes();
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
        Query q=dr.child(year+"Notes"+"/"+user);

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
        mViewModel = ViewModelProviders.of(this).get(Notes1ViewModel.class);
        // TODO: Use the ViewModel
    }

}
class MyAdapter4 extends ArrayAdapter<String>
{
    Context context;
    String nameT[];
    String dateT[];
    int rImage;
    MyAdapter4(Context c,String namee[],String dd[],int imgs){
        super(c,R.layout.notes_list,R.id.mainTitleN,namee);
        this.context=c;
        this.nameT=namee;
        this.rImage=imgs;
        this.dateT=dd;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater= (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=layoutInflater.inflate(R.layout.notes_list,parent,false);


        ImageView image=row.findViewById(R.id.NoteImage);
        TextView title=row.findViewById(R.id.mainTitleN);
        TextView description=row.findViewById(R.id.subTitleN);
        image.setImageResource(rImage);
        title.setText(nameT[position]);
        description.setText("Created On-: "+dateT[position]);
        return row;
    }
}
