package com.thu.dulich1;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.amigold.fundapter.interfaces.DynamicImageLoader;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonSyntaxException;
import com.kosalgeek.android.json.JsonConverter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import thu.bean.ThoiTiet;
import thu.bean.TinTuc;
import thu.singleton.MySingleton;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class ThoiTietActivity extends AppCompatActivity {
    String username;
    String key = "599c422e3d0124ac57bbb499bd1b0c83";
    String diadiem="danang";
    EditText edNhap;
    ImageView im,xem;
    String doc_c,icon,mota,wt,sucgio,apsuat,doam,tcao,tthap;
    TextView nhietdo,tvmota,tvwt,tvsucgio,tvapsuat,tvdoam,tvtcao,tvtthap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thoi_tiet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent callerIntent=getIntent();
        Bundle packageFromCaller = callerIntent.getBundleExtra("MyPackage");
        username = packageFromCaller.getString("username");
        Toast.makeText(getApplicationContext(),username,Toast.LENGTH_SHORT).show();
        edNhap = (EditText)findViewById(R.id.hi);
        xem = (ImageView)findViewById(R.id.hii);


        im = (ImageView)findViewById(R.id.condIcon);
        nhietdo=  (TextView)findViewById(R.id.nhietdo) ;
        tvmota =  (TextView)findViewById(R.id.mota) ;
        tvwt=  (TextView)findViewById(R.id.wt) ;
        tvsucgio=  (TextView)findViewById(R.id.sucgio) ;
        tvapsuat=  (TextView)findViewById(R.id.apsuat) ;
        tvdoam=  (TextView)findViewById(R.id.doam) ;
        tvtcao=  (TextView)findViewById(R.id.tcao) ;
        tvtthap = (TextView)findViewById(R.id.tthap) ;

//        TranslateAnimation animation = new TranslateAnimation(-900.0f, 1000.0f,
//                0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
//        animation.setDuration(3000);  // animation duration
//        animation.setRepeatCount(5);  // animation repeat count
//        animation.setRepeatMode(2);   // repeat animation (left to right, right to left )
//        //animation.setFillAfter(true);
//
//        m1.startAnimation(animation);  // start animation
        im.setVisibility(View.GONE);
        thoitiet();
        xem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diadiem = edNhap.getText().toString();
                thoitiet();

            }
        });


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

        Intent in = new Intent(ThoiTietActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("username",username);
        in.putExtra("MyPackage", bundle);
        startActivity(in);
        finish();
    }

    private void thoitiet(){


        String url = "http://api.openweathermap.org/data/2.5/weather?q="+diadiem+"&appid="+key;


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray("weather");
                    JSONObject collegeData = result.getJSONObject(0);

                    JSONObject jsonObject1 = new JSONObject(response);
                    JSONObject collegeData1 = jsonObject1.getJSONObject("wind");

                    JSONObject jsonObject2 = new JSONObject(response);
                    JSONObject collegeData2 = jsonObject2.getJSONObject("main");

                    JSONObject jsonObject3 = new JSONObject(response);
                    JSONObject collegeData3 = jsonObject3.getJSONObject("sys");

                    icon = collegeData.getString("icon");
                    wt = collegeData.getString("main");
                    mota = collegeData.getString("description");
                    sucgio = collegeData1.getString("speed");

                    doc_c = collegeData2.getString("temp");
                    double thu = Double.parseDouble(doc_c);
                    int k = (int)Math.ceil((thu-273.15));

                    tcao = collegeData2.getString("temp_min");
                    tthap = collegeData2.getString("temp_max");
                    double tc = Double.parseDouble(tcao);
                    int ktc = (int)Math.ceil((tc-273.15));
                    double tt = Double.parseDouble(tthap);
                    int ktt = (int)Math.ceil((tt-273.15));

                    apsuat =collegeData2.getString("pressure");
                    doam = collegeData2.getString("humidity");

                     String path = "http://openweathermap.org/img/w/" + icon+ ".png";
                    im.setVisibility(View.VISIBLE);
                    Picasso.with(getApplicationContext()).load(path).into(im);

                    nhietdo.setText(""+k);
                    tvmota.setText(mota);
                    tvwt.setText(wt);
                    tvsucgio.setText("Sức gió: "+sucgio+" m/s");
                    tvapsuat.setText("Ap suất: "+apsuat+" hpa");
                    tvdoam.setText("Độ ẩm: "+doam+" %");
                    tvtcao.setText(""+ktc);
                    tvtthap.setText(""+ktt);

                    //tvmatdomay.setText("Mây: "+matdomay);
                }catch(JSONException e){
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        ;//ket thuc stringresquet
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


}
