package com.hhhh.ffmpegmaster.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hhhh.ffmpegmaster.R;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class FileExplorerActivity extends ListActivity {

    private static final String TAG = "FFMpegFileExplorer";
    private String mCurrentPath=null;
    private String 			mRoot = "/sdcard";
    private TextView mTextViewLocation;
    private File[]			mFiles;
    private static final String[] VIDEO_EXTS=new String[] {
            ".mp4",".flv","mov","m4v"
    };
    private static final String[] AUDIO_EXTS=new String[] {
            ".mp3",".aac","wav"
    };

    private static boolean isSelectVideo=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_explorer);
        mTextViewLocation = (TextView) findViewById(R.id.textview_path);

        String mode=getIntent().getStringExtra("SELECT_MODE");
        if(mode.equals("video")){
            setTitle("选择视频文件");
            isSelectVideo=true;
        }else{
            setTitle("选择音频文件");
            isSelectVideo=false;
        }
        getDirectory(mRoot);
    }

    @Override
    public void onBackPressed() {
        if(mCurrentPath!=null && !mCurrentPath.equals(mRoot)){
            File f = new File(mCurrentPath);
            mCurrentPath=f.getParent();
            getDirectory(mCurrentPath);
            return ;
        }
        super.onBackPressed();
    }
    protected static boolean checkExtension(File file) {
        if(isSelectVideo){
            for(int i=0;i<VIDEO_EXTS.length;i++) {
                if(file.getName().indexOf(VIDEO_EXTS[i]) > 0) {
                    return true;
                }
            }
        }else{
            for(int i=0;i<AUDIO_EXTS.length;i++) {
                if(file.getName().indexOf(AUDIO_EXTS[i]) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private void sortFilesByDirectory(File[] files) {
        Arrays.sort(files, new Comparator<File>() {

            public int compare(File f1, File f2) {
                return Long.valueOf(f1.length()).compareTo(f2.length());
            }
        });
    }

    private void getDirectory(String dirPath) {
        try {
            mTextViewLocation.setText("Location: " + dirPath);

            File f = new File(dirPath);
            File[] temp = f.listFiles();
            sortFilesByDirectory(temp);

            File[] files = null;
            if(!dirPath.equals(mRoot)) {
                files = new File[temp.length + 1];
                System.arraycopy(temp, 0, files, 1, temp.length);
                files[0] = new File(f.getParent());
            } else {
                files = temp;
            }
            mFiles = files;
            setListAdapter(new FileExplorerAdapter(this, files, temp.length == files.length));
        } catch(Exception ex) {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) { //当list里的一个item被点击的时候调用
        File file = mFiles[position];
        mCurrentPath=file.getAbsolutePath();
        if (file.isDirectory()) {
            if (file.canRead())
                getDirectory(mCurrentPath);
            else {
                Toast.makeText(this, "[" + file.getName() + "] folder can't be read!", Toast.LENGTH_LONG).show();
            }
        } else {
            if(!checkExtension(file)) {
                Toast.makeText(this, "Not Support This File extensions!", Toast.LENGTH_LONG).show();
                return;
            }else /*if(MediaInfo.isSupport(file.getAbsolutePath()))*/{
                startPlayer(file.getAbsolutePath());
            }
            //暂时不做格式判断
//            else{
//                Toast.makeText(this, "Not Support This File extensions!", Toast.LENGTH_LONG).show();
//            }
        }
    }

    private void startPlayer(String filePath) {
        Intent i = new Intent(); ///这里发送出去，
        Bundle b = new Bundle();
        b.putString("SELECT_VIDEO", filePath);
        i.putExtras(b);
        this.setResult(RESULT_OK, i);
        this.finish();

//    	Intent i = new Intent(this, VideoPlayer.class);
//    	i.putExtra("videofilename", filePath);
//    	startActivity(i);
    }
}
