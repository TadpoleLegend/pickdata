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
	
	
	@RequestMapping(value = Constants.SLASH + ServletPathConstants.INDEX)
	public String index(HttpServletRequest request,HttpServletResponse response, Model model){
		return "login";
	}
	
	
	
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

	@RequestMapping(value = Constants.SLASH + ServletPathConstants.POINT_INDEX)
	public String point_index(HttpServletRequest request,HttpServletResponse response, Model model){
		return "shouyinjiangkong/center";
	}
	
	@RequestMapping(value = Constants.SLASH + ServletPathConstants.CARD_INDEX)
	public String card_index(HttpServletRequest request,HttpServletResponse response, Model model){
		return "kapianjiangkong/center";
	}
	
	
	@RequestMapping(value = Constants.SLASH + ServletPathConstants.OPENBOX_INDEX)
	public String openbox_index(HttpServletRequest request,HttpServletResponse response, Model model){
		return "kaixiangjiangkong/index";
	}
	
	@RequestMapping(value = Constants.SLASH + ServletPathConstants.COINBOX_INDEX)
	public String coinbox_index(HttpServletRequest request,HttpServletResponse response, Model model){
		return "coinboxjiangkong/main";
	}
	
	@RequestMapping(value = Constants.SLASH + ServletPathConstants.STATION_INDEX)
	public String station_index(HttpServletRequest request,HttpServletResponse response, Model model){
		request.getSession().removeAttribute(Constants.defaultFleetNo_session);
		request.getSession().removeAttribute(Constants.station_url);
		request.getSession().removeAttribute(Constants.station_fleets);
		request.getSession().removeAttribute(Constants.station_car);
		return "stationjiangkong/main";
	}
	
	
	@RequestMapping(value = Constants.SLASH + ServletPathConstants.SYS_CONFIG)
	public String sys_config(HttpServletRequest request,HttpServletResponse response, Model model){
		 return "sys/main";
	}
	
	@RequestMapping(value = Constants.SLASH + ServletPathConstants.USER_CONFIG)
	public String user_config(HttpServletRequest request,HttpServletResponse response, Model model){
		 return "user/main";
	}
	
	
	@RequestMapping(value = Constants.SLASH + ServletPathConstants.LOGOUT)
	public String logout(HttpServletRequest request,HttpServletResponse response, Model model){
		HttpSession session = request.getSession();
		session.removeAttribute(Constants.SESSION_ACCOUNT_KEY);
		session.removeAttribute(Constants.site_map);
		session.removeAttribute(Constants.openbox_opt_session);
		session.removeAttribute(Constants.user_menus);
		session.removeAttribute(Constants.coinbox_session);
		session.removeAttribute(Constants.station_opt_session);
		session.removeAttribute(Constants.defaultFleetNo_session);
		session.removeAttribute(Constants.station_url);
		session.removeAttribute(Constants.station_fleets);
		session.removeAttribute(Constants.station_car);
		return "login";
	}
}
