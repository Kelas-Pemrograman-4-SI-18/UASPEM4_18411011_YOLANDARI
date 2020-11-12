package com.example.penjualanjilbab.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.penjualanjilbab.session.PrefSetting;
import com.example.test.R;

public class Profile extends AppCompatActivity {

    TextView txtUserName, txtNamaLengkap, txtEmail, txtNoHp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtUserName = (TextView) findViewById(R.id.UserName);
        txtNamaLengkap = (TextView) findViewById(R.id.NamaLengkap);
        txtEmail = (TextView) findViewById(R.id.Email);
        txtNoHp = (TextView) findViewById(R.id.NoHp);

        txtUserName.setText(PrefSetting.userName);
        txtNamaLengkap.setText(PrefSetting.namaLengkap);
        txtEmail.setText(PrefSetting.email);
        txtNoHp.setText(PrefSetting.noHp);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Profile.this, HomeAdminActivity.class);
        startActivity(i);
        finish();
    }
}