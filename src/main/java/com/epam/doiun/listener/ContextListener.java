package com.epam.doiun.listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

import com.epam.doiun.bean.CaptchaBean;
import com.epam.doiun.captcha.AbstractCaptchaManager;
import com.epam.doiun.captcha.SessionCaptchaManagerImpl;
import com.epam.doiun.constants.ApplicationConstants;
import com.epam.doiun.constants.ExceptionMessage;
import com.epam.doiun.dao.ConnectionHolder;
import com.epam.doiun.dao.ConnectionManager;
import com.epam.doiun.dao.OrderDAO;
import com.epam.doiun.dao.ProductDAO;
import com.epam.doiun.dao.ThreadLocalConnectionHolder;
import com.epam.doiun.dao.UserDAO;
import com.epam.doiun.dao.mysql.MySQLHikariConnectionManager;
import com.epam.doiun.dao.mysql.MySQLOrderDAO;
import com.epam.doiun.dao.mysql.MySQLProductDao;
import com.epam.doiun.dao.mysql.MySQLUserDAO;
import com.epam.doiun.services.OrderService;
import com.epam.doiun.services.OrderServiceImpl;
import com.epam.doiun.services.ProductService;
import com.epam.doiun.services.ProductServiceImpl;
import com.epam.doiun.services.TransactionManager;
import com.epam.doiun.services.UserService;
import com.epam.doiun.services.UserServiceImpl;
import com.epam.doiun.util.extractor.FileExtractor;

/**
 * Application Lifecycle Listener implementation class ContextLstener
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {

	private static final Logger LOGGER = Logger
			.getLogger(ContextListener.class);

	public void contextDestroyed(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		((AbstractCaptchaManager) context
				.getAttribute(ApplicationConstants.CAPTCHA_MANAGER.getValue()))
				.shutdownCaptchaCleaner();
		context.removeAttribute(ApplicationConstants.CAPTCHA_MANAGER.getValue());
		context.removeAttribute(ApplicationConstants.CAPTCHA_MAP.getValue());
		((ConnectionManager) context
				.getAttribute(ApplicationConstants.CONNECTION_MANAGER
						.getValue())).closeDataSource();
	}

	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		setRootFolderForMediaFiles(context);
		setCaptchaHashMap(context);
		loadCaptchaManager(context);
		initServices(context);
		initFileExtractor(context);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Context initialized.");
		}
	}

	private void loadCaptchaManager(ServletContext context) {

		String captchaManagerClass = context
				.getInitParameter(ApplicationConstants.CAPTCHA_MANAGER
						.getValue());

		AbstractCaptchaManager captchaManager;
		try {
			Class<?> clazz = Class.forName(captchaManagerClass);
			captchaManager = (AbstractCaptchaManager) clazz.getConstructor(
					ServletContext.class).newInstance(context);
		} catch (ClassCastException | ClassNotFoundException
				| InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException ex) {
			LOGGER.error(ExceptionMessage.CAPTCHA_MANAGER_LOAD_EXCEPTION
					.getMessage());
			captchaManager = new SessionCaptchaManagerImpl(context);
		}
		context.setAttribute(ApplicationConstants.CAPTCHA_MANAGER.getValue(),
				captchaManager);
	}

	private void setCaptchaHashMap(ServletContext context) {
		HashMap<Integer, CaptchaBean> captchaMap = new HashMap<Integer, CaptchaBean>();
		context.setAttribute(ApplicationConstants.CAPTCHA_MAP.getValue(),
				captchaMap);
	}

	private void initServices(ServletContext context) {
		ConnectionManager connectionManager = new MySQLHikariConnectionManager();
		ConnectionHolder connectionHolder = new ThreadLocalConnectionHolder();

		UserDAO userDAO = new MySQLUserDAO(connectionHolder);
		UserService userService = (UserService) Proxy.newProxyInstance(this
				.getClass().getClassLoader(),
				new Class<?>[] { UserService.class }, new TransactionManager(
						connectionManager, connectionHolder,
						new UserServiceImpl(userDAO)));
		context.setAttribute(ApplicationConstants.USER_SERVICE.getValue(),
				userService);

		ProductDAO productDAO = new MySQLProductDao(connectionHolder);
		ProductService productService = (ProductService) Proxy
				.newProxyInstance(this.getClass().getClassLoader(),
						new Class<?>[] { ProductService.class },
						new TransactionManager(connectionManager,
								connectionHolder, new ProductServiceImpl(
										productDAO)));

		OrderDAO orderDAO = new MySQLOrderDAO(connectionHolder);
		OrderService orderService = (OrderService) Proxy.newProxyInstance(this
				.getClass().getClassLoader(),
				new Class<?>[] { OrderService.class }, new TransactionManager(
						connectionManager, connectionHolder,
						new OrderServiceImpl(orderDAO)));
		context.setAttribute(ApplicationConstants.PRODUCT_SERVICE.getValue(),
				productService);
		context.setAttribute(ApplicationConstants.ORDER_SERVICE.getValue(),
				orderService);
		context.setAttribute(
				ApplicationConstants.CONNECTION_MANAGER.getValue(),
				connectionManager);
	}

	private void initFileExtractor(ServletContext context) {
		FileExtractor fileExtractor = new FileExtractor(context);
		context.setAttribute(ApplicationConstants.FILE_EXTRACTOR.getValue(),
				fileExtractor);
	}

	private void setRootFolderForMediaFiles(ServletContext context) {
		String rootFolderForMediaFiles = context
				.getInitParameter(ApplicationConstants.ROOT_MEDIA_FOLDER
						.getValue());
		context.setAttribute(ApplicationConstants.ROOT_MEDIA_FOLDER.getValue(),
				rootFolderForMediaFiles);
	}
}
