package com.thu.dulich1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import thu.singleton.MySingleton;

public class LoginActivity extends AppCompatActivity {
    EditText edTen, edPass;
    TextView tvResgiter;
    Button btDangNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edTen = (EditText)findViewById(R.id.edTen);
        edPass = (EditText)findViewById(R.id.edPass);
        tvResgiter = (TextView)findViewById(R.id.tvDK);
        btDangNhap = (Button)findViewById(R.id.btdangnhap);

        tvResgiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginActivity.this, ResgiterActivity.class);
                startActivity(in);
            }
        });

        btDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edTen.getText().toString().length()==0 ) {
                    edTen.setError("Vui  lòng nhập tên đăng nhập");
                    return;

                }else if ( edPass.getText().toString().length()==0){
                    edPass.setError("Vui lòng nhập mật khẩu");
                    return;
                } else {
                    String url = "http://pmthu.esy.es/dulichviet/loginn.php";


                    StringRequest stringRequest = new StringRequest(Request.Method.POST,

                            url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("thanhcong")) {

                                Intent in = new Intent(LoginActivity.this, MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("username", edTen.getText().toString());
                                in.putExtra("MyPackage", bundle);
                                startActivity(in);
                            } else {
                                Toast.makeText(getApplicationContext(), " Login that bai", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(getApplicationContext(), "L0i roi", Toast.LENGTH_SHORT).show();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {


                            Map<String, String> parms = new HashMap<>();
                            parms.put("username", edTen.getText().toString());

                            parms.put("password", edPass.getText().toString());

                            return parms;
                        }
                    };//ket thuc stringresquet
                    MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

                }
            }
        });

    }

}
