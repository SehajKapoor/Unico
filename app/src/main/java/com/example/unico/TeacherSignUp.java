package com.example.unico;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TeacherSignUp extends AppCompatActivity  {
    Spinner spin2,spin3,spin4,spin5;
    EditText name,email,phone,password,username,answer,confirm_password;
    Button register;
    DatabaseClass dc;
    String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11;
    DatabaseReference db;
    CheckBox show_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_sign_up);
        spin2=findViewById(R.id.spinner2);
        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Gender"))
                {

                }
                else
                {
                    s4=spin2.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        show_password=findViewById(R.id.checkBox3);
        show_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    confirm_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }
                else
                {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirm_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });

        spin3=findViewById(R.id.spinner3);
        spin3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Security Questions"))
                {

                }
                else
                {
                    s9=spin3.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin4=findViewById(R.id.spinner4);
        spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Incharge of"))
                {

                }
                else
                {
                    s5=spin4.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin5=findViewById(R.id.spinner5);
        spin5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Designation"))
                {

                }
                else
                {
                    s6=spin5.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        db= FirebaseDatabase.getInstance().getReference();
        name=findViewById(R.id.editText3);
        email=findViewById(R.id.editText4);
        phone=findViewById(R.id.editText7);
        username=findViewById(R.id.editText8);
        password=findViewById(R.id.editText9);
        confirm_password=findViewById(R.id.editText10);
        answer=findViewById(R.id.editText11);
        dc=new DatabaseClass(this);
//        register=findViewById(R.id.button5);
//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                s1=name.getText().toString().trim();
//                s2=email.getText().toString().trim();
//                s3=phone.getText().toString().trim();
//                s6=spin5.getSelectedItem().toString();
//                s7=username.getText().toString().trim();
//                s8=password.getText().toString().trim();
//                s10=answer.getText().toString().trim();
//                s11=confirm_password.getText().toString().trim();
//                getData obj=new getData(s1,s2,s3,s6,s4,s5,s7,s8,s9,s10);
//                db.child("Teacher").push().setValue(obj);
//
//                if(s8.equals(s11))
//                {
//
//                    boolean register=dc.addDataT(s1,s2,s3,s4,s5,s6,s7,s8,s9,s10);
//
//                    if(register)
//                    {
//                        Toast.makeText(TeacherSignUp.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
//                    }
//                    else
//                    {
//                        Toast.makeText(TeacherSignUp.this, "Error while registering.Try again!!", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                finish();
//            }
//        });

    }
}
//
//class getData {
//
//   public String name,email,contact,designation,gender,incharge,username,password,question,answer;
//    public getData(String name, String email, String contact, String designation, String gender, String incharge, String username, String password, String question, String answer) {
//
//        {
//            this.name=name;
//            this.email=email;
//            this.contact=contact;
//            this.designation=designation;
//            this.gender=gender;
//            this.incharge=incharge;
//            this.username=username;
//            this.password=password;
//            this.question=question;
//            this.answer=answer;
//        }
//
//    }
//    }
