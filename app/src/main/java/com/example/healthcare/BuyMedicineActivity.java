
package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;


public class BuyMedicineActivity extends AppCompatActivity {



    private ArrayList<String[]> packages = new ArrayList<>();
    private HashMap<String, String> item;
    private ArrayList<HashMap<String, String>> list;
    private SimpleAdapter sa;
    private ListView lst;
    private Button gotocart, backbtn;
    private SearchView searchView;
    Animation scaleUp, scaleDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine);

        searchView = findViewById(R.id.searchView);
        lst = findViewById(R.id.listviewmedicine);
        backbtn = findViewById(R.id.backbtn);
        gotocart = findViewById(R.id.gotocardbtn);
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        backbtn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent){
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    backbtn.startAnimation(scaleUp);
                }else if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    backbtn.startAnimation(scaleDown);
                }
                return false;
                }
            });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(BuyMedicineActivity.this, HomeActivity.class));
                    }
                }, 1000);
            }
        });

        gotocart.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent){
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    gotocart.startAnimation(scaleUp);
                }else if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    gotocart.startAnimation(scaleDown);
                }
                return false;
            }
        });

        gotocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(BuyMedicineActivity.this, CartMedicineActivity.class));
                    }
                }, 1000);
            }
        });

        loadPackagesFromFile();

        list = new ArrayList<>();
        for (String[] packageData : packages) {
            item = new HashMap<>();
            item.put("line1", packageData[0]);
            item.put("line2", packageData[1]);
            item.put("line3", packageData[2]);
            item.put("line4", packageData[3]);
            item.put("line5", "Total cost: " + packageData[4] + "$");
            list.add(item);
        }

        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[] {"line1", "line2", "line3", "line4", "line5"},
                new int[] {R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedpreferences.getString("username", "");
                String product = packages.get(i)[0];
                float price = Float.parseFloat(packages.get(i)[4]);

                Toast.makeText(getApplicationContext(), username + "-" + product, Toast.LENGTH_SHORT).show();

                Database db = new Database(getApplicationContext(), "healthcare", null,1);
                db.addCart(username, product, price, "medicine");
                Toast.makeText(getApplicationContext(), "Record inserted to Cart", Toast.LENGTH_SHORT).show();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                sa.getFilter().filter(s);
                return false;
            }
        });

    }

    private void loadPackagesFromFile() {
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.packages);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] packageData = line.split(";");
                packages.add(packageData);
                Log.d("Wczytano", line);

            }
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


/**
package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class BuyMedicineActivity extends AppCompatActivity {



   private String[][] packages =
            {
                    {"Mamadha Premium", "", "", "", "99"},
                    {"Ogestan Inozytol 4000", "", "", "", "29"},
                    {"Cheers Vitamins & Minerals ", "", "", "", "89"},
                    {"Formeds Prenaps Ferr C+", "", "", "", "49"},
                    {"Acti-Globin", "", "", "", "69"},
                    {"Now Foods EVE- Multiwitamina ", "", "", "", "19"},
                    {"SINGULARIS Probio-Gwiazdki", "", "", "", "39"},
                    {"HERBAPOL Herbatka Fix ", "", "", "", "19"},
                    {"Focatis Junior Bio", "", "", "", "49"},
                    {"Novativ Multiwitamina", "", "", "", "9"}
            };


    HashMap<String, String> item;
    ArrayList list;
    SimpleAdapter sa;
    ListView lst;
    Button gotocart, backbtn;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine);

        searchView = findViewById(R.id.searchView);
        lst = findViewById(R.id.listviewmedicine);
        backbtn = findViewById(R.id.backbtn);
        gotocart = findViewById(R.id.gotocardbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BuyMedicineActivity.this, HomeActivity.class));
            }
        });

        gotocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BuyMedicineActivity.this, CartMedicineActivity.class));
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
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedpreferences.getString("username","").toString();
                String product  = packages[i][0];
                float price  = Float.parseFloat(packages[i][4]);

                Toast.makeText(getApplicationContext(), username + "-" + product, Toast.LENGTH_SHORT).show();

                Database db = new Database(getApplicationContext(), "healthcare", null,1);

                db.addCart(username, product, price, "medicine");
                Toast.makeText(getApplicationContext(), "Record inserted to Cart", Toast.LENGTH_SHORT).show();

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                sa.getFilter().filter(s);
                return false;
            }
        });

    }
}
 **/