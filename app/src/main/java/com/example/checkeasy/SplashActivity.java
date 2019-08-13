package com.example.checkeasy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        handler.sendEmptyMessageDelayed(0, 3000);
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
}
