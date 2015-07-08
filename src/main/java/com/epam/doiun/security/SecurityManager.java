package com.epam.doiun.security;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.epam.doiun.constants.Role;

public class SecurityManager {

	private SecuritySettings settings;
	private Map<String, Constraint> constraints;

	public SecurityManager(String path) throws JAXBException {
		unMarshale(path);
		constraints = new HashMap<String, Constraint>();
		for (Constraint constraint : settings.getConstraints()) {
			constraints.put(constraint.getUrlPattern(), constraint);
		}
	}

	public boolean isConstraintExisting(String url) {
		return constraints.containsKey(url);
	}

	public boolean isAccessAllowed(String url, Role role) {
		return constraints.get(url).getRoles().contains(role.toString());
	}

	private void unMarshale(String path) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext
				.newInstance(SecuritySettings.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		settings = (SecuritySettings) jaxbUnmarshaller.unmarshal(new File(
				getClass().getClassLoader().getResource(path).getFile()));
	}
}
