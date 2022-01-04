package com.example.unico;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.Year;
import java.util.ArrayList;
import java.util.Map;

public class StudentSignUp extends AppCompatActivity implements  View.OnClickListener {
    EditText name,email,phone,password,username,answer,confirm_password,year,roll_no;
    Button register;
    Spinner spin2,spin3,spin4year;
    DatabaseClass dc;
    String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s13="Not Paid";
    String p1,p2,p3,p4,p5;
    static public int TotalAttendance=0,MarkedAttendance=0;
    double s12;
    boolean b=true;
    DatabaseReference db;
    CheckBox show_password;
  static   ArrayList<String> subjects;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up);
        dc=new DatabaseClass(this);
        db= FirebaseDatabase.getInstance().getReference();
        spin2=findViewById(R.id.spinner2);
        spin3=findViewById(R.id.spinner3);
        spin4year=findViewById(R.id.spinner4);




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

        name=findViewById(R.id.editText3);
        roll_no=findViewById(R.id.editText5);
        email=findViewById(R.id.editText4);
      //  year=findViewById(R.id.editText5);
        phone=findViewById(R.id.editText7);

        password=findViewById(R.id.editText9);
        confirm_password=findViewById(R.id.editText10);
        answer=findViewById(R.id.editText11);
        register=findViewById(R.id.button5);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String y="";
        s1 = name.getText().toString().trim();
        s2 = email.getText().toString().trim();
        s3 = phone.getText().toString().trim();
        s4 = roll_no.getText().toString().trim();
        s5=spin2.getSelectedItem().toString();

        Bundle bundle=getIntent().getExtras();
        final String year =bundle.getString("year");

        if (year.equals("Year1"))
        {
            s6="Y1";
        }
        else if (year.equals("Year2"))
        {
            s6="Y2";
        }
        else if (year.equals("Year3"))
        {
            s6="Y3";
        }else if (year.equals("Year4"))
        {
            s6="Y4";
        }
        s8 = password.getText().toString().trim();
        s9=spin3.getSelectedItem().toString();
        s10 = answer.getText().toString().trim();
        s11 = confirm_password.getText().toString().trim();

        s7 = s4+s6;
        b=checkError();
        if (b==true)
        {
//            yearViseClasses obj2=new yearViseClasses(s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,"null","null",0,s13);
//
//            db.child(year+"Students").push().setValue(obj2);
//            addAttendanceStudent obj=  new addAttendanceStudent(s7,TotalAttendance,MarkedAttendance);
            if (year.equals("Year1"))
            {

                p1="Mechanics";
                p2="Math 1";
                p3="Chemistry";
                p4="C Programming";
                p5="Engineering Drawing 1";
            }
            else if (year.equals("Year2"))
            {
                p1="Maths 2";
                p2="Relational Database";
                p3="Electronics";
                p4="C++ Programming";
                p5="Electronics Lab";
            }
            else if (year.equals("Year3"))
            {
                p1="Visual Basic Programming";
                p2="Data Structures";
                p3="Microprocessor";
                p4="Computer Networks";
                p5="Computer Networks Lab";

            }
            else if (year.equals("Year4"))
            {
                p1="Mobile Computing";
                p2="Computer Architecture";
                p3="Operating System";
                p4="Java Programming";
                p5="Project Lab";

            }

//
//            db.child(year+s1+s4+"Attendance"+p1).push().setValue(obj);
//
//            db.child(year+s1+s4+"Attendance"+p2).push().setValue(obj);
//
//            db.child(year+s1+s4+"Attendance"+p3).push().setValue(obj);
//
//            db.child(year+s1+s4+"Attendance"+p4).push().setValue(obj);
//
//            db.child(year+s1+s4+"Attendance"+p5).push().setValue(obj);
//

            Toast.makeText(StudentSignUp.this, "Registered Successfully", Toast.LENGTH_SHORT).show();



            final Dialog d=new Dialog(this);
            d.setContentView(R.layout.roll_no_display);
            d.setTitle("Username");
            final TextView t=d.findViewById(R.id.textView6);

            t.setText("Your Username is "+s7);

            d.show();

        }
    }

        public boolean checkError()
        {
            if (s1.isEmpty())
            {
                name.setError("Enter Name");
                b=false;
            }

            if (s2.isEmpty())
            {
                email.setError("Enter Valid Email");
                b=false;
            }
            if (s3.length()!=10)
            {
                phone.setError("Enter Valid Phone Number");
                b=false;
            }
            if (s4.isEmpty())
            {
                roll_no.setError("Enter Valid Roll Number");
                b=false;
            }
//        if (s5.isEmpty()&& s5.equals("Gender"))
//        {
//            spin2.("Choose Valid Option");
//        }
//        else
//        {
//            phone.setError("Null");
//        }
            if (s8.isEmpty())
            {
                password.setError("Enter Valid Password");
                b=false;
            }

            if (s11!=s8)
            {
                confirm_password.setError("Password Does Not Match");
                password.setError("Password Does Not Match");
                b=true;
            }

            if (s10.isEmpty())
            {
                answer.setError("Enter Valid Answer");
                b=false;
            }
            return b;
        }





}
//
//class yearViseClasses
//{
//    public String name,email,contact,roll_no,gender,year,username,password,question,answer,attendance,date,fee_status;
//    public double marks;
//    public yearViseClasses(String name, String email, String contact, String roll_no, String gender, String year, String username, String password, String question, String answer,String attendance, String date,double marks,String fee_status)
//    {
//        this.name=name;
//        this.email=email;
//        this.contact=contact;
//        this.roll_no=roll_no;
//        this.gender=gender;
//        this.year=year;
//        this.username=username;
//        this.password=password;
//        this.question=question;
//        this.answer=answer;
//        this.attendance=attendance;
//        this.date=date;
//        this.marks=marks;
//        this.fee_status=fee_status;
//    }
//}
//
//class addAttendanceStudent
//{
//
//    public String username;
//    public int total,present;
//    public addAttendanceStudent(String username,int total,int present)
//    {
//
//        this.username=username;
//        this.total=total;
//        this.present=present;
//
//    }
//}



