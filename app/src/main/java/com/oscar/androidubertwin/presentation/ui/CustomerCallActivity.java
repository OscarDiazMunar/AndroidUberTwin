package com.oscar.androidubertwin.presentation.ui;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.oscar.androidubertwin.R;
import com.oscar.androidubertwin.di.CustomerCallActivity.CustomerCallComponent;
import com.oscar.androidubertwin.di.CustomerCallActivity.CustomerCallModule;
import com.oscar.androidubertwin.di.CustomerCallActivity.DaggerCustomerCallComponent;
import com.oscar.androidubertwin.domain.model.DataNotification;
import com.oscar.androidubertwin.domain.model.Notification;
import com.oscar.androidubertwin.domain.model.SenderFCM;
import com.oscar.androidubertwin.domain.model.Token;
import com.oscar.androidubertwin.presentation.presenter.CustomerCallPresenter.CustomerCallPresenter;
import com.oscar.androidubertwin.presentation.view.ICustomerCallView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.oscar.androidubertwin.utils.GlobalVariables.lastLocation;

/**
 * The type Customer call activity.
 */
public class CustomerCallActivity extends AppCompatActivity implements ICustomerCallView {

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
    /**
     * The Btn accept.
     */
    @BindView(R.id.btnAccept)
    Button btnAccept;
    /**
     * The Btn decline.
     */
    @BindView(R.id.btnDecline)
    Button btnDecline;

    private MediaPlayer mediaPlayer;
    /**
     * The Customer id.
     */
    String customerId;

    /**
     * The Presenter.
     */
    @Inject
    CustomerCallPresenter presenter;

    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_call);
        ButterKnife.bind(this);

        initializeDagger();
        presenter.setView(this);

        initMediaPlayer();

        if (getIntent() != null) {
            latitude = getIntent().getDoubleExtra("lat", -1.0);
            longitude = getIntent().getDoubleExtra("lng", -1.0);
            LatLng currentPosition = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            String destination = Double.toString(latitude) + "," + Double.toString(longitude);
            customerId = getIntent().getStringExtra("customer");
            presenter.getDirection(destination, currentPosition);
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
    public void setToastNotification(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
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

    /**
     * On view clicked.
     *
     * @param view the view
     */
    @OnClick({R.id.btnAccept, R.id.btnDecline})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAccept:
                Intent intent = new Intent(CustomerCallActivity.this, DriverTracking.class);
                intent.putExtra("lat", latitude);
                intent.putExtra("lng", longitude);
                intent.putExtra("customer", customerId);
                startActivity(intent);
                finish();
                break;
            case R.id.btnDecline:
                if (!TextUtils.isEmpty(customerId)){
                    cancelBooking(customerId);
                    finish();
                }
                break;
        }
    }

    private void cancelBooking(String customerId) {
        Token token = new Token(customerId);
        Notification notification = new Notification(getString(R.string.call_title_notification),
                                                    getString(R.string.call_body_notification));

        SenderFCM senderFCM = new SenderFCM(notification, token.getToken(), new DataNotification(".", ".", 1));
        presenter.sendMessageNotification(senderFCM);
    }
}
