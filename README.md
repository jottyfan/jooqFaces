# jooqFaces
combining jooq usage and java server faces

# concept
The idea is to open a database connection in the jsf restore view phase and keep it open until the render response phase is executed.

# usage
Register the phase listeners in your faces-config.xml file by adding these two tags to your lifecycle tag:

<phase-listener>de.jooqFaces.JooqFacesRenderResponsePhaseListener</phase-listener>
<phase-listener>de.jooqFaces.JooqFacesRestoreViewPhaseListener</phase-listener>

You can access the jooq relevant datasource by getting the application map's entry with key jooqConnection.

In your web.xml, register the database driver for example on a postgresql database and your connection information (replace your connection data):

<context-param>
	<param-name>jooqFacesDriver</param-name>
	<param-value>org.postgresql.Driver</param-value>
</context-param>

<context-param>
	<param-name>jooqFacesUrl</param-name>
	<param-value>jdbc:postgresql://myhost:myport/mydatabase?user=myuser&password=mypassword&ssl=true</param-value>
</context-param>

<context-param>
	<param-name>jooqFacesSqldialect</param-name>
	<param-value>POSTGRES_9_5</param-value>
</context-param>

# extensions

If you want to omit telling your password to web.xml, you could also extend from JooqFacesRestoreViewPhaseListener and write your own one, let's call it MyJooqFacesRestoreViewPhaseListener. Then, you might overwrite the method getUrl to set up your own url string on any other way you want. Of course, you have to register your MyJooqFacesRestoreViewPhaseListener instead of the JooqFacesRestoreViewPhaseListener in faces-config.xml's lifecycle tag.
