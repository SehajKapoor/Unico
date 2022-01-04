package com.example.unico;

import androidx.lifecycle.ViewModelProviders;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class FormFragement extends Fragment {

    private FormFragementViewModel mViewModel;

    public static FormFragement newInstance() {
        return new FormFragement();
    }
    EditText nameE,emailE,rollE,contactE;
    Spinner genderS,yearS;
    Button b5;
    DatabaseReference db;
    String a,b,c,d,e,f, rollNo,year,u;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.form_fragement_fragment, container, false);

        db= FirebaseDatabase.getInstance().getReference();

        year = getArguments().getString("year");
         rollNo = getArguments().getString("rollNo");
        nameE=v.findViewById(R.id.editText3);
        emailE=v.findViewById(R.id.editText4);
        contactE=v.findViewById(R.id.editText7);
     //   rollE=v.findViewById(R.id.editText5);
        genderS=v.findViewById(R.id.spinner2);
        yearS=v.findViewById(R.id.spinner4);
        b5=v.findViewById(R.id.button5);

        retrieveData();
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTable();
            }
        });




        return v;
    }

    private void updateTable() {
        final String name = nameE.getText().toString();
        final String email = emailE.getText().toString();
        final String contact = contactE.getText().toString();
       // final String roll = rollE.getText().toString();
        final String gender = genderS.getSelectedItem().toString();
        final String year = yearS.getSelectedItem().toString();
        Query q = db.child(year+"Students").orderByChild("roll_no").equalTo(rollNo);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ds.getRef().child("name").setValue(name);
                    ds.getRef().child("email").setValue(email);
                    ds.getRef().child("contact").setValue(contact);
                 //   ds.getRef().child("roll_no").setValue(roll);
                    ds.getRef().child("gender").setValue(gender);
                    ds.getRef().child("year").setValue(year);
                }
                Toast.makeText(getActivity(), name+" updated successfully !!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void retrieveData()
    {
        Query q= db.child(year+"Students").orderByChild("roll_no").equalTo(rollNo);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {

                    Map m= (Map) ds.getValue();

                    String a=(String) m.get("name");
                    String b=(String) m.get("email");
                    String c=(String) m.get("contact");
                    String d=(String) m.get("gender");
                    String e=(String) m.get("year");
                    String f=(String) m.get("roll_no");
                    nameE.setText(a);
                    emailE.setText(b);
                    contactE.setText(c);
                    if(d.equals("Male"))
                    {
                        genderS.setSelection(1);
                    }
                    else
                    {
                        genderS.setSelection(2);
                    }
                    if(e.equals("Year1"))
                    {
                        yearS.setSelection(1);
                    }
                    else if (e.equals("Year2"))
                    {
                        yearS.setSelection(2);
                    }
                    else if (e.equals("Year3"))
                    {
                        yearS.setSelection(3);
                    }
                    else if (e.equals("Year4"))
                    {
                        yearS.setSelection(4);
                    }
//                    rollE.setText(f);
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
        mViewModel = ViewModelProviders.of(this).get(FormFragementViewModel.class);
        // TODO: Use the ViewModel
    }

}
