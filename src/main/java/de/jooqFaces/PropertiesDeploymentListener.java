package de.jooqFaces;

import java.io.*;
import java.sql.*;
import java.util.*;import javax.servlet.*;

import org.apache.logging.log4j.*;

/**
 * 
 * @author henkej
 *
 */
public class PropertiesDeploymentListener implements ServletContextListener {
	
	private static final Logger LOGGER = LogManager.getLogger(PropertiesDeploymentListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		try {
			Enumeration<Driver> drivers = DriverManager.getDrivers();
			while (drivers.hasMoreElements()) {
				DriverManager.deregisterDriver(drivers.nextElement());
			}
		} catch (SQLException | SecurityException e) {
			LOGGER.error("Error deregistering drivers", e);
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			ServletContext ctx = event.getServletContext();
			beforeInitialization(ctx);
			String propertiesFileName = (String) ctx.getInitParameter(EJooqApplicationScope.JOOQ_FACES_PROPERTIES.get());
			if (propertiesFileName == null) {
				throw new IOException(
						"undefined properties file name in application scope, define it in your web.xml's context-param on name "
								+ EJooqApplicationScope.JOOQ_FACES_PROPERTIES.get());
			}
			Properties properties = new Properties();
			properties.load(new FileInputStream(propertiesFileName));
			for (Map.Entry<Object, Object> entry : properties.entrySet()) {
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				ctx.setInitParameter(key, value);
			}
			// ensure to have all needed parameters loaded
			if (ctx.getInitParameter(EJooqApplicationScope.JOOQ_FACES_SQLDIALECT.get()) == null) {
				throw new IOException("no " + EJooqApplicationScope.JOOQ_FACES_SQLDIALECT.get()
						+ " defined in your properties file " + propertiesFileName);
			}
			if (ctx.getInitParameter(EJooqApplicationScope.JOOQ_FACES_URL.get()) == null) {
				throw new IOException("no " + EJooqApplicationScope.JOOQ_FACES_URL.get() + " defined in your properties file "
						+ propertiesFileName);
			}
			if (ctx.getInitParameter(EJooqApplicationScope.JOOQ_FACES_DRIVER.get()) == null) {
				throw new IOException("no " + EJooqApplicationScope.JOOQ_FACES_DRIVER.get()
						+ " defined in your properties file " + propertiesFileName);
			}
			afterInitialization(ctx);
		} catch (IOException e) {
			LOGGER.error("Error loading needed parameters from properties file", e);
		}
	}

	/**
	 * executed directly after initialization if no exception is thrown
	 * 
	 * @param ctx
	 *          the context to use
	 * @throws IOException
	 *           for input output exceptions
	 */
	public void afterInitialization(ServletContext ctx) throws IOException {
		// to be implemented in extending classes
	}

	/**
	 * executed directly before initialization after getting the context from the servlet
	 * 
	 * @param ctx
	 *          the context to use
	 * @throws IOException
	 *           for input output exceptions
	 */
	public void beforeInitialization(ServletContext ctx) throws IOException {
		// to be implemented in extending classes
	}
}
