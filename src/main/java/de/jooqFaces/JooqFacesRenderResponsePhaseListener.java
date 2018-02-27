package de.jooqFaces;

import java.sql.*;
import java.util.*;

import javax.faces.context.*;
import javax.faces.event.*;

import org.jooq.*;
import org.jooq.exception.*;

/**
 * 
 * @author henkej
 *
 */
public class JooqFacesRenderResponsePhaseListener implements PhaseListener {
	private final static long serialVersionUID = 1L;

	/**
	 * close connection
	 */
	public void afterPhase(PhaseEvent event) {
		try {
			Map<String, Object> applMap = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
			DSLContext dslContext = (DSLContext) applMap.get(EJooqApplicationScope.JOOQ_FACES_DSLCONTEXT.get());
			dslContext.configuration().connectionProvider().acquire().close();
			dslContext.close();
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void beforePhase(PhaseEvent event) {
	}

	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}
}
