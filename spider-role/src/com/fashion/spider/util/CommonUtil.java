package com.fashion.spider.util;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 公共工具类
 * 
 */
public class CommonUtil {

	private static final Log log = LogFactory.getLog(CommonUtil.class);

	/**
	 * 得到String的MD5码
	 * @param srcString 将要加密码的字符串
	 * @return String 加密后的字符串
	 */
	public static String getMD5(String srcString) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(srcString.getBytes("UTF8"));
			byte s[] = md.digest();
			String result = "";
			for (int i = 0; i < s.length; i++) {
				result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
			}
			return result;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}
	
	/**
	 * 格式化文件大小
	 * @param size
	 * @return
	 */
	public static String formatDataSize(Long dataSize) {
		double byteSize = dataSize / 1024;
		String suffix = "KB";
		if (byteSize > 1000) {
			byteSize = byteSize / 1024;
			suffix = "MB";
		}
		DecimalFormat format = new DecimalFormat("0.00");
		if ("0.00".equals(format.format(byteSize))) {
			return "0.01KB";
		}
		return format.format(byteSize) + suffix;
	}
	
	/**
	 * 格式化数字
	 * @param number 原数字
	 * @param fmt 格式 如 "0.00"
	 * @return
	 */
	public static String formatNumber(double number, String fmt) {
		DecimalFormat format = new DecimalFormat(fmt);
		return format.format(number);
	}
	
	/**
	 * 随机数
	 * @param width 几位的
	 * @return
	 */
	public static String randomNum(int width) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < width; i++) {
			int n = new Random().nextInt(10);
			sb.append(n);
		}
		return sb.toString();
	}
	
	/** ================= 转换编码 ======================= **/
	public static String iso2utf8(String str) {
		try {
			return new String(str.getBytes("ISO8859-1"), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 产生16位的订单号
	 * @return
	 */
	public static String getOrderNo(){
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(date) + CommonUtil.randomNum(6);
	}
	
}
