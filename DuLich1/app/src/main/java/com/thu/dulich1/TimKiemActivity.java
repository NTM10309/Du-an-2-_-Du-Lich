package com.thu.dulich1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import thu.bean.SoTay;
import thu.bean.TinTuc;
import thu.singleton.MySingleton;

public class TimKiemActivity extends AppCompatActivity {
    EditText edtk,ednd;
    ListView lvTT,lvND;
    ImageView goi,goinoidung;
    ArrayList<TinTuc> tintuctList;
    FunDapter<TinTuc> adapter;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent callerIntent=getIntent();
        Bundle packageFromCaller = callerIntent.getBundleExtra("MyPackage");
        username = packageFromCaller.getString("username");
        final TabHost tab = (TabHost) findViewById(android.R.id.tabhost);
        tab.setup();

        TabHost.TabSpec spec;
        // Tạo tab1
        spec = tab.newTabSpec("t1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Tìm người dùng");

        tab.addTab(spec);
        // Tạo tab2
        spec = tab.newTabSpec("t2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Tìm tin tức");
        tab.addTab(spec);
        tab.setCurrentTab(0);
        // Thiết lập tab mặc định được chọn ban đầu là tab 0
        tab.setCurrentTab(0);
        edtk = (EditText)findViewById(R.id.tim_noid);
        lvTT = (ListView) findViewById(R.id.lvTT);
        goi = (ImageView) findViewById(R.id.goi);

        ednd = (EditText)findViewById(R.id.tim_noidung);
        lvND = (ListView) findViewById(R.id.lvND);
        goinoidung = (ImageView) findViewById(R.id.goinoidung);

        goi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timnguoidung();
            }
        });
        goinoidung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timtintuc();
            }
        });

    }
    private void timnguoidung(){
        String url = "http://pmthu.esy.es/dulichviet/timnguoidung.php";


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // if(response.contains("thanhcong")){
                Toast.makeText(TimKiemActivity.this, "ok", Toast.LENGTH_SHORT).show();
                try {
                tintuctList = new JsonConverter<TinTuc>().toArrayList(response, TinTuc.class);
                BindDictionary<TinTuc> dictionary = new BindDictionary<>();
                dictionary.addStringField(R.id.tvnguoi, new StringExtractor<TinTuc>() {
                    @Override
                    public String getStringValue(TinTuc tt, int position) {
                        //Toast.makeText(SoTayFragment.this.getActivity(), "tt " +tt.getMaso(),Toast.LENGTH_SHORT).show();
                        return tt.getUsername();
                    }
                });

                dictionary.addDynamicImageField(R.id.imnguoi, new StringExtractor<TinTuc>() {
                            @Override
                            public String getStringValue(TinTuc tt, int position) {
                                String path = "http://pmthu.esy.es/dulichviet/images/" + tt.getBinhluan() + "";
                                return path;
                            }
                        },
                        new DynamicImageLoader() {
                            @Override
                            public void loadImage(String url, ImageView imageView) {
                                Picasso.with(TimKiemActivity.this)
                                        .load(url)
                                        .placeholder(R.drawable.hodler)
                                        .error(R.drawable.ic_launcher)

                                        .into(imageView);

                            }
                        }
                );

                adapter = new FunDapter<>(
                        TimKiemActivity.this,
                        tintuctList,
                        R.layout.custom_timnguoi_layout,
                        dictionary

                );

                lvTT.setAdapter(adapter);
                } catch (IllegalStateException | JsonSyntaxException exception) {
                    Toast.makeText(getApplicationContext(),"Không tìm thấy nội dung.",Toast.LENGTH_SHORT).show();
                }
                lvTT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(TimKiemActivity.this, XemNguoiDungActivity.class);
                        TinTuc tinTuc = tintuctList.get(position);
                        intent.putExtra("nguoidung", tinTuc.getUsername());
                        intent.putExtra("username", username);
                        startActivity(intent);
                    }

                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Toast.makeText(QuanlyFragment.this.getActivity(), "Có lỗi", Toast.LENGTH_SHORT).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("username", edtk.getText().toString());
                return parms;
            }
        };//ket thuc stringresquet
        MySingleton.getInstance(TimKiemActivity.this).addToRequestQueue(stringRequest);

    }
    private void timtintuc(){
        String url = "http://pmthu.esy.es/dulichviet/timtintuc.php";


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // if(response.contains("thanhcong")){
                Toast.makeText(TimKiemActivity.this, "ok", Toast.LENGTH_SHORT).show();
                try {
                    tintuctList = new JsonConverter<TinTuc>().toArrayList(response, TinTuc.class);
                    BindDictionary<TinTuc> dictionary = new BindDictionary<>();
                    dictionary.addStringField(R.id.tvusername_tk, new StringExtractor<TinTuc>() {
                        @Override
                        public String getStringValue(TinTuc tt, int position) {

                            return tt.getUsername();
                        }
                    });

                    dictionary.addDynamicImageField(R.id.imuser_tk, new StringExtractor<TinTuc>() {
                                @Override
                                public String getStringValue(TinTuc tt, int position) {
                                    String path = "http://192.168.56.1:8080/dulichviet/images/" + tt.getBinhluan() + "";
                                    return path;
                                }
                            },
                            new DynamicImageLoader() {
                                @Override
                                public void loadImage(String url, ImageView imageView) {
                                    Picasso.with(TimKiemActivity.this)
                                            .load(url)
                                            .placeholder(R.drawable.hodler)
                                            .error(R.drawable.ic_launcher)

                                            .into(imageView);

                                }
                            }
                    );
                    dictionary.addStringField(R.id.tvmota_tk, new StringExtractor<TinTuc>() {
                        @Override
                        public String getStringValue(TinTuc tt, int position) {

                            return tt.getMota();
                        }
                    });
                    adapter = new FunDapter<>(
                            TimKiemActivity.this,
                            tintuctList,
                            R.layout.custom_timnoidung_layout,
                            dictionary

                    );
                    dictionary.addDynamicImageField(R.id.imanh_tk, new StringExtractor<TinTuc>() {
                                @Override
                                public String getStringValue(TinTuc tt, int position) {
                                    String path = "http://pmthu.esy.es/dulichviet/images/" + tt.getHinhanh() + "";
                                    return path;
                                }
                            },
                            new DynamicImageLoader() {
                                @Override
                                public void loadImage(String url, ImageView imageView) {
                                    Picasso.with(TimKiemActivity.this)
                                            .load(url)
                                            .placeholder(R.drawable.hodler)
                                            .error(R.drawable.ic_launcher)
                                            .into(imageView);
                                }
                            }
                    );
                    lvND.setAdapter(adapter);
                } catch (IllegalStateException | JsonSyntaxException exception) {
                    Toast.makeText(getApplicationContext(),"Không tìm thấy nội dung.",Toast.LENGTH_SHORT).show();
                }
                lvND.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent in = new Intent(TimKiemActivity.this, ChiTietActivity.class);
                        TinTuc tinTuc = tintuctList.get(position);
                        in.putExtra("xem",tinTuc.getMand());
                        in.putExtra("tintuc", tinTuc);
                        in.putExtra("username", username);
                        startActivity(in);

                    }

                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Toast.makeText(QuanlyFragment.this.getActivity(), "Có lỗi", Toast.LENGTH_SHORT).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("username", ednd.getText().toString());
                return parms;
            }
        };//ket thuc stringresquet
        MySingleton.getInstance(TimKiemActivity.this).addToRequestQueue(stringRequest);

    }

}
