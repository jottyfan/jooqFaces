# jooqFaces
combining jooq usage and java server faces

# concept
The idea is to open a database connection in the jsf restore view phase and keep it open until the render response phase is executed.

# usage
If configured, you can access the jooq context using this code snipplet:
````java
DSLContext jooqContext = (DSLContext) facesContext.getExternalContext()
.getApplicationMap().get(EJooqApplicationScope.JOOQ_FACES_DSLCONTEXT.get());
````

# configuration
Register the phase listeners in your faces-config.xml file by adding these two tags to your lifecycle tag:

````xml
<phase-listener>de.jooqFaces.JooqFacesRenderResponsePhaseListener</phase-listener>
<phase-listener>de.jooqFaces.JooqFacesRestoreViewPhaseListener</phase-listener>
````

You can access the jooq relevant datasource by getting the application map's entry with key jooqConnection.

In your web.xml, register the database driver for example on a postgresql database and your connection information (replace your connection data):

````xml
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
````
# alternative
If you want to use a properties file instead, you might ignore the context params jooqFacesDriver, jooqFacesUrl and jooqFacesSqldialect in your web.xml and replace them by the context param jooqFacesProperties that contains the full url to the properties file that contains these parameters:

````xml
<context-param>
  <param-name>jooqFacesProperties</param-name>
  <param-value>/etc/tomcat/my.properties</param-value>
</context-param>
````
Additionally, add this line to your web.xml to load the content of the properties file into the web application context:
````xml
	<listener>
		<listener-class>de.jooqFaces.PropertiesDeploymentListener</listener-class>
	</listener>
````
The my.properties might look like that way:
````
jooqFacesDriver = org.postgresql.Driver
jooqFacesUrl = jdbc:postgresql://myhost:myport/mydatabase?user=myuser&password=mypassword&ssl=true
jooqFacesSqldialect = POSTGRES_9_5
````
All other key value pairs are added to the application context as well. If you want to remove them after loading, extend PropertiesDeploymentListener and overwrite the method afterInitialization.

