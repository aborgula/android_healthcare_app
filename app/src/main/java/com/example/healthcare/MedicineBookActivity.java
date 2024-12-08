package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MedicineBookActivity extends AppCompatActivity {

    EditText fullname, address, phonenumber, pincode;
    Button book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_book);

        fullname = findViewById(R.id.fullname);
        address = findViewById(R.id.address);
        phonenumber = findViewById(R.id.contact);
        pincode = findViewById(R.id.pincode);
        book = findViewById(R.id.book);

        Intent intent = getIntent();
        String[] price = intent.getStringExtra("price").toString().split(java.util.regex.Pattern.quote(":"));
        String date = intent.getStringExtra("date");

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "").toString();

                Database db = new Database(getApplicationContext(), "healthcare",null, 1);
                db.addOrder(username, fullname.getText().toString(), address.getText().toString(), phonenumber.getText().toString(), Integer.parseInt(pincode.getText().toString()), date.toString(), "", Float.parseFloat(price[1].toString()), "medicine");
                db.removeCard(username,"medicine");
                Toast.makeText(getApplicationContext(), "Booking is done successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(MedicineBookActivity.this, HomeActivity.class));
            }
        });
    }
}