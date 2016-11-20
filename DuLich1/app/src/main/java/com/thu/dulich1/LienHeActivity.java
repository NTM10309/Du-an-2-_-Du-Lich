package com.thu.dulich1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class LienHeActivity extends AppCompatActivity {
    String username = "";
    TextView lh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent callerIntent=getIntent();
        Bundle packageFromCaller = callerIntent.getBundleExtra("MyPackage");
        username = packageFromCaller.getString("username");

        lh = (TextView)findViewById(R.id.lh);
        lh.setText("- Du Lịch Việt là ứng dụng giúp những người yêu thích du lịch, khám phá địa điểm mới có thể chia sẽ với nhau những địa điểm nổi tiếng trên cả nước."
                +"\n \n"+
                "- Ứng dụng Du Lịch Việt được cung cấp những tiện ích, địa điểm trên Google map, thời tiết..."
                +"\n \n"+
                "THÔNG TIN LIÊN HỆ"
                +"\n \n"+
                "1. Phan Minh Thư"+"\n"+
                "Email: minhthuvlt@gmail.com"+"\n"+
                "Số Điện Thoại: 0975.126.409"+"\n \n"+
                "2. Lê Văn Mẫn"+"\n"+
                "Email: manlvpd01362@fpt.edu.vn"+"\n \n"+
                "3. Nguyễn Tiến Nhật"+"\n"+
                "Email: nhatntpd01414@fpt.edu.vn"

        );
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

        Intent in = new Intent(LienHeActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("username",username);
        in.putExtra("MyPackage", bundle);
        startActivity(in);
        finish();
    }
}
