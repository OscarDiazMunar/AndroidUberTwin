package com.oscar.androidubertwin.presentation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.oscar.androidubertwin.R;
import com.oscar.androidubertwin.presentation.presenter.MainActivityPresenter.MainActivityPresenter;
import com.oscar.androidubertwin.presentation.view.IMainActivityView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IMainActivityView {

    @BindView(R.id.btnSing)
    Button btnSing;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.layout_main)
    ConstraintLayout layoutMain;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    private MainActivityPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainActivityPresenter(this, this);
        presenter.onCreate();

    }

    @OnClick({R.id.btnSing, R.id.btnRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSing:
                presenter.showLoginDialog();
                break;
            case R.id.btnRegister:
                Log.e("barbie", "loca");
                //showRegisterDialog();
                presenter.showRegisterDialog();
                break;
        }
    }

    @Override
    public void setMessageError(int type, String mesage) {

    }


    @Override
    public void setMessageSnackBar(String message) {
        Snackbar.make(layoutMain, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToWelcome() {
        startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void enableButtons(boolean enable) {
        btnRegister.setEnabled(enable);
        btnSing.setEnabled(enable);
    }
}
