package com.meirong.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.meirong.common.Constants;
import com.meirong.entity.User;
import com.meirong.util.CustomizedPropertyConfigurer;

public class CheckSessionOutFilter implements Filter {
	
	protected FilterConfig filterConfig = null;

	/**
	 * Take this filter out of service.
	 */
	public void destroy() {
		this.filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hsrq = (HttpServletRequest) request;
		HttpServletResponse hsrp = (HttpServletResponse) response;
		User user = (User) hsrq.getSession().getAttribute(Constants.SESSION_ACCOUNT_KEY);
		if(user == null){
		String currentURL = hsrq.getRequestURI();
		if (currentURL.indexOf(CustomizedPropertyConfigurer.getCtxProperty("global.webapp.loginAction")) < 0) {
				String loginPage = hsrq.getContextPath() + "/" + CustomizedPropertyConfigurer.getCtxProperty("global.webapp.loginPage"); 
				hsrp.sendRedirect(loginPage);
				return;
		} 
	}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}
}
