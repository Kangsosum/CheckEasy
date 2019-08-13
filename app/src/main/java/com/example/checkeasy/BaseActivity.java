package com.example.checkeasy;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.checkeasy.interfaces.IFragmentManager;

public class BaseActivity extends AppCompatActivity implements IFragmentManager {

    @Override
    public void changeFragment(int fragmentId, Bundle bundle) {

    }
}
