package com.hhhh.ffmpegmaster.util;

import android.content.Context;

/**
 * Created by xhy on 2018/5/11 0011.
 *
 * @author xhy
 * @data 2018/5/11 0011
 */

public class DemoFunctions {

    public final static String TAG="DemoFunctions";
    /**
     * 演示音频和视频合成, 也可以认为是音频替换.
     *
     * 音视频合成:\n把一个纯音频文件和纯视频文件合并成一个mp4格式的多媒体文件, 如果源视频之前有音频,会首先删除音频部分. \n\n
     */
//    public static int demoAVMerge(Context ctx, VideoEditor editor, String srcVideo, String dstPath)
//    {
//        int ret=-1;
//        MediaInfo info=new MediaInfo(srcVideo,false);
//        if(info.prepare())
//        {
//            String video2=srcVideo;
//            String video3=null;
//            //如果源视频中有音频,则先删除音频
//            if(info.isHaveAudio()){
//                video3=SDKFileUtils.createFileInBox(info.fileSuffix);
//                editor.executeDeleteAudio(video2, video3);
//
//                video2=video3;
//            }
//            String audio=CopyDefaultVideoAsyncTask.copyFile(ctx,"aac20s.aac");
//            ret=editor.executeVideoMergeAudio(video2,audio, dstPath);
//            SDKFileUtils.deleteFile(video3);
//        }
//
//        return ret;
//    }
}
