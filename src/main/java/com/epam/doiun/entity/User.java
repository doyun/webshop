package com.epam.doiun.entity;

import java.io.Serializable;

import com.epam.doiun.constants.Role;

public class User implements Serializable {

	private static final long serialVersionUID = -5727275725974687206L;

	private Integer id;
	private String email;
	private String firstName;
	private String lastName;
	private String passhash;
	private String avatarPath;
	private Role role;

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the passhash
	 */
	public String getPasshash() {
		return passhash;
	}

	/**
	 * @param passhash
	 *            the passhash to set
	 */
	public void setPasshash(String passhash) {
		this.passhash = passhash;
	}

	/**
	 * @return the avatarPath
	 */
	public String getAvatarPath() {
		return avatarPath;
	}

	/**
	 * @param avatarPath
	 *            the avatarPath to set
	 */
	public void setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", firstName="
				+ firstName + ", lastName=" + lastName + ", passhash="
				+ passhash + ", avatarPath=" + avatarPath + ", role=" + role
				+ "]";
	}

}
