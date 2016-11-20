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

public class ResgiterActivity extends AppCompatActivity {
    EditText edTen,edEmail, edPass;
    TextView tvLogin;
    Button btDangKy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgiter);

        edTen = (EditText)findViewById(R.id.edTen);
        edEmail = (EditText)findViewById(R.id.edEmail);
        edPass = (EditText)findViewById(R.id.edPass);
        btDangKy = (Button)findViewById(R.id.btdangky);
        tvLogin = (TextView)findViewById(R.id.tvDN);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ResgiterActivity.this, LoginActivity.class);
                startActivity(in);
            }
        });

        btDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edTen.getText().toString().length()==0 ) {
                    edTen.setError("Vui  lòng nhập tên đăng nhập");
                   return;
                }
                else if(edPass.getText().toString().length()==0){
                    edPass.setError("Vui lòng nhập mật khẩu");
                    return;

                }else if(edEmail.getText().toString().length()==0) {
                    edEmail.setError("Vui lòng nhập Email");
                    return;
                }else {
                    String url = "http://pmthu.esy.es/dulichviet/register.php";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST,
                            url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("thanhcong")) {
                                Intent in = new Intent(ResgiterActivity.this, LoginActivity.class);
                                startActivity(in);
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
                            parms.put("username", edTen.getText().toString());
                            parms.put("email", edEmail.getText().toString());
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
