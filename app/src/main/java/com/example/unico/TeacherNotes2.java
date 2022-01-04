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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TeacherNotes2 extends Fragment {
    EditText heading,content;
    Button save;
    DatabaseReference dr;
    final String user=TeacherSignIn.username;
    String DateToStr;
    String h;

    private TeacherNotes2ViewModel mViewModel;

    public static TeacherNotes2 newInstance() {
        return new TeacherNotes2();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.teacher_notes2_fragment, container, false);


        getDateFunction();


        dr= FirebaseDatabase.getInstance().getReference();

        heading=v.findViewById(R.id.Ntitle);
        content=v.findViewById(R.id.Ncontent);
        //content.setSelection(0);
        save=v.findViewById(R.id.save);
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
                    h=heading.getText().toString();
                    final String d=content.getText().toString();
                    addStudentNotes1 obj=  new addStudentNotes1(h,d,DateToStr);
                    dr.child(user+"Notes").push().setValue(obj);
                    Toast.makeText(getActivity(), "Notes Saved", Toast.LENGTH_SHORT).show();
                    FragmentTransaction fr=getFragmentManager().beginTransaction();
                    fr.replace(R.id.nav_host_fragment,new TeacherNotes1());
                    fr.addToBackStack(null);
                    fr.commit();


                }

            }
        });


        return v;
    }
    private void getDateFunction() {

        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy   HH:mm:ss");
        DateToStr = format.format(curDate);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TeacherNotes2ViewModel.class);
        // TODO: Use the ViewModel
    }

}

class addStudentNotes1
{
    public String note,date,heading;
    public addStudentNotes1(String hh,String note,String dd)
    {
        this.heading=hh;
        this.note=note;
        this.date=dd;
    }

}
