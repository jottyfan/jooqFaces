package de.jooqFaces;

/**
 * 
 * @author henkej
 *
 */
public enum EJooqApplicationScope {
	/**
	 * jooqFacesUrl
	 */
	JOOQ_FACES_URL("jooqFacesUrl"),
	/**
	 * jooqFacesDriver
	 */
	JOOQ_FACES_DRIVER("jooqFacesDriver"),
	/**
	 * jooqFacesConnection
	 */
	JOOQ_FACES_DSLCONTEXT("jooqFacesConnection"),
	/**
	 * jooqFacesSqldialect
	 */
	JOOQ_FACES_SQLDIALECT("jooqFacesSqldialect"),
	/**
	 * jooqFacesProperties
	 */
	JOOQ_FACES_PROPERTIES("jooqFacesProperties");

	private final String s;

	private EJooqApplicationScope(String s) {
		this.s = s;
	}

	/**
	 * @return the value
	 */
	public final String get() {
		return s;
	}
}
