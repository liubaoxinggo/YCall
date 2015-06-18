package com.fhit.ycall.util;

import java.util.HashMap;
import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;

/**
 * ����˫����Ƶ�źŵ���
 * @author liubaoxing
 *
 */
public class DTMFUtil {

	private static final int DTMF_DURATION_MS = 120; // �����Ĳ���ʱ�� ,��Ϊ-1 ���������
	
	private static final int DTMF_TONE_VOLUME = 100; // �����Ĵ�С����Χ��0,100��

    private Object mToneGeneratorLock = new Object(); // ������������

    private ToneGenerator mToneGenerator;             // ����������

    private boolean mDTMFToneEnabled;         // ϵͳ��������������������־λ
	// �洢DTMF Tones
    private HashMap<Integer, Integer> mToneMap ;
    
    
    private static DTMFUtil mdDtmfUtil;
    
    private DTMFUtil (){
    	
    }
    public static DTMFUtil getInstance(){
    	if(mdDtmfUtil == null){
    		synchronized (DTMFUtil.class) {
				if(mdDtmfUtil == null){
					mdDtmfUtil = new DTMFUtil();
				}
			}
    	}
    	return mdDtmfUtil;
    }
    /**
     * ��ʼ��
     * @param context
     */
    @SuppressLint("UseSparseArrays") 
    public void init(Context context){
        try {
        	AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        	int ringerMode = audioManager.getRingerMode();
        	//��������ʱ������
        	if(ringerMode == AudioManager.RINGER_MODE_SILENT || ringerMode == AudioManager.RINGER_MODE_VIBRATE){
        		mDTMFToneEnabled = false;
        		return;
        	}
//        	int volume = audioManager.getStreamVolume(AudioManager.STREAM_DTMF);
//        	int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_DTMF);
//        	System.out.println("audioManager.getStreamVolume(AudioManager.STREAM_DTMF) = "+volume);
//        	System.out.println("audioManager.getStreamMaxVolume(AudioManager.STREAM_DTMF) = "+maxVolume);
        	mDTMFToneEnabled = true;
			synchronized (mToneGeneratorLock) {
				if(mDTMFToneEnabled && mToneGenerator == null){
					mToneGenerator = new ToneGenerator(AudioManager.STREAM_DTMF, DTMF_TONE_VOLUME);
					mToneMap = new HashMap<Integer, Integer>();
			    	mToneMap.put(0, ToneGenerator.TONE_DTMF_0);
			    	mToneMap.put(1, ToneGenerator.TONE_DTMF_1);
			        mToneMap.put(2, ToneGenerator.TONE_DTMF_2);
			        mToneMap.put(3, ToneGenerator.TONE_DTMF_3);
			        mToneMap.put(4, ToneGenerator.TONE_DTMF_4);
			        mToneMap.put(5, ToneGenerator.TONE_DTMF_5);
			        mToneMap.put(6, ToneGenerator.TONE_DTMF_6);
			        mToneMap.put(7, ToneGenerator.TONE_DTMF_7);
			        mToneMap.put(8, ToneGenerator.TONE_DTMF_8);
			        mToneMap.put(9, ToneGenerator.TONE_DTMF_9);
				}
			}
		} catch (Exception e) {
			mDTMFToneEnabled = false;
			mToneGenerator = null;
			mToneMap = null;
			LogUtil.eSave("infos", "DTMFUtil ��ʼ��ʵ��ʱ�쳣", e.getCause());
		}
    }
    /**
     * ����DTMF��
     * @param tone
     */
    public void play(int tone){
    	if(!mDTMFToneEnabled){
    		return;
    	}
    	int currentTone = mToneMap.get(Integer.valueOf(tone));
    	synchronized (mToneGeneratorLock) {
			if(mToneGenerator != null){
				mToneGenerator.startTone(currentTone, DTMF_DURATION_MS);
			}
		}
    }
    /**
     * �ͷ���Դ
     */
    public void release(){
    	if(mToneGenerator != null){
    		mToneGenerator.release();
    		mToneGenerator = null;
    	}
    	if(mToneMap != null){
    		mToneMap.clear();
    		mToneMap = null;
    	}
    }
}
































