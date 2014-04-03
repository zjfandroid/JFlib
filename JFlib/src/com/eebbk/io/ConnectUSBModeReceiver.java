package com.eebbk.io;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.eebbk.example.VideoApplication;

/**
 * 
 * USB模式连接接收器。
 *
 */
public class ConnectUSBModeReceiver extends BroadcastReceiver {
	
	/**
	 * 注册USB模式连接接收器。
	 * 这里采用代码动态注册，只有在程序启动的时候才注册，主要是为了检测 SD 卡移除退出模块。
	 * 
	 * @param context
	 */
	public final static void registeredReceiver(VideoApplication context) {
		if (null == context) {
			return;
		}
		
		IntentFilter intentFilter = new IntentFilter();
        //intentFilter.addAction(Intent.ACTION_UMS_CONNECTED);
        intentFilter.addAction(Intent.ACTION_MEDIA_REMOVED);
        intentFilter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
        intentFilter.addAction(Intent.ACTION_MEDIA_BAD_REMOVAL);
        intentFilter.addAction(Intent.ACTION_MEDIA_EJECT);
        intentFilter.addDataScheme("file");
        context.registerReceiver(new ConnectUSBModeReceiver(), intentFilter);
	}
	
	public ConnectUSBModeReceiver() {
		
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		VideoApplication app = null;
		try {
			app = (VideoApplication) context;
		} catch (Exception e) {
			e.printStackTrace();
			app = null;
		}
		
		if (null == app) {
			return;
		}
		
        String action = intent.getAction();
        
        boolean matched = false;
        
        if (Intent.ACTION_MEDIA_REMOVED.equals(action) || 
        		Intent.ACTION_MEDIA_UNMOUNTED.equals(action) ||
        		Intent.ACTION_MEDIA_UNMOUNTABLE.equals(action) ||
        		Intent.ACTION_MEDIA_BAD_REMOVAL.equals(action) || 
        		Intent.ACTION_MEDIA_EJECT.equalsIgnoreCase(action)) {
        		matched = true;
        }
        
        // 退出模块
        if (matched) {
        	app.exitAllActivity();
        }
	}

}

