package com.example.checkeasy.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.checkeasy.BaseActivity;

public abstract class BaseFragment extends Fragment{
    protected View mView; // 상속받은 프래그먼트들이 사용할 변수
    protected Context mContext;

    protected static final String ARG_PARAM1 = "param1";
    protected static final String ARG_PARAM2 = "param2";
    protected String mParam1;
    protected String mParam2;

    protected abstract void initLayout();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initLayout();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mContext = getActivity().getApplicationContext();
    }

    protected void changeFragment(int fragmentId, Bundle bundle){
        ((BaseActivity)getActivity()).selectItem(fragmentId,bundle);
    }
}
