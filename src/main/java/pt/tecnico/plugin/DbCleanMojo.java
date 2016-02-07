package pt.tecnico.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

/**
 * Clean database tables.
 */
@Mojo(	name = "dbclean",
	defaultPhase = LifecyclePhase.GENERATE_TEST_RESOURCES,
	requiresProject = true)
public class DbCleanMojo extends AbstractMojo {

    /** The directory where the resource files are located.  */
    @Parameter(defaultValue = "${basedir}/src/main/resources/")
    protected String resDir;

    /** Database connection driver.  */
    @Parameter(property = "dbclean.driver", defaultValue = "com.mysql.jdbc.Driver")
    protected String driver;

    /** Database location URL.  */
    @Parameter(property = "dbclean.url", defaultValue = "jdbc:mysql://localhost/")
    protected String url;

    /** Database resource file.  */
    @Parameter(property = "dbclean.resource", defaultValue = "fenix-framework-jvstm-ojb.properties")
    protected String resource;

    /** Database name.  */
    @Parameter(property = "dbclean.dbase", defaultValue = "")
    protected String dbase;

    /** Database user.  */
    @Parameter(property = "dbclean.user", defaultValue = "")
    protected String user;

    /** Database user's password.  */
    @Parameter(property = "dbclean.passwd", defaultValue = "")
    protected String passwd;

    private Connection conn = null;

    public void execute() throws MojoExecutionException
    {
      try {
        getDbase(resource);
	cleanup();
      } catch (Exception e) { getLog().warn(e); } // getLog().error(e);
    }

    public void cleanup() throws Exception {
      Class.forName(driver);
      System.err.println(user+"@"+url+dbase); // debug
      connect();
      System.err.println("Connected..."); // debug
      try { sql("DROP DATABASE " + dbase);
      } catch (SQLException e) { System.err.println(e); }
      sql("CREATE DATABASE " + dbase);
      disconnect();
    }

    public void sql(String com) throws SQLException {
      Statement stmt = conn.createStatement();
      stmt.executeUpdate(com);
      stmt.close();
    }

    public void connect() throws SQLException {
      conn = DriverManager.getConnection(url, user, passwd);
    }
    public void disconnect() throws SQLException {
      if (conn != null) conn.close();
      conn = null;
    }

    /* read database information from resource file */
    private void getDbase(String fileName) {
      File file = new File(resDir+fileName);

      try (Scanner scanner = new Scanner(file)) {
	while (scanner.hasNextLine()) {
	  String line = scanner.nextLine().trim();
	  if (line.length() == 0) continue;
	  if (line.charAt(0) == '#') continue;
	  String[] attr = line.split("=");
	  if (attr[0].equals("dbUsername")) user = attr[1].trim();
	  if (attr[0].equals("dbPassword")) passwd = attr[1].trim();
	  if (attr[0].equals("dbAlias")) {
	    int i = attr[1].trim().lastIndexOf('/');
	    int j = attr[1].trim().lastIndexOf('?');
	    dbase = attr[1].trim().substring(i+1,j);
	  }
	}
	scanner.close();
      } catch (IOException e) { e.printStackTrace(); }
    }
}
