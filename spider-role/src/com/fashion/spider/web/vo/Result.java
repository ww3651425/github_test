package com.fashion.spider.web.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 数据操作结果实体类
 */
@SuppressWarnings("serial")
public class Result implements java.io.Serializable {

	private int code;
	private String message;
	
	public boolean OK() {
		return code == 1;
	}
	
	public static String error(){
		return error("");
	}
	/***
	 * 错误
	 * @param msg
	 * @return
	 */
	public static String error(String message){
		Result result = new Result();
		result.setCode(0);
		result.setMessage(message);
		return JSON.toJSONString(result, SerializerFeature.BrowserCompatible);
	}
	
	/***
	 * 操作成功
	 * @return
	 */
	public static String success(){
		return success("");
	}
	
	/***
	 * 成功
	 * @return
	 */
	public static String success(String message){
		Result result = new Result();
		result.setCode(1);
		result.setMessage(message);
		return JSON.toJSONString(result,SerializerFeature.BrowserCompatible);
	}


	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;//UnicodeUtil.stringToUnicode(message);
	}
	
	@Override
	public String toString(){
		return String.format("RESULT: CODE:%d,MSG:%s", code, message);
	}

}
