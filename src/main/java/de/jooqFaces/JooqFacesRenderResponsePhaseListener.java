package de.jooqFaces;

import java.sql.*;
import java.util.*;

import javax.faces.context.*;
import javax.faces.event.*;

import org.apache.logging.log4j.*;
import org.jooq.*;
import org.jooq.exception.*;

/**
 * 
 * @author henkej
 *
 */
public class JooqFacesRenderResponsePhaseListener implements PhaseListener {
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(JooqFacesRenderResponsePhaseListener.class);
	
	/**
	 * close connection
	 */
	public void afterPhase(PhaseEvent event) {
		try {
			Map<String, Object> applMap = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
			DSLContext dslContext = (DSLContext) applMap.get(EJooqApplicationScope.JOOQ_FACES_DSLCONTEXT.get());
			dslContext.configuration().connectionProvider().acquire().close();
			dslContext.close();
			LOGGER.debug("Closed jooq connection in render response");
		} catch (DataAccessException | SQLException e) {
			LOGGER.error("Error closing jooq connection in render response", e);
		}
	}

	public void beforePhase(PhaseEvent event) {
	}

	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}
}
