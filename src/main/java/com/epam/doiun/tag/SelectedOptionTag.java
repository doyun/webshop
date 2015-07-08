package com.epam.doiun.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

public class SelectedOptionTag extends TagSupport {
	private static final long serialVersionUID = 7659203826250447879L;
	private static final Logger LOGGER = Logger
			.getLogger(SelectedOptionTag.class);
	private String parameter;
	private String value;

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			if (value.equals(pageContext.getRequest().getParameter(parameter))) {
				pageContext.getOut().write("selected");
			}
		} catch (IOException e) {
			LOGGER.error("Exception : " + e.getMessage());
		}
		return SKIP_BODY;
	}

}
