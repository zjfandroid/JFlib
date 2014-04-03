package com.eebbk.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * SD����Ϣ�㲥 
 * ���ڵ�һ�������onCreate��ע�� sd.registerReceiver(MainActivity.this);
 * ����ʱ���
 * SDCardBroadcastReceiver.getInstance().unregisterReceiver(this);
 * ÿ���õ���Activity��onResume()
 * SDCardBroadcastReceiver.getInstance().setOnSDCardActionCallback(this);
 * Activity Ҫ implements SDCardActionCallback ����ӿ�
 * Ҳ�ɵ�����application��ͳһ����
 * @version 1.0
 * @author �Ž���
 * @update 2013-5-19 ����5:48:06
 */
public class SDCardBroadcastReceiver extends BroadcastReceiver {
	public static final String ACTION_SDCARD_MEDIA_EJECT = "android.intent.action.MEDIA_EJECT";
	public static final String ACTION_SDCARD_MEDIA_UNMOUNTED = "android.intent.action.MEDIA_UNMOUNTED";
	public static final String ACTION_SDCARD_MEDIA_REMOVED = "android.intent.action.MEDIA_REMOVED";
	public static final String ACTION_SDCARD_MEDIA_MOUNTED = "android.intent.action.MEDIA_MOUNTED";

	private SDCardActionCallback mSDCardActionCallback = null;

	private static SDCardBroadcastReceiver uniqueInstance;

	public SDCardBroadcastReceiver(Context pContext) {
		uniqueInstance = this;
	}

	public void setOnSDCardActionCallback(SDCardActionCallback pSDCardActionCallback) {
		mSDCardActionCallback = pSDCardActionCallback;
	}

	public static SDCardBroadcastReceiver getInstance() {
		if (uniqueInstance != null)
			return uniqueInstance;
		else
			return null;
	}

	@Override
	public void onReceive(Context context, Intent intent) {

		Log.e("SDCard", intent.getAction());
		if (mSDCardActionCallback != null)
			mSDCardActionCallback.onCallback(intent.getAction());
	}

	public void registerReceiver(Context pContext) {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION_SDCARD_MEDIA_EJECT);
		intentFilter.addAction(ACTION_SDCARD_MEDIA_UNMOUNTED);
		intentFilter.addAction(ACTION_SDCARD_MEDIA_REMOVED);
		intentFilter.addAction(ACTION_SDCARD_MEDIA_MOUNTED);
		intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
		intentFilter.addDataScheme("file");
		pContext.registerReceiver(this, intentFilter);
	}

	public void unregisterReceiver(Context pContext) {
		try {
			pContext.unregisterReceiver(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public interface SDCardActionCallback {
		public void onCallback(String pAction);
	}

}