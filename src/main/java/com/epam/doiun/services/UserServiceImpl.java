package com.epam.doiun.services;

import org.apache.log4j.Logger;

import com.epam.doiun.anotation.Transactional;
import com.epam.doiun.bean.LoginBean;
import com.epam.doiun.bean.RegistrationFormBean;
import com.epam.doiun.constants.Role;
import com.epam.doiun.dao.UserDAO;
import com.epam.doiun.entity.User;
import com.epam.doiun.util.SecurityAppManager;

public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = Logger
			.getLogger(UserServiceImpl.class);
	private UserDAO userDAO;

	public UserServiceImpl(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public boolean existsUser(String email) {
		return userDAO.exist(email);
	}

	@Override
	@Transactional
	public void addUser(RegistrationFormBean bean) {
		User user = new User();
		user.setEmail(bean.getEmail());
		user.setFirstName(bean.getFirstName());
		user.setLastName(bean.getLastName());
		user.setPasshash(SecurityAppManager.createPasshash(bean.getPassword()));
		user.setAvatarPath(bean.getAvatarPath());
		user.setRole(Role.USER);
		userDAO.createUser(user);
	}

	@Override
	public boolean isRightLoginInfo(LoginBean bean) {
		User user = userDAO.getUser(bean.getEmail());
		if (user == null) {
			return false;
		}
		return user.getPasshash().equals(
				SecurityAppManager.createPasshash(bean.getPassword()));
	}

	@Override
	public User getUser(String email) {
		return userDAO.getUser(email);
	}
}
