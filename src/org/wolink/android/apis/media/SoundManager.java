package org.wolink.android.apis.media;

import java.util.HashMap;
import java.util.Vector;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;

public class SoundManager {
    private SoundPool mSoundPool;
    private HashMap<String, Integer> mSoundPoolMap;
    private AudioManager mAudioManager;
    private Context mContext;
    private Handler mHandler = new Handler();
    private Vector<String> mSoundQueue = new Vector<String>();
    private int curStreamId;
    static private SoundManager _instance;

    /**
     * Requests the instance of the Sound Manager and creates it if it does not
     * exist.
     *
     * @return Returns the single instance of the SoundManager
     */
    static synchronized public SoundManager getInstance() {
        if (_instance == null)
            _instance = new SoundManager();
        return _instance;
    }
    
    private SoundManager() {
    	// Nothing
    }
    
    /**
     * Initializes the storage for the sounds
     *
     * @param theContext The Application context
     */

    public void initSounds(Context theContext) {
        mContext = theContext;
        mSoundPool = new SoundPool(1,
                AudioManager.STREAM_MUSIC, 0);
        mSoundPoolMap = new HashMap<String, Integer>();
        mAudioManager = (AudioManager) mContext
                .getSystemService(Context.AUDIO_SERVICE);
    }

    /**
     * Add a new Sound to the SoundPool
     *
     * @param key - The Sound Index for Retrieval
     * @param SoundID - The Android ID for the Sound asset.
     */

    public void addSound(String key, int SoundID) {
        mSoundPoolMap.put(key, mSoundPool.load(mContext, SoundID, 1));
    }
    
    /**
     *
     * @param key the key we need to get the sound later
     * @param afd  the file store in the asset
     */
    public void addSound(String key, AssetFileDescriptor afd) {
        mSoundPoolMap.put(key, mSoundPool.load(
                afd.getFileDescriptor(),
                afd.getStartOffset(), afd.getLength(), 1));
    }
   
    /**
     * play the sound loaded to the SoundPool by the key we set
     * @param key  the key in the map
     */
    public void playSound(String key) {
        stopSound();
        mSoundQueue.add(key);
        playNextSound();
    }
   
	/**
	 * play the sounds have loaded in SoundPool
	 * @param keys the files key stored in the map
	 * @throws InterruptedException
	 */
    public void playSeqSounds(String keys[]) {
    	stopSound();
    	for(String key : keys) {
    		mSoundQueue.add(key);
    	}
    	playNextSound();
    }

    /**
     * Stop the current sound
     */
    public void stopSound() {
    	mHandler.removeCallbacks(mPlayNext);
    	mSoundQueue.clear();
        mSoundPool.stop(curStreamId);
    }

    /**
     * Deallocates the resources and Instance of SoundManager
     */
    public void cleanup() {
    	stopSound();
        if (mSoundPoolMap.size() > 0) {
            for (String key : mSoundPoolMap.keySet()) {
                mSoundPool.unload(mSoundPoolMap.get(key));
            }
        }
        mSoundPool.release();
        mSoundPool = null;
        mSoundPoolMap.clear();
        _instance = null;
    }

    private void playNextSound() {
    	if (mSoundQueue.isEmpty() != true) {
	    	String key = mSoundQueue.remove(0);
	    	int soundId = mSoundPoolMap.get(key);
	        float streamVolume = 0.0f;
	        streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
	        streamVolume /= mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	        
	        curStreamId = mSoundPool.play(soundId, streamVolume, streamVolume, 1, 0, 1.0f); 
	        
	        mHandler.postDelayed(mPlayNext, 500);
	    }
    }
    
	private Runnable mPlayNext = new Runnable() {
		public void run() {
			mSoundPool.stop(curStreamId);
			playNextSound();			
		}
	};

}