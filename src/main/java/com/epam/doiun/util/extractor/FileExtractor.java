package com.epam.doiun.util.extractor;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.epam.doiun.constants.ApplicationConstants;

public class FileExtractor {

	private String appPath;

	public FileExtractor(ServletContext context) {
		appPath = (String) context.getAttribute(ApplicationConstants.ROOT_MEDIA_FOLDER.getValue());
		File fileSaveDir = new File(appPath 
				+ ApplicationConstants.AVATAR_DIR.getValue());
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}
	}

	public String downloadFile(HttpServletRequest request, Part part)
			throws IOException {
		String fileName = "";
		String fileExtension = getFileExtension(part);
		if (!"".equals(fileExtension)) {
			fileName = ApplicationConstants.AVATAR_DIR.getValue()
					+ File.separator + UUID.randomUUID() + fileExtension;
			part.write(appPath + fileName);
		}
		return fileName;
	}

	private String getFileExtension(Part part) {
		String filename = "";
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				filename = content.substring(content.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		int index = filename.lastIndexOf('.');
		if (index == -1) {
			return "";
		}
		String fileExtension = filename.substring(index);
		return fileExtension;
	}

}
