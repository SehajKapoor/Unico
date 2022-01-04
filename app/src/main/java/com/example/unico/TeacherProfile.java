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

public class TeacherProfile extends AppCompatActivity {
    TextView t34,t36,t37,t38,t39,t40,t41,t42;
    String user=TeacherSignIn.username;
    DatabaseReference dr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        t34=findViewById(R.id.textView34);
        t36=findViewById(R.id.textView36);
        t37=findViewById(R.id.textView37);
        t38=findViewById(R.id.textView38);
        t39=findViewById(R.id.textView39);
        t40=findViewById(R.id.textView40);
        t41=findViewById(R.id.textView41);
        t42=findViewById(R.id.textView42);
        dr= FirebaseDatabase.getInstance().getReference();
        retreive_dataT();


    }
    private void retreive_dataT() {


        Query q= dr.child("Teacher").orderByChild("username").equalTo(user);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                    Map m = (Map) ds.getValue();

                    String name = (String) m.get("name");
                    String user = (String) m.get("username");
                    String contact = (String) m.get("contact");
                    String email = (String) m.get("email");
                    String d= (String) m.get("designation");
                    String gender= (String) m.get("gender");
                   // String year= (String) m.get("year");
                    String pass= (String) m.get("password");


                    t34.setText(name);
                    t36.setText(user);
                    t37.setText(contact);
                    t38.setText(email);
                    t39.setText(d);
                    t40.setText(gender);
                   // t41.setText(year);
                    t42.setText(pass);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
