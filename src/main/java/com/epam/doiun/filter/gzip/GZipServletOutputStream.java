package com.epam.doiun.filter.gzip;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;

import org.apache.log4j.Logger;

public class GZipServletOutputStream extends ServletOutputStream {

	private static final Logger LOGGER = Logger
			.getLogger(GZipServletOutputStream.class);
	private GZIPOutputStream gZipOutput;
	private ServletResponse response;
	private ByteArrayOutputStream baos;

	public GZipServletOutputStream(ServletResponse response) throws IOException {
		super();
		this.response = response;
		baos = new ByteArrayOutputStream();
	}

	@Override
	public void close() throws IOException {
		getOutput().close();
	}

	@Override
	public void flush() throws IOException {
		getOutput().flush();
	}

	@Override
	public void write(byte b[]) throws IOException {
		getOutput().write(b);
	}

	@Override
	public void write(byte b[], int off, int len) throws IOException {
		getOutput().write(b, off, len);
	}

	@Override
	public void write(int b) throws IOException {
		getOutput().write(b);
	}

	@Override
	public boolean isReady() {
		return false;
	}

	@Override
	public void setWriteListener(WriteListener writeListener) {
	}

	private OutputStream getOutput() throws IOException {
		String contentType = response.getContentType();
		if (contentType != null && contentType.startsWith("text")) {
			if (gZipOutput == null) {
				gZipOutput = new GZIPOutputStream(baos);
			}
			return gZipOutput;
		}
		return baos;
	}

	public void writeToResponse() throws IOException {
		response.getOutputStream().write(baos.toByteArray());
		response.getOutputStream().close();
	}

	public int getContentLength() {
		return baos.size();
	}
}
