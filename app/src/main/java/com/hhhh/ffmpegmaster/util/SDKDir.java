package com.hhhh.ffmpegmaster.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by xhy on 2018/5/11 0011.
 *
 * @author xhy
 * @data 2018/5/11 0011
 */

public class SDKDir {
    public static final String TMP_DIR= Environment.getExternalStorageState()+"/sdcard/FFmpegMaster/";

    public static String getPath()
    {
        File file=new File(TMP_DIR);
        if(file.exists()==false){
            file.mkdir();
        }
        return TMP_DIR;
    }
}
