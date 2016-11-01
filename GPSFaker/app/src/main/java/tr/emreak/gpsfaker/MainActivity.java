package tr.emreak.gpsfaker;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements
        android.location.LocationListener, OnMapReadyCallback {

    private static int REQUEST_CODE = 1010;
    private static int GPS_REQUEST = 1011;
    public static double LATITUDE;
    public static double LONGITUDE;

    private TextView txt_longitude;
    private TextView txt_latitude;
    private Location mLastLocation;
    private GoogleMap mMap;
    public Context ctx;
    private String mLastUpdateTime;

    private Intent gpsService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.btn_startService);
        txt_longitude = (TextView) findViewById(R.id.txt_longitude);
        txt_latitude = (TextView) findViewById(R.id.txt_latitude);
        ctx = getApplicationContext();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        LocationManager mLM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLM.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,5, this);
        mLM.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 5, this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gpsService = new Intent(MainActivity.this, MyGPSService.class);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkDrawOverlayPermission()) {


                        startService(gpsService);
                        //MainActivity.this.finish();
                    }
                } else {
                    startActivity(gpsService);
                    //MainActivity.this.finish();
                }

            }
        });

        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        GPS_REQUEST);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
            return;
        }
    }


    @TargetApi(23)
    public boolean checkDrawOverlayPermission() {
        /** check if we already  have permission to draw over other apps */
        if (!Settings.canDrawOverlays(this)) {
            /** if not construct intent to request permission */
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            /** request permission via start activity for result */
            startActivityForResult(intent, REQUEST_CODE);
            return false;
        }
        return true;
    }

    @TargetApi(23)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /** check if received result code
         is equal our requested code for draw permission  */
        if (requestCode == REQUEST_CODE) {
            /** if so check once again if we have permission */
            if (Settings.canDrawOverlays(this)) {
                // continue here - permission was granted
                startService(gpsService);
                //MainActivity.this.finish();
            }
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

        LATITUDE = mLastLocation.getLatitude();
        LONGITUDE = mLastLocation.getLongitude();

        txt_latitude.setText(String.valueOf(LATITUDE));
        txt_longitude.setText(String.valueOf(LONGITUDE));

        updateUI();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void updateUI() {
        LATITUDE = mLastLocation.getLatitude();
        LONGITUDE = mLastLocation.getLongitude();

        txt_latitude.setText(String.valueOf(LATITUDE));
        txt_longitude.setText(String.valueOf(LONGITUDE));

        Log.d("UI Update", "Lat: " + LATITUDE + ", Lon: " + LONGITUDE);

        mMap.clear();
        LatLng marker = new LatLng(LATITUDE, LONGITUDE);
        mMap.addMarker(new MarkerOptions().position(marker).title("Du bist hier"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 14));
    }

    @Override
    protected void onPause() {
        super.onPause();
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

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (mLastLocation == null) {
                    mLastLocation = new Location(LocationManager.GPS_PROVIDER);

                }
                mLastLocation.setLatitude(latLng.latitude);
                mLastLocation.setLongitude(latLng.longitude);
                updateUI();
            }
        });

        LatLng marker = new LatLng(52.51269961, 13.32700443);
        mMap.addMarker(new MarkerOptions().position(marker).title("..."));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 14));
    }


}
