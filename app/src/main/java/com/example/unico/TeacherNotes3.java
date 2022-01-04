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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class TeacherNotes3 extends Fragment {
    EditText heading,content;
    Button save,deleteNote;
    String h;
    DatabaseReference dr;
    final String user=TeacherSignIn.username;

    private TeacherNotes3ViewModel mViewModel;

    public static TeacherNotes3 newInstance() {
        return new TeacherNotes3();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.teacher_notes3_fragment, container, false);

        dr= FirebaseDatabase.getInstance().getReference();
        h = getArguments().getString("heading");
        heading=v.findViewById(R.id.Notestitle);
        content=v.findViewById(R.id.NcontentNotes);
        retrieve_data();
        save=v.findViewById(R.id.saveNotes);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (heading.getText().toString().isEmpty())
                {
                    heading.setError("Enter Title");
                    //    Toast.makeText(getActivity(), "Enter Title", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    updateNotes();
                    FragmentTransaction fr=getFragmentManager().beginTransaction();
                    fr.replace(R.id.nav_host_fragment,new TeacherNotes1());
                    fr.addToBackStack(null);
                    fr.commit();

                }

            }
        });

        deleteNote=v.findViewById(R.id.deleteNote);
        deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Query q=dr.child(user+"Notes").orderByChild("heading").equalTo(h);
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds: dataSnapshot.getChildren())
                        {
                            ds.getRef().removeValue();
                            Toast.makeText(getActivity(), "Note Deleated", Toast.LENGTH_SHORT).show();
                            FragmentTransaction fr=getFragmentManager().beginTransaction();
                            fr.replace(R.id.nav_host_fragment,new TeacherNotes1());
                            fr.addToBackStack(null);
                            fr.commit();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        return v;
    }
    public void retrieve_data()
    {
        Query q=dr.child(user+"Notes").orderByChild("heading").equalTo(h);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Map m= (Map) ds.getValue();

                    String t=(String) m.get("heading");
                    String c=(String) m.get("note");
                    heading.setText(t);
                    content.setText(c);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void updateNotes()
    {
        final String head = heading.getText().toString();
        final String con = content.getText().toString();
        Query q = dr.child(user+"Notes").orderByChild("heading").equalTo(h);


        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ds.getRef().child("heading").setValue(head);
                    ds.getRef().child("note").setValue(con);

                }
                Toast.makeText(getActivity(), "Notes updated successfully !!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TeacherNotes3ViewModel.class);
        // TODO: Use the ViewModel
    }

}
