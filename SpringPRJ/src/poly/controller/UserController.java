package poly.controller;

import static poly.util.CmmUtil.nvl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import poly.dto.UserDTO;
import poly.service.IUserService;

@Controller
public class UserController {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="UserService")
	private IUserService userService;
	
	@RequestMapping(value="user/userLogin")
	public String userLogin() {
		log.info(this.getClass() + "user/userLogin start!!");
		log.info(this.getClass() + "user/userLogin end!!");
		return "/user/userLogin";
	}
	
	@RequestMapping(value="user/userLoginProc")
	public String userLoginProc(HttpServletRequest request, Model model, HttpSession session) throws Exception {
		log.info(this.getClass() + "user/userLoginProc start!!");
		String id = nvl(request.getParameter("id"));
		String pwd = nvl(request.getParameter("pwd"));
		
		log.info("id : " + id);
		log.info("pwd : " + pwd);
		
		UserDTO uDTO = new UserDTO();
		uDTO.setId(id);
		uDTO.setPwd(pwd);
		
		uDTO = userService.getUserInfo(uDTO);
		log.info("uDTO null? : " + (uDTO == null));
		String msg = "";
		String url = "";
		if (uDTO == null) {
			msg = "로그인 실패";
		} else {
			log.info("uDTO no : " + uDTO.getNo());
			log.info("uDTO id : " + uDTO.getId());
			log.info("uDTO name : " + uDTO.getName());
			msg = "로그인 성공";
			session.setAttribute("no", uDTO.getNo());
			session.setAttribute("name", uDTO.getName());
		}
		
		url = "/";
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		log.info(this.getClass() + "user/userLoginProc end!!");
		return "/redirect";
	}
	
	@RequestMapping(value="/user/logOut")
	public String logOut(HttpSession session, Model model) throws Exception {
		log.info(this.getClass() + "user/logOut start!!");
		
		String msg = "";
		String url = "";
		
		msg = "로그아웃 성공";
		
		session.invalidate();
		
		url = "/";
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		log.info(this.getClass() + "user/logOut end");
		return "/redirect";
	}
}
