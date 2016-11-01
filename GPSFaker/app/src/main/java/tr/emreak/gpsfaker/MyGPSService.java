package tr.emreak.gpsfaker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Random;

import tr.emreak.gpsfaker.lib.FloatingActionButton;
import tr.emreak.gpsfaker.lib.FloatingActionMenu;
import tr.emreak.gpsfaker.lib.SubActionButton;

public class MyGPSService extends Service {

    private final IBinder mBinder = new LocalBinder();

    private FloatingActionButton rightLowerButton;
    private FloatingActionButton topCenterButton;

    private FloatingActionMenu rightLowerMenu;
    private FloatingActionMenu topCenterMenu;

    private boolean serviceWillBeDismissed;

    private static final int VEHICLE_CAR = 2;
    private static final int VEHICLE_BIKE = 1;
    private static final int VEHICLE_NONE = 0;
    private static final int VEHICLE_BED = -1;

    private static final int DIRECTION_UP = 0;
    private static final int DIRECTION_RIGHT = 1;
    private static final int DIRECTION_DOWN = 2;
    private static final int DIRECTION_LEFT = 3;

    private static int VEHICLE = VEHICLE_NONE;

    private Context parent;
    private LocationManager mLM;
    private LocationListener mLL;
    private static double LONGITUDE;
    private static double LATITUDE;


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public class LocalBinder extends Binder {
        MyGPSService getService() {
            // Return this instance of LocalService so clients can call public methods
            return MyGPSService.this;
        }
    }


    @Override
    public void onStart(Intent intent, int startId) {
        boolean isCloseMOCK = Settings.Secure.getInt(getContentResolver(),
                Settings.Secure.ALLOW_MOCK_LOCATION, 0) == 0;

        this.parent = getBaseContext();

        mLM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            mLM.removeTestProvider(LocationManager.GPS_PROVIDER);
            mLM.removeTestProvider(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {}

        mLM.addTestProvider(LocationManager.GPS_PROVIDER , false, true, false, false, false, false, false, Criteria.POWER_LOW, Criteria.ACCURACY_HIGH);
        mLM.addTestProvider(LocationManager.NETWORK_PROVIDER , true, false, false, false, false, false, false, Criteria.POWER_LOW, Criteria.ACCURACY_HIGH);
        mLM.setTestProviderEnabled(LocationManager.GPS_PROVIDER , true);

    }

    @Override
    public void onCreate() {
        super.onCreate();

        serviceWillBeDismissed = false;

        this.LATITUDE = MainActivity.LATITUDE;
        this.LONGITUDE = MainActivity.LONGITUDE;


        // Set up the white button on the lower right corner
        // more or less with default parameter
        final ImageView fabIconVehicle = new ImageView(this);
        fabIconVehicle.setImageDrawable(getDrawable(R.drawable.ic_directions_walk));
        WindowManager.LayoutParams params = FloatingActionButton.Builder.getDefaultSystemWindowParams(this);

        rightLowerButton = new FloatingActionButton.Builder(this)
                .setContentView(fabIconVehicle)
                .setSystemOverlay(true)
                .setLayoutParams(params)
                .build();

        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this);
        final ImageView rlIcon1 = new ImageView(this);
        final ImageView rlIcon2 = new ImageView(this);
        final ImageView rlIcon3 = new ImageView(this);
        final ImageView rlIcon4 = new ImageView(this);

        rlIcon1.setImageDrawable(getDrawable(R.drawable.ic_local_hotel));
        rlIcon2.setImageDrawable(getDrawable(R.drawable.ic_directions_walk));
        rlIcon3.setImageDrawable(getDrawable(R.drawable.ic_directions_bike));
        rlIcon4.setImageDrawable(getDrawable(R.drawable.ic_directions_car));

        // Build the menu with default options: light theme, 90 degrees, 72dp radius.
        // Set 4 default SubActionButtons
        final SubActionButton rlSub1 = rLSubBuilder.setContentView(rlIcon1).build();
        final SubActionButton rlSub2 = rLSubBuilder.setContentView(rlIcon2).build();
        final SubActionButton rlSub3 = rLSubBuilder.setContentView(rlIcon3).build();
        final SubActionButton rlSub4 = rLSubBuilder.setContentView(rlIcon4).build();
        rightLowerMenu = new FloatingActionMenu.Builder(this, true)
                .addSubActionView(rlSub1, rlSub1.getLayoutParams().width, rlSub1.getLayoutParams().height)
                .addSubActionView(rlSub2, rlSub2.getLayoutParams().width, rlSub2.getLayoutParams().height)
                .addSubActionView(rlSub3, rlSub3.getLayoutParams().width, rlSub3.getLayoutParams().height)
                .addSubActionView(rlSub4, rlSub4.getLayoutParams().width, rlSub4.getLayoutParams().height)
                .setStartAngle(180)
                .setEndAngle(270)
                .attachTo(rightLowerButton)
                .build();


        rlSub1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabIconVehicle.setImageDrawable(getDrawable(R.drawable.ic_local_hotel));
                VEHICLE = VEHICLE_BED;
                rightLowerMenu.close(true);
            }
        });

        rlSub2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabIconVehicle.setImageDrawable(getDrawable(R.drawable.ic_directions_walk));
                VEHICLE = VEHICLE_NONE;
                rightLowerMenu.close(true);
            }
        });

        rlSub3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabIconVehicle.setImageDrawable(getDrawable(R.drawable.ic_directions_bike));
                VEHICLE = VEHICLE_BIKE;
                rightLowerMenu.close(true);
            }
        });

        rlSub4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabIconVehicle.setImageDrawable(getDrawable(R.drawable.ic_directions_car));
                VEHICLE = VEHICLE_CAR;
                rightLowerMenu.close(true);
            }
        });
        ////////////////////////////////////////////////////////

        // Set up the large red button on the top center side
        // With custom button and content sizes and margins
        int redActionButtonSize = getResources().getDimensionPixelSize(R.dimen.red_action_button_size);
        int redActionButtonMargin = getResources().getDimensionPixelOffset(R.dimen.red_action_button_margin);
        int redActionButtonContentSize = getResources().getDimensionPixelSize(R.dimen.red_action_button_content_size);
        int redActionButtonContentMargin = getResources().getDimensionPixelSize(R.dimen.red_action_button_content_margin);
        int redActionMenuRadius = getResources().getDimensionPixelSize(R.dimen.red_action_menu_radius);
        int blueSubActionButtonSize = getResources().getDimensionPixelSize(R.dimen.blue_sub_action_button_size);
        int blueSubActionButtonContentMargin = getResources().getDimensionPixelSize(R.dimen.blue_sub_action_button_content_margin);

        ImageView fabIconStar = new ImageView(this);
        fabIconStar.setImageDrawable(getDrawable(R.drawable.ic_navi));

        FloatingActionButton.LayoutParams fabIconStarParams = new FloatingActionButton.LayoutParams(redActionButtonContentSize, redActionButtonContentSize);
        fabIconStarParams.setMargins(redActionButtonContentMargin,
                redActionButtonContentMargin,
                redActionButtonContentMargin,
                redActionButtonContentMargin);

        WindowManager.LayoutParams params2 = FloatingActionButton.Builder.getDefaultSystemWindowParams(this);
        params2.width = redActionButtonSize;
        params2.height = redActionButtonSize;

        topCenterButton = new FloatingActionButton.Builder(this)
                .setSystemOverlay(true)
                .setContentView(fabIconStar, fabIconStarParams)
                .setBackgroundDrawable(R.drawable.button_action_red_selector)
                .setPosition(FloatingActionButton.POSITION_TOP_CENTER)
                .setLayoutParams(params2)
                .build();

        // Set up customized SubActionButtons for the right center menu
        SubActionButton.Builder tCSubBuilder = new SubActionButton.Builder(this);
        tCSubBuilder.setBackgroundDrawable(getDrawable(R.drawable.button_action_blue_selector));

        SubActionButton.Builder tCRedBuilder = new SubActionButton.Builder(this);
        tCRedBuilder.setBackgroundDrawable(getDrawable(R.drawable.button_action_red_selector));

        FrameLayout.LayoutParams blueContentParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        blueContentParams.setMargins(blueSubActionButtonContentMargin,
                blueSubActionButtonContentMargin,
                blueSubActionButtonContentMargin,
                blueSubActionButtonContentMargin);

        // Set custom layout params
        FrameLayout.LayoutParams blueParams = new FrameLayout.LayoutParams(blueSubActionButtonSize, blueSubActionButtonSize);
        tCSubBuilder.setLayoutParams(blueParams);
        tCRedBuilder.setLayoutParams(blueParams);

        ImageView tcIcon1 = new ImageView(this);
        ImageView tcIcon2 = new ImageView(this);
        ImageView tcIcon3 = new ImageView(this);
        ImageView tcIcon4 = new ImageView(this);
        ImageView tcIcon5 = new ImageView(this);
        ImageView tcIcon6 = new ImageView(this);

        tcIcon1.setImageDrawable(getDrawable(R.drawable.ic_check));
        tcIcon2.setImageDrawable(getDrawable(R.drawable.ic_chevron_right)); // right
        tcIcon3.setImageDrawable(getDrawable(R.drawable.ic_chevron_down)); // down
        tcIcon4.setImageDrawable(getDrawable(R.drawable.ic_chevron_up)); // up
        tcIcon5.setImageDrawable(getDrawable(R.drawable.ic_chevron_left)); // left
        tcIcon6.setImageDrawable(getDrawable(R.drawable.ic_action_cancel));

        SubActionButton tcSub1 = tCSubBuilder.setContentView(tcIcon1, blueContentParams).build();
        SubActionButton tcSub2 = tCSubBuilder.setContentView(tcIcon2, blueContentParams).build();
        SubActionButton tcSub3 = tCSubBuilder.setContentView(tcIcon3, blueContentParams).build();
        SubActionButton tcSub4 = tCSubBuilder.setContentView(tcIcon4, blueContentParams).build();
        SubActionButton tcSub5 = tCSubBuilder.setContentView(tcIcon5, blueContentParams).build();
        SubActionButton tcSub6 = tCRedBuilder.setContentView(tcIcon6, blueContentParams).build();


        // Build another menu with custom options
        topCenterMenu = new FloatingActionMenu.Builder(this, true)
                .addSubActionView(tcSub1, tcSub1.getLayoutParams().width, tcSub1.getLayoutParams().height)
                .addSubActionView(tcSub2, tcSub2.getLayoutParams().width, tcSub2.getLayoutParams().height)
                .addSubActionView(tcSub3, tcSub3.getLayoutParams().width, tcSub3.getLayoutParams().height)
                .addSubActionView(tcSub4, tcSub4.getLayoutParams().width, tcSub4.getLayoutParams().height)
                .addSubActionView(tcSub5, tcSub5.getLayoutParams().width, tcSub5.getLayoutParams().height)
                .addSubActionView(tcSub6, tcSub6.getLayoutParams().width, tcSub6.getLayoutParams().height)
                .setRadius(redActionMenuRadius)
                .setStartAngle(0)
                .setEndAngle(180)
                .attachTo(topCenterButton)
                .build();

        topCenterMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu menu) {

            }

            @Override
            public void onMenuClosed(FloatingActionMenu menu) {
                if (serviceWillBeDismissed) {
                    MyGPSService.this.stopSelf();
                    serviceWillBeDismissed = false;
                }
            }
        });

        tcSub1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topCenterMenu.close(true);
            }
        });

        tcSub2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(DIRECTION_RIGHT);
            }
        });

        tcSub3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(DIRECTION_DOWN);
            }
        });

        tcSub4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(DIRECTION_UP);
            }
        });

        tcSub5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(DIRECTION_LEFT);
            }
        });

        // make the red button terminate the service
        tcSub6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceWillBeDismissed = true; // the order is important
                topCenterMenu.close(true);
            }
        });



    }

    @Override
    public void onDestroy() {
        if(rightLowerMenu != null && rightLowerMenu.isOpen()) rightLowerMenu.close(false);
        if(topCenterMenu != null && topCenterMenu.isOpen()) topCenterMenu.close(false);
        if(rightLowerButton != null) rightLowerButton.detach();
        if(topCenterButton != null) topCenterButton.detach();

        super.onDestroy();
    }


    private void move(int direction){

        double step;
        switch (VEHICLE){
            case VEHICLE_BED:
                step = 0;
                break;
            case VEHICLE_NONE:
                step = 0.00005;
                break;
            case VEHICLE_BIKE:
                step = 0.00015;
                break;
            case VEHICLE_CAR:
                step = 0.00040;
                break;
            default:
                step = 0.00005;
        }

        switch(direction){
            case DIRECTION_UP:
                LATITUDE += step;
                break;
            case DIRECTION_RIGHT:
                LONGITUDE += step;
                break;
            case DIRECTION_DOWN:
                LATITUDE -= step;
                break;
            case DIRECTION_LEFT:
                LONGITUDE -= step;
                break;
            default:

        }

        mLM = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        Location newLocation = new MockLocation(LocationManager.NETWORK_PROVIDER);

        newLocation.setLatitude(LATITUDE);
        newLocation.setLongitude(LONGITUDE);
        newLocation.setAccuracy(16F);
        newLocation.setAltitude(0D);
        newLocation.setBearing(0F);
        newLocation.setTime(System.currentTimeMillis());
        newLocation.setElapsedRealtimeNanos(System.nanoTime());

        String provider = LocationManager.NETWORK_PROVIDER;
        Random r = new Random();
        if (r.nextInt(100) >= 50) {
            provider = LocationManager.GPS_PROVIDER;
        }
        newLocation.setProvider(provider);
        mLM.setTestProviderEnabled(provider, true);
        mLM.setTestProviderStatus(provider, LocationProvider.AVAILABLE, null, System.currentTimeMillis());
        mLM.setTestProviderLocation(provider, newLocation);

        Log.d("Service", "Moooooving to: Lat: "+ LATITUDE + ", Long: "+ LONGITUDE);
        Log.d("Service", newLocation.toString());
    }


    public class MockLocation extends Location{

        private String mProvider;
        private long mTime = 0;
        private long mElapsedRealtimeNanos = 0;
        private double mLatitude = 0.0;
        private double mLongitude = 0.0;
        private boolean mHasAltitude = false;
        private double mAltitude = 0.0f;
        private boolean mHasSpeed = false;
        private float mSpeed = 0.0f;
        private boolean mHasBearing = false;
        private float mBearing = 0.0f;
        private boolean mHasAccuracy = false;
        private float mAccuracy = 0.0f;
        private Bundle mExtras = null;
        private boolean mIsFromMockProvider = false;

        // Cache the inputs and outputs of computeDistanceAndBearing
        // so calls to distanceTo() and bearingTo() can share work
        private double mLat1 = 0.0;
        private double mLon1 = 0.0;
        private double mLat2 = 0.0;
        private double mLon2 = 0.0;
        private float mDistance = 0.0f;
        private float mInitialBearing = 0.0f;
        // Scratchpad
        private final float[] mResults = new float[2];

        public MockLocation(String provider) {
            super(provider);
            this.mTime = 0;
            this.mElapsedRealtimeNanos = 0;
            this.mLatitude = 0;
            this.mLongitude = 0;
            this.mHasAltitude = false;
            this.mAltitude = 0;
            this.mHasSpeed = false;
            this.mSpeed = 0;
            this.mHasBearing = false;
            this.mBearing = 0;
            this.mHasAccuracy = false;
            this.mAccuracy = 0;
            this.mExtras = null;
            this.mIsFromMockProvider = false;
        }

        @Override
        public boolean isFromMockProvider() {
            return false;
        }
    }

}
