package com.fashion.spider.web.controller.index;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.fashion.spider.web.controller.BaseController;

/***
 * 首页
 *
 */
@Controller
public class IndexController extends BaseController {
	@Autowired
	// 实例化对象
	/***
	 * 首页
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("null")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpServletRequest req, HttpServletResponse res,
			ModelMap map) {
		return "index/index.ftl";

	}

}