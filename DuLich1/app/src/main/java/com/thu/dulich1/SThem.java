package com.thu.dulich1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import thu.singleton.MySingleton;

public class SThem extends AppCompatActivity {
    EditText eddiadiem, ednoidung;
    String username = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sthem);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        Toast.makeText(getApplicationContext(), username, Toast.LENGTH_SHORT).show();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "dgdfgdfg"+username, Toast.LENGTH_SHORT).show();
                themsotay();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eddiadiem = (EditText)findViewById(R.id.eddiadiem);
        ednoidung = (EditText)findViewById(R.id.ednoidung);
    }
    private void themsotay(){
        String url = "http://pmthu.esy.es/dulichviet/insertsotay.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("thanhcong")) {
//                    Intent in = new Intent(ResgiterActivity.this,LoginActivity.class);
//                    startActivity(in);
                    Toast.makeText(getApplicationContext(), " ok con de!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), " Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
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
                parms.put("username", username);
                parms.put("diadiem", eddiadiem.getText().toString());
                parms.put("noidung", ednoidung.getText().toString());
                parms.put("ngaytao", "" + ngayDang());
                return parms;
            }
        };//ket thuc stringresquet
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }
    public String ngayDang(){
        Date date = new Date();
        SimpleDateFormat formats = new SimpleDateFormat("dd/MM/yyyy");
        String daynews=	formats.format(date);
        return daynews;
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

        Intent in = new Intent(SThem.this, MainActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("username", username);
        in.putExtra("MyPackage", bundle);

        startActivity(in);
        finish();
    }


}

