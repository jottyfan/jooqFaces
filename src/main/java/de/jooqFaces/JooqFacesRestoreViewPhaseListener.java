package de.jooqFaces;

import java.io.*;
import java.sql.*;

import javax.faces.context.*;
import javax.faces.event.*;
import javax.servlet.*;

import org.apache.logging.log4j.*;
import org.jooq.*;
import org.jooq.impl.*;

/**
 * 
 * @author henkej
 *
 */
public class JooqFacesRestoreViewPhaseListener implements PhaseListener {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(JooqFacesRestoreViewPhaseListener.class);
	
	public void afterPhase(PhaseEvent event) {
	}

	/**
	 * get driver from initial context
	 * 
	 * @param servletContext
	 *          the context of this function call
	 * @return the parameter value of the jooq faces driver
	 */
	public String getDriver(ServletContext servletContext) {
		return servletContext.getInitParameter(EJooqApplicationScope.JOOQ_FACES_DRIVER.get());
	}

	/**
	 * get driver connection url from initial context
	 * 
	 * @param servletContext
	 *          the context of this function call
	 * @return the parameter value of the jooq faces url
	 */
	public String getUrl(ServletContext servletContext) {
		return servletContext.getInitParameter(EJooqApplicationScope.JOOQ_FACES_URL.get());
	}

	/**
	 * find jooq sql dialect class for dialectName
	 * 
	 * @param dialectName
	 *          name of dialect
	 * @return SQLDialect if found, null otherwise
	 */
	public static final SQLDialect findDialect(String dialectName) {
		if (dialectName == null) {
			LOGGER.error("Sql dialect name is null");
			return null;
		} else {
			for (SQLDialect dialect : SQLDialect.values()) {
				LOGGER.trace("Sql dialect comparing: dialectName={}, loopDialect={}", dialectName, dialect);
				if (dialectName.equalsIgnoreCase(dialect.name())) {
					LOGGER.debug("Sql dialect found: dialectName={}, foundDialect={}", dialectName, dialect);
					return dialect;
				}
			}
			LOGGER.error("Sql dialect not found: dialectName={}", dialectName);
			return null;
		}
	}

	/**
	 * get jooq sql dialect from initial context
	 * 
	 * @param servletContext
	 *          the context of this function call
	 * @return the dialect or null
	 */
	public SQLDialect getSqlDialect(ServletContext servletContext) {
		String dialectName = servletContext.getInitParameter(EJooqApplicationScope.JOOQ_FACES_SQLDIALECT.get());
		return findDialect(dialectName);
	}

	/**
	 * register connection
	 */
	public void beforePhase(PhaseEvent event) {
		try {
			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String driver = getDriver(ctx);
			String url = getUrl(ctx);
			SQLDialect sqlDialect = getSqlDialect(ctx);
			if (driver == null) {
				throw new IOException(
						"undefined driver in application scope, define it in your web.xml's context-param on name "
								+ EJooqApplicationScope.JOOQ_FACES_DRIVER.get());
			}
			if (url == null) {
				throw new IOException(
						"undefined connection data url in application scope, define it in your web.xml's context-param on name "
								+ EJooqApplicationScope.JOOQ_FACES_URL.get());
			}
			if (sqlDialect == null) {
				throw new IOException(
						"undefined sql dialect in application scope, define it in your web.xml's context-param on name "
								+ EJooqApplicationScope.JOOQ_FACES_SQLDIALECT.get());
			}
			Class.forName(driver);
			Connection connection = DriverManager.getConnection(url);
			DSLContext dslContext = DSL.using(connection, sqlDialect);
			FacesContext.getCurrentInstance().getExternalContext().getApplicationMap()
					.put(EJooqApplicationScope.JOOQ_FACES_DSLCONTEXT.get(), dslContext);
			LOGGER.debug("Created new jooq connection and put into facescontext");
			LOGGER.trace("newConnection={}", dslContext);
		} catch (ClassNotFoundException | IOException | SQLException e) {
			LOGGER.error("Error creating jooq connection", e);
		}
	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
