package com.epam.doiun.services;

import com.epam.doiun.bean.LoginBean;
import com.epam.doiun.bean.RegistrationFormBean;
import com.epam.doiun.entity.User;

public interface UserService {

	boolean existsUser(String email);
	
	void addUser(RegistrationFormBean bean);
	
	boolean isRightLoginInfo(LoginBean bean);
	
	User getUser(String email);
}
