package com.epam.doiun.bean;

import java.util.Date;

public class CaptchaBean {

	private String answer;
	private long date;

	public CaptchaBean(String answer) {
		this.answer = answer;
		this.date = new Date().getTime();
	}

	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @return the date
	 */
	public long getDate() {
		return date;
	}
}
