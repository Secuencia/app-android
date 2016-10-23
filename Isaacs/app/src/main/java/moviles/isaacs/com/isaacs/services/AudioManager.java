package moviles.isaacs.com.isaacs.services;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by sfrsebastian on 10/23/16.
 */

public class AudioManager {
    private MediaRecorder mRecorder = null;

    private MediaPlayer mPlayer = null;

    private static AudioManager instance;

    private boolean isRecording;

    private boolean isPlaying;

    private String storagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "audiofiles" + File.separator;

    public static AudioManager getInstance(){
        if(instance == null){
            instance = new AudioManager();
        }
        return instance;
    }

    private AudioManager(){
        File path = new File(storagePath);
        if(!path.exists()){
            path.mkdir();
        }
    }

    public String getFilePath(String fileName){
        return storagePath + fileName;
    }

    public void startRecording(String filePath) {
        if(!isRecording){
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setOutputFile(filePath);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            try {
                mRecorder.prepare();
            } catch (IOException e) {
                Log.e("AudioManager", "Falla grabacion");
            }

            mRecorder.start();
            isRecording = true;
        }
    }

    public void stopRecording() {
        if(isRecording){
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
            isRecording = false;
        }
    }

    public void startPlaying(String filePath) {
        if(!isPlaying){
            mPlayer = new MediaPlayer();
            try {
                mPlayer.setDataSource(filePath);
                mPlayer.prepare();
                mPlayer.start();
            } catch (IOException e) {
                Log.e("AudioManager", "Falla reproducción");
            }
        }
    }

    public void stopPlaying() {
        if(mPlayer != null){
            mPlayer.release();
            mPlayer = null;
        }
    }

    public boolean isRecording() {
        return isRecording;
    }
}
