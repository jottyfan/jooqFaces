package de.jooqFaces;

import javax.faces.*;
import javax.faces.context.*;
import javax.faces.lifecycle.*;

/**
 * 
 * @author jotty
 *
 */
public class JooqFacesContextFactory extends FacesContextFactory {

	private FacesContextFactory facesContextFactory;

	public JooqFacesContextFactory(FacesContextFactory facesContextFactory) {
		this.facesContextFactory = facesContextFactory;
	}

	@Override
	public FacesContext getFacesContext(Object context, Object request, Object response, Lifecycle lifecycle)
			throws FacesException {
		return new JooqFacesContext(facesContextFactory.getFacesContext(context, request, response, lifecycle));
	}
}
