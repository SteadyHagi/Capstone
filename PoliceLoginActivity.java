package com.thkang.svdr;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by thkan on 2017-11-23.
 */

public class PoliceLoginActivity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //View rootView = (RelativeLayout)inflater.inflate(R.layout.content_main, container, false);

        Bundle extras = getActivity().getIntent().getExtras();
        CharSequence s = "전달 받은 값은 ";
        int id=0;

        if (extras == null) {
            s = "error";
        }
        else {
            id = extras.getInt("notificationId");
        }
        NotificationManager nm =
                (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        //노티피케이션 제거
        if(id==101)
            nm.cancel(id);

        RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.activity_login_police, container, false);

        final EditText etPUsername = (EditText) rootView.findViewById(R.id.etPUsername);
        final EditText etPPassword = (EditText) rootView.findViewById(R.id.etPPassword);
        final Button bPLogin = (Button) rootView.findViewById(R.id.bPLogin);

        bPLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String Userid = etPUsername.getText().toString();
                final String password = etPPassword.getText().toString();
                DBHelper helper = new DBHelper(PoliceLoginActivity.this.getActivity());

                String pass = helper.searchPassword(Userid);

                if(password.equals(pass)){
                    Fragment fragment = new PoliceLayout();
                    if(fragment != null){
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_main, fragment);
                        ft.commit();
                    }
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(PoliceLoginActivity.this.getActivity());
                    builder.setMessage("Login Failed")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                }
            }
        });
        return rootView;
    }

}
