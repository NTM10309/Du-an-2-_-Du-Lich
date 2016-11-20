package com.thu.dulich1;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.clans.fab.FloatingActionMenu;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.kosalgeek.android.photoutil.PhotoLoader;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import id.gits.baso.BasoProgressView;
import thu.bean.CircleImageView;
import thu.singleton.MySingleton;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView lv, email;
    View hView;
    String to,a,kq,i,selectedPhoto;
    EditText ed,ed2;


    NavigationView navigationView;
    ImageView avatar;
    CircleImageView imv;

    FloatingActionMenu materialDesignFAM;
    com.github.clans.fab.FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;

    GalleryPhoto galleryPhoto;
    final int GALLERY_REQUEST = 22131;
    Bitmap bitmap;
    File file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // lvTT = (ListView)findViewById(R.id.lvTinTuc);
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("doctor_id",a);
                args.putString("avatar",to);
                AddNewsActivityFragment aa = new AddNewsActivityFragment();
                aa.setArguments(args);
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.content_main, aa, aa.getTag()).commit();

            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("doctor_id",a);
                args.putString("avatar",to);
                ThemFragment aa = new ThemFragment();
                aa.setArguments(args);
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.content_main, aa, aa.getTag()).commit();




            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("doctor_id",a);
                QuanlyFragment aa = new QuanlyFragment();
                aa.setArguments(args);
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.content_main, aa, aa.getTag()).commit();

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        hView =  navigationView.inflateHeaderView(R.layout.nav_header_main);
        lv = (TextView) hView.findViewById(R.id.t);
        email = (TextView) hView.findViewById(R.id.tvemail);

        avatar = (ImageView)hView.findViewById(R.id.avatar);

        budle();
        loadAvatar();


        AddNewsActivityFragment aa = new AddNewsActivityFragment();

        Bundle args = new Bundle();
        args.putString("doctor_id",a);
        args.putString("avatar",to);
        aa.setArguments(args);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, aa, aa.getTag()).commit();



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        menu = navigationView.getMenu();
        if("thu".equals(a)){
            menu.findItem(R.id.quantri).setEnabled(true);
        }else{
            menu.findItem(R.id.quantri).setEnabled(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.timkiem) {
            Intent in = new Intent(MainActivity.this, TimKiemActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("username", a);
            in.putExtra("MyPackage", bundle);
            startActivity(in);
        }
        else if (id == R.id.nav_camera) {
            Bundle args = new Bundle();
            args.putString("avatar",to);
            args.putString("doctor_id", a);
            AddNewsActivityFragment aa = new AddNewsActivityFragment();
            aa.setArguments(args);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, aa, aa.getTag()).commit();
        } else if (id == R.id.nav_gallery) {
            Bundle args = new Bundle();
            args.putString("doctor_id",a);
            args.putString("avatar",to);
            ThemFragment aa = new ThemFragment();
            aa.setArguments(args);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, aa, aa.getTag()).commit();
        } else if (id == R.id.nav_slideshow) {
            Bundle args = new Bundle();
            args.putString("doctor_id",a);
            QuanlyFragment aa = new QuanlyFragment();
            aa.setArguments(args);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, aa, aa.getTag()).commit();
        } else if (id == R.id.node) {
            Bundle args = new Bundle();
            args.putString("doctor_id",a);
            SoTayFragment aa = new SoTayFragment();
            aa.setArguments(args);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, aa, aa.getTag()).commit();
        } else if (id == R.id.anhdaidien) {
//            Bundle args = new Bundle();
//            args.putString("doctor_id",a);
//            AnhCaNhanFragment aa = new AnhCaNhanFragment();
//            aa.setArguments(args);
//            FragmentManager manager = getSupportFragmentManager();
//            manager.beginTransaction().replace(R.id.content_main, aa, aa.getTag()).commit();
            anhcanhan();
        } else if (id == R.id.doimk) {
           doimatkhau();
        } else if (id == R.id.dangxuat) {
            Intent in = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(in);
        }else if (id == R.id.thoitiet) {
            Intent in = new Intent(MainActivity.this, ThoiTietActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("username", a);
            in.putExtra("MyPackage", bundle);
            startActivity(in);
        }else if (id == R.id.dieukhoan) {
            Intent in = new Intent(MainActivity.this, DieuKhoanActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("username", a);
            in.putExtra("MyPackage", bundle);
            startActivity(in);
        }else if (id == R.id.lienhe) {
            Intent in = new Intent(MainActivity.this, LienHeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("username", a);
            in.putExtra("MyPackage", bundle);
            startActivity(in);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private void budle(){
        Intent callerIntent = getIntent();
        Bundle packageFromCaller = callerIntent.getBundleExtra("MyPackage");
        a = packageFromCaller.getString("username");
        lv.setText(a);
    }

    private void loadAvatar(){
        String url = "http://pmthu.esy.es/dulichviet/users.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                //new JsonConverter<Product>().toArrayList(jsonText, Product.class);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject COLLEGEDATA = jsonArray.getJSONObject(0);
                    to = COLLEGEDATA.getString("avatar");
                    email.setText(COLLEGEDATA.getString("email"));
                    String path = "http://pmthu.esy.es/dulichviet/images/" + to + "";
                    Picasso.with(getApplicationContext()).load(path).into(avatar);


                }catch(JSONException e){
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(getApplicationContext(), "L0i roi", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> parms = new HashMap<>();
                parms.put("username", a);
                return parms;
            }
        };//ket thuc stringresquet
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    private void doimatkhau(){
        AlertDialog.Builder them = new AlertDialog.Builder(
                MainActivity.this);
        them.setTitle("Đổi mật khẩu.");
        them.setIcon(R.drawable.doimk);
        final TextView tv = new TextView(MainActivity.this);
        tv.setText("");
        ed = new EditText(MainActivity.this);
        ed.setHint("Nhập Email");
        ed.setGravity(Gravity.CENTER);
        ed.setBackgroundResource(R.drawable.background_edittext);

        final TextView tv2 = new TextView(MainActivity.this);
        tv2.setText("");
        ed2 = new EditText(MainActivity.this);
        ed2.setHint("Nhập mật khẩu mới");
        ed2.setGravity(Gravity.CENTER);
        ed2.setBackgroundResource(R.drawable.background_edittext);

        LinearLayout layout = new LinearLayout(MainActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(tv);
        layout.addView(ed);
        layout.addView(tv2);
        layout.addView(ed2);
        them.setView(layout);
        them.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateUser();
                    }
                });

        them.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                finish();
                dialog.cancel();
            }
        });
        them.create().show();


    }

    private void updateUser(){

        String url = "http://pmthu.esy.es/dulichviet/updatepassword.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,

                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("thanhcong")){
                    Toast.makeText(MainActivity.this," cap nhat thanh cong", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this," cap nhat khong thanh cong", Toast.LENGTH_SHORT).show();
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
                parms.put("email", ed.getText().toString());
                parms.put("password", ed2.getText().toString());

                return parms;
            }
        };//ket thuc stringresquet



        MySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);

    }

    private void anhcanhan(){
        AlertDialog.Builder them = new AlertDialog.Builder(
                MainActivity.this);
        them.setTitle("Đổi ảnh đại diện.");
        them.setIcon(R.drawable.doimk);

        imv = new CircleImageView(MainActivity.this);
        imv.setBackgroundResource(R.drawable.hello);
        imv.setMaxHeight(40);
        imv.setMaxWidth(40);
       // imv.setScaleType(ImageView.ScaleType.FIT_CENTER);
       // ed.setBackgroundResource(R.drawable.background_edittext);


        LinearLayout layout = new LinearLayout(MainActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(imv);
        them.setView(layout);
        galleryPhoto = new GalleryPhoto(MainActivity.this);
        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonanh();
            }
        });
        them.setPositiveButton("Đổi ảnh",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        uphinh();
                    }
                });

        them.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                finish();
                dialog.cancel();
            }
        });
        them.create().show();

    }
    private void chonanh(){
        Intent in = galleryPhoto.openGalleryIntent();
        startActivityForResult(in, GALLERY_REQUEST);
    }
    private  void uphinh(){
        kq = (String.valueOf(Random()));
        try {
            String url = "http://pmthu.esy.es/dulichviet/updavatar.php";


            bitmap = ImageLoader.init().from(selectedPhoto).requestSize(1024,1024).getBitmap();


            final String encodeIamge = ImageBase64.encode(bitmap);

            StringRequest stringRequest = new StringRequest(Request.Method.POST,

                    url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.contains("thanhcong")){
                        Toast.makeText(MainActivity.this," cap nhat thanh cong", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this," cap nhat khong thanh cong", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    //  Toast.makeText(CapNhatFragment.this.getActivity(), "L0i roi", Toast.LENGTH_SHORT).show();

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {


                    Map<String, String> parms = new HashMap<>();

                    parms.put("username", a);

                    parms.put("hinhanh", file.getName().toString().replace(file.getName().toString(),gio()+kq+".jpeg"));

                    return parms;
                }
            };//ket thuc stringresquet

            String url1 = "http://pmthu.esy.es/dulichviet/uplavatar.php";
            StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(MainActivity.this,response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Toast.makeText(CapNhatFragment.this.getActivity(),"loi upload", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("i", encodeIamge);
                    params.put("r", kq);

                    return params;
                }
            };
            MySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest1);


            MySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);
        } catch (FileNotFoundException e) {
            // Toast.makeText(CapNhatFragment.this.getActivity(),"Loi anhhhhh",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == GALLERY_REQUEST){
                galleryPhoto.setPhotoUri(data.getData());
                String photoPath = galleryPhoto.getPath();
                selectedPhoto = photoPath;
                file = new File(photoPath);
                i = file.getName().toString();

                try {
                    Bitmap bitmap =  PhotoLoader.init().from(photoPath).requestSize(512,512).getBitmap();

                    imv.setImageBitmap(bitmap);




                } catch (FileNotFoundException e) {
                    Toast.makeText(MainActivity.this,"Loi anh",Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public String gio(){
        Date date = new Date();
        SimpleDateFormat formats = new SimpleDateFormat("yyyyMMdd");
        String daynew =	formats.format(date);
        return daynew;
    }
    public int Random(){
        //tong tu 10 den 19
        Random rand = new Random();
        int num = rand.nextInt(10000000);
        return num;

    }
}
