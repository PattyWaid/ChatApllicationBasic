package com.example.prathamesh.splashscreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txt_splash;
    private ImageView img_splash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_splash = (TextView) findViewById(R.id.txt_splash);
        img_splash = (ImageView) findViewById(R.id.img_splash);
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.splash_animation);
        txt_splash.startAnimation(myanim);
        Animation img = new TranslateAnimation(Animation.START_ON_FIRST_FRAME,300,Animation.ABSOLUTE,Animation.ABSOLUTE);
        img.setDuration(4000);
        img.setFillAfter(true);
        img_splash.startAnimation(img);
        final Intent myIntent  = new Intent(this,Register.class);
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(myIntent);
                    finish();

                }
            }
        };
        timer.start();
    }
}
