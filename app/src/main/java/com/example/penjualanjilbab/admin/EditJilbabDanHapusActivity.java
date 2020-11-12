package com.example.penjualanjilbab.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.penjualanjilbab.server.BaseURL;
import com.example.penjualanjilbab.server.VolleyMultipart;
import com.example.test.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditJilbabDanHapusActivity extends AppCompatActivity {

    EditText edtkodejilbab, edtJenis, edtmerk, edtukuran, edtwarna, edtharga;
    ImageView imgGambarJilbab;
    Button btnTakeImg, editData, btnUploadGambar, hapusData;

    String strkodejilbab, strJenis, strmerk, strukuran, strwarna, strharga, strgamabr, _id;

    private RequestQueue mRequestQueue;

    Bitmap bitmap;

    private final int CameraR_PP = 1;
    String mCurrentPhotoPath;

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_jilbab_dan_hapus);

        getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        edtkodejilbab = (EditText) findViewById(R.id.edtkodejilbab);
        edtJenis = (EditText) findViewById(R.id.edtJenis);
        edtmerk = (EditText) findViewById(R.id.edtmerk);
        edtukuran = (EditText) findViewById(R.id.edtukuran);
        edtwarna = (EditText) findViewById(R.id.edtwarna);
        edtharga = (EditText) findViewById(R.id.edtharga);

        imgGambarJilbab = (ImageView) findViewById(R.id.gambar);

        btnTakeImg = (Button) findViewById(R.id.btnTakeImage);
        editData = (Button) findViewById(R.id.editData);
        btnUploadGambar = (Button) findViewById(R.id.btnUploadGambar);
        hapusData = (Button) findViewById(R.id.hapusData);

        Intent i = getIntent();
        strkodejilbab = i.getStringExtra("kodejilbab");
        strJenis = i.getStringExtra("Jenis");
        strmerk = i.getStringExtra("merk");
        strukuran = i.getStringExtra("ukuran");
        strwarna = i.getStringExtra("warna");
        strharga = i.getStringExtra("harga");
        strgamabr = i.getStringExtra("gambar");
        _id = i.getStringExtra("_id");

        edtkodejilbab.setText(strkodejilbab);
        edtJenis.setText(strJenis);
        edtmerk.setText(strmerk);
        edtukuran.setText(strukuran);
        edtwarna.setText(strwarna);
        edtharga.setText(strharga);
        Picasso.get().load(BaseURL.baseUrl + "gambar/" + strgamabr)
                .into(imgGambarJilbab);

        btnTakeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeImage();
            }
        });
        editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDataTanpaGambar();
            }
        });

        btnUploadGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDataDenganGambar(bitmap);
            }
        });

        hapusData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditJilbabDanHapusActivity.this);

                builder.setTitle("Konfirmasi");
                builder.setMessage("Yakin ingin menghapus buku " + strJenis + " ? ");

                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        hapusData();
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });
    }

    public void takeImage(){
        addPermission();
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(EditJilbabDanHapusActivity.this.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.i("Tags", "IOException");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(cameraIntent, CameraR_PP);
            }
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == EditJilbabDanHapusActivity.RESULT_CANCELED) {
            return;
        }
        if (requestCode == CameraR_PP) {
            try {

                bitmap = MediaStore.Images.Media.getBitmap(EditJilbabDanHapusActivity.this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
                imgGambarJilbab.setImageBitmap(bitmap);
//                uploadPotoProfile(bitmap);
                if (imgGambarJilbab.getDrawable() != null) {
                    int newHeight = 300; // New height in pixels
                    int newWidth = 300;
                    imgGambarJilbab.requestLayout();
                    imgGambarJilbab.getLayoutParams().height = newHeight;
                    // Apply the new width for ImageView programmatically
                    imgGambarJilbab.getLayoutParams().width = newWidth;
                    // Set the scale type for ImageView image scaling
                    imgGambarJilbab.setScaleType(ImageView.ScaleType.FIT_XY);
                    ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) imgGambarJilbab.getLayoutParams();
                    marginParams.setMargins(0, 10, 0, 0);
                    btnUploadGambar.setVisibility(View.VISIBLE);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(EditJilbabDanHapusActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addPermission() {
        Dexter.withActivity(EditJilbabDanHapusActivity.this)
                .withPermissions(

                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            //Toast.makeText(getActivity(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(EditJilbabDanHapusActivity.this, "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (bitmap != null){
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
        }
        return byteArrayOutputStream.toByteArray();
    }

    private void editDataDenganGambar(final Bitmap bitmap) {

        pDialog.setMessage("Mohon Tunggu Sedang Mengupload Gambar !!!!!");
        showDialog();
        VolleyMultipart volleyMultipartRequest = new VolleyMultipart(Request.Method.PUT, BaseURL.updatehijab + _id,
                new Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        mRequestQueue.getCache().clear();
                        hideDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            System.out.println("ress = " + jsonObject.toString());
                            String strMsg = jsonObject.getString("msg");
                            boolean status= jsonObject.getBoolean("error");
                            System.out.println("response server = " +  jsonObject.toString());
                            if(status == false){
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                Intent i = new Intent(EditJilbabDanHapusActivity.this, ActivityDataJilbab.class);
                                startActivity(i);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
                        Toast.makeText(EditJilbabDanHapusActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("kodejilbab", edtkodejilbab.getText().toString());
                params.put("Jenis", edtJenis.getText().toString());
                params.put("merk", edtmerk.getText().toString());
                params.put("ukuran", edtukuran.getText().toString());
                params.put("warna", edtwarna.getText().toString());
                params.put("harga", edtharga.getText().toString());
                return params;
            }
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("gambar", new VolleyMultipart.DataPart(imagename + ".jpg", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue = Volley.newRequestQueue(EditJilbabDanHapusActivity.this);
        mRequestQueue.add(volleyMultipartRequest);
    }

    public void editDataTanpaGambar(){

        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("kodejilbab", edtkodejilbab.getText().toString());
        params.put("Jenis", edtJenis.getText().toString());
        params.put("ukuran", edtukuran.getText().toString());
        params.put("merk", edtmerk.getText().toString());
        params.put("warna", edtwarna.getText().toString());
        params.put("harga", edtharga.getText().toString());
        params.put("gambar", strgamabr);

        pDialog.setMessage("Mohon Tunggu.....");
        showDialog();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT,BaseURL.updatehijab + _id, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            String strMsg = response.getString("msg");
                            boolean status= response.getBoolean("error");
                            if(status == false){
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                Intent i = new Intent(EditJilbabDanHapusActivity.this, ActivityDataJilbab.class);
                                startActivity(i);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
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

    public void hapusData(){

        pDialog.setMessage("Mohon Tunggu.....");
        showDialog();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.DELETE,BaseURL.deletehijab + _id, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            String strMsg = response.getString("msg");
                            boolean status= response.getBoolean("error");
                            if(status == false){
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                Intent i = new Intent(EditJilbabDanHapusActivity.this, ActivityDataJilbab.class);
                                startActivity(i);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
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
        if(!pDialog.isShowing()){
            pDialog.show();
        }
    }

    private void hideDialog(){
        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
    }
}