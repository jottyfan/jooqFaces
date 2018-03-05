package de.jooqFaces;

import java.sql.*;
import java.util.*;

import javax.faces.application.*;
import javax.faces.application.FacesMessage.*;
import javax.faces.component.*;
import javax.faces.context.*;
import javax.faces.render.*;

import org.jooq.*;
import org.jooq.exception.*;

/**
 * 
 * @author jotty
 *
 */
public class JooqFacesContext extends FacesContext {

	private FacesContext facesContext;

	public JooqFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
		setCurrentInstance(this);
	}

	@Override
	public void addMessage(String clientId, FacesMessage message) {
		facesContext.addMessage(clientId, message);
	}

	@Override
	public Application getApplication() {
		return facesContext.getApplication();
	}

	@Override
	public Iterator<String> getClientIdsWithMessages() {
		return facesContext.getClientIdsWithMessages();
	}

	@Override
	public ExternalContext getExternalContext() {
		return facesContext.getExternalContext();
	}

	@Override
	public Severity getMaximumSeverity() {
		return facesContext.getMaximumSeverity();
	}

	@Override
	public Iterator<FacesMessage> getMessages() {
		return facesContext.getMessages();
	}

	@Override
	public Iterator<FacesMessage> getMessages(String clientId) {
		return facesContext.getMessages(clientId);
	}

	@Override
	public RenderKit getRenderKit() {
		return facesContext.getRenderKit();
	}

	@Override
	public boolean getRenderResponse() {
		return facesContext.getRenderResponse();
	}

	@Override
	public boolean getResponseComplete() {
		
		return facesContext.getResponseComplete();
	}

	@Override
	public ResponseStream getResponseStream() {
		return facesContext.getResponseStream();
	}

	@Override
	public ResponseWriter getResponseWriter() {
		return facesContext.getResponseWriter();
	}

	@Override
	public UIViewRoot getViewRoot() {
		return facesContext.getViewRoot();
	}

	@Override
	public void release() {
		facesContext.release();
	}

	@Override
	public void renderResponse() {
		facesContext.renderResponse();
	}

	@Override
	public void responseComplete() {
		try {
			ExternalContext externalContext = facesContext.getExternalContext();
			if (externalContext == null) {
				throw new JooqFacesException("external context of current faces context is null");
			}
			Map<String, Object> applicationMap = externalContext.getApplicationMap();
			if (applicationMap == null) {
				throw new JooqFacesException("application map of current faces context is null");
			}
			DSLContext dslContext = (DSLContext) applicationMap.get(EJooqApplicationScope.JOOQ_FACES_DSLCONTEXT.get());
			if (dslContext == null) {
				throw new JooqFacesException("no dsl context found in application map");
			}
			dslContext.configuration().connectionProvider().acquire().close();
			dslContext.close();
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (JooqFacesException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		facesContext.responseComplete();
	}

	@Override
	public void setResponseStream(ResponseStream responseStream) {
		facesContext.setResponseStream(responseStream);
	}

	@Override
	public void setResponseWriter(ResponseWriter responseWriter) {
		facesContext.setResponseWriter(responseWriter);
	}

	@Override
	public void setViewRoot(UIViewRoot root) {
		facesContext.setViewRoot(root);
	}
}
