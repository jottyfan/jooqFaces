package de.jooqFaces;

import java.io.*;
import java.sql.*;
import java.util.*;

import javax.servlet.*;

/**
 * 
 * @author henkej
 *
 */
public class PropertiesDeploymentListener implements ServletContextListener {
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		try {
			Enumeration<Driver> drivers = DriverManager.getDrivers();
			while (drivers.hasMoreElements()) {
				DriverManager.deregisterDriver(drivers.nextElement());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			ServletContext ctx = event.getServletContext();
			String propertiesFileName = (String) ctx.getInitParameter(EJooqApplicationScope.JOOQ_FACES_PROPERTIES.get());
			if (propertiesFileName == null) {
				throw new IOException(
						"undefined properties file name in application scope, define it in your web.xml's context-param on name "
								+ EJooqApplicationScope.JOOQ_FACES_PROPERTIES.get());
			}
			Properties properties = new Properties();
			properties.load(new FileInputStream(propertiesFileName));
			ctx.setInitParameter(EJooqApplicationScope.JOOQ_FACES_SQLDIALECT.get(),
					properties.getProperty(EJooqApplicationScope.JOOQ_FACES_SQLDIALECT.get()));
			ctx.setInitParameter(EJooqApplicationScope.JOOQ_FACES_URL.get(),
					properties.getProperty(EJooqApplicationScope.JOOQ_FACES_URL.get()));
			ctx.setInitParameter(EJooqApplicationScope.JOOQ_FACES_DRIVER.get(),
					properties.getProperty(EJooqApplicationScope.JOOQ_FACES_DRIVER.get()));
			// for security reasons, remove the url
			properties.remove(EJooqApplicationScope.JOOQ_FACES_URL.get());
			ctx.setInitParameter(EJooqApplicationScope.JOOQ_FACES_PROPERTIES.get(), properties.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
