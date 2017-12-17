package com.thkang.svdr;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Random;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by thkan on 2017-11-20.
 */

public class UserLayout extends Fragment implements OnMapReadyCallback {

    private NotificationHelper helper;

    private String text;
    private String tt;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.nav_user_layout, container, false);

        final Button safetyButton = (Button) rootView.findViewById(R.id.safetyButton);
        final Button helpButton = (Button) rootView.findViewById(R.id.help);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                helper = new NotificationHelper(UserLayout.this.getActivity());

                Notification.Builder builder = helper.getSVDRChannelNotification("사용자 추적 시작", "[POLICE]근처 긴급상황 발생");
                helper.getManager().notify(101,builder.build() );
            }
        });

        safetyButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                /*
                String url = "http://172.30.1.52:3000/pp";
                String value = "37.541 126.986";
                ContentValues values = new ContentValues();
                values.put("xx", 37.541);
                values.put("yy", 126.986);
                // AsyncTask를 통해 HttpURLConnection 수행.
                NetworkTask networkTask = new NetworkTask(url, values);
                networkTask.execute();
                */

                String t = "[현재 위치 안전도] \n";
                String ts = text;
                String tt = "\n-> '안전' 입니다.";
                AlertDialog.Builder builder = new AlertDialog.Builder(UserLayout.this.getActivity());
                builder.setMessage(t+ts+tt)
                        .setNegativeButton("Back", null)
                        .create()
                        .show();
            }
        });

        return rootView;
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            AlertDialog.Builder builder = new AlertDialog.Builder(UserLayout.this.getActivity());
            builder.setMessage(""+s)
                    .setNegativeButton("Back", null)
                    .create()
                    .show();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        MapFragment fragment = (MapFragment)getChildFragmentManager().findFragmentById(R.id.map);

        fragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        LatLng marker = new LatLng(37.2843,127.0444);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker,15));

        googleMap.addMarker(new MarkerOptions().title("Here is safety!").position(marker));

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            public boolean onMarkerClick(Marker marker) {
                String pre = "[현재위치]";
                text = "\n latitude ="
                        + marker.getPosition().latitude + ", longitude ="
                        + marker.getPosition().longitude+"\n";
                AlertDialog.Builder builder = new AlertDialog.Builder(UserLayout.this.getActivity());
                builder.setMessage(pre+text)
                        .setNegativeButton("Back", null)
                        .create()
                        .show();
                return false;
            }

        });
    }

}
