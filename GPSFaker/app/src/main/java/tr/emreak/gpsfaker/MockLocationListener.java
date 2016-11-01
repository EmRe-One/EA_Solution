package tr.emreak.gpsfaker;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


/**
 * Created by Emre Ak on 21.08.2016.
 */
public class MockLocationListener implements LocationListener {

    public MockLocationListener(){
        Log.d("MockLocationListener", "Create");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("MockLocationListener", "On Location Changed: Lat: " + location.getLatitude() + ", " +
                                        "Long: " + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("MockLocationListener", "On Status Changed");
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
