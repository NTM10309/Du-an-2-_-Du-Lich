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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
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

import thu.bean.TinTuc;
import thu.singleton.MySingleton;

public class XemNguoiDungActivity extends AppCompatActivity {
    String username, username1;
    ImageView anhbia;
    ListView lvTT;
    ArrayList<TinTuc> tintuctList;
    FunDapter<TinTuc> adapter;
    SwipeRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_nguoi_dung);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        username = intent.getStringExtra("nguoidung");
        username1 = intent.getStringExtra("username");
        Toast.makeText(getApplicationContext(),username, Toast.LENGTH_SHORT).show();

        lvTT = (ListView)findViewById(R.id.lvTinTuc);
        loadData();
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                loadData();
            }
        });
    }
    private void loadData() {

        String url = "http://pmthu.esy.es/dulichviet/checktintuc.php";


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // if(response.contains("thanhcong")){
                Toast.makeText(XemNguoiDungActivity.this, "ok", Toast.LENGTH_SHORT).show();
                try {
                    tintuctList = new JsonConverter<TinTuc>().toArrayList(response, TinTuc.class);
                   // Toast.makeText(XemNguoiDungActivity.this, "llllllll"+tintuctList, Toast.LENGTH_SHORT).show();
                    BindDictionary<TinTuc> dictionary = new BindDictionary<>();
                    dictionary.addStringField(R.id.tvusername, new StringExtractor<TinTuc>() {
                        @Override
                        public String getStringValue(TinTuc tt, int position) {
                            return tt.getUsername();
                        }
                    });

                    dictionary.addStringField(R.id.tvngaydang, new StringExtractor<TinTuc>() {
                        @Override
                        public String getStringValue(TinTuc tt, int position) {
                            return tt.getNgaydang();
                        }
                    });

                    dictionary.addStringField(R.id.tvmota, new StringExtractor<TinTuc>() {
                        @Override
                        public String getStringValue(TinTuc tt, int position) {
                            return tt.getMota();
                        }
                    });

                    dictionary.addStringField(R.id.tvthich, new StringExtractor<TinTuc>() {
                        @Override
                        public String getStringValue(TinTuc tt, int position) {
                            return "" + tt.getThich()+" Bình luận";
                        }
                    });

                    dictionary.addStringField(R.id.tvxem, new StringExtractor<TinTuc>() {
                        @Override
                        public String getStringValue(TinTuc tt, int position) {
                            return "" + tt.getXem()+" Lượt xem";
                        }
                    });

                    dictionary.addDynamicImageField(R.id.imanh, new StringExtractor<TinTuc>() {
                                @Override
                                public String getStringValue(TinTuc tt, int position) {
                                    String path = "http://pmthu.esy.es/dulichviet/images/" + tt.getHinhanh() + "";
                                    return path;
                                }
                            },
                            new DynamicImageLoader() {
                                @Override
                                public void loadImage(String url, ImageView imageView) {
                                    Picasso.with(XemNguoiDungActivity.this)
                                            .load(url)
                                            .placeholder(R.drawable.hodler)
                                            .error(R.drawable.ic_launcher)
                                            .into(imageView);
                                }
                            }
                    );

                    dictionary.addDynamicImageField(R.id.imuser, new StringExtractor<TinTuc>() {
                                @Override
                                public String getStringValue(TinTuc tt, int position) {
                                    String path = "http://pmthu.esy.es/dulichviet/images/" + tt.getBinhluan() + "";
                                    return path;
                                }
                            },
                            new DynamicImageLoader() {
                                @Override
                                public void loadImage(String url, ImageView imageView) {
                                    Picasso.with(XemNguoiDungActivity.this)
                                            .load(url)
                                            .placeholder(R.drawable.hodler)
                                            .error(R.drawable.ic_launcher)

                                            .into(imageView);

                                }
                            }
                    );

                    adapter = new FunDapter<>(
                            XemNguoiDungActivity.this,
                            tintuctList,
                            R.layout.tintuc_layout,
                            dictionary

                    );
                    lvTT.setAdapter(adapter);
                } catch (IllegalStateException | JsonSyntaxException exception) {
                }

                lvTT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent in = new Intent(getApplicationContext(), ChiTietActivity.class);
                        TinTuc tinTuc = tintuctList.get(position);

                        in.putExtra("xem",tinTuc.getMand());
                        in.putExtra("tintuc", tinTuc);
                        in.putExtra("username", username1);
                        startActivity(in);
                    }

                });

                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }

                //  }else {
                // Toast.makeText(QuanlyFragment.this.getActivity(), "That bai", Toast.LENGTH_SHORT).show();
                // }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Toast.makeText(QuanlyFragment.this.getActivity(), "Có lỗi", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("name", username);
                return parms;
            }
        };//ket thuc stringresquet
        MySingleton.getInstance(XemNguoiDungActivity.this).addToRequestQueue(stringRequest);
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

        Intent in = new Intent(XemNguoiDungActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("username", username1);
        in.putExtra("MyPackage", bundle);

        startActivity(in);
        finish();
    }
}
