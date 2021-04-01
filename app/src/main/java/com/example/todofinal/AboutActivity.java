package com.example.todofinal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AboutActivity extends AppCompatActivity {
    private static final String APP_RATE_FRAGMENT_SATE = "state_of_app_rating_fragment";
    private boolean isAppRateFragmentDisplayed = false;

    // create and return a intent that launches this activity
    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

//        if (savedInstanceState != null) {
//            isAppRateFragmentDisplayed = savedInstanceState.getBoolean(APP_RATE_FRAGMENT_SATE);
//
//            if (isAppRateFragmentDisplayed) {
//                // if the fragment is displayed, show the fragment
//                displayFragment();
//            } else {
//                closeFragment();
//            }
//        }
    }

    public void addRemoveFragment(View view) {
        if (isAppRateFragmentDisplayed) {
            closeFragment();
        } else {
            displayFragment();
        }
    }

    public void displayFragment() {
        RateAppFragment rateAppFragment = new RateAppFragment();

        // get the fragment manager and start a transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // add the simple fragment
        fragmentTransaction.add(R.id.app_rating_fragment, rateAppFragment).addToBackStack(null).commit();

        // set boolean flag to indicate fragment is open
        isAppRateFragmentDisplayed = true;
    }

    public void closeFragment() {
        // get the fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // check to see if the fragment is already showing
        RateAppFragment rateAppFragment = (RateAppFragment) fragmentManager.findFragmentById(R.id.app_rating_fragment);
        if (rateAppFragment != null) {
            // create and commit the transaction to remove the fragment
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(rateAppFragment).commit();
            isAppRateFragmentDisplayed = false;
        }
    }

    // go back to the calling activity
    public void goBack(View view) {
        finish();
    }
}