package de.jooqFaces;

import static org.junit.Assert.*;

import javax.servlet.*;

import org.jooq.*;
import org.junit.*;
import org.mockito.*;

/**
 * 
 * @author henkej
 *
 */
public class TestJooqFacesRestoreViewPhaseListener {

	/**
	 * test the before phase
	 */
	@Test
	public void testBeforePhase() {
		// TODO: setup using a database that can be configured in junit only
		// Map<String, Object> applicationMap = new HashMap<>();
		// DSLContext dslContext = Mockito.mock(DSLContext.class);
		// applicationMap.put(EJooqApplicationScope.JOOQ_FACES_DSLCONTEXT.get(), dslContext);
		// PhaseEvent event = Mockito.mock(PhaseEvent.class);
		// FacesContext facesContext = MockFacesContext.create();
		// ExternalContext externalContext = Mockito.mock(ExternalContext.class);
		// ServletContext servletContext = Mockito.mock(ServletContext.class);
		// Mockito.when(servletContext.getInitParameter(EJooqApplicationScope.JOOQ_FACES_DRIVER.get())).thenReturn("org.postgresql.Driver");
		// Mockito.when(servletContext.getInitParameter(EJooqApplicationScope.JOOQ_FACES_URL.get())).thenReturn("jdbc:postgresql:mydatabase");
		// Mockito.when(servletContext.getInitParameter(EJooqApplicationScope.JOOQ_FACES_SQLDIALECT.get())).thenReturn("POSTGRES_9_5");
		// Mockito.when(externalContext.getContext()).thenReturn(servletContext);
		// Mockito.when(externalContext.getApplicationMap()).thenReturn(applicationMap);
		// Mockito.when(facesContext.getExternalContext()).thenReturn(externalContext);
		//
		// assertNull(applicationMap.get(EJooqApplicationScope.JOOQ_FACES_DSLCONTEXT));
		//
		// new JooqFacesRestoreViewPhaseListener().beforePhase(event);
		//
		// assertNotNull(applicationMap.get(EJooqApplicationScope.JOOQ_FACES_DSLCONTEXT));
	}

	/**
	 * test the get driver method
	 */
	@Test
	public void testGetDriver() {
		ServletContext servletContext = Mockito.mock(ServletContext.class);
		Mockito.when(servletContext.getInitParameter(EJooqApplicationScope.JOOQ_FACES_DRIVER.get())).thenReturn("myDriver");
		assertEquals("myDriver", new JooqFacesRestoreViewPhaseListener().getDriver(servletContext));
	}

	/**
	 * test the get url method
	 */
	@Test
	public void testGetUrl() {
		ServletContext servletContext = Mockito.mock(ServletContext.class);
		Mockito.when(servletContext.getInitParameter(EJooqApplicationScope.JOOQ_FACES_URL.get())).thenReturn("myUrl");
		assertEquals("myUrl", new JooqFacesRestoreViewPhaseListener().getUrl(servletContext));
	}

	/**
	 * test the get sql dialect method
	 */
	@Test
	public void testGetSqlDialect() {
		ServletContext servletContext = Mockito.mock(ServletContext.class);
		assertNull(new JooqFacesRestoreViewPhaseListener().getSqlDialect(servletContext));
		Mockito.when(servletContext.getInitParameter(EJooqApplicationScope.JOOQ_FACES_SQLDIALECT.get()))
				.thenReturn("POSTGRES_9_5");
		assertEquals(SQLDialect.POSTGRES_9_5, new JooqFacesRestoreViewPhaseListener().getSqlDialect(servletContext));
	}
}
