package com.thu.dulich1;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.kosalgeek.android.photoutil.PhotoLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import thu.bean.TinTuc;
import thu.singleton.MySingleton;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class CapNhatFragment extends Fragment {
    String kq = null;
    Bitmap bitmap;
    String i = null;
    String username;

    File file;
    EditText edMota,edChitiet;
    final String TAG = this.getClass().getSimpleName();
    ImageView imgAnh;
    Button btThemanh, btDang;

    String news = "";
    GalleryPhoto galleryPhoto;
    final int GALLERY_REQUEST = 22131;
    String selectedPhoto;
    String r;
    Bundle Add;

        TinTuc tt;

    String arr[]={
            "Điểm phượt đẹp",
            "Thiên nhiên đẹp",
            "Khác.."};

    public CapNhatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_cap_nhat, container, false);
        edMota = (EditText)view.findViewById(R.id.mota);
        edChitiet = (EditText)view.findViewById(R.id.chitiet);
        btThemanh = (Button)view.findViewById(R.id.chonanh);
        btDang = (Button)view.findViewById(R.id.dang);
        imgAnh = (ImageView)view.findViewById(R.id.ivImages);
        Spinner sp = (Spinner)view.findViewById(R.id.sp);

        Bundle b = getArguments();
        username = b.getString("username");

        Add = getArguments();
        news = Add.getString("tintuc");







        ArrayAdapter<String> adapter=new ArrayAdapter<String>
                (
                        this.getActivity(),
                        android.R.layout.simple_spinner_item,
                        arr
                );
        //phải gọi lệnh này để hiển thị danh sách cho Spinner
        adapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        //Thiết lập adapter cho Spinner
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity().)
                //mota.setText(""+(position + 1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       Toast.makeText(CapNhatFragment.this.getActivity(),news, Toast.LENGTH_SHORT).show();



        galleryPhoto = new GalleryPhoto(CapNhatFragment.this.getActivity());
        btThemanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = galleryPhoto.openGalleryIntent();

                startActivityForResult(in, GALLERY_REQUEST);

            }
        });

        btDang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                kq = (String.valueOf(Random()));
                try {
                    String url = "http://pmthu.esy.es/dulichviet/update.php";


                    bitmap = ImageLoader.init().from(selectedPhoto).requestSize(1024,1024).getBitmap();


                    final String encodeIamge = ImageBase64.encode(bitmap);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST,

                            url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.contains("thanhcong")){
                                Toast.makeText(CapNhatFragment.this.getActivity()," cap nhat thanh cong", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(CapNhatFragment.this.getActivity()," cap nhat khong thanh cong", Toast.LENGTH_SHORT).show();
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
                            parms.put("mand", news);
                            parms.put("username", username);
                            parms.put("mota", edMota.getText().toString());
                            parms.put("chitiet", edChitiet.getText().toString());
                            parms.put("hinhanh", file.getName().toString().replace(file.getName().toString(),gio()+kq+".jpeg"));
                            parms.put("ngaydang", ngayDang());
                            parms.put("iddanhmuc", "1");

                            // Toast.makeText(ThemFragment.this.getActivity(),"kakakakakak", Toast.LENGTH_SHORT).show();
                            //Log.d("eeeeeeeeeeeeee",kq);
                            return parms;
                        }
                    };//ket thuc stringresquet

                    String url1 = "http://pmthu.esy.es/dulichviet/upload.php";
                    StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(CapNhatFragment.this.getActivity(),response, Toast.LENGTH_SHORT).show();
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
                    MySingleton.getInstance(CapNhatFragment.this.getActivity()).addToRequestQueue(stringRequest1);


                    MySingleton.getInstance(CapNhatFragment.this.getActivity()).addToRequestQueue(stringRequest);
                } catch (FileNotFoundException e) {
                   // Toast.makeText(CapNhatFragment.this.getActivity(),"Loi anhhhhh",Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
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

                // Log.d("nnnnnnnnnnnnnnnnnn", galleryPhoto.getPath());
                // Log.d("yyyyyyyyyyyyyyy", file.getName());
                try {
                    Bitmap bitmap =  PhotoLoader.init().from(photoPath).requestSize(512,512).getBitmap();

                    imgAnh.setImageBitmap(bitmap);




                } catch (FileNotFoundException e) {
                    Toast.makeText(CapNhatFragment.this.getActivity(),"Loi anh",Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public String gio(){
        Date date = new Date();
        SimpleDateFormat formats = new SimpleDateFormat("yyyMMdd");
        String daynew =	formats.format(date);
        return daynew;
    }
    public int Random(){
        //tong tu 10 den 19
        Random rand = new Random();
        int num = rand.nextInt(10000000);
        return num;

    }
    public String ngayDang(){
        Date date = new Date();
        SimpleDateFormat formats = new SimpleDateFormat("dd/MM/yyyy");
        String daynews=	formats.format(date);
        return daynews;
    }
}
