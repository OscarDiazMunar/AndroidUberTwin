package com.oscar.androidubertwin.presentation.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.oscar.androidubertwin.R;
import com.oscar.androidubertwin.di.DriverTracking.DaggerDriverTrackingComponent;
import com.oscar.androidubertwin.di.DriverTracking.DriverTrackingComponent;
import com.oscar.androidubertwin.di.DriverTracking.DriverTrackingModule;
import com.oscar.androidubertwin.domain.model.DataNotification;
import com.oscar.androidubertwin.domain.model.Notification;
import com.oscar.androidubertwin.domain.model.SenderFCM;
import com.oscar.androidubertwin.domain.model.Token;
import com.oscar.androidubertwin.presentation.presenter.DriverTrackingPresenter.DriverTrackingPresenter;
import com.oscar.androidubertwin.presentation.view.IDriverTrackingView;
import com.oscar.androidubertwin.utils.Constants;
import com.oscar.androidubertwin.utils.GlobalVariables;

import java.util.List;

import javax.inject.Inject;

import static com.oscar.androidubertwin.utils.GlobalVariables.lastLocation;

/**
 * The type Driver tracking.
 */
public class DriverTracking extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        LocationListener,
        IDriverTrackingView{

    private GoogleMap mMap;
    private double riderlat;
    private double riderLng;

    private static final int MY_PERMISSION_REQUEST_CODE = 7000;
    private static final int PLAY_SERVICE_RES_REQUEST = 7001;

    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;

    private static final int UPDATE_INTERVAL = 5000;
    private static final int FATEST_INTERVAL = 3000;
    private static final int DISPLACEMENT = 10;

    private Marker driverMarker;
    private Polyline direction;
    private PolylineOptions polylineOptions;

    private GeoFire geoFire;
    private String customerId;

    /**
     * The Presenter.
     */
    @Inject
    DriverTrackingPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_tracking);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        initializeDagger();
        presenter.onCreate();
        presenter.setView(this);

        if (getIntent() != null){
            riderlat = getIntent().getDoubleExtra("lat", -1.0);
            riderLng = getIntent().getDoubleExtra("lng", -1.0);
            customerId = getIntent().getStringExtra("customer");
        }

        setUpLocation();
    }

    private void initializeDagger() {
        DriverTrackingComponent driverTrackingComponent = DaggerDriverTrackingComponent.builder()
                .driverTrackingModule(new DriverTrackingModule()).build();
        driverTrackingComponent.inject(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Circle riderMarker;
        riderMarker = mMap.addCircle(new CircleOptions()
            .center(new LatLng(riderlat, riderLng))
            .radius(50)
            .strokeColor(Color.BLUE)
            .fillColor(0x220000FF)
            .strokeWidth(5.0f));
        driverArrivedNotification();
    }

    private void driverArrivedNotification() {
        geoFire = new GeoFire(FirebaseDatabase.getInstance().getReference(Constants.DBTables.driver_table));
        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(riderlat, riderLng), 0.05f);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                sendArrivedNotification(customerId);
            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });
    }

    private void sendArrivedNotification(String customerId) {
        Token token = new Token(customerId);

        Notification notification = new Notification(getString(R.string.title_driver_arrived),
                getString(R.string.body_driver_arrived, GlobalVariables.currentUser.getName()));
        SenderFCM senderFCM = new SenderFCM(notification, token.getToken(), new DataNotification(".", ".", 2));
        presenter.sendMessageNotification(senderFCM);
    }

    private void setUpLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, MY_PERMISSION_REQUEST_CODE);
        } else {
            if (checkPlayServices()) {
                buildGoogleApiClient();
                createLocationRequest();
                displayLocation();
            }
        }
    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FATEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    private void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICE_RES_REQUEST).show();
            } else {
                Toast.makeText(this, R.string.device_not_supported, Toast.LENGTH_SHORT).show();
                finish();
            }
            return false;
        }
        return true;
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    private void displayLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (lastLocation != null) {
            final double latitude = lastLocation.getLatitude();
            final double longitude = lastLocation.getLongitude();

            LatLng latLng = new LatLng(latitude, longitude);

            if (driverMarker != null){
                driverMarker.remove();
            }
            driverMarker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("You")
                .icon(BitmapDescriptorFactory.defaultMarker()));

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.0f));

            if (direction != null){
                direction.remove();
            }
            presenter.getDirection(riderlat+","+riderLng, latLng);


        } else {
            Log.d("ERROR", "Cannot get your location");
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        displayLocation();
    }


    @Override
    public void setCameraUpdapte(LatLngBounds bounds) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 90);
        mMap.animateCamera(cameraUpdate);
    }

    @Override
    public void setPolyLineOptions(List<LatLng> listPolyline) {
        polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.RED);
        polylineOptions.width(5);
        polylineOptions.geodesic(true);
        polylineOptions.addAll(listPolyline);
        direction = mMap.addPolyline(polylineOptions);
    }

    @Override
    public void setToastNotification(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
