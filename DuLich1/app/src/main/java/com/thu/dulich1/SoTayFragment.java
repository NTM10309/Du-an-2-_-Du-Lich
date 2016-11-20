package com.thu.dulich1;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.amigold.fundapter.interfaces.DynamicImageLoader;
import com.amigold.fundapter.interfaces.ItemClickListener;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.android.json.JsonConverter;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import thu.bean.SoTay;
import thu.bean.TinTuc;
import thu.singleton.MySingleton;


/**
 * A simple {@link Fragment} subclass.
 */
public class SoTayFragment extends Fragment {
    ProgressBar progressBar;
    String a;
    ListView lvST;
    Button btadd;
    ArrayList<SoTay> sotayList;
    FunDapter<SoTay> adapter;
    SwipeRefreshLayout refreshLayout;
    public SoTayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_so_tay, container, false);
        progressBar = (ProgressBar)view.findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        registerForContextMenu(view.findViewById(R.id.lvSoTay));
        Bundle b = getArguments();
        a = b.getString("doctor_id");
        lvST = (ListView)view.findViewById(R.id.lvSoTay);
        Toast.makeText(SoTayFragment.this.getActivity(), a, Toast.LENGTH_SHORT).show();

        btadd = (Button)view.findViewById(R.id.add);

        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), SThem.class);
                in.putExtra("username",a);
                startActivity(in);
            }
        });

        loadData();
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                loadData();
            }
        });
        return view;
    }

    public void loadData(){
        progressBar.setVisibility(View.VISIBLE);
        String url = "http://pmthu.esy.es/dulichviet/checksotay.php";


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // if(response.contains("thanhcong")){
                Toast.makeText(SoTayFragment.this.getActivity(), "ok", Toast.LENGTH_SHORT).show();

                sotayList = new JsonConverter<SoTay>().toArrayList(response, SoTay.class);
                BindDictionary<SoTay> dictionary = new BindDictionary<>();
                dictionary.addStringField(R.id.stt, new StringExtractor<SoTay>() {
                    @Override
                    public String getStringValue(SoTay tt, int position) {
                        //Toast.makeText(SoTayFragment.this.getActivity(), "tt " +tt.getMaso(),Toast.LENGTH_SHORT).show();
                        return "" + tt.getMaso();
                    }
                });

                dictionary.addStringField(R.id.diadiem, new StringExtractor<SoTay>() {
                    @Override
                    public String getStringValue(SoTay tt, int position) {
                        return tt.getDiadiem();
                    }
                });

                dictionary.addStringField(R.id.noidung, new StringExtractor<SoTay>() {
                    @Override
                    public String getStringValue(SoTay tt, int position) {
                        return tt.getNoidung();
                    }
                });
                dictionary.addStringField(R.id.ngaytao, new StringExtractor<SoTay>() {
                    @Override
                    public String getStringValue(SoTay tt, int position) {
                        return tt.getNgaytao();
                    }
                });





                adapter = new FunDapter<>(
                        SoTayFragment.this.getActivity(),
                        sotayList,
                        R.layout.sotay_layout,
                        dictionary

                );

                lvST.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }

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
                parms.put("name", a);
                return parms;
            }
        };//ket thuc stringresquet
        MySingleton.getInstance(SoTayFragment.this.getActivity()).addToRequestQueue(stringRequest);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, R.id.menuShowdelete, Menu.NONE, "Xóa sổ tay!");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuShowdelete:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

                SoTay selectsotay = sotayList.get(info.position);
                deleteItem(selectsotay.getMaso());
                sotayList.remove(info.position);
                Toast.makeText(SoTayFragment.this.getActivity(),""+selectsotay.getMaso(), Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }
    public void deleteItem(final int id) {
        String url = "http://pmthu.esy.es/dulichviet/deletesotay.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("thanhcong")) {
                    Toast.makeText(SoTayFragment.this.getActivity(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SoTayFragment.this.getActivity(), "Xóa không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(QuanlyFragment.this.getActivity(),"loi upload", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("id", id + "");

                return params;
            }
        };
        MySingleton.getInstance(SoTayFragment.this.getActivity()).addToRequestQueue(stringRequest);
    }
}
