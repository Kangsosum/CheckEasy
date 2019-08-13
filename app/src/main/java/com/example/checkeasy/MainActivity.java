package com.example.checkeasy;

import android.Manifest;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends BaseActivity implements GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, OnMapReadyCallback {
    static final String TAG = "MainAcitivy";
    private GoogleMap map;
    private String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int MY_PERMISSION_CODE = 1111;
    private boolean mLocationPermissionGranted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        updateLocationUI();

        LatLng SEOUL = new LatLng(33.456394, 126.565603);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("제주대학교");
        markerOptions.snippet("공과대학 3호관");
        map.addMarker(markerOptions);

        map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        map.animateCamera(CameraUpdateFactory.zoomTo(12));

        click_gps_btn();
    }

    private void click_gps_btn() {
        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Button zoomGps = (Button) findViewById(R.id.map_gps);

        zoomGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    // requestPermissions 한 퍼미션 지정한 수 만큼 물어보는 역할
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 만약 requestPermissions 로 지정한 requestCode 가 MY_PERMISSION_NFC 일 때
        if (requestCode == MY_PERMISSION_CODE) {
            mLocationPermissionGranted = true;
        } else {
            Toast.makeText(MainActivity.this, "해당 권한 활성화 필요", Toast.LENGTH_SHORT).show();
        }
        updateLocationUI();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void updateLocationUI() {
        if (map == null) {
            return;
        }

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, permission, MY_PERMISSION_CODE);
            mLocationPermissionGranted = true;
            Log.d(TAG, "checkPermission");
        } else {
            ActivityCompat.requestPermissions(this, permission, MY_PERMISSION_CODE);
        }

        if (mLocationPermissionGranted) {
            map.setMyLocationEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
            map.setOnMyLocationButtonClickListener(this);
            map.setOnMyLocationClickListener(this);
        }else {
            map.setMyLocationEnabled(false);
            map.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }
}
