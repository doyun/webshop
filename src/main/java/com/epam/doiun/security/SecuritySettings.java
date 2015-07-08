package com.epam.doiun.security;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "security")
@XmlAccessorType(XmlAccessType.FIELD)
public class SecuritySettings {

	@XmlElement(name = "constraint")
	private List<Constraint> constraints;

	/**
	 * @return the constraints
	 */
	public List<Constraint> getConstraints() {
		return constraints;
	}

	/**
	 * @param constraints
	 *            the constraints to set
	 */
	public void setConstraints(List<Constraint> constraints) {
		this.constraints = constraints;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SecuritySettings [constraints=" + constraints + "]";
	}

}
