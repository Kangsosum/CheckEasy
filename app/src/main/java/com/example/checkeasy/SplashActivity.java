package com.example.checkeasy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.lang.ref.WeakReference;

public class SplashActivity extends AppCompatActivity {
    private String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int MY_PERMISSION_CODE = 1111;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permission, MY_PERMISSION_CODE);
        } else {
            handler.sendEmptyMessageDelayed(0, 2000);
        }

    }

    private SplashActivityHandler handler = new SplashActivityHandler(this);

    private static class SplashActivityHandler extends Handler {
        private final WeakReference<SplashActivity> mActivity;

        public SplashActivityHandler(SplashActivity activity) {
            this.mActivity = new WeakReference<SplashActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent(mActivity.get(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mActivity.get().startActivity(intent);
        }
    }

    @Override
    // requestPermissions 한 퍼미션 지정한 수 만큼 물어보는 역할
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 만약 requestPermissions 로 지정한 requestCode 가 MY_PERMISSION_NFC 일 때
        if (requestCode == MY_PERMISSION_CODE) {
        } else {
            Toast.makeText(SplashActivity.this, "해당 권한 활성화 필요", Toast.LENGTH_SHORT).show();
        }
        handler.sendEmptyMessageDelayed(0, 2000);
    }
}
