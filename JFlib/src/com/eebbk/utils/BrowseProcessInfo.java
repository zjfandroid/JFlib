package com.eebbk.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.os.Debug;

import com.eebbk.entitys.ProcessInfo;
import com.eebbk.ui.ShowInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * �ڴ�鿴����̹���
 * @version 1.0
 * @author �Ž���
 * @update 2013-5-26 ����2:40:53
 */
public class BrowseProcessInfo {
	private ActivityManager mActivityManager;
	
	public BrowseProcessInfo(ActivityManager mActivityManager) {
		super();
		this.mActivityManager = mActivityManager;
	}

	//���ϵͳ�����ڴ���Ϣ 
	public static String getSystemAvaialbeMemorySize(ActivityManager mActivityManager){ 
        //���MemoryInfo���� 
        MemoryInfo memoryInfo = new MemoryInfo() ; 
        //���ϵͳ�����ڴ棬������MemoryInfo������ 
        mActivityManager.getMemoryInfo(memoryInfo) ; 
        long memSize = memoryInfo.availMem ; 
        //�ַ�����ת�� 
        String availMemStr = formateFileSize(memSize); 
        ShowInfo.printLogW("ϵͳ�����ڴ棺_________"+availMemStr);
        return availMemStr ; 
    } 

    //����ϵͳ�����ַ�ת��long -String KB/MB 
    private static String formateFileSize(long size){ 
    	return size/1024/1024+" MB";
//        return Formatter.formatFileSize(SynApplication.MY_SELF.getApplicationContext(), size);  
    } 
    
    // ProcessInfo Model�� �����������н����Ϣ 
    private List<ProcessInfo> processInfoList = null; 
    
    public List<ProcessInfo> getRunningAppProcessInfo() { 

        // ProcessInfo Model��   �����������н����Ϣ 
        processInfoList = new ArrayList<ProcessInfo>(); 

        // ͨ�����ActivityManager��getRunningAppProcesses()�������ϵͳ�������������еĽ�� 
        List<ActivityManager.RunningAppProcessInfo> appProcessList = mActivityManager.getRunningAppProcesses(); 

        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessList) { 
            // ���ID�� 
            int pid = appProcessInfo.pid; 
            // �û�ID ������Linux��Ȩ�޲�ͬ��IDҲ�Ͳ�ͬ ����root�� 
            int uid = appProcessInfo.uid; 
            // �����Ĭ���ǰ������������android��process=""ָ�� 
            String processName = appProcessInfo.processName; 
            // ��øý��ռ�õ��ڴ� 
            int[] myMempid = new int[] { pid }; 
            
            // ��MemoryInfoλ��android.os.Debug.MemoryInfo���У�����ͳ�ƽ�̵��ڴ���Ϣ 
            Debug.MemoryInfo[] memoryInfo = mActivityManager 
                    .getProcessMemoryInfo(myMempid); 
            // ��ȡ���ռ�ڴ�����Ϣkb��λ 
            int memSize = memoryInfo[0].dalvikPrivateDirty; 

            ShowInfo.printLogW("processName: " + processName + "  pid: " + pid 
                    + " uid:" + uid + " memorySize is -->" + memSize + "kb"); 

            // ����һ��ProcessInfo���� 
            ProcessInfo processInfo = new ProcessInfo(); 
            processInfo.setPid(pid); 
            processInfo.setUid(uid); 
            processInfo.setMemSize(memSize); 
            processInfo.setProcessName(processName); 
            processInfoList.add(processInfo); 

            // ���ÿ����������е�Ӧ�ó���(��),��ÿ��Ӧ�ó���İ��� 
            String[] packageList = appProcessInfo.pkgList; 

            ShowInfo.printLogW("process id is " + pid + "has " + packageList.length); 

            for (String pkg : packageList) { 
            	ShowInfo.printLogW("packageName " + pkg + " in process id is -->"+ pid); 
            } 
        } 
        return processInfoList;
    } 

}
