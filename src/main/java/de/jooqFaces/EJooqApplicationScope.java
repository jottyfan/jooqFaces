package de.jooqFaces;

/**
 * 
 * @author henkej
 *
 */
public enum EJooqApplicationScope {
	JOOQ_FACES_URL("jooqFacesUrl"), //
	JOOQ_FACES_DRIVER("jooqFacesDriver"), //
	JOOQ_FACES_DSLCONTEXT("jooqFacesConnection"), //
	JOOQ_FACES_SQLDIALECT("jooqFacesSqldialect"), //
	JOOQ_FACES_PROPERTIES("jooqFacesProperties");

	private final String s;

	private EJooqApplicationScope(String s) {
		this.s = s;
	}

	public final String get() {
		return s;
	}
}
