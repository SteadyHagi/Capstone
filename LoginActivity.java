package com.thkang.svdr;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * Created by thkan on 2017-11-23.
 */

public class LoginActivity extends Fragment implements OnMapReadyCallback {

    private Fragment userlayout = new UserLayout();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //View rootView = (RelativeLayout)inflater.inflate(R.layout.content_main, container, false);


        RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.activity_login, container, false);

        final EditText etUsername = (EditText) rootView.findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) rootView.findViewById(R.id.etPassword);
        final Button bLogin = (Button) rootView.findViewById(R.id.bLogin);

        bLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String Userid = etUsername.getText().toString();
                final String password = etPassword.getText().toString();


                DBHelper helper = new DBHelper(LoginActivity.this.getActivity());

                String pass = helper.searchPassword(Userid);

                if(password.equals(pass)){
                    //Intent intent = new Intent(getActivity(), UserLayout.class);
                  //  intent.putExtra("Userid", Userid);
                    //startActivity(intent);

                    Fragment fragment  = new UserLayout();

                    if(fragment != null){
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_main, fragment);
                        ft.commit();
                    }
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this.getActivity());
                    builder.setMessage("Login Failed")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                }

            }
        });

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
