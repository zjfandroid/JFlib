package com.eebbk.io;

import android.content.Context;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * @description 提供用户数据的存取及文件存在校验
 * @version 1.0
 * @author 张建峰
 * @update 2013-1-10 上午8:39:20
 */
public class SerializableUserData{

    public SerializableUserData( ){}

    /**
     * @description 保存生词听写选择记录
     * @version 1.0
     * @author 张建峰
     * @update 2012-12-24 上午8:41:44
     */
    public static void saveDictationSelected( Context context, String fileName, Object obj ){
        OutputStream out = null;
        ObjectOutputStream oout = null;
        try{
            out = context.openFileOutput( fileName, 0 );
            oout = new ObjectOutputStream( out );

            oout.writeObject( obj );// 保存对象
        }catch( Exception e ){
            e.printStackTrace( );
        }finally{
            try{
                oout.close( );
                out.close( );
            }catch( Exception e ){
                e.printStackTrace( );
            }
        }
    }

    /**
     * @description 读取生词听写选择记录
     * @version 1.0
     * @author 张建峰
     * @update 2012-12-24 上午8:45:20
     */
    @SuppressWarnings("unchecked")
    public static <T extends Object> T loadDictationSelected( Context context, String fileName ){
        InputStream in = null;
        ObjectInputStream oin = null;
        Object obj = null;
        try{
            in = context.openFileInput( fileName );
            oin = new ObjectInputStream( in );

            obj = oin.readObject( );
        }catch( Exception e ){
            e.printStackTrace( );
        }finally{
            try{
                oin.close( );
                in.close( );
            }catch( Exception e ){
                e.printStackTrace( );
            }
        }
        return (T) obj;
    }

    /**
     * @description 检查文件是否存在
     * @version 1.0
     * @author 张建峰
     * @update 2012-12-24 上午8:43:07
     */
    public static boolean check( Context context, String fileName ){
        try{
            context.openFileInput( fileName ).close( );
        }catch( Exception e ){
            return false;
        }
        return true;
    }
    
	/**
	 * 将一个对象序列化成二进制数组
	 */
    public static byte[] SerializeToByte(Object obj)
    {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream(); //构造一个字节输出流
    	byte[] buf = null;
    	
    	try {
    		ObjectOutputStream oos = new ObjectOutputStream(baos); //构造一个类输出流
    		oos.writeObject(obj);
    		buf = baos.toByteArray(); //从这个地层字节流中把传输的数组给一个新的数组
    		
    		oos.flush();
    		oos.close();
    		baos.close();
    	} catch (IOException e1) {
    		e1.printStackTrace();
    	}
    	
    	return buf;
    }

    /**
     * 将一个二进制数组反序列化
     */
    @SuppressWarnings("unchecked")
	public static <T extends Object> T DeseralizeToArraylist(byte[] buf)
    {
    	Object obj = null;
    	
        try {
			//构建一个类输入流，地层用字节输入流实现
			ByteArrayInputStream bais = new ByteArrayInputStream(buf);
			ObjectInputStream ois = new ObjectInputStream(bais);
			obj = ois.readObject(); //读取类
			
			ois.close();
			bais.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return (T)obj;
    }
	
	/** 
     * 把字节数组保存为一个文件 
     *  
     * @param b 
     * @param outputFile 
     * @return 
     */  
    public static File getFileFromBytes(byte[] b, String outputFile) {  
        File ret = null;  
        BufferedOutputStream stream = null;  
        try {  
            ret = new File(outputFile);
            if(!ret.exists()){
            	ret.createNewFile();
            }
            
            FileOutputStream fstream = new FileOutputStream(ret);  
            stream = new BufferedOutputStream(fstream);  
            stream.write(b);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (stream != null) {  
                try {  
                    stream.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return ret;  
    } 
}
