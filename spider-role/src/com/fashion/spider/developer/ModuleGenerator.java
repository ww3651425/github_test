package com.fashion.spider.developer;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;

import com.fashion.spider.util.FileOperate;


public class ModuleGenerator {
	
	private String entityPath = "src/com/fashion/spider/model";
	private String controllerPath = "src/com/fashion/spider/web/controller/admin";
	private String viewPath = "WebRoot/WEB-INF/content/admin";

	private String controllerTemplatePath = "src/com/fashion/spider/developer/controllerTemplate.tlt";
	private String viewListTemplatePath = "src/com/fashion/spider/developer/viewListTemplate.tlt";
	private String viewFromTemplatePath = "src/com/fashion/spider/developer/viewFromTemplate.tlt";
	
	
	//得到实体名称
	private String getEntityName(File entity) {
		return entity.getName().substring(0, entity.getName().indexOf("."));
	}
	
	private String getImportPath(String path) {
		StringBuffer result = new StringBuffer();
		String [] strings = path.split("/");
		for(int i = 1, size = strings.length; i < size; i++) {
			result.append(".").append(strings[i]);
		}
		return result.substring(1);
	}
	
	public void createFile(boolean viewListFlag, boolean viewFormFlag, boolean controllerFlag) {
		try {
			//得到所有实体类文件
			File entityDir = new File(entityPath);
			entityPath = getImportPath(entityPath);
			
			File[] entities = entityDir.listFiles();
			
			FileOperate fo = new FileOperate(); //文件操作类
			
			String content = ""; 		//生成文件的类容
			String filePath = ""; 		//生成文件
			String fileFolder = "";		//生成文件的位置
			
			
			for (File entity : entities) {
				if (!entity.getName().endsWith("java")) {
					continue;
				}
				System.out.println(entity.getName());
				if( !entity.getName().equals("Search_HotRecord.java") ){
					continue;
				}
				String entityName = getEntityName(entity);
				
				//controller
				if (controllerFlag) {
					String controllerContent = fo.readTxt(controllerTemplatePath, "UTF-8");
					String className = entityName.replace("_", "");
					content = controllerContent.replace("#entityName#", entityName);
					content = content.replace("#entityName?toLowerCase()#", entityName.toLowerCase());
					
					content = content.replace("#className#", className);
					content = content.replace("#className?toLowerCase()#", className.toLowerCase());
					
					filePath = controllerPath  + "/" + className + "Controller.java";
					if (!new File(filePath).exists()) {
						fo.createFile(filePath, content);
					}
				}
				//视图 list
				if (viewListFlag) {
					String viewListContent = fo.readTxt(viewListTemplatePath, "UTF-8");
					content = viewListContent.replace("#entityName#", entityName);
					
					fileFolder = viewPath + "/" + entityName.toLowerCase();
					filePath = fileFolder  + "/list.ftl";
					if (!new File(filePath).exists()) {
						fo.createFolder(fileFolder);
						fo.createFile(filePath, content);
					}
				}
				//视图 form
				if (viewFormFlag) {
					String viewFromContent = fo.readTxt(viewFromTemplatePath, "UTF-8");
					try {
						StringBuffer htmlForm = new StringBuffer("");
						StringBuffer hidden = new StringBuffer("");
						PropertyDescriptor[] props = Introspector.getBeanInfo(Class.forName(entityPath + "." + entityName)).getPropertyDescriptors();
						for (PropertyDescriptor p : props) {
							if( p.getName().equals("class") ){
								continue;
							}
							if(p.getName().equals("updated") || p.getName().equals("created") || p.getName().equals("id")){
								hidden.append("<input name=\"" + p.getName() + "\" type=\"hidden\" id=\"" + p.getName() + "\" value=\"${entity." + p.getName() + "}\" />\n");
							}else{
								htmlForm.append("\n<tr>");
								htmlForm.append("<td bgcolor=\"#F7F7F7\" width=\"20%\" >" + p.getName() + ":</td>");
								htmlForm.append("<td ><input class='css_input' name=\"" + p.getName() + "\" type=\"text\" id=\"" + p.getName() + "\" value=\"${entity." + p.getName() + "}\" /></td>");
								htmlForm.append("</tr>\n");
							}
						}
						htmlForm.append("\n<tr>");
						htmlForm.append(hidden.toString());
						htmlForm.append("<td colspan=2><input type='submit' name='Submit' value='submit'></td>");
						htmlForm.append("</tr>\n");
						
						content = viewFromContent.replace("#entityName#", entityName);
						content = content.replace("#entityName?toLowerCase()#", entityName.toLowerCase());
						content = content.replace("#htmlForm#", htmlForm.toString());
					} catch (Exception e) {
						e.printStackTrace();
					}
					fileFolder = viewPath + "/" + entityName.toLowerCase();
					filePath = fileFolder  + "/form.ftl";
					if (!new File(filePath).exists()) {
						fo.createFolder(fileFolder);
						fo.createFile(filePath, content);
					}
				}					
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			ModuleGenerator mg = new ModuleGenerator();
			mg.createFile(true, true, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
