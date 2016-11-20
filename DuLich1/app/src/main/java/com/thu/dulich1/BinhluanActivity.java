package com.thu.dulich1;

import android.content.Intent;
import android.nfc.FormatException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import thu.bean.BinhLuan;
import thu.bean.TinTuc;
import thu.singleton.MySingleton;

public class BinhluanActivity extends AppCompatActivity {
    ImageView addcomment, bavatar;
    EditText comment;
    TinTuc tintuc;
    String username, view, avat;
    int idnd = 0;
    ListView lvbinhluan;
    StringRequest stringRequest;
    ArrayList<BinhLuan> binhluanList;
    FunDapter<BinhLuan> adapter;
    SwipeRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binhluan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        avat = intent.getStringExtra("avat");
        Toast.makeText(getApplicationContext(), avat, Toast.LENGTH_SHORT).show();
        bavatar = (ImageView)findViewById(R.id.bavatar);
        comment = (EditText)findViewById(R.id.comment);
        addcomment = (ImageView)findViewById(R.id.addcomment);

        if (getIntent().getSerializableExtra("tintuc") != null) {

            tintuc = (TinTuc) getIntent().getSerializableExtra("tintuc");
            idnd = tintuc.getMand();
        }

        addcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment();
                loadData();
                updateLikes();
                comment.setText("");


            }
        });
        loadData();
        lvbinhluan = (ListView)findViewById(R.id.lvbinhluan);
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                loadData();
                updateLikes();
            }
        });

        registerForContextMenu(lvbinhluan);

    }

    private void addComment(){
        Toast.makeText(getApplicationContext(), "" +idnd, Toast.LENGTH_SHORT).show();
        String url = "http://pmthu.esy.es/dulichviet/comment.php";

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

                Toast.makeText(getApplicationContext(), "Có lỗiiii", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("noidung", comment.getText().toString());
                parms.put("username", username);
                parms.put("ngaybl", "" + ngayDang());
                parms.put("mand", "" + idnd);
                parms.put("avat", avat);
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


    public void loadData() {

            String url = "http://pmthu.esy.es/dulichviet/loadbinhluan.php";


            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        binhluanList = new JsonConverter<BinhLuan>().toArrayList(response, BinhLuan.class);
                        BindDictionary<BinhLuan> dictionary = new BindDictionary<>();
                        dictionary.addStringField(R.id.buser, new StringExtractor<BinhLuan>() {
                            @Override
                            public String getStringValue(BinhLuan tt, int position) {
                                //Toast.makeText(SoTayFragment.this.getActivity(), "tt " +tt.getMaso(),Toast.LENGTH_SHORT).show();
                                return tt.getUsername();
                            }
                        });

                        dictionary.addStringField(R.id.bnoidung, new StringExtractor<BinhLuan>() {
                            @Override
                            public String getStringValue(BinhLuan tt, int position) {
                                return tt.getNoidung();
                            }
                        });

                        dictionary.addStringField(R.id.bngaydang, new StringExtractor<BinhLuan>() {
                            @Override
                            public String getStringValue(BinhLuan tt, int position) {
                                return tt.getNgaybl();
                            }
                        });
                        dictionary.addDynamicImageField(R.id.bavatar, new StringExtractor<BinhLuan>() {
                                    @Override
                                    public String getStringValue(BinhLuan tt, int position) {
                                        String path = "http://pmthu.esy.es/dulichviet/images/"+ tt.getAvat() + "";
                                        //String path = null;

                                        return path ;
                                    }
                                },
                                new DynamicImageLoader() {
                                    @Override
                                    public void loadImage(String url, ImageView imageView) {
                                        Picasso.with(BinhluanActivity.this)
                                                .load(url)
                                                .placeholder(R.drawable.hodler)
                                                .error(R.drawable.ic_launcher)

                                                .into(imageView);

                                    }
                                }
                        );

                        adapter = new FunDapter<>(
                                BinhluanActivity.this,
                                binhluanList,
                                R.layout.binhluan_layout,
                                dictionary

                        );

                        lvbinhluan.setAdapter(adapter);


                    } catch(IllegalStateException | JsonSyntaxException exception) {

                    }
                    if (refreshLayout.isRefreshing()) {
                        refreshLayout.setRefreshing(false);
                    }
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
                    // parms.put("username", username);
                    parms.put("mand", "" + idnd);
                    return parms;
                }
            };//ket thuc stringresquet
            MySingleton.getInstance(BinhluanActivity.this).addToRequestQueue(stringRequest);

    }

    private void updateLikes(){
        String url = "http://pmthu.esy.es/dulichviet/update_binhluan.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,

                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                if(response.contains("thanhcong")){
                    Toast.makeText(BinhluanActivity.this," cap nhat thanh cong", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(BinhluanActivity.this," cap nhat khong thanh cong", Toast.LENGTH_SHORT).show();
                }
                } catch(IllegalStateException | JsonSyntaxException exception) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> parms = new HashMap<>();
                parms.put("mand", ""+idnd);
                parms.put("thich",""+ binhluanList.size());

                return parms;
            }
        };//ket thuc stringresquet



        MySingleton.getInstance(BinhluanActivity.this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, R.id.menuShowdelete, Menu.NONE, "Xóa bình luận!");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menuShowdelete){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

            BinhLuan selectbinhluan = binhluanList.get(info.position);
            deleteItem(selectbinhluan.getMacom());
            binhluanList.remove(info.position);
           // Toast.makeText(getApplicationContext(), "iiiiiiiiiiiii"+selectbinhluan.getMand(), Toast.LENGTH_SHORT).show();

            adapter.notifyDataSetChanged();


        }
        return super.onContextItemSelected(item);
    }


    public void deleteItem(final int id) {
        String url = "http://pmthu.esy.es/dulichviet/delete_binhluan.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("thanhcong")) {
                    Toast.makeText(getApplicationContext(), "Xóa bình luận thành công", Toast.LENGTH_SHORT).show();
                    updateLikes();
                } else {
                    Toast.makeText(getApplicationContext(), "Bạn không có quyền xóa bình luận này!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "loi", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("macom",""+ id);
                params.put("username", username);

                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent in = new Intent(BinhluanActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("username", username);
        in.putExtra("MyPackage", bundle);

        startActivity(in);
        finish();
    }

}
