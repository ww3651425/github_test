<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8" http-equiv="content-type" />
<title>${test}</title>
</head>
<body>
<#include "/include/header.ftl">

<p>
结构说明：DButils(数据库) + Ehcache(缓存) + SpingMvc + Quartz(定时任务) + Freemarker(视图)<br />
	<p style="padding-left:30px;" >
	 cache  -------- 缓存ehcache<br />
	 db --------   数据库操作<br />
	 developer -------- 开发工具，代码生成<br />
	 model --------   实体类<br />
	 util --------   常用的操作类<br />
	 web -------- <br />
	 web.controller --------  SpingMvc Controller<br />
	 web.filter  --------  过滤器，基于web.xml访问控制<br />
	 web.interceptor --------   SpingMvc 拦截器<br />
	 web.listener  --------  初始化系统<br />
	</p>
</p>

<#include "/include/footer.ftl">
</body>
</html>