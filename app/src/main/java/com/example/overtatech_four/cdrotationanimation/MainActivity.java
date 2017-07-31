package com.example.overtatech_four.cdrotationanimation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.provider.SyncStateContract;
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
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private static final int DEFAULT_SPEED = 3000;
    private static final int MAX = 15000;
    ObjectAnimator rotationAnim;
    boolean isRotating=false;
    ImageView imageView;
    Button play,pause,stop;
    SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        seekBar=(SeekBar) findViewById(R.id.seek_bar);
        seekBar.setProgress(DEFAULT_SPEED);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //if (i==0||(i*100)==MAX) return;
                int progress=MAX-(i*MAX)/100;
                if (progress==0||progress==MAX||progress<500) return;
                chageRotationSpeed(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
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
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
                    rotationAnim.setDuration(DEFAULT_SPEED);
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
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
    public void chageRotationSpeed(int progress) {
            if (rotationAnim!=null){
                rotationAnim.setDuration(progress);
            }
    }
}
