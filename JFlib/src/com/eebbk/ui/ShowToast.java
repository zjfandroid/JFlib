package com.eebbk.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 *	��ʾToast��ʾ��Ϣ
 * @version 1.0
 * @author �Ž���
 * @update 2013-1-18 ����8:42:47
 */
public class ShowToast {
	private static Toast mToast;

	/**
	 * @param text
	 *            ��ʾ�ı�
	 * @description ��ʾToast��ʾ��Ϣ��һ��ֻ��ʾһ��
	 * @version 1.0
	 * @author �Ž���
	 * @update 2012-12-18 ����3:02:19
	 */
	public static void show(Context context, String text) {
		setText(context, text);
		mToast.setGravity(Gravity.BOTTOM , 0, 80);
		mToast.show();
	}
	
	public static void show(Context context, String text,int yOffset) {
		setText(context, text);
		mToast.setGravity(Gravity.TOP , 0, yOffset);
		mToast.show();
	}

	@SuppressLint("ShowToast")
	private static void setText(Context context, String text) {
		if (mToast == null) {
			mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
	}
}
