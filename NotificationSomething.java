package com.thkang.svdr;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by thkan on 2017-12-17.
 */

public class NotificationSomething extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        String extras = getIntent().getStringExtra("menuFragment");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (extras != null) {

            // Here we can decide what do to -- perhaps load other parameters from the intent extras such as IDs, etc
            if (extras.equals("Police")) {

                Fragment favoritesFragment = new PoliceLoginActivity();

                fragmentTransaction.replace(R.id.content_main, favoritesFragment);
                fragmentTransaction.commit();
            }
        }
        NotificationManager nm =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    }
}
