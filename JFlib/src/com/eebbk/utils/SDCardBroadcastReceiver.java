package com.eebbk.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * SD卡消息广播 
 * 先在第一个界面的onCreate里注册 sd.registerReceiver(MainActivity.this);
 * 销毁时解绑
 * SDCardBroadcastReceiver.getInstance().unregisterReceiver(this);
 * 每个用到的Activity的onResume()
 * SDCardBroadcastReceiver.getInstance().setOnSDCardActionCallback(this);
 * Activity 要 implements SDCardActionCallback 这个接口
 * 也可单独在application里统一处理。
 * @version 1.0
 * @author 张建峰
 * @update 2013-5-19 下午5:48:06
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