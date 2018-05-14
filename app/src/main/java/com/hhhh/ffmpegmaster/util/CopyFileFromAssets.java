package com.hhhh.ffmpegmaster.util;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by xhy on 2018/5/11 0011.
 *
 * @author xhy
 * @data 2018/5/11 0011
 */

public class CopyFileFromAssets {
    public static void copy(Context mContext, String ASSETS_NAME,
                            String savePath, String saveName) {
        String filename = savePath + "/" + saveName;

        File dir = new File(savePath);
        // 如果目录不中存在，创建这个目录

        if (!dir.exists())
            dir.mkdir();
        try {
            if (!(new File(filename)).exists()) {
                InputStream is = mContext.getResources().getAssets()
                        .open(ASSETS_NAME);
                FileOutputStream fos = new FileOutputStream(filename);
                byte[] buffer = new byte[7168];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void testCopy(Context context) {
        String path=context.getFilesDir().getAbsolutePath();
        String name="test.txt";
        CopyFileFromAssets.copy(context, name, path, name);
    }
}
