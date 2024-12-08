package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;

public class HealthArticle extends AppCompatActivity {

    private String[][] packages =
            {
                    {"Mental Health", "", "", "", "Click More Details"},
                    {"Self - Discipline", "", "", "", "Click More Details"},
                    {"Food & Health", "", "", "", "Click More Details"},
                    {"Moral Injury", "", "", "", "Click More Details"}
            };

    private int[] images = {
            R.drawable.mentalarticle,
            R.drawable.d4ee4ae48a2db3f0ad76dc95b4f928bb,
            R.drawable.fit,
            R.drawable.moralarticle
    };

    HashMap<String, String> item;
    ArrayList list;
    SimpleAdapter sa;
    ListView listView;

    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_article);

        back = findViewById(R.id.backbtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HealthArticle.this, HomeActivity.class));
            }
        });

        listView = findViewById(R.id.listarticles);
        list = new ArrayList();
        for(int i = 0; i<packages.length; i++){
            item = new HashMap<String, String>();
            item.put("line1", packages[i][0]);
            item.put("line2", packages[i][1]);
            item.put("line3", packages[i][2]);
            item.put("line4", packages[i][3]);
            item.put("line5", "Total cost: " + packages[i][4] + "");
            list.add(item);
        }

        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[] {"line1", "line2", "line3", "line4", "line5"},
                new int[] {R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        listView.setAdapter(sa);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent it = new Intent(HealthArticle.this, HealthArticlesDetailsActivity.class);
                it.putExtra("text1", packages[i][0]);
                it.putExtra("text2", images[i]);
                startActivity(it);
            }
        });
    }
}