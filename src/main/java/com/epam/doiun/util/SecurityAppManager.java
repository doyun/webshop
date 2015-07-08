package com.epam.doiun.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

/**
 * Provides methods for security.
 * 
 * @author Nikita
 *
 */
public final class SecurityAppManager {

	private static final Logger LOGGER = Logger
			.getLogger(SecurityAppManager.class);

	/**
	 * Creates passhash for password using MD5.
	 * 
	 * @param password
	 * @return
	 */
	public static String createPasshash(String password) {

		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error(e.getMessage());
		}

		digest.update(password.getBytes(Charset.forName("UTF-8")));
		byte[] hash = digest.digest();
		StringBuilder sb = new StringBuilder();

		for (byte b : hash) {
			sb.append(String.format("%02X", b));
		}
		return sb.toString();
	}
}
