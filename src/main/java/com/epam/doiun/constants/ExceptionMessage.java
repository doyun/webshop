package com.epam.doiun.constants;

public enum ExceptionMessage {
	
	CLOSE_STATEMENT_EXCEPTION_MESSAGE("Fail to close statement."),
	INVOKE_EXCEPTION("Fail to invoke method of service."),
	COMMIT_FAIL("Fail to commit transaction."),
	CONNECTION_CLOSE_EXCEPTION("Fail to close connection."),
	CONNECTION_GET_EXCEPTION("Fail to get connection."),
	ROLLBACK_EXCEPTION("Fail to rollback transaction."),
	CAPTCHA_MANAGER_LOAD_EXCEPTION("Fail to load captcha manager from init parameters."),
	AVATAR_LOAD_EXCEPTION("Fail to load avatar picture."),
	METHOD_EXTRACTION_EXCEPTION("Fail to extract method."),
	STATUS("status"),
	INCORRECT_COLUMN_NAME("Incorrect column name!");

	private String message;

	private ExceptionMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the value
	 */
	public String getMessage() {
		return message;
	}

}
