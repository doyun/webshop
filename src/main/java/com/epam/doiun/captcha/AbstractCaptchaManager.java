package com.epam.doiun.captcha;

import com.epam.doiun.bean.CaptchaBean;
import com.epam.doiun.constants.ApplicationConstants;
import com.epam.doiun.constants.ValidationStatus;
import nl.captcha.Captcha;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.text.renderer.DefaultWordRenderer;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class AbstractCaptchaManager {

	protected static final Logger LOGGER = Logger.getLogger(AbstractCaptchaManager.class);
	protected static final String CAPTCHA = "captcha";
	protected static final String ID_CAPTCHA = "idCaptcha";
	private static final List<Color> COLORS;
	private static final List<Font> FONTS;
	private Map<Integer, CaptchaBean> captchaMap;
	private ScheduledExecutorService scheduler;
	private Integer timeout;

	static {
		COLORS = new ArrayList<Color>();
		COLORS.add(Color.black);
		COLORS.add(Color.red);
		COLORS.add(Color.darkGray);
		COLORS.add(Color.green);
		COLORS.add(Color.magenta);

		FONTS = new ArrayList<Font>();
		FONTS.add(new Font("Arial", Font.ITALIC, 40));
		FONTS.add(new Font(Font.SERIF, Font.ITALIC, 40));
		FONTS.add(new Font(Font.SANS_SERIF, Font.BOLD, 40));
	}

	@SuppressWarnings("unchecked")
	public AbstractCaptchaManager(ServletContext context) {
		captchaMap = Collections
				.synchronizedMap((Map<Integer, CaptchaBean>) context
						.getAttribute(ApplicationConstants.CAPTCHA_MAP
								.getValue()));

		scheduler = Executors.newScheduledThreadPool(1);
		timeout = Integer.valueOf(context
				.getInitParameter(ApplicationConstants.CAPTCHA_TIMEOUT
						.getValue()));
		startCaptchaCleaner();
	}

	public String generate(HttpServletRequest request,
			HttpServletResponse response) {
		Captcha captcha = new Captcha.Builder(170, 50)
				.addText(new DefaultWordRenderer(COLORS, FONTS))
				.addBackground(
						new GradiatedBackgroundProducer(Color.white,
								Color.white))
				.addNoise().addNoise().addNoise()
				.gimp().addBorder().build();
		remember(new CaptchaBean(captcha.getAnswer()), request, response);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] bytes = null;
		try {
			ImageIO.write(captcha.getImage(), "png", baos);
			bytes = baos.toByteArray();
			baos.close();
		} catch (IOException e) {
			LOGGER.error("Fail to write image to ByteArrayOutputStream." + e.getMessage());
		}
		return new String(Base64.getEncoder().encode(bytes));
	}

	public void shutdownCaptchaCleaner() {
		scheduler.shutdown();
	}

	public abstract ValidationStatus isValid(HttpServletRequest request);

	protected abstract void remember(CaptchaBean captcha,
			HttpServletRequest request, HttpServletResponse response);

	protected Map<Integer, CaptchaBean> getCaptchaMap() {
		return captchaMap;
	}

	private void startCaptchaCleaner() {
		scheduler.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				long currentDate = new Date().getTime();
				for (Entry<Integer, CaptchaBean> entry : captchaMap.entrySet()) {
					if ((currentDate - entry.getValue().getDate()) > timeout) {
						captchaMap.remove(entry.getKey());
					}
				}
			}
		}, 0, 10, TimeUnit.MINUTES);
	}

}
