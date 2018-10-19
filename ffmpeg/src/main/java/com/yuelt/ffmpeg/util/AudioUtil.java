package com.yuelt.ffmpeg.util;

import android.util.Log;

import com.yuelt.ffmpeg.tool.FFmpegCmd;

import java.io.File;

/**
 * description $desc$
 * author      Created by lxzh
 * date        2018/10/18
 */
public class AudioUtil {
    public interface OnAudioHandleListener{
        void onAudioHandleBegin(File srcFile);
        void onAudioHandleEnd(File dstFile, long duration);
    }

    public enum AmplifyMode{
        Auto,
        Large,
        Medium,
        Low
    }

    private final static String TAG="AudioUtil";

    public static void amplify(final File file,final long duration,AmplifyMode mode,final OnAudioHandleListener listener){
        String tmpName=FileUtil.createTempFile(file);
        final File tmpFile=new File(tmpName);
        FFmpegCmd.execute(FFmpegUtil.amplifyAudio(file.getAbsolutePath(), tmpName), new FFmpegCmd.OnHandleListener() {
            @Override
            public void onBegin() {
                Log.i(TAG,"Start amplify audio**********");
                listener.onAudioHandleBegin(file);
            }

            @Override
            public void onEnd(int result) {
                Log.i(TAG,"Amplify audio finished**********result="+result);
                try {
                    file.delete();
                }catch (Exception ex){
                    Log.i(TAG,"Delete original file failed!");
                }
                listener.onAudioHandleEnd(tmpFile,duration);
            }
        });

    }
}
