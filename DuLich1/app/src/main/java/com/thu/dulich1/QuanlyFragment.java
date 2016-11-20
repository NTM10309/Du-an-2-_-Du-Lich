package com.thu.dulich1;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class QuanlyFragment extends Fragment {

    String username;
    ListView lvTT;
    ArrayList<TinTuc> tintuctList;
   FunDapter<TinTuc> adapter;
    SwipeRefreshLayout refreshLayout;
    ProgressBar progressBar;
    public QuanlyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_quanly, container, false);

        registerForContextMenu(view.findViewById(R.id.lvTinTuc));
        progressBar = (ProgressBar)view.findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        Bundle b = getArguments();
        username = b.getString("doctor_id");
        loadData();
        lvTT = (ListView)view.findViewById(R.id.lvTinTuc);

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
        String url = "http://pmthu.esy.es/dulichviet/checktintuc.php";


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // if(response.contains("thanhcong")){
                    Toast.makeText(QuanlyFragment.this.getActivity(), "ok", Toast.LENGTH_SHORT).show();
                try{
                tintuctList = new JsonConverter<TinTuc>().toArrayList(response, TinTuc.class);
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
                                //String path = null;

                                return path;
                            }
                        },
                        new DynamicImageLoader() {
                            @Override
                            public void loadImage(String url, ImageView imageView) {
                                Picasso.with(QuanlyFragment.this.getActivity())
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
                                    String path = "http://pmthu.esy.es/dulichviet/images/"+ tt.getBinhluan() + "";
                                    //String path = null;

                                    return path ;
                                }
                            },
                            new DynamicImageLoader() {
                                @Override
                                public void loadImage(String url, ImageView imageView) {
                                    Picasso.with(QuanlyFragment.this.getActivity())
                                            .load(url)
                                            .placeholder(R.drawable.hodler)
                                            .error(R.drawable.ic_launcher)

                                            .into(imageView);

                                }
                            }
                    );

                adapter = new FunDapter<>(
                        QuanlyFragment.this.getActivity(),
                        tintuctList,
                        R.layout.tintuc_layout,
                        dictionary

                );
                lvTT.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                } catch(IllegalStateException | JsonSyntaxException exception) {

                }
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
        MySingleton.getInstance(QuanlyFragment.this.getActivity()).addToRequestQueue(stringRequest);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, R.id.menuShowupdate, Menu.NONE, "Chỉnh sửa bài viêt!");
        menu.add(Menu.NONE, R.id.menuShowdelete, Menu.NONE, "Xóa bài viết!");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuShowupdate:
                AdapterView.AdapterContextMenuInfo infoo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

                TinTuc selecttintucc = tintuctList.get(infoo.position);

                Bundle args = new Bundle();
               args.putString("username", username);
                args.putString("tintuc", "" + selecttintucc.getMand());
                CapNhatFragment aa = new CapNhatFragment();
                aa.setArguments(args);
               FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.content_main, aa, aa.getTag()).commit();

                return true;
            case R.id.menuShowdelete:

                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

                TinTuc selecttintuc = tintuctList.get(info.position);
                deleteItem(selecttintuc.getMand());
                tintuctList.remove(info.position);
                Toast.makeText(QuanlyFragment.this.getActivity(),""+selecttintuc.getMand(), Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }
    public void deleteItem(final int id){
        String url = "http://pmthu.esy.es/dulichviet/delete.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("thanhcong")){
                    Toast.makeText(QuanlyFragment.this.getActivity()," xoa thanh cong", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(QuanlyFragment.this.getActivity()," xoa khong thanh cong", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              // Toast.makeText(QuanlyFragment.this.getActivity(),"loi upload", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("id", id +"");

                return params;
            }
        };
        MySingleton.getInstance(QuanlyFragment.this.getActivity()).addToRequestQueue(stringRequest);
    }

}
