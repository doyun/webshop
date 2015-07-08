package com.epam.doiun.util;

import javax.servlet.http.HttpSession;

import com.epam.doiun.constants.ApplicationConstants;
import com.epam.doiun.services.UserService;

public class LoginManager {

	public static void login(HttpSession session, String email, UserService service){
		session.setAttribute(ApplicationConstants.USER.getValue(), service.getUser(email));
	}
	
	public static void logout(HttpSession session){
		session.invalidate();
	}
}
