package de.jooqFaces;

/**
 * 
 * @author henkej
 *
 */
public enum EJooqApplicationScope {
	JOOQ_FACES_URL("jooqFacesConnection"), JOOQ_FACES_DRIVER("jooqFacesDriver"), JOOQ_FACES_DSLCONTEXT("jooqFacesConnection"), JOOQ_FACES_SQLDIALECT("jooqFacesSqldialect");

	private final String s;

	private EJooqApplicationScope(String s) {
		this.s = s;
	}

	public final String get() {
		return s;
	}
}
