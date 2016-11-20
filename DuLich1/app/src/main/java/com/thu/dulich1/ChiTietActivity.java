package com.thu.dulich1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.android.json.JsonConverter;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import thu.bean.BinhLuan;
import thu.bean.SoTay;
import thu.bean.TinTuc;
import thu.singleton.MySingleton;

public class ChiTietActivity extends AppCompatActivity {
    ImageView imanh, bavatar;
    TextView tvmota, tvchititet, tvngay, buser, bnoidung, bngaydang;
    EditText comment;
    TinTuc tintuc;
    String username;
    String view;
    int xem = 0;
    int idnd = 0;
    Button addcomment;
    ListView lvbinhluan;

    ArrayList<BinhLuan> binhluanList;
    FunDapter<BinhLuan> adapter;
    SwipeRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        Toast.makeText(getApplicationContext(), "ssssssssssssss"+username, Toast.LENGTH_SHORT).show();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imanh = (ImageView) findViewById(R.id.imanh);
        tvmota = (TextView) findViewById(R.id.tvmota);
        tvchititet = (TextView) findViewById(R.id.tvchitiet);
        tvngay = (TextView) findViewById(R.id.tvngay);

//        bavatar = (ImageView)findViewById(R.id.bavatar);
//        comment = (EditText)findViewById(R.id.comment);
//        addcomment = (Button)findViewById(R.id.addcomment);


        if (getIntent().getSerializableExtra("tintuc") != null) {


            tintuc = (TinTuc) getIntent().getSerializableExtra("tintuc");
            tvmota.setText(tintuc.getMota());
            tvchititet.setText(tintuc.getChitiet());
            tvngay.setText(tintuc.getNgaydang());

                String path = "http://pmthu.esy.es/dulichviet/images/" + tintuc.getHinhanh() + "";
                Picasso.with(getApplicationContext()).load(path).into(imanh);

                view = tvngay.getText().toString();
                xem  = tintuc.getXem();
                idnd = tintuc.getMand();
               //username = tintuc.getUsername();
            String lat  = tintuc.getLat();
            String lag  = tintuc.getLang();

           Toast.makeText(getApplicationContext(), ""+lat+" : "+lag, Toast.LENGTH_SHORT).show();

            luotxem();
        }



    }

    private void luotxem(){
        String url = "http://pmthu.esy.es/dulichviet/view.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("thanhcong")) {
                    Toast.makeText(getApplicationContext(), " thanh cong!", Toast.LENGTH_SHORT).show();
//                    Intent in = new Intent(ResgiterActivity.this,LoginActivity.class);
//                    startActivity(in);
                } else {
                    Toast.makeText(getApplicationContext(), "  thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Có lỗi", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("mand", "" + idnd);
                parms.put("xem", "" + xem);
                return parms;
            }
        };//ket thuc stringresquet
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent in = new Intent(ChiTietActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("username", username);
        in.putExtra("MyPackage", bundle);

        startActivity(in);
        finish();
    }





}
