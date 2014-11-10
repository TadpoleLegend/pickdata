package com.meirong.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.meirong.common.Constants;
import com.meirong.common.ServletPathConstants;
import com.meirong.entity.User;
import com.meirong.service.UserService;


@Controller
public class BaseController {

	@Resource(name = "userService")
	private UserService userService;
	
	
	
	
	
	@RequestMapping(value = Constants.SLASH + ServletPathConstants.LOGIN)
	public String login(HttpServletRequest request,HttpServletResponse response, Model model){
		try {
			HttpSession session = request.getSession();
			String usrId  = request.getParameter("username");
			String pwd  = request.getParameter("password");
			User user = this.userService.findUser(usrId, pwd);
			if(user!=null){
					session.setAttribute(Constants.SESSION_ACCOUNT_KEY, user);
					return "user/bus/data";
			}else{
				request.setAttribute("msg", "服务连接失败,请联系管理员");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "系统错误,请联系管理员");
			return "login";
		}
		return "index";
	}
}
