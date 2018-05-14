package com.hhhh.ffmpegmaster.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hhhh.ffmpegmaster.R;
import com.hhhh.ffmpegmaster.databinding.ActivityGreaytyBinding;

public class GreaytyActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityGreaytyBinding mBinding;
    private final static int SELECT_FILE_REQUEST_CODE=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_greayty);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_greayty);
        mBinding.textAvfilter.setOnClickListener(this);
        mBinding.textCutvideo.setOnClickListener(this);
        mBinding.idMainSelectVideo.setOnClickListener(this);
        mBinding.textProAudioVedio.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_main_select_video:
                startSelectVideoActivity();
                break;
            case R.id.text_pro_audio_vedio:

                Intent intent=new Intent(GreaytyActivity.this,AVEditorDemoActivity.class);

                intent.putExtra("videopath1",mBinding.idMainTvvideopath.getText().toString());
//                intent.putExtra("outvideo", demo.isOutVideo);
//                intent.putExtra("outaudio", demo.isOutAudio);
//                intent.putExtra("demoID", demo.mHintId);
//                intent.putExtra("textID", demo.mTextId);
                startActivity(intent);

                break;
        }
    }

    private void startSelectVideoActivity() {
        Intent i = new Intent(this, FileExplorerActivity.class);
        i.putExtra("SELECT_MODE", "video");
        startActivityForResult(i,SELECT_FILE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                if(requestCode==SELECT_FILE_REQUEST_CODE){
                    Bundle b = data.getExtras();
                    String string = b.getString("SELECT_VIDEO");
                    Log.i("sno","SELECT_VIDEO is:"+string);
                    if(mBinding.idMainTvvideopath!=null)
                        mBinding.idMainTvvideopath.setText(string);
                }
                break;

            default:
                break;
        }
    }
}
