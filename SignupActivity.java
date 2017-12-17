package com.thkang.svdr;

import android.app.AlertDialog;
import android.app.Fragment;
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
import android.widget.Toast;

/**
 * Created by thkan on 2017-12-03.
 */

public class SignupActivity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.activity_signup, container, false);

        final DBHelper helper = new DBHelper(this.getActivity());

        final EditText etUsername = (EditText) rootView.findViewById(R.id.etName);
        final EditText etUserid = (EditText) rootView.findViewById(R.id.etUserid);
        final EditText etPassword = (EditText) rootView.findViewById(R.id.etfPassword);
        final EditText etConfirmpassword = (EditText) rootView.findViewById(R.id.etConfirmpassword);
        final EditText etProtectname = (EditText) rootView.findViewById(R.id.etProtectname);
        final EditText etPhone = (EditText) rootView.findViewById(R.id.etPhone);
        final Button bSignup = (Button) rootView.findViewById(R.id.bSignup);


            bSignup.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    final String Username = etUsername.getText().toString();
                    final String Userid = etUserid.getText().toString();
                    final String Password = etPassword.getText().toString();
                    final String Confirmpassword = etConfirmpassword.getText().toString();
                    final String Protectname = etProtectname.getText().toString();
                    final String Phone = etPhone.getText().toString();

                    if(!Password.equals(Confirmpassword)) {
                        //pop up msg
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this.getActivity());
                        builder.setMessage("Passwords don't match!")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                    Contact c = new Contact();

                    c.setName(Username);
                    c.setUserid(Userid);
                    c.setPassword(Password);
                    c.setProtectname(Protectname);
                    c.setPhone(Phone);

                    helper.insertContact(c);

                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this.getActivity());
                    builder.setMessage("Sign Up Successes!")
                            .setNegativeButton("Back", null)
                            .create()
                            .show();

                    Fragment fragment  = new LoginActivity();

                    if(fragment != null){
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_main, fragment);
                        ft.commit();
                    }

                }
            });
        return rootView;
    }
}
