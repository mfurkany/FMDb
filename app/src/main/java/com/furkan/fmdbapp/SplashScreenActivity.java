package com.furkan.fmdbapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config= new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(MenuActivity.class)
                .withBackgroundColor(Color.parseColor("#202020"))
                .withSplashTimeOut(1500)
                .withLogo(R.drawable.splashscreen);

        View easySplashScreen= config.create();
        setContentView(easySplashScreen);

    }
}
