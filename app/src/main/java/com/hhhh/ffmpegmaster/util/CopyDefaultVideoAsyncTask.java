package com.hhhh.ffmpegmaster.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by xhy on 2018/5/11 0011.
 *
 * @author xhy
 * @data 2018/5/11 0011
 */

public class CopyDefaultVideoAsyncTask extends AsyncTask<Object, Object, Boolean> {
    private ProgressDialog mProgressDialog;
    private Context mContext=null;
    private TextView tvHint;
    private String fileName;

    /**
     * 同步拷贝, 阻塞执行.
     * @param ctx
     * @param fileName
     * @return
     */
    public static String copyFile(Context ctx,String fileName)
    {
        String str=SDKDir.TMP_DIR+fileName;
        if(SDKFileUtils.fileExist(str)==false){
            CopyFileFromAssets.copy(ctx, fileName, SDKDir.TMP_DIR, fileName);
        }
        return str;
    }
    /**
     *
     * @param ctx
     * @param tvhint  拷贝后, 把拷贝到的目标完整路径显示到这个TextView上.
     * @param file   需要拷贝的文件名字.
     */
    public CopyDefaultVideoAsyncTask(Context ctx,TextView tvhint,String file){
        mContext=ctx;
        tvHint=tvhint;
        fileName=file;
    }
    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("正在拷贝...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        super.onPreExecute();
    }
    @Override
    protected synchronized Boolean doInBackground(Object... params) {
        // TODO Auto-generated method stub

        //copy ping20s.mp4
        String str=SDKDir.TMP_DIR+fileName;
        if(SDKFileUtils.fileExist(str)==false){
            CopyFileFromAssets.copy(mContext, fileName, SDKDir.TMP_DIR, fileName);
        }
        return null;
    }
    @Override
    protected void onPostExecute(Boolean result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        if( mProgressDialog!=null){
            mProgressDialog.cancel();
            mProgressDialog=null;
        }

        String str=SDKDir.TMP_DIR+fileName;
        if(SDKFileUtils.fileExist(str)){
            Toast.makeText(mContext, "默认视频文件拷贝完成.视频样片路径:"+str, Toast.LENGTH_SHORT).show();
            if(tvHint!=null)
                tvHint.setText(str);
        }else{
            Toast.makeText(mContext, "抱歉! 默认视频文件拷贝失败,请联系我们:视频样片路径:"+str, Toast.LENGTH_SHORT).show();
        }


    }
}
