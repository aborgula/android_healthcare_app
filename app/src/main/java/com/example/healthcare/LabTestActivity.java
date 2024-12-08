package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class LabTestActivity extends AppCompatActivity {

    Button back, gotocard;

    private String[][] packages =
            {
                    {"Package 1 : Full Body Checkup", "", "", "", "999"},
                    {"Package 2 : Blood Glucose Fasting", "", "", "", "299"},
                    {"Package 3 : Covid-19 Antibody - IgG", "", "", "", "899"},
                    {"Package 4 : Thyroid Check", "", "", "", "499"},
                    {"Package 5 : Immunity Check", "", "", "", "699"}
            };

    HashMap<String, String> item;
    ArrayList list;
    SimpleAdapter sa;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test);

        back = findViewById(R.id.backbtn);
        gotocard = findViewById(R.id.gotocardbtn);
        listView = findViewById(R.id.listviewlabtests);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LabTestActivity.this, HomeActivity.class));
            }
        });

        gotocard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LabTestActivity.this, CartLabActivity.class));

                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "").toString();
                String product;
            }
        });

        list = new ArrayList();
        for(int i = 0; i<packages.length; i++){
            item = new HashMap<String, String>();
            item.put("line1", packages[i][0]);
            item.put("line2", packages[i][1]);
            item.put("line3", packages[i][2]);
            item.put("line4", packages[i][3]);
            item.put("line5", "Total cost: " + packages[i][4] + "$");
            list.add(item);
        }

        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[] {"line1", "line2", "line3", "line4", "line5"},
                new int[] {R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        listView.setAdapter(sa);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedpreferences.getString("username","").toString();
                String product  = packages[i][0];
                float price  = Float.parseFloat(packages[i][4]);

                Toast.makeText(getApplicationContext(), username + "-" + product, Toast.LENGTH_SHORT).show();

                Database db = new Database(getApplicationContext(), "healthcare", null,1);
                if(db.checkCart(username, product)==1){
                    Toast.makeText(getApplicationContext(), "product already added", Toast.LENGTH_SHORT).show();
                }else{
                    db.addCart(username, product, price, "lab");
                    Toast.makeText(getApplicationContext(), "Record inderted to Cart", Toast.LENGTH_SHORT).show();
                }
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("product", username);
                editor.apply();
            }
        });
    }
}