package com.eebbk.io;

import android.content.Context;

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
}
