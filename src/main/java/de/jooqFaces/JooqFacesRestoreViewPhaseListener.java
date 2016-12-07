package de.jooqFaces;

import java.io.*;
import java.sql.*;

import javax.faces.context.*;
import javax.faces.event.*;
import javax.servlet.*;

import org.jooq.*;
import org.jooq.impl.*;

/**
 * 
 * @author henkej
 *
 */
public class JooqFacesRestoreViewPhaseListener implements PhaseListener {
	private final static long serialVersionUID = 1L;

	public void afterPhase(PhaseEvent event) {
	}

	/**
	 * get driver from initial context
	 * 
	 * @param servletContext
	 * @return
	 */
	public String getDriver(ServletContext servletContext) {
		return (String) servletContext.getInitParameter(EJooqApplicationScope.JOOQ_FACES_DRIVER.get());
	}

	/**
	 * get driver connection url from initial context
	 * 
	 * @param servletContext
	 * @return
	 */
	public String getUrl(ServletContext servletContext) {
		return (String) servletContext.getInitParameter(EJooqApplicationScope.JOOQ_FACES_URL.get());
	}

	/**
	 * get jooq sql dialect from initial context
	 * 
	 * @param servletContext
	 * @return
	 */
	public SQLDialect getSqlDialect(ServletContext servletContext) {
		String dialectName = (String) servletContext.getInitParameter(EJooqApplicationScope.JOOQ_FACES_SQLDIALECT.get());
		if ("CUBRID".equalsIgnoreCase(dialectName)) {
			return SQLDialect.CUBRID;
		} else if ("DEFAULT".equalsIgnoreCase(dialectName)) {
			return SQLDialect.DEFAULT;
		} else if ("DERBY".equalsIgnoreCase(dialectName)) {
			return SQLDialect.DERBY;
		} else if ("FIREBIRD".equalsIgnoreCase(dialectName)) {
			return SQLDialect.FIREBIRD;
		} else if ("FIREBIRD_2_5".equalsIgnoreCase(dialectName)) {
			return SQLDialect.FIREBIRD_2_5;
		} else if ("FIREBIRD_3_0".equalsIgnoreCase(dialectName)) {
			return SQLDialect.FIREBIRD_3_0;
		} else if ("H2".equalsIgnoreCase(dialectName)) {
			return SQLDialect.H2;
		} else if ("HSQLDB".equalsIgnoreCase(dialectName)) {
			return SQLDialect.HSQLDB;
		} else if ("MARIADB".equalsIgnoreCase(dialectName)) {
			return SQLDialect.MARIADB;
		} else if ("MYSQL".equalsIgnoreCase(dialectName)) {
			return SQLDialect.MYSQL;
		} else if ("POSTGRES".equalsIgnoreCase(dialectName)) {
			return SQLDialect.POSTGRES;
		} else if ("POSTGRES_9_3".equalsIgnoreCase(dialectName)) {
			return SQLDialect.POSTGRES_9_3;
		} else if ("POSTGRES_9_4".equalsIgnoreCase(dialectName)) {
			return SQLDialect.POSTGRES_9_4;
		} else if ("POSTGRES_9_5".equalsIgnoreCase(dialectName)) {
			return SQLDialect.POSTGRES_9_5;
		} else if ("SQL99".equalsIgnoreCase(dialectName)) {
			return SQLDialect.SQL99;
		} else if ("SQLITE".equalsIgnoreCase(dialectName)) {
			return SQLDialect.SQLITE;
		} else {
			return null;
		}
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
			FacesContext.getCurrentInstance().getExternalContext().getApplicationMap()
					.put(EJooqApplicationScope.JOOQ_FACES_DSLCONTEXT.get(), DSL.using(connection, sqlDialect));
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
		}
	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
