package poly.controller;


import static poly.util.CmmUtil.nvl;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PracticeController {

	private Logger log = Logger.getLogger(this.getClass());
	
	@RequestMapping(value="practice")
	public String Index() {
		
		log.info(this.getClass());
		
		return "/practice";
	}
	
	@RequestMapping(value="table")
	public String Table() {
		
		log.info("table start!!");
		
		return "/table";
	}
	
	@RequestMapping(value="get")
	public String get(HttpServletRequest request, ModelMap model) throws Exception {
		String name = nvl(request.getParameter("name"));
		
		model.addAttribute("name", name);
		return "/get";
	}
	
	@RequestMapping(value="post")
	public String post() {
		return "/postForm";
	}
	
	@RequestMapping(value="doPost", method=RequestMethod.POST)
	public String doPost(HttpServletRequest request, ModelMap model) throws Exception {
		String name = nvl(request.getParameter("name"));
		
		model.addAttribute("name", name);
		return "/get";
	}
	
}