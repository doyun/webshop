package com.epam.doiun.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.epam.doiun.constants.ExceptionMessage;
import com.epam.doiun.constants.Role;
import com.epam.doiun.dao.ConnectionHolder;
import com.epam.doiun.dao.UserDAO;
import com.epam.doiun.entity.User;
import com.epam.doiun.exception.DAOException;

public class MySQLUserDAO implements UserDAO {

	private static final String FIND_USER_BY_EMAIL = "SELECT * FROM user WHERE email=?";
	private static final String CREATE_USER = "INSERT INTO user (first_name, last_name, email, passhash, avatar_path, role) VALUES (?, ?, ?, ?, ?, ?)";
	private static final Logger LOGGER = Logger.getLogger(MySQLUserDAO.class);

	private ConnectionHolder connectionHolder;

	public MySQLUserDAO(ConnectionHolder connectionHolder) {
		this.connectionHolder = connectionHolder;
	}

	@Override
	public boolean exist(String email) {
		if (getUser(email) != null) {
			return true;
		}
		return false;
	}

	@Override
	public void createUser(User user) {
		Connection con = connectionHolder.get();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(CREATE_USER);

			int i = 1;
			pstmt.setString(i++, user.getFirstName());
			pstmt.setString(i++, user.getLastName());
			pstmt.setString(i++, user.getEmail());
			pstmt.setString(i++, user.getPasshash());
			pstmt.setString(i++, user.getAvatarPath());
			pstmt.setString(i++, user.getRole().toString());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("Fail to create user. " + user + e.getMessage());
			throw new DAOException(e);
		} finally {
			closePreparedStatement(pstmt);
		}
	}

	@Override
	public User getUser(String email) {
		Connection con = connectionHolder.get();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(FIND_USER_BY_EMAIL);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return extractUser(rs);
			}
		} catch (SQLException e) {
			LOGGER.error("Fail to find user in getUser() method. "
					+ e.getMessage());
			throw new DAOException(e);
		} finally {
			closePreparedStatement(pstmt);
		}
		return null;
	}

	private User extractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setEmail(rs.getString("email"));
		user.setAvatarPath(rs.getString("avatar_path"));
		user.setFirstName(rs.getString("first_name"));
		user.setLastName(rs.getString("last_name"));
		user.setPasshash(rs.getString("passhash"));
		user.setRole(Role.valueOf(rs.getString("role")));
		return user;
	}

	private void closePreparedStatement(PreparedStatement pstmt) {
		try {
			pstmt.close();
		} catch (SQLException e) {
			LOGGER.error("Fail to close PreparedStatement.");
			throw new DAOException(
					ExceptionMessage.CLOSE_STATEMENT_EXCEPTION_MESSAGE
							.getMessage(),
					e);
		}
	}
}
