package com.example.penjualanjilbab.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

import com.example.penjualanjilbab.session.PrefSetting;
import com.example.penjualanjilbab.session.SessionManager;
import com.example.penjualanjilbab.users.LoginActivity;
import com.example.test.R;

public class HomeAdminActivity extends AppCompatActivity {


    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;
    CardView cardExit, cardDataJilbab, cardInputDataJilbab, cardProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);


        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharedPreferences();

        session = new SessionManager(HomeAdminActivity.this);

        prefSetting.isLogin(session, prefs);


        cardExit = (CardView) findViewById(R.id.cardExit);
        cardDataJilbab = (CardView) findViewById(R.id.cardDataJilbab);
        cardInputDataJilbab = (CardView) findViewById(R.id.cardInputDataJilbab);
        cardProfile = (CardView) findViewById(R.id.cardProfile);

        cardExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                session.setSessid(0);
                Intent i = new Intent(HomeAdminActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        cardDataJilbab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeAdminActivity.this, ActivityDataJilbab.class);
                startActivity(i);
                finish();
            }
        });

        cardInputDataJilbab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeAdminActivity.this, InputDataJilbab.class);
                startActivity(i);
                finish();
            }
        });

        cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeAdminActivity.this, Profile.class);
                startActivity(i);
                finish();
            }
        });
    }
}