package com.oscar.androidubertwin.presentation.ui;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.oscar.androidubertwin.R;
import com.oscar.androidubertwin.di.CustomerCallActivity.CustomerCallComponent;
import com.oscar.androidubertwin.di.CustomerCallActivity.CustomerCallModule;
import com.oscar.androidubertwin.di.CustomerCallActivity.DaggerCustomerCallComponent;
import com.oscar.androidubertwin.presentation.presenter.CustomerCallPresenter.CustomerCallPresenter;
import com.oscar.androidubertwin.presentation.view.ICustomerCallView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.oscar.androidubertwin.utils.GlobalVariables.lastLocation;

/**
 * The type Customer call activity.
 */
public class CustomerCallActivity extends AppCompatActivity implements ICustomerCallView{

    /**
     * The Img mapnoti.
     */
    @BindView(R.id.img_mapnoti)
    CircleImageView imgMapnoti;
    /**
     * The Txt time.
     */
    @BindView(R.id.txtTime)
    TextView txtTime;
    /**
     * The Txt distance.
     */
    @BindView(R.id.txtDistance)
    TextView txtDistance;
    /**
     * The Txt address.
     */
    @BindView(R.id.txtAddress)
    TextView txtAddress;

    private MediaPlayer mediaPlayer;

    /**
     * The Presenter.
     */
    @Inject
    CustomerCallPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_call);
        ButterKnife.bind(this);

        initializeDagger();
        presenter.setView(this);

        initMediaPlayer();

        if (getIntent() != null){
            double latitude = getIntent().getDoubleExtra("lat", -1.0);
            double longitude = getIntent().getDoubleExtra("lng", -1.0);
            LatLng currentPosition = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            String destination = Double.toString(latitude)+","+Double.toString(longitude);
            presenter.getDirection( destination, currentPosition);
        }
    }

    private void initializeDagger() {
        CustomerCallComponent customerCallComponent = DaggerCustomerCallComponent.builder()
                                                            .customerCallModule(new CustomerCallModule()).build();
        customerCallComponent.inject(this);

    }

    private void initMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this, R.raw.alertnotification);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    public void setInformationNotification(String distance, String duration, String endAddress) {
        txtDistance.setText(distance);
        txtTime.setText(duration);
        txtAddress.setText(endAddress);
    }

    @Override
    protected void onPause() {
        mediaPlayer.release();
        super.onPause();
    }

    @Override
    protected void onStop() {
        mediaPlayer.release();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }
}
