package com.hhhh.ffmpegmaster.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hhhh.ffmpegmaster.MainActivity;
import com.hhhh.ffmpegmaster.R;
import com.hhhh.ffmpegmaster.util.CopyDefaultVideoAsyncTask;
import com.hhhh.ffmpegmaster.util.SDKFileUtils;
import com.hhhh.ffmpegmaster.videocompress.CompressListener;
import com.hhhh.ffmpegmaster.videocompress.Compressor;
import com.netcompss.ffmpeg4android.CommandValidationException;
import com.netcompss.ffmpeg4android.GeneralUtils;
import com.netcompss.ffmpeg4android.Prefs;
import com.netcompss.loader.LoadJNI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

import static com.hhhh.ffmpegmaster.util.SDKFileUtils.fileExist;

public class AVEditorDemoActivity extends AppCompatActivity {

    private String srcVideo;
    private Compressor mCompressor;
    private String mvideoPath;
    private LoadJNI vk;
    private String workLog;
    private String vkLogPath;

    private boolean commandValidationFailedFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aveditor_demo);
        currentInputVideoPath = getIntent().getStringExtra("videopath1");
        mCompressor = new Compressor(this);
        workLog = getApplicationContext().getFilesDir() + "/";
        vkLogPath = workLog + "vk.log";

        findViewById(R.id.id_test_cmd_btn).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ByteOrderMark")
            @Override
            public void onClick(View v) {

               // execCommand(cmd);
                //1.常规的压缩代码
               // cmd = "ffmpeg -y -i "+ currentInputVideoPath+" -strict experimental -s 320x240 -r 30 -aspect 4:3 -ab 48000 -ac 2 -ar 22050 -vcodec mpeg4 -b 2097152 "+currentOutputVideoPath;
                //2.视频画面局部裁剪
                // cmd = "ffmpeg -y -i "+currentInputVideoPath+" -strict experimental -vf crop=0:0:0:5 -s 320x240 -r 15 -aspect 3:4 -ab 12288 -vcodec mpeg4 -b 2097152 -sample_fmt s16 "+currentOutputVideoPath;

                //3.旋转90度
//                currentOutputVideoPath = "/mnt/sdcard/videokit/outxuanzhuan90du"+System.currentTimeMillis()+".mp4";
//                cmd = "ffmpeg -y -i "+currentInputVideoPath+" -strict experimental -vf transpose=1 -s 160x120 -r 30 -aspect 4:3 -ab 48000 -ac 2 -ar 22050 -b 2097k "+currentOutputVideoPath;

                //4.从视频内提取一帧保存为图片
//                currentOutputVideoPath = "/mnt/sdcard/videokit/tiquyizhenbaocun"+System.currentTimeMillis()+".jpg";
//                cmd = "ffmpeg -y -i "+currentInputVideoPath+" -strict experimental -an -r 1/2 -ss 00:00:00.000 -t 00:00:03 "+currentOutputVideoPath;

                //5.从视频中提取音频文件
//                currentOutputVideoPath = "/mnt/sdcard/videokit/tiquyinpin"+System.currentTimeMillis()+".mp3";
//                cmd = "ffmpeg -y -i "+currentInputVideoPath+" -strict experimental -vn -ar 44100 -ac 2 -ab 256k -f mp3 "+currentOutputVideoPath;

                //6.对视频中的音频重新编码
//                currentOutputVideoPath = "/mnt/sdcard/videokit/chongxinbianmayinpin"+System.currentTimeMillis()+".mp4";
//                cmd = "ffmpeg -y -i "+currentInputVideoPath+" -strict experimental -vcodec copy -acodec libmp3lame -ab 64k -ac 2 -b 1200000 -ar 22050 "+currentOutputVideoPath;

//                //7.改变视频的分辨率，4:3 or 16:9(有问题)
//                currentOutputVideoPath = "/mnt/sdcard/videokit/gaibianfenbianlv"+System.currentTimeMillis()+".mp4";
//                cmd = "ffmpeg -y -i "+currentInputVideoPath+" -strict experimental -vf transpose=3 -s 320x240 -r 15 -aspect 3:4 -ab 12288 -vcodec mpeg4 -b 2097152 -sample_fmt s16 "+currentOutputVideoPath;


                //8.从视频中剪辑一段时间的视频
//                currentOutputVideoPath = "/mnt/sdcard/videokit/jianjiyiduanshipin"+System.currentTimeMillis()+".mp4";
//                cmd = "ffmpeg -ss 00:00:01.000 -y -i "+currentInputVideoPath+" -strict experimental -t 00:00:12.000 -s 320x240 -r 15 -vcodec mpeg4 -b 2097152 -ab 48000 -ac 2 -b 2097152 -ar 22050 "+currentOutputVideoPath;

                //9.视频添加水印
//                currentOutputVideoPath = "/mnt/sdcard/videokit/watermark"+System.currentTimeMillis()+".mp4";
//                String[] complexCommand = {"ffmpeg","-y" ,"-i", currentInputVideoPath,
//                        "-strict","experimental", "-vf", "movie=/sdcard/videokit/watermark.png [watermark]; [in][watermark] overlay=main_w-overlay_w-10:10 [out]","-s", "320x240","-r", "30", "-b", "15496k", "-vcodec", "mpeg4","-ab", "48000", "-ac", "2", "-ar", "22050",
//                        currentOutputVideoPath};

                //10.添加字幕(有问题)
//                currentOutputVideoPath = "/mnt/sdcard/videokit/tianjiazimu"+System.currentTimeMillis()+".mp4";
//                cmd = "ffmpeg -y -i "+currentInputVideoPath+" -i /sdcard/videokit/in.txt -strict experimental -vcodec libx264 -preset ultrafast -crf 24 -scodec copy "+currentOutputVideoPath;

                //11.将一个mp3音频文件转为m4a格式

//                currentOutputVideoPath = "/mnt/sdcard/videokit/mp3tom4a"+System.currentTimeMillis()+".m4a";
//                cmd = "ffmpeg -i "+currentInputVideoPath+" "+currentOutputVideoPath;

                //12.将一个视频和一个音频文件渲染为一个H.264编码的视频（需要额外的编码库）
//                currentOutputVideoPath = "/mnt/sdcard/videokit/mp3tom4a"+System.currentTimeMillis()+".mp4";
//                cmd = "ffmpeg -y -i "+currentInputVideoPath+"-strict experimental -vcodec libx264 -crf 24 -acodec aac "+currentOutputVideoPath;

                //13.添加复古滤镜(有问题)
//                currentOutputVideoPath = "/mnt/sdcard/videokit/addfugulvjing"+System.currentTimeMillis()+".m4a";
//                cmd = "ffmpeg -i "+currentInputVideoPath+"-strict experimental -vf curves=vintage -s 640x480 -r 30 -aspect 4:3 -ab 48000 -ac 2 -ar 22050 -b 2097k -vcodec mpeg4 "+currentOutputVideoPath;

                //14.黑白滤镜
//                currentOutputVideoPath = "/mnt/sdcard/videokit/blackandwhite"+System.currentTimeMillis()+".m4a";
//                cmd = "ffmpeg -i "+currentInputVideoPath+"-strict experimental -vf hue=s=0 -vcodec mpeg4 -b 2097152 -s 320x240 -r 30 "+currentOutputVideoPath;


                //15.更换视频音轨

//                String[] complexCommand = {"ffmpeg","-y" ,"-i", "/sdcard/videokit/sample.mp4","-i", "/sdcard/videokit/in.mp3", "-strict","experimental",
//                        "-map", "0:v", "-map", "1:a",
//                        "-s", "320x240","-r", "30", "-b", "15496k", "-vcodec", "mpeg4","-ab", "48000", "-ac", "2", "-ar", "22050","-shortest","/sdcard/videokit/out.mp4"};


                //16.GIF图片转视频，或者视频转为GIF图片

                /*//Compress animated gif:
                fmpeg -f gif -i /sdcard/videokit/pic1.gif -strict experimental -r 10 /sdcard/videokit/pic1_1.gif

                //Convert mp4 to animated gif:
                ffmpeg -y -i /sdcard/videokit/in.mp4 -strict experimental -r 20 /sdcard/videokit/out.gif

                //Convert animated gif to mp4:
                ffmpeg -y -f gif -i /sdcard/videokit/infile.gif /sdcard/videokit/outfile.mp4*/

                //17.加水印，音频替换
               // String commandStr = "ffmpeg -y -loop 1 -i /sdcard/videokit/pic001.jpg -i /sdcard/videokit/in.mp3 -strict experimental -s 1270x720 -r 25 -aspect 16:9 -vcodec mpeg4 -vcodec mpeg4 -ab 48000 -ac 2 -b 2097152 -ar 22050 -shortest /sdcard/videokit/out2.mp4";


                //18.将两个视频并排显示

//                String[] complexCommand = {"ffmpeg","-y" ,"-i", "/sdcard/Movies/sample.mp4","-i", "/sdcard/Movies/sample2.mp4", "-strict","experimental",
//                        "-filter_complex",
//                        "[0:v:0]pad=iw*2:ih[bg];" +
//                                "[bg][1:v:0]overlay=w",
//                        "-s", "320x240","-r", "30", "-b", "15496k", "-vcodec", "mpeg4","-ab", "48000", "-ac", "2", "-ar", "22050",
//                        "/sdcard/videokit/out.mp4"};

                //19.加快视频和音频文件播放速度，也就是时间压缩
                //String[] complexCommand = {"ffmpeg","-y" ,"-i", "/sdcard/videokit/in.mp4","-strict","experimental", "-filter_complex", "[0:v]setpts=0.5*PTS[v];[0:a]atempo=2.0[a]","-map","[v]","-map","[a]", "-b", "2097k","-r","60", "-vcodec", "mpeg4", "/sdcard/videokit/out.mp4"};

                //Advanced filtering
                //String[] complexCommand = {"ffmpeg","-y" ,"-i", "/sdcard/videokit/in.mp4","-strict","experimental", "-vf", "crop=iw/2:ih:0:0,split[tmp],pad=2*iw[left]; [tmp]hflip[right]; [left][right] overlay=W/2", "-vb", "20M", "-r", "23.956", "/sdcard/videokit/out.mp4"};

                //20.将一组图片序列渲染为视频文件，图片必须按照规律的格式有序。 此命令严格要求图片序列尺寸统一，对应输出视频的分辨率
                /*currentOutputVideoPath = "/mnt/sdcard/videokit/xuanranjpg"+System.currentTimeMillis()+".mp4";
                cmd = "ffmpeg -y -r 0.5 -i /sdcard/videokit/pic%03d.jpg "+currentOutputVideoPath;*/

                //21.添加音频，png也可以，这里的图片尺寸应该为320x240
                //ffmpeg -y -r 1 -i /sdcard/videokit/pic00%d.jpg -i /sdcard/videokit/in.mp3 -strict experimental -ar 44100 -ac 2 -ab 256k -b 2097152 -ar 22050 -vcodec mpeg4 -b 2097152 -s 320x240 /sdcard/videokit/out.mp4


                //22.将两个同尺寸的视频文件拼接并输出

//                String[] complexCommand = {"ffmpeg","-y","-i", "/sdcard/videokit/in1.mp4", "-i", "/sdcard/videokit/in2.mp4", "-strict","experimental", "-filter_complex", "[0:0] [0:1] [1:0] [1:1] concat=n=2:v=1:a=1", "/sdcard/videokit/out.mp4"};
//                //
//                //23.拼接不同编码，不同尺寸，不同帧率，不同纵横比的视频
//
//                String[] complexCommand = {"ffmpeg","-y","-i","/storage/emulated/0/videokit/sample.mp4",
//                        "-i","/storage/emulated/0/videokit/in.mp4","-strict","experimental",
//                        "-filter_complex",
//                        "[0:v]scale=640x480,setsar=1:1[v0];[1:v]scale=640x480,setsar=1:1[v1];[v0][0:a][v1][1:a] concat=n=2:v=1:a=1",
//                        "-ab","48000","-ac","2","-ar","22050","-s","640x480","-r","30","-vcodec","mpeg4","-b","2097k","/storage/emulated/0/vk2_out/out.mp4"};



                //24.为视频添加淡入和淡出过渡效果
                //String[] complexCommand = {"ffmpeg","-y" ,"-i", "/sdcard/videokit/in.m4v","-acodec", "copy", "-vf", "fade=t=in:st=0:d=5, fade=t=out:st=20:d=5", "/sdcard/videokit/out.mp4"};


                //25.添加用PS的曲线制作的滤镜，也就是可以自己制作滤镜 在PS中，选择图像>调整>曲线，调出曲线窗口，或者直接按快捷键Ctrl+N，
                // 调出曲线窗口 将调整好的效果导出为acv文件，将其路径添加到ffmpeg命令中
                //String[] complexCommand={"ffmpeg","-y","-i","/storage/emulated/0/vk2/in.mp4","-strict","experimental","-vf","curves=psfile=/storage/emulated/0/videokit/sepia.acv","-b","2097k","-vcodec","mpeg4","-ab","48000","-ac","2","-ar","22050","/storage/emulated/0/videokit/out.mp4"}﻿;

                //26.色彩通道滤镜，类似PS中的颜色通道

//                String[] complexCommand = {"ffmpeg","-y" ,"-i", "/sdcard/videokit/sample.mp4","-strict", "experimental", "-filter_complex",
//                        "[0:v]colorchannelmixer=.393:.769:.189:0:.349:.686:.168:0:.272:.534:.131[colorchannelmixed];[colorchannelmixed]eq=1.0:0:1.3:2.4:1.0:1.0:1.0:1.0[color_effect]",
//                        "-map", "[color_effect]","-map", "0:a", "-vcodec", "mpeg4","-b", "15496k", "-ab", "48000", "-ac", "2", "-ar", "22050","/sdcard/videokit/out.mp4"};
//
               //27.将视频导出图片序列，在由图片序列合成视频
//                ffmpeg 输入一个叫 toolba.mkv 的文件，让它以每秒一帧的速度，
//                从第 26 秒开始一直截取 7 秒长的时间，截取到的每一幅图像，都用 3 位数字自动生成从小到大的文件名
                currentOutputVideoPath = "/mnt/sdcard/videokit/%d"+".jpg";
                cmd = "ffmpeg -i "+currentInputVideoPath+" -r 1 -ss 00:00:01 -t 00:00:05 %03d.png";

                //execCommand(cmd);


                vk = new LoadJNI();
                try {


                    vk.run(GeneralUtils.utilConvertToComplex(cmd), workLog, getApplicationContext());

                    Log.i(Prefs.TAG, "vk.run finished.");
                    // copying vk.log (internal native log) to the videokit folder
                    GeneralUtils.copyFileToFolder(vkLogPath, currentOutputVideoPath);
                } catch (CommandValidationException e) {
                    Log.e(Prefs.TAG, "vk run exeption.", e);
                    commandValidationFailedFlag = true;
                } catch (Throwable e) {
                    Log.e(Prefs.TAG, "vk run exeption.", e);
                }
                finally {

                }

                // finished Toast
                String rc = null;
                if (commandValidationFailedFlag) {
                    rc = "Command Vaidation Failed";
                }
                else {
                    rc = GeneralUtils.getReturnCodeFromLog(vkLogPath);
                }
                final String status = rc;

            }
        });
    }


    private String currentInputVideoPath = "";
    private String currentOutputVideoPath = "/mnt/sdcard/videokit/out"+System.currentTimeMillis()+".mp4";
    String cmd = "-y -i " + currentInputVideoPath + " -strict experimental -vf transpose=1 -s 160x120 -r 30 -aspect 4:3 -ab 48000 -ac 2 -ar 22050 -b 2097k "+currentOutputVideoPath;

    private MaterialDialog mMaterialDialog;
    private void execCommand(String cmd) {
        File mFile = new File(currentOutputVideoPath);
        if (mFile.exists()) {
            mFile.delete();
        }
        mCompressor.execCommand(cmd, new CompressListener() {
            @Override
            public void onExecSuccess(String message) {
                Log.i("AVEditorDemoActivity", "success " + message+"     "+mvideoPath);
                mvideoPath = currentOutputVideoPath;


            }

            @Override
            public void onExecFail(String reason) {
                Log.i("AVEditorDemoActivity", "fail " + reason);

            }

            @Override
            public void onExecProgress(String message) {
                Log.i("AVEditorDemoActivity", "progress " + message);



            }
        });
    }
}
