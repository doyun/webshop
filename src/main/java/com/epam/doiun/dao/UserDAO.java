package com.epam.doiun.dao;

import com.epam.doiun.entity.User;

public interface UserDAO {

	boolean exist(String email);

	void createUser(User user);

	User getUser(String email);
}
