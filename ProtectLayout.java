package com.thkang.svdr;

import android.Manifest;
import android.app.Fragment;
import android.app.Notification;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by thkan on 2017-11-20.
 */

public class ProtectLayout extends Fragment implements OnMapReadyCallback {

    private NotificationHelper helper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout rootView = (RelativeLayout)inflater.inflate(R.layout.nav_protect_layout, container, false);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        MapFragment fragment = (MapFragment)getChildFragmentManager().findFragmentById(R.id.protect_map);
        fragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }
        LatLng marker = new LatLng(37.2843,127.0444);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker,15));

        googleMap.addMarker(new MarkerOptions().title("Here is safety!").position(marker));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            public void onMapClick(LatLng point) {
                double lat = point.latitude;
                double lon = point.longitude;
                int result = Double.compare(lat, 37.296);
                int result2 = Double.compare(lon, 127.050);
                if((result>0) && (result2>0)){
                    helper = new NotificationHelper(ProtectLayout.this.getActivity());
                    Notification.Builder builder = helper.getSVDRChannelNotification("위험 지역 진입 알림!", "해당 위치는 위험 지역입니다.");
                    helper.getManager().notify(102,builder.build());
                    String txt = "\n latitude ="
                            + point.latitude + ", longitude ="
                            + point.longitude+"\n";
                    String t2 = "[현재 위치 안전도]\n : '위험' 입니다.";
                    Toast.makeText(getContext(), txt+t2, Toast.LENGTH_LONG)
                            .show();
                }else{
                    String text = "\n latitude ="
                            + point.latitude + ", longitude ="
                            + point.longitude+"\n";
                    String t1 = "[현재 위치 안전도]\n : '안전' 입니다.";
                    Toast.makeText(getContext(), text+t1, Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }
}