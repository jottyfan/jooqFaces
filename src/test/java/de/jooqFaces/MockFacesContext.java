package de.jooqFaces;

import javax.faces.context.*;

import org.mockito.*;
import org.mockito.invocation.*;
import org.mockito.stubbing.*;

/**
 * 
 * @author henkej
 *
 */
public abstract class MockFacesContext extends FacesContext {
	private MockFacesContext() {
	}

	private static final Release RELEASE = new Release();

	private static class Release implements Answer<Void> {
		@Override
		public Void answer(InvocationOnMock invocation) throws Throwable {
			setCurrentInstance(null);
			return null;
		}
	}

	/**
	 * @return create a faces context mock
	 */
	public static FacesContext create() {
		FacesContext context = Mockito.mock(FacesContext.class);
		setCurrentInstance(context);
		Mockito.doAnswer(RELEASE).when(context).release();
		return context;
	}
}
