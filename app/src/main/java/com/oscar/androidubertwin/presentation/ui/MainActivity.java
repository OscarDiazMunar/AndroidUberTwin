package com.oscar.androidubertwin.presentation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import com.oscar.androidubertwin.R;
import com.oscar.androidubertwin.di.MainActivity.DaggerMainComponent;
import com.oscar.androidubertwin.di.MainActivity.MainComponent;
import com.oscar.androidubertwin.di.MainActivity.MainModule;
import com.oscar.androidubertwin.presentation.presenter.MainActivityPresenter.MainActivityPresenter;
import com.oscar.androidubertwin.presentation.view.IMainActivityView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity implements IMainActivityView {

    /**
     * The Btn sing.
     */
    @BindView(R.id.btnSing)
    Button btnSing;
    /**
     * The Btn register.
     */
    @BindView(R.id.btnRegister)
    Button btnRegister;
    /**
     * The Layout main.
     */
    @BindView(R.id.layout_main)
    ConstraintLayout layoutMain;
    /**
     * The Progress bar.
     */
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    /**
     * The Presenter.
     */
    @Inject
    MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initializeDagger();
        presenter.onCreate();
        presenter.setView(this);

    }

    private void initializeDagger() {
        MainComponent mainComponent = DaggerMainComponent.builder()
                                        .mainModule(new MainModule(this)).build();
        mainComponent.inject(this);
    }

    /**
     * On view clicked.
     *
     * @param view the view
     */
    @OnClick({R.id.btnSing, R.id.btnRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSing:
                presenter.showLoginDialog();
                break;
            case R.id.btnRegister:
                presenter.showRegisterDialog();
                break;
        }
    }

    @Override
    public void setMessageSnackBar(String message) {
        Snackbar.make(layoutMain, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToWelcome() {
        startActivity(new Intent(MainActivity.this, MapsActivity.class));
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
