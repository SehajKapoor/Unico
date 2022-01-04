package com.example.unico;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TeacherRegistry extends AppCompatActivity {
    Spinner spin2,spin3,spin4,spin5;
    EditText name,email,phone,password,username,answer,confirm_password;
    Button register;
    DatabaseClass dc;
    String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11;
    DatabaseReference db;
    public String DateToStr;
    String date;
    boolean b;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    CheckBox show_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_registry);
        spin2=findViewById(R.id.spinner2);

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

//        spin3=findViewById(R.id.spinner3);
//        spin3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if(parent.getItemAtPosition(position).equals("Security Questions"))
//                {
//
//                }
//                else
//                {
//                    s9=spin3.getSelectedItem().toString();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//        spin4=findViewById(R.id.spinner4);
//        spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if(parent.getItemAtPosition(position).equals("Incharge of"))
//                {
//
//                }
//                else
//                {
//                    s5=spin4.getSelectedItem().toString();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        spin5=findViewById(R.id.spinner5);

        db= FirebaseDatabase.getInstance().getReference();
        getDateFunction1();
        name=findViewById(R.id.editText3);
        email=findViewById(R.id.editText4);
        phone=findViewById(R.id.editText7);
        password=findViewById(R.id.editText9);
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        confirm_password=findViewById(R.id.editText10);
      //  answer=findViewById(R.id.editText11);
        dc=new DatabaseClass(this);
        register=findViewById(R.id.button5);
        date=DateToStr;
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s1=name.getText().toString().trim();
                s2=email.getText().toString().trim();
                s3=phone.getText().toString().trim();
                s4=spin2.getSelectedItem().toString();
                s6=spin5.getSelectedItem().toString();
                Random r=new Random();
                int otp1=r.nextInt(100);

                s7=s1+otp1;
                s8=password.getText().toString().trim();
             //   s10=answer.getText().toString().trim();
                s11=confirm_password.getText().toString().trim();
                b=true;
                b=checkError();
                if (b) {
                    getData obj = new getData(s1, s2, s3, s6, s4, s7, s8, date);
                    db.child("Teacher").push().setValue(obj);


                    Toast.makeText(TeacherRegistry.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    final Dialog d = new Dialog(TeacherRegistry.this);
                    //    final Dialog d=new Dialog(this);
                    d.setContentView(R.layout.username_display);
                    d.setTitle("Username");
                    final TextView t = d.findViewById(R.id.uu);

                    t.setText("Your Username is " + s7);

                    d.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(TeacherRegistry.this, ManasgementMain.class);
                            startActivity(i);
                            finish();

                        }
                    }, 3000);

                }

                    else
                    {
                        Toast.makeText(TeacherRegistry.this, "Error while registering.Try again!!", Toast.LENGTH_SHORT).show();
                    }

            }
        });

    }
    public void getDateFunction1()
    {
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy ");
        DateToStr = format.format(curDate);
    }

    public boolean checkError()
    {
        if (s1.isEmpty())
        {
            name.setError("Enter Name");
            b=false;
        }
        if (s2.matches(emailPattern))
        {

        }
        else
        {
            email.setError("Enter Valid Email");
            b=false;
        }
        if (s3.length()!=10)
        {
            phone.setError("Enter Valid Phone Number");
            b=false;
        }
        if (s4.equals("Gender")) {
            ((TextView) spin2.getSelectedView()).setError("Choose Valid Option");
            b = false;
        }

        if (s6.equals("Designation"))
        {
            ((TextView)spin5.getSelectedView()).setError("Choose Valid Option");
            b=false;
        }
        if (s8.isEmpty())
        {
            password.setError("Password Missing");
            b=false;
        }

        if (s11.equals(s8))
        {

        }
        else
        {
            confirm_password.setError("Password Does Not Match");
            b=false;
        }

        return b;
    }
}

class getData {

    public String name,email,contact,designation,gender,username,password,date;
    public getData(String name, String email, String contact, String designation, String gender, String username, String password,String date) {

        {
            this.name=name;
            this.email=email;
            this.contact=contact;
            this.designation=designation;
            this.gender=gender;
            this.username=username;
            this.password=password;
            this.date=date;

            }

    }
}

