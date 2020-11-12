package com.example.penjualanjilbab.pembeli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.penjualanjilbab.server.BaseURL;
import com.example.test.R;
import com.squareup.picasso.Picasso;

public class DetailJilbab extends AppCompatActivity {

    EditText edtkojilbab, edtJenis, edtmerk, edtukuran, edtwarna, edtharga;
    ImageView imgGambarBuku;

    String strkodejilbab, strJenis, strmerk, strukuran, strwarna, strharga, strGamabr, _id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_jilbab);


        edtkojilbab = (EditText) findViewById(R.id.edtkodejilbab);
        edtJenis = (EditText) findViewById(R.id.edtJenis);
        edtmerk = (EditText) findViewById(R.id.edtmerk);
        edtukuran = (EditText) findViewById(R.id.edtukuran);
        edtwarna = (EditText) findViewById(R.id.edtwarna);
        edtharga = (EditText) findViewById(R.id.edtharga);

        imgGambarBuku = (ImageView) findViewById(R.id.gambar);

        Intent i = getIntent();
        strkodejilbab = i.getStringExtra("kodejilbab");
        strJenis = i.getStringExtra("Jenis");
        strmerk = i.getStringExtra("merk");
        strukuran = i.getStringExtra("ukuran");
        strwarna = i.getStringExtra("warna");
        strharga = i.getStringExtra("harga");
        strGamabr = i.getStringExtra("gambar");
        _id = i.getStringExtra("_id");

        edtkojilbab.setText(strkodejilbab);
        edtJenis.setText(strJenis);
        edtmerk.setText(strmerk);
        edtukuran.setText(strukuran);
        edtwarna.setText(strwarna);
        edtharga.setText(strharga);
        Picasso.get().load(BaseURL.baseUrl + "gambar/" + strGamabr)
                .into(imgGambarBuku);
    }
}