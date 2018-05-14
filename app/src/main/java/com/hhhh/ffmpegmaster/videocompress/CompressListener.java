package com.hhhh.ffmpegmaster.videocompress;

/**
 * Created by xhy on 2018/5/11 0011.
 *
 * @author xhy
 * @data 2018/5/11 0011
 */

public interface CompressListener {
    public void onExecSuccess(String message);
    public void onExecFail(String reason);
    public void onExecProgress(String message);
}
