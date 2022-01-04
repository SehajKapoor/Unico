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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class edit_notes extends Fragment {
EditText heading,content;
Button save,delete;
DatabaseReference dr;
final String user=StudentSignIn.username;
final String year=StudentSignIn.Syear;
String DateToStr;
    String h;

    private EditNotesViewModel mViewModel;

    public static edit_notes newInstance() {
        return new edit_notes();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.edit_notes_fragment, container, false);

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
                    addStudentNotes obj=  new addStudentNotes(h,d,DateToStr);
                    dr.child(year+"Notes"+"/"+user).push().setValue(obj);
                    Toast.makeText(getActivity(), "Notes Saved", Toast.LENGTH_SHORT).show();
                    FragmentTransaction fr=getFragmentManager().beginTransaction();
                    fr.replace(R.id.nav_host_fragment,new Notes1());
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
        mViewModel = ViewModelProviders.of(this).get(EditNotesViewModel.class);
        // TODO: Use the ViewModel
    }

}
class addStudentNotes
{
   public String note,date,heading;
   public addStudentNotes(String hh,String note,String dd)
   {
       this.heading=hh;
       this.note=note;
       this.date=dd;

       }

}
