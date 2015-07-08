package com.epam.doiun.security;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "constraint")
@XmlAccessorType(XmlAccessType.FIELD)
public class Constraint {

	@XmlElement(name = "url-pattern")
	private String urlPattern;
	@XmlElement(name = "role")
	private List<String> roles;

	/**
	 * @return the urlPattern
	 */
	public String getUrlPattern() {
		return urlPattern;
	}

	/**
	 * @param urlPattern
	 *            the urlPattern to set
	 */
	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}

	/**
	 * @return the role
	 */
	public List<String> getRoles() {
		return roles;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Constraint [urlPattern=" + urlPattern + ", roles=" + roles
				+ "]";
	}

}
