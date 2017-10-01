package com.hg.mylifetomorrow.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hg.mylifetomorrow.domain.Message;
import com.hg.mylifetomorrow.domain.Role;
import com.hg.mylifetomorrow.domain.User;
import com.hg.mylifetomorrow.exception.HgException;
import com.hg.mylifetomorrow.service.UserService;

@Controller
public class MLTController {
	
	@Autowired
    private UserService userService;
	  
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String indexPage() {
		return "index";
	}
	
	private List<String> getRoles(Principal user){
		final List<String> roles=new ArrayList<String>();
		UserDetails  activeUser = (UserDetails ) ((Authentication) user).getPrincipal();
		
		for(GrantedAuthority authority:activeUser.getAuthorities()){
			roles.add(authority.getAuthority());
		}
		return roles;
	}
	
	@RequestMapping("/authentication")
	public @ResponseBody Message user(Principal user) {
		final List<String> roles=getRoles(user);
		final Message msg=new Message();
		if(user!=null && user.getName()!=null && !user.getName().isEmpty() ){
			msg.setSuccess(true);
			if(roles.contains(Role.ROLE_ADMIN.name())){
				msg.setMessage(Role.ROLE_ADMIN.name());
			} else {
				msg.setMessage(Role.ROLE_USER.name());
			}
		} else {
			msg.setSuccess(false);
			msg.setMessage("Login authentication failed!");
		}
	  return msg;
	}
  
	@RequestMapping(value = {"/registerUser" })
	public @ResponseBody Message registerUser(@RequestBody User user) {
		user.setRole(Role.ROLE_USER);
		int status=0;
		final Message msg=new Message();
		try{
			status=userService.createUser(user);
			if(status==1){
				msg.setSuccess(true);
			}
		}catch(HgException e){	
			System.out.println(e);
			msg.setSuccess(false);
			final int errorCode=e.getSQLErrorCode();
			if(errorCode==1062){
				msg.setMessage("Email already registered!");
			} else {
				msg.setMessage("Registration failed!.Please try again.");
			}
		}
		
		return msg;
	}

	@RequestMapping(value="/logoutUser")
	public @ResponseBody Message logoutPage (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final Message msg=new Message();
		if (auth != null){   
			new CookieClearingLogoutHandler(AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY)
					.logout(request, response, auth);;
			new SecurityContextLogoutHandler().logout(request, response, auth);
			msg.setSuccess(true);
		} else {
			msg.setSuccess(false);
			msg.setMessage("Logout failed!");
		}
		return msg;
	}


}