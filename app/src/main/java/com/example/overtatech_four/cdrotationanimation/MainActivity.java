package com.example.overtatech_four.cdrotationanimation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ObjectAnimator rotationAnim;
    boolean isRotating=false;
    ImageView imageView;
    Button play,pause,stop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        imageView=(ImageView) findViewById(R.id.image);
        play=(Button) findViewById(R.id.play);
        pause=(Button) findViewById(R.id.pause);
        stop=(Button) findViewById(R.id.stop);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRotating)
                    applyAnimation();
            }
        });
        
        pause.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if (isRotating){
                    pause();
                    pause.setText("RESUME");
                }else {
                    resume();
                    pause.setText("PAUSE");
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ObjectAnimator scaleAnimX = ObjectAnimator.ofFloat(imageView, "scaleX", 1.0f, 0.95f);
                ObjectAnimator scaleAnimY = ObjectAnimator.ofFloat(imageView, "scaleY", 1.0f, 0.95f);
                AnimatorSet animatorSet=new AnimatorSet();
                scaleAnimX.setRepeatCount(1);
                scaleAnimX.setRepeatMode(ValueAnimator.REVERSE);
                scaleAnimY.setRepeatCount(1);
                scaleAnimY.setRepeatMode(ValueAnimator.REVERSE);
                animatorSet.setDuration(200);
                animatorSet.playTogether(scaleAnimX,scaleAnimY);
                animatorSet.start();

                if (rotationAnim==null){
                    rotationAnim = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f);
                    rotationAnim.setDuration(3000);
                    rotationAnim.setInterpolator(new LinearInterpolator());
                    rotationAnim.setRepeatCount(ValueAnimator.INFINITE);
                }

                if (isRotating){
                    rotationAnim.pause();
                    isRotating=false;
                }else{
                    rotationAnim.resume();
                    isRotating=true;
                }
            }
        });

    }
    public void applyAnimation(){
        isRotating = true;
        rotationAnim = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f);
        rotationAnim.setDuration(3000);
        rotationAnim.setInterpolator(new LinearInterpolator());
        rotationAnim.setRepeatCount(ValueAnimator.INFINITE);
        rotationAnim.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void pause(){
        if (isRotating){
            if (rotationAnim!=null)
            rotationAnim.pause();
            isRotating=false;
        }

    }
    public void cancel(){
        if (isRotating){
            if (rotationAnim!=null) {
                rotationAnim.cancel();
                isRotating = false;
            }
        }

    }
    public void resume(){
        if (rotationAnim!=null) {
            rotationAnim.resume();
            isRotating=true;
        }else {
            applyAnimation();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
