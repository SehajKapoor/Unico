package com.example.unico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class StudentProfile extends AppCompatActivity {
TextView t34,t36,t37,t38,t39,t40,t41,t42,t43;
String Year=StudentSignIn.Syear;
String user=StudentSignIn.username;
String y;
DatabaseReference dr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        t34=findViewById(R.id.textView34);
        t36=findViewById(R.id.textView36);
        t37=findViewById(R.id.textView37);
        t38=findViewById(R.id.textView38);
        t39=findViewById(R.id.textView39);
        t40=findViewById(R.id.textView40);
        t41=findViewById(R.id.textView41);
        t42=findViewById(R.id.textView42);
        t43=findViewById(R.id.textView43);
        dr= FirebaseDatabase.getInstance().getReference();
        retreive_data();
    }

    private void retreive_data() {

        if (Year.equals("Y1"))
        {
            y="Year1";
        }
        else if (Year.equals("Y2"))
        {
            y="Year2";
        }
        else if (Year.equals("Y2"))
        {
            y="Year2";
        }
        else if (Year.equals("Y2"))
        {
            y="Year2";
        }

        Query q= dr.child(y+"Students").orderByChild("username").equalTo(user);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                    Map m = (Map) ds.getValue();

                    String name = (String) m.get("name");
                    String roll = (String) m.get("roll_no");
                    String contact = (String) m.get("contact");
                    String email = (String) m.get("email");
                    String user= (String) m.get("username");
                    String gender= (String) m.get("gender");
                    String year= (String) m.get("year");
                    String fee= (String) m.get("fee_status");
                    String pass= (String) m.get("password");


                    t34.setText(name);
                    t36.setText(roll);
                    t37.setText(contact);
                    t38.setText(email);
                    t39.setText(user);
                    t40.setText(gender);
                    t41.setText(year);
                    t42.setText(fee);
                    t43.setText(pass);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
