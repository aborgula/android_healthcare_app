
package com.example.healthcare;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;



public class RegisterActivity extends AppCompatActivity {


    EditText edusername, edmail, edpassword, edconfirm;
    Button btn;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edconfirm = findViewById(R.id.confirm);
        tv = findViewById(R.id.already);
        edusername = findViewById(R.id.username);
        edmail = findViewById(R.id.mail);
        edpassword = findViewById(R.id.password);
        btn = findViewById(R.id.register);




        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edusername.getText().toString();
                String mail = edmail.getText().toString();
                String password = edpassword.getText().toString();
                String confirm = edconfirm.getText().toString();
                Database db = new Database(getApplicationContext(), "healthcare", null, 1);

                if(username.length() == 0 || mail.length() == 0 || password.length() == 0 || confirm.length() == 0){
                    Toast.makeText(getApplicationContext(), "Fill all the fields", Toast.LENGTH_SHORT).show();
                }else{
                    if(password.compareTo(confirm) == 0){
                        if(isValid(password)){
                            db.register(username,mail,password);
                            Toast.makeText(getApplicationContext(), "Record inserted", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));


                        }else{
                            Toast.makeText(getApplicationContext(), "Password must contain at least 6 letters, one special sign, a digit and a letter", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public static boolean isValid(String passwordhere){
        int f1=0, f2=0, f3=0;
        if(passwordhere.length() < 6){
            return false;
        }else{
            for (int p=0; p<passwordhere.length(); p++){
                if(Character.isLetter(passwordhere.charAt(p))){
                    f1=1;
                }
            }
            for (int p=0; p<passwordhere.length(); p++){
                if(Character.isDigit(passwordhere.charAt(p))){
                    f2=1;
                }
            }
            for (int p=0; p<passwordhere.length(); p++){
                char c = passwordhere.charAt(p);
                if(c>=33 && c<=46 || c==64){
                    f3 = 1;
                }
            }
            if(f1==1 && f2==1 && f3==1){
                return true;
            }
            return false;
        }
    }




}