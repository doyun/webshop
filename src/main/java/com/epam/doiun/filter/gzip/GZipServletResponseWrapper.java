package com.epam.doiun.filter.gzip;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.log4j.Logger;

public class GZipServletResponseWrapper extends HttpServletResponseWrapper {

	private static final Logger LOGGER = Logger
			.getLogger(GZipServletResponseWrapper.class);

	private GZipServletOutputStream wrapperOutputStream;
	private PrintWriter printWriter;

	public GZipServletResponseWrapper(HttpServletResponse response)
			throws IOException {
		super(response);
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (printWriter != null) {
			throw new IllegalStateException(
					"PrintWriter obtained already - can't get OutputStream");
		}
		if (wrapperOutputStream == null) {
			wrapperOutputStream = new GZipServletOutputStream(getResponse());
		}
		return wrapperOutputStream;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (printWriter == null && wrapperOutputStream != null) {
			throw new IllegalStateException(
					"OutputStream obtained already - can't get PrintWriter");
		}
		if (printWriter == null) {
			wrapperOutputStream = new GZipServletOutputStream(getResponse());
			printWriter = new PrintWriter(new OutputStreamWriter(
					wrapperOutputStream, getResponse().getCharacterEncoding()));
		}
		return printWriter;
	}

	@Override
	public void setContentType(String type) {
		super.setContentType(type);
		if (type.startsWith("text")) {
			setContentEncoding("gzip");
		}
	}

	private void setContentEncoding(String contentEncoding) {
		((HttpServletResponse) getResponse()).addHeader("Content-Encoding",
				contentEncoding);
	}

	@Override
	public void setContentLength(int len) {
	}

	@Override
	public void setContentLengthLong(long len) {
	}

	/**
	 * Flush OutputStream or PrintWriter
	 *
	 * @throws IOException
	 */
	@Override
	public void flushBuffer() throws IOException {

		if (printWriter != null) {
			printWriter.flush();
		}

		try {
			if (wrapperOutputStream != null) {
				wrapperOutputStream.flush();
			}
		} catch (IOException e) {
			throw e;
		}
	}

	public void close() throws IOException {
		if (printWriter != null) {
			printWriter.close();
		}
		if (wrapperOutputStream != null) {
			wrapperOutputStream.close();
		}
		if(wrapperOutputStream != null){
			wrapperOutputStream.writeToResponse();
			getResponse().setContentLength(wrapperOutputStream.getContentLength());
		}
	}
}
