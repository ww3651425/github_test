package com.fashion.spider.web.vo;

/**
 * 
 * 附件上传
 */
@SuppressWarnings("serial")
public class FileBean implements java.io.Serializable{
	
	private String filePath; //服务器上的路径
	private String fileName; //服务器上的文件名
	private String name;//上传时的名字
	private String sufix; //后缀
	private Long size; //大小
	private String sizeStr; // 大小字符串表示
	private String description; //说明
	//图片才有的属性
	private int width; //宽
	private int height; //高
	
	public FileBean() {
	}
	
	//======================= getter and setter ==================================
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSufix() {
		return sufix;
	}

	public void setSufix(String sufix) {
		this.sufix = sufix;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getSizeStr() {
		return sizeStr;
	}

	public void setSizeStr(String sizeStr) {
		this.sizeStr = sizeStr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
