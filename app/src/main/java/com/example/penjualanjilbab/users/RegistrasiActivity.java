package com.example.penjualanjilbab.users;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.penjualanjilbab.server.BaseURL;
import com.example.test.R;
import com.ornach.nobobutton.NoboButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RegistrasiActivity extends AppCompatActivity {

    Button btnBack;
    NoboButton btnRegistrasi;
    EditText edtUsername, edtNamaLengkap, edtEmail, edtNoHp, edtPassword;
    ProgressDialog pDialog;

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        getSupportActionBar().hide();

        mRequestQueue = Volley.newRequestQueue(this);

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtNamaLengkap = (EditText) findViewById(R.id.edtNamaLengkap);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtNoHp = (EditText) findViewById(R.id.edtNoHp);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        btnRegistrasi = (NoboButton) findViewById(R.id.btnRegistrasi);
        btnBack = (Button) findViewById(R.id.btnBack);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistrasiActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistrasiActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });


        btnRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUserName = edtUsername.getText().toString();
                String strNamaLengkap = edtNamaLengkap.getText().toString();
                String strEmail = edtEmail.getText().toString();
                String strNoHp = edtNoHp.getText().toString();
                String strPassword = edtPassword.getText().toString();

                if (strUserName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                } else if (strNamaLengkap.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "NamaLengkap Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                } else if (strEmail.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                } else if (strNoHp.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Nomor Handphone Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                } else if (strPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Password Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                } else {
                    registrasi(strUserName, strNamaLengkap, strEmail, strNoHp, strPassword);
                }
            }
        });
    }

     public void registrasi(String userName, String namaLengkap, String email, String noHp, String password){

            // Post params to be sent to the server
         HashMap<String, String> params = new HashMap<String, String>();
         params.put("userName", userName);
         params.put("namaLengkap", namaLengkap);
         params.put("email", email);
         params.put("noHp", noHp);
         params.put("role", "2");
         params.put("password", password);

         pDialog.setMessage("Mohon Tunggu...");
         showDialog();

         JsonObjectRequest req = new JsonObjectRequest(BaseURL.register, new JSONObject(params),
                 new Response.Listener<JSONObject>() {
                     @Override
                     public void onResponse(JSONObject response) {
                         hideDialog();
                         try {
                             String strMsg = response.getString("msg");
                             boolean status = response.getBoolean("error");
                             if(status == false) {
                                 Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                 Intent i = new Intent(RegistrasiActivity.this, LoginActivity.class);
                                 startActivity(i);
                                 finish();
                             }else {
                                 Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                             }
                             } catch(JSONException e) {
                                 e.printStackTrace();
                             }
                         }
                 }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 VolleyLog.e("Error: ", error.getMessage());
                 hideDialog();
             }
         });
         mRequestQueue.add(req);
     }

     private void showDialog(){
        if (!pDialog.isShowing()) {
            pDialog.show();
        }
     }
     private void hideDialog(){
         if (pDialog.isShowing()) {
             pDialog.dismiss();
         }
     }
     }