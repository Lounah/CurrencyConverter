package com.lounah.currencyconverter.presentation.common;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

public abstract class BaseFragment extends Fragment {

    protected abstract int getLayoutRes();

    protected abstract void initUI();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutRes = getLayoutRes();
        return inflater.inflate(layoutRes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    protected View findViewById(int viewId) {
        return Objects.requireNonNull(getView()).findViewById(viewId);
    }

    protected void showToast(String msg) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).showToast(msg);
    }

    protected void showToast(int msgRes) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).showToast(msgRes);
    }
}
