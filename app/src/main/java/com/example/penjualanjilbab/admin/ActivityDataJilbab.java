package com.example.penjualanjilbab.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.penjualanjilbab.adapter.AdapterHijab;
import com.example.penjualanjilbab.model.ModelHijab;
import com.example.penjualanjilbab.server.BaseURL;
import com.example.test.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityDataJilbab extends AppCompatActivity {

    ProgressDialog pDialog;

    AdapterHijab adapter;
    ListView list;

    ArrayList<ModelHijab> newsList = new ArrayList<ModelHijab>();
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_jilbab);

        getSupportActionBar().setTitle("Data Jilbab");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);
        newsList.clear();
        adapter = new AdapterHijab(ActivityDataJilbab.this, newsList);
        list.setAdapter(adapter);
        getAllJilbab();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ActivityDataJilbab.this, HomeAdminActivity.class);
        startActivity(i);
        finish();
    }

    private void getAllJilbab() {
        // Pass second argument as "null" for GET requests
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, BaseURL.lihatdatajilbab, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            if (status == false) {
                                Log.d("data jilbab= ", response.toString());
                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    final ModelHijab hijab = new ModelHijab();
                                    final String _id = jsonObject.getString("_id");
                                    final String Jenis = jsonObject.getString("Jenis");
                                    final String kodejilbab = jsonObject.getString("kodejilbab");
                                    final String merk = jsonObject.getString("merk");
                                    final String ukuran = jsonObject.getString("ukuran");
                                    final String warna = jsonObject.getString("warna");
                                    final String harga = jsonObject.getString("harga");
                                    final String gambar = jsonObject.getString("gambar");
                                    hijab.setKodejilbab(kodejilbab);
                                    hijab.setJenis(Jenis);
                                    hijab.setMerk(merk);
                                    hijab.setUkuran(ukuran);
                                    hijab.setWarna(warna);
                                    hijab.setHarga(harga);
                                    hijab.setGambar(gambar);
                                    hijab.set_id(_id);

                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            // TODO Auto-generated method stub
                                            Intent a = new Intent(ActivityDataJilbab.this, EditJilbabDanHapusActivity.class);
                                            a.putExtra("kodejilbab", newsList.get(position).getKodejilbab());
                                            a.putExtra("_id", newsList.get(position).get_id());
                                            a.putExtra("Jenis", newsList.get(position).getJenis());
                                            a.putExtra("merk", newsList.get(position).getMerk());
                                            a.putExtra("ukuran", newsList.get(position).getUkuran());
                                            a.putExtra("warna", newsList.get(position).getWarna());
                                            a.putExtra("harga", newsList.get(position).getHarga());
                                            a.putExtra("gambar", newsList.get(position).getGambar());
                                            startActivity(a);
                                        }
                                    });
                                    newsList.add(hijab);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });

        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}