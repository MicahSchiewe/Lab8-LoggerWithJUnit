package edu.baylor.ecs.si;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Timer {

	private static Logger logger = Logger.getLogger(Timer.class.getName());

	static {
		// Note to writer: do not rely on a try/catch block running to completion to
		// close a resource! It may leak a file handle!
		try (InputStream configFile = Timer.class.getClassLoader().getResourceAsStream("logger.properties")) {
			LogManager.getLogManager().readConfiguration(configFile);
		} catch (IOException ex) {
			System.out.println("WARNING: Could not open configuration file");
			System.out.println("WARNING: Logging not configured (console output only)");
		}
		logger.info("starting the app");
	}

	/*
	 * Just sleep
	 */
	private static void method(long time) throws InterruptedException {
		Thread.sleep(time);
	}

	public static long timeMe(long timeToWait) throws TimerException {
		Long timeNow = null;
		try {
			if (timeToWait < 0) {
				throw new TimerException("Cannot be less than zero");
			}
			timeNow = System.currentTimeMillis();
			method(timeToWait);

		} catch (InterruptedException e) {
			logger.severe("InterruptedException rised");
			throw new TimerException("Sleep exception", e);
		} finally {
			if (timeNow != null) {
				logger.info("Calling took: " + (System.currentTimeMillis() - timeNow));
				logger.info("* should take: " + timeToWait);
			} else {
				logger.warning("Call did not begin properly");
			}
		}
		return timeNow;
	}
}
