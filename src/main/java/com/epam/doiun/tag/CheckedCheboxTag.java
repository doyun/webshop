package com.epam.doiun.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

public class CheckedCheboxTag extends TagSupport {
	private static final long serialVersionUID = 7659203826250447879L;
	private static final Logger LOGGER = Logger
			.getLogger(CheckedCheboxTag.class);
	private int entityId;
	private List<Integer> list;

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	public void setList(List<Integer> list) {
		this.list = list;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			if (list != null && list.contains(entityId)) {
				pageContext.getOut().write("checked");
			}
		} catch (IOException e) {
			LOGGER.error("Exception : " + e.getMessage());
		}
		return SKIP_BODY;
	}

}
