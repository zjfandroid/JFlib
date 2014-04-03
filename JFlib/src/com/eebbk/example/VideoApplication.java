package com.eebbk.example;

import java.util.LinkedList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;

import com.eebbk.io.ConnectUSBModeReceiver;

public class VideoApplication extends Application implements  ActivityLifecycleCallbacks {
	/** 保存当前程序所有 Activity 实例 */
	private LinkedList<ActivityInfo> mExistedActivitys;
	@Override
	public void onCreate() {
        super.onCreate();
        init();
    }
	
	@SuppressLint("NewApi")
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		unregisterActivityLifecycleCallbacks(this);
		if (null != mExistedActivitys) {
			mExistedActivitys.clear();
		}
	}
	
	
	@SuppressLint("NewApi")
	private void init() {
		// 注册广播监听器
		ConnectUSBModeReceiver.registeredReceiver(this);
		mExistedActivitys = new LinkedList<ActivityInfo> ();
		registerActivityLifecycleCallbacks(this);
	}

	@SuppressLint("NewApi")
	@Override
	public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
		if (null != mExistedActivitys && null != activity) {
			// 把新的 activity 添加到最前面，和系统的 activity 堆栈保持一致
			mExistedActivitys.offerFirst(new ActivityInfo(activity, ActivityInfo.STATE_CREATE));
		}
	}

	@Override
	public void onActivityDestroyed(Activity activity) {
		if (null != mExistedActivitys && null != activity) {
			mExistedActivitys.remove(activity);
		}
	}

	@Override
	public void onActivityPaused(Activity activity) {
	}

	@Override
	public void onActivityResumed(Activity activity) {
	}

	@Override
	public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
	}

	@Override
	public void onActivityStarted(Activity activity) {
	}

	@Override
	public void onActivityStopped(Activity activity) {
	}

	@SuppressLint("NewApi")
	public void exitAllActivity() {
		if (null != mExistedActivitys) {
			
			// 先暂停监听（省得同时在2个地方操作列表）
			unregisterActivityLifecycleCallbacks(this);
			
			// 弹出的时候从头开始弹，和系统的 activity 堆栈保持一致
			for (ActivityInfo info : mExistedActivitys) {
				if (null == info || null == info.mActivity) {
					continue;
				}
				
				try {
					info.mActivity.finish();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			mExistedActivitys.clear();

			// 退出完之后再添加监听
			registerActivityLifecycleCallbacks(this);
		}
	}
	
	public final class ActivityInfo {
		public final static int STATE_NONE = 0;
		public final static int STATE_CREATE = 1;
		@SuppressLint("NewApi")
		public final static int STATE_RUNNING = 2;
		public final static int STATE_PAUSE = 3;
		
		Activity mActivity;
		int mState;
		
		ActivityInfo() {
			mActivity = null;
			mState = STATE_NONE;
		}
		
		ActivityInfo(Activity activity, int state) {
			mActivity = activity;
			mState = state;
		}
	}
}


