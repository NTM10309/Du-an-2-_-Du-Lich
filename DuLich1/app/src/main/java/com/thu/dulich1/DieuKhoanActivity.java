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

public class DieuKhoanActivity extends AppCompatActivity {
    String user = "";
    TextView dieukhoan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dieu_khoan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent callerIntent=getIntent();
        Bundle packageFromCaller = callerIntent.getBundleExtra("MyPackage");
        user = packageFromCaller.getString("username");

        dieukhoan = (TextView)findViewById(R.id.dieukhoan);
        dieukhoan.setText("1. Ứng dụng Du Lịch Việt được được phát triển và quyền sở hữu của sinh viên cao đẳng thực hành FPT Polytechnic Đà Nẵng."
                +"\n"+
                "Nghiêm cấm mọi cá nhân tổ chức sao chép dưới mọi hình thức."
                +"\n \n"+
                "2. Ứng dụng Du Lịch Việt được cung cấp miễn phí cho tất cả người dùng."
                +"\n \n"+
                "3. Mọi phat ngôn, hình ảnh nguoiwf dùng phải mang tính hợp pháp, không dùng những từ ngữ thô tục, không đả kích chính quyền, trái nghịch" +
                "với thuần phong mỹ thuật của người Việt Nam." +
                "Nếu người dùng vi phạm chúng tôi buộc phải xóa bài viết và khóa tài khoản của người dùng."
                +"\n \n"+
                "4. Mọi hoạt động của người dùng trên ứng dụng không được vi phạm pháp luật do nhà nước cấm."

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

        Intent in = new Intent(DieuKhoanActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("username",user);
        in.putExtra("MyPackage", bundle);
        startActivity(in);
        finish();
    }

}
