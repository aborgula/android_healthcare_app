package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class DoctorDetailActivity extends AppCompatActivity {

    TextView titletv;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    EditText fullname, address, fees, phonenumber;
    Button back, datepicker, timepicker, bookappointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);

        bookappointment = findViewById(R.id.bookbtn);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorDetailActivity.this, FindDoctorActivity.class));
            }
        });



        datepicker = findViewById(R.id.selectdatebtn);
        timepicker = findViewById(R.id.selecttimebtn);
        fullname = findViewById(R.id.fullname);
        address = findViewById(R.id.address);
        fees = findViewById(R.id.fees);
        phonenumber = findViewById(R.id.number);
        titletv = findViewById(R.id.title);
        Intent it = getIntent();
        String title = it.getStringExtra("title");
        titletv.setText(title);

        fullname.setKeyListener(null);
        address.setKeyListener(null);
        phonenumber.setKeyListener(null);
        fees.setKeyListener(null);

        switch(title) {
            case "dermatologist": {
                fullname.setText("Katerin Winsley");
                address.setText("Chicago");
                fees.setText("100");
                phonenumber.setText("574444672");
                break;
            }
            case "dietician": {
                fullname.setText("Wendy Warlock");
                address.setText("Idaho");
                fees.setText("50");
                phonenumber.setText("445132443");
                break;
            }
            case "surgeon": {
                fullname.setText("Bob Horan");
                address.setText("Washington");
                fees.setText("200");
                phonenumber.setText("509987787");
                break;
            }
            case "cardiologist": {
                fullname.setText("Marlon McMiller");
                address.setText("Iowa");
                fees.setText("170");
                phonenumber.setText("702220999");
                break;
            }
            case "dentist": {
                fullname.setText("Abraham Machon");
                address.setText("Minnessota");
                fees.setText("70");
                phonenumber.setText("776232444");
                break;
            }
        }

        initDatePicker();
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        initTimePicker();
        timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });


        bookappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Database db = new Database(getApplicationContext(), "healthcare",null, 1);
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username","").toString();

                if(db.checkAppointmentExists(username, titletv.getText().toString() + " => " + fullname.getText().toString(), address.getText().toString(), phonenumber.getText().toString(), datepicker.getText().toString(), timepicker.getText().toString()) == 1) {
                    Toast.makeText(getApplicationContext(), "Appointment already booked", Toast.LENGTH_SHORT).show();
                } else {
                    db.addOrder(username, titletv.getText().toString() + " => " + fullname.getText().toString(), address.getText().toString(), phonenumber.getText().toString(), 0, datepicker.getText().toString(), timepicker.getText().toString(), Float.parseFloat(fees.getText().toString()), "appointment");
                    Toast.makeText(getApplicationContext(), "Appointment done successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DoctorDetailActivity.this, HomeActivity.class));
               }

            }
        });
    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int i, int i1, int i2) {
                i1 = i1 +1;
                datepicker.setText(i2 + "/" + i1 + "/" + i);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;
        datePickerDialog = new DatePickerDialog(this,style,dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis()+86400000);
    }

    private void initTimePicker(){
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1){
                timepicker.setText(i + ":" + i1);
            }
        };

        Calendar cal = Calendar.getInstance();
        int hrs = cal.get(Calendar.HOUR);
        int mins = cal.get(Calendar.MINUTE);

        int style = AlertDialog.THEME_HOLO_DARK;
        timePickerDialog = new TimePickerDialog(this, style, timeSetListener,hrs,mins,true);
    }

    float x1,x2, y1, y2;

    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                float deltaX = x2 - x1;
                float deltaY = y2 - y1;

                if(Math.abs(deltaX) > Math.abs(deltaY)){
                    if(deltaX > 0){
                        Intent intent = new Intent(DoctorDetailActivity.this, DoctorDetail2.class);
                        intent.putExtra("title", titletv.getText().toString());
                        startActivity(intent);
                        // Swipe w prawo
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    } else {
                        // Swipe w lewo
                        Intent intent = new Intent(DoctorDetailActivity.this, DoctorDetail1.class);
                        intent.putExtra("title", titletv.getText().toString());
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    }
                }
                break;
        }
        return false;
    }

}