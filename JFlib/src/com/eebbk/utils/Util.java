package com.eebbk.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用工具类
 * 
 * */
public class Util {
	private static final String TAG = "Util";

    /**
     * 产生16位MD5值
     * 
     * @return 唯一字符串
     * @version 1.0
     * @author 张建峰
     * @update 2013-4-20 下午3:00:27
     */
    public static String getMd5Id( String fileName ){
        StringBuffer buf = null;
        try{
            MessageDigest md = MessageDigest.getInstance( "MD5" );
            md.update( ( fileName ).getBytes( ) );
            byte b[] = md.digest( );
            int i;
            buf = new StringBuffer( "" );
            for( int offset = 0; offset < b.length; offset++ ){
                i = b[ offset ];
                if( i < 0 ) i += 256;
                if( i < 16 ) buf.append( "0" );
                buf.append( Integer.toHexString( i ) );
            }
        }catch( NoSuchAlgorithmException e ){
            e.printStackTrace( );
            return fileName;
        }
        return buf.toString( ).substring( 8, 24 );
    }

    /**
     * 截取字符串中数字,若该数大于整形最大值，截取其前9位,无数字则返回其hashCode
     * 
     * @version 1.0
     * @author 张建峰
     * @update 2013-4-20 下午3:17:03
     */
    public static int getNumbers( String content ){
        Pattern pattern = Pattern.compile( "\\d+" );
        Matcher matcher = pattern.matcher( content );
        StringBuilder num = new StringBuilder( "" );
        while( matcher.find( ) ){
            num.append( matcher.group( ) );
        }
        
        if( "".equals( num.toString( ) ) ){
            return content.hashCode( );
        }

        long id = Long.valueOf( num.toString( ) );
        if( id > Integer.MAX_VALUE ){
            return Integer.valueOf( num.toString( ).substring( 0, 9 ) );
        }
        return ( int )id;
    }
    
	/**
	 * 将bitmap存入SDCard指定路径中
	 **/
	public boolean save(Bitmap bitmap, String path, String name) {

		// 创建一个流对象
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// 将图片压缩
		bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
		byte[] buffer = baos.toByteArray();
		
		Log.i(TAG, "buffer.length = " + buffer.length);
		
		File dirPath = new File(path);      
		if (!dirPath.exists()) { 
			// 如果路径不存在,创建路径
			dirPath.mkdir();      
		}
		File file = new File(path + name);
		if (!file.exists()) {
			try {
				// 如果文件不存在,创建文件
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(buffer);
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 将bitmap转换成byte[]
	 * 
	 * @param bitmap
	 * @return byte[]
	 */
	public static byte[] covertBitmapToByte(Bitmap bitmap){
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 5, baos);
				byte[] bytes = baos.toByteArray();
				Log.i(TAG, "byte.length = " + bytes.length);
				return bytes ; 
	}
	
	/**
	 * 将PointF类型的list转换为float类型
	 * @param list
	 * @return List<Float>
	 */
	public static List<Float> covertPointfToFloat(List<PointF> list){
		if(null == list || list.size() == 0){
			Log.e(TAG, "nullpointer");
			return null;
		}
		List<Float> result = new ArrayList<Float>();
		int size = list.size();
		for(int i = 0; i < size; i ++){
			result.add(list.get(i).x);
			result.add(list.get(i).y);
		}
		return result;
	}
	
	/**
	 * 将float类型的list转换为pointf类型
	 * @param list
	 * @return List<PointF>
	 */
	public static List<PointF> covertFloatToPointf(List<Float> list){
		if(null == list || list.size() == 0){
			Log.e(TAG, "nullpointer");
			return null;
		}
		List<PointF> result = new ArrayList<PointF>();
		int size = list.size();
		for(int i = 0; i < size; i += 2){
			PointF p = new PointF(list.get(i), list.get(i + 1));
			result.add(p);
		}
		return result;
	}

	/**
	 * 将序列化的对象转换成byte[]
	 * 
	 * @param obj
	 * @return  byte[]
	 */
	public static byte[] covertObjectToBytes(Serializable  obj) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream bos = null;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			return bos.toByteArray();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				oos.close();
				bos.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 将字节对象转换成目标对象
	 * @param bytes
	 * @return Object
	 */
	public static Object getObjectFromByte(byte[] bytes) {

		if (bytes == null || bytes.length == 0) {
			Log.e(TAG, "getObjectFromByte nullpointer");
			return null;
		}
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		try {
			ObjectInputStream ois = new ObjectInputStream(bis);
			ois.close();
			return ois.readObject();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	/**
	 * 用于处理左右滑动列表并且左右两边带按钮 中的按钮的隐藏与显示效果
	 * 
	 * @param left_view
	 *            左边按钮
	 * @param right_view
	 *            右边按钮
	 * @param curruntPosition
	 *            数据当前位置
	 * @param count
	 *            数据总长度(从0起算)
	 *            
	 */
	public static void setButtonVisableState(View left_view, View right_view,
			int curruntPosition, int count) {

		if (0 >= count) {
			left_view.setVisibility(View.INVISIBLE);
			right_view.setVisibility(View.INVISIBLE);
			return;
		}

		// 第一页
		if (0 == curruntPosition) {
			left_view.setVisibility(View.INVISIBLE);
			right_view.setVisibility(View.VISIBLE);

			// 最后一页
		} else if (count == curruntPosition) {
			right_view.setVisibility(View.INVISIBLE);
			left_view.setVisibility(View.VISIBLE);

			// 中间页
		} else {
			left_view.setVisibility(View.VISIBLE);
			right_view.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 通过layout(l,t,r,b)重新定位实现将ImageView移动到指定位置(随手指移动)
	 * 
	 * @param imgView
	 *            需要定位的view
	 * @param startX
	 *            view起始x坐标
	 * @param startY
	 *            view起始y坐标
	 * @param endX
	 *            view目标x坐标
	 * @param endY
	 *            view目标y坐标
	 * 
	 */
	public static void moveViewTo(ImageView imgView, float startX,
			float startY, float endX, float endY) {

		int offsetX = (int) (endX - startX);
		int offsetY = (int) (endY - startY);

		imgView.layout(
				imgView.getLeft() - 140 + offsetX,
				imgView.getTop() - 170 + offsetY,
				imgView.getRight() - 140 + offsetX,
				imgView.getBottom() - 170 + offsetY);
	}

	/**
	 *  显示安卓默认风格的提示(toast) 
	 *  
	 *  @param c
	 *  
	 *  @param content
	 */
	public static void showShorToast(Context c, String content) {

		Toast mtoast = Toast.makeText(c, content, Toast.LENGTH_SHORT);
		mtoast.show();
	}


	/**
	 * 判断某个点的坐标是否在某个矩形范围内,在其中返回true,否则返回false
	 * 
	 * @param leftTopPoint
	 *            矩形左上角坐标
	 * @param rightBottomPoint
	 *            矩形右下角坐标
	 * @param point
	 *            需要判断的目标点 return boolean
	 * 
	 * @return 
	 **/
	public static boolean isContainPointF(PointF leftTopPoint,
			PointF rightBottomPoint, PointF point) {

		RectF targetRect = new RectF(leftTopPoint.x, leftTopPoint.y,
				rightBottomPoint.x, rightBottomPoint.y);

		return targetRect.contains(point.x, point.y);
	}


	/**
	 * 通过指定view得到其指定宽高bitmap
	 * 
	 * @param view
	 *            需要绘制的View
	 * @param width
	 *            目标bitmap的宽度
	 * @param height
	 *            目标bitmap的高度
	 * @return 
	 */
	public static Bitmap getBitmapFromView(View view, int width, int height) {

		int widthSpec = View.MeasureSpec.makeMeasureSpec(width,
				View.MeasureSpec.EXACTLY);
		int heightSpec = View.MeasureSpec.makeMeasureSpec(height,
				View.MeasureSpec.EXACTLY);

		view.measure(widthSpec, heightSpec);
		view.layout(0, 0, width, height);

		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		canvas.drawColor(Color.TRANSPARENT);
		view.draw(canvas);

		Log.i(TAG, "getBitmapFromView");
		return bitmap;
	}
	
	/**
	 * 将View转化为Bitmap
	 * 
	 * @param view
	 **/
	public static Bitmap covertViewToBitmap(View view){
		
		// 设置拖拽窗口显示内容
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		return bmp;
	}
	
	
	/**
	 * 新建指定位置和大小的空背景ImageView
	 * 
	 * @param context
	 * 
	 * @param startX
	 *            窗口欲显示的左上角x坐标(相对于父控件)
	 * @param startY
	 *            窗口欲显示的左上角y坐标(相对于父控件)
	 * @param width
	 *            view宽度
	 * @param height
	 *            view高度
	 * 
	 * @return ImageView
	 * */
	public static ImageView createImageView(Context context, int startX,
			int startY, int width, int height) {

		ImageView imageView = new ImageView(context);
		ViewGroup.LayoutParams vlp = new LayoutParams(width, height);
		imageView.setLayoutParams(vlp);
		imageView.layout(startX, startY, startX + width, startY + height);

		return imageView;
	}
	
	/**
	 * 计算两个PointF点之间的直线距离
	 * 
	 * @param p1
	 * @param p2
	 * 
	 * @return
	 **/
	public static float getTwoPointFDistance(PointF p1, PointF p2) {

		double result = (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y)
				* (p1.y - p2.y);

		return (float) Math.sqrt(result);
	}
	
	
	
	/**
	 * 去除ArrayList中的重复元素
	 *
	 * @param list
	 *
	 * @return
	 **/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List removeDuplicate(List list) {

		HashSet<Object> h = new HashSet<Object>(list);
		list.clear();
		list.addAll(h);
		return list;
	}
	
	/**
	 * 获取当天日期
	 * 
	 * @return 日期
	 */
	public static String getDate(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(); 
		return sf.format(date);
	}
	
	/**
	 * 将单词中的空格换成",",
	 * 用于解决文本显示不全,
	 * 也不显示省略号的问题
	 */
	public static String replaceChStr(String str){
		String s = str.replaceAll(" ", ",");
		if(s.length() > 4){
			return s.substring(0, 3)+"…";
		}
		return s;
	}
	
	public static String replaceEngStr(String str , int len){
		if(str.length() > len){
			return str.substring(0, len-1)+"…";
		}
		return str;
	}
	
	public static final String[] str = {
		"ai", "air", "al", "ar", "au", "aw", "ay", "bl", "br", "ch", "cl", "cr", "dr", "ds", "ea", "ear",
		"ee", "eir", "er", "ere", "ew", "ey", "fl", "fr", "gl", "gr", "ie", "igh", "ir", "kn", "ng", "nk", "oa", "oi", "ong", "oo",
		"oor", "or", "ore", "ou", "our", "ow", "oy", "pl", "pr", "scr", "sh", "sk", "sl", "sm", "sn", "spr", "squ", "st", "str",
		"sw","th","tr","ts","tw","ue","ui","ur","wh"
	};
	
	private static List<String> impotantWordsList;
	static {
		impotantWordsList = new ArrayList<String>();
		for (String s : str) {
			impotantWordsList.add(s);
		}
	}
   
	/**
	 * 将单词重点组合显示红色
	 * @param string
	 * @param tv
	 * 起始结束为止开闭关系为[start, end);
	 */
	public static void setFontType(String string, TextView tv){
		
		SpannableString spannableString = new SpannableString(string);
		
		int len = string.length();
		for(int i = 0; i < len;){
			if(i + 3 <= len){
				if(impotantWordsList.contains(string.substring(i, i + 3))){
					 spannableString.setSpan(new ForegroundColorSpan(Color.RED), i, i + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					 i += 3;
				}else if(impotantWordsList.contains(string.substring(i, i + 2))){
					 spannableString.setSpan(new ForegroundColorSpan(Color.RED), i, i + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					i += 2;
				}else{
					i += 1;
				}
			}else if(i + 2 <= len){
				String sub = string.substring(i, i + 2);
				if(impotantWordsList.contains(sub)){
					 spannableString.setSpan(new ForegroundColorSpan(Color.RED), i, i + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					 i += 2;
				}else{
					i += 1;
				}
				
			}else if(i + 1 <= len){
				i += 1;
				continue;
			}
		}
	    tv.setText(spannableString);
	}
	
	public static String getWordByList(ArrayList<byte[]> byteList) {
		String word="";
		for (byte[] bytes : byteList) {
			try {
				word += (new String(bytes,"utf-8")).replaceAll("[^a-zA-Z]", "");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return word;
	}

	public static void getWordsByBytes(SpannableStringBuilder ssb, byte[] bytes, boolean isNeedSpace,StringBuilder impLetter) throws UnsupportedEncodingException {
		if(isNeedSpace&&ssb.length()>0&&!(ssb.charAt(ssb.length()-1)=='\t')&&!(ssb.charAt(ssb.length()-1)=='\n')){
			ssb.append("-");
		}
		int size= bytes.length;
		
		for(int j = 0; j < size ;){
			
			if(( ( bytes[ j ] & 0xff ) == 0xff ) && ( ( bytes[ j + 1 ] & 0xff ) == 0xff )){
				j += 2; // 先跳过两个0xff
				
				if((( bytes[ j ] & 0xff ) == 0x80 ) && ((bytes[ j + 1 ] & 0xff ) == 0xa9)){ // 重点音节控制字
//					重点音节用 FF FF 80 A9标示。譬如：fall中all为重点音节  FF FF 80 A9 all FF FE 80 A9
//					扩展单词块中同样使用该控制字 。譬如：small sm FF FF 80 A9 all  FF FE 80 A9

					j += 2; // 跳过0x80 0xa9
					
					int start = ssb.length();
					while(((bytes[j]&0xff)!=0xff)||((bytes[j+1]&0xff)!=0xfe)){
						byte[] b = {bytes[j]};
						String strImp = new String( b, "utf-8" ); 
						ssb.append(strImp);
						j++;
						
						if(null != impLetter){
							impLetter.append(strImp);
						}
					}
					
					if(null != impLetter){
						ssb.setSpan(new ForegroundColorSpan(Color.RED), start, ssb.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
					}
					
//					Integer[] idx = {start,ssb.length()};
//					flashList.add(idx);
					
				}else if((( bytes[ j ] & 0xff ) == 0x81 ) && ((bytes[ j + 1 ] & 0xff ) == 0x80)){
//					对于字母组合分开的单词，拼读时按拼读顺序出现，如mate，m，a  e，t，a  e出现时中间留空格。 
//					对于这种方式，在这种音节中间加入控制字，FF FF 81 80 ID譬如：a FF FF 81 80 84 80 80 80 FF FE 81 80e
//					这里的ID，是指在本单词的第几个音节应该填放在a和e中间 
					j+=2;
				}
			}else if(( ( bytes[ j ] & 0xff ) == 0xff ) && ( ( bytes[ j + 1 ] & 0xff ) == 0xfe )){ 
				
				j += 2; //跳过ff fe
				
				if((( bytes[ j ] & 0xff ) == 0x80 ) && ((bytes[ j + 1 ] & 0xff ) == 0xa9)){ // 重点音节控制字
					j+=2;////跳过80 a9
				}
				
			}else{
				byte[] b = {bytes[j]};
				ssb.append(new String( b, "utf-8" ));
				j++;
			}
		}
	}
}

