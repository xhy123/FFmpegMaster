package com.hhhh.ffmpegmaster.activity;

import android.Manifest;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;

import com.hhhh.ffmpegmaster.R;
import com.hhhh.ffmpegmaster.databinding.ActivityAvfilterBinding;
import com.hhhh.ffmpegmaster.databinding.ActivityGreaytyBinding;

public class AvfilterActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    static {
        System.loadLibrary("native-lib");
    }

    ActivityAvfilterBinding mBingding;
    SurfaceHolder surfaceHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBingding = DataBindingUtil.setContentView(this,R.layout.activity_avfilter);
        surfaceHolder = mBingding.surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 5);
        }

        Log.v("zzw", Environment.getExternalStorageDirectory()
                .getAbsolutePath() );
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                play(surfaceHolder.getSurface());
            }
        }).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public native int play(Object surface);
}
