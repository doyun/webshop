package com.epam.doiun.servlet;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.doiun.constants.ApplicationConstants;

/**
 * Servlet implementation class ImageServlet
 */
@WebServlet("/media")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String rootMediaFolder;

	@Override
	public void init() throws ServletException {
		super.init();
		rootMediaFolder = (String) getServletContext().getAttribute(
				ApplicationConstants.ROOT_MEDIA_FOLDER.getValue());
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setHeader(
				"Content-Type",
				getServletContext().getMimeType(
						rootMediaFolder + request.getParameter("path")));

		BufferedOutputStream output = null;

		output = new BufferedOutputStream(response.getOutputStream());
		Path path = Paths.get(rootMediaFolder + request.getParameter("path"));
		byte[] data = Files.readAllBytes(path);
		output.write(data);
	}
}
