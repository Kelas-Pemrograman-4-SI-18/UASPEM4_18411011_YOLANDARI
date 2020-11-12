package com.example.penjualanjilbab.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.penjualanjilbab.model.ModelHijab;
import com.example.penjualanjilbab.server.BaseURL;
import com.example.test.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterHijab extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelHijab> item;

    public AdapterHijab(Activity activity, List<ModelHijab> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.content_hijab, null);


        TextView Jenis = (TextView) convertView.findViewById(R.id.txtJenis);
        TextView merk    = (TextView) convertView.findViewById(R.id.txtmerk);
        TextView ukuran  = (TextView) convertView.findViewById(R.id.txtukuran);
        TextView warna   = (TextView) convertView.findViewById(R.id.txtwarna);
        TextView harga    = (TextView) convertView.findViewById(R.id.txtharga);
        ImageView gambar   = (ImageView) convertView.findViewById(R.id.gambar);

        Jenis.setText(item.get(position).getJenis());
        merk.setText(item.get(position).getMerk());
        ukuran.setText(item.get(position).getUkuran());
        warna.setText(item.get(position).getWarna());
        harga.setText("Rp." + item.get(position).getHarga());
        Picasso.get().load(BaseURL.baseUrl + "gambar/" + item.get(position).getGambar())
                .resize(40, 40)
                .centerCrop()
                .into(gambar);
        return convertView;
    }
}
