// JDBC Fun
// License: MIT http://opensource.org/licenses/MIT
// Copyright: 2014 Christopher Davis <http://christopherdavis.me>
package org.chrisguitarguy.jdbcfun.resource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Fetches JDBC Connections from an environment variable.
 *
 * @since   1.0
 */
public class MysqlJdbcResource implements Resource<Connection>
{
    final private static String DEFAULT_ENV = "DATABASE_URL";
    final private static String SCHEME = "mysql";

    private String envVariable;
    private String defaultValue;

    public MysqlJdbcResource(String envVariable, String defaultValue)
    {
        this.envVariable = envVariable;
        this.defaultValue = defaultValue;
    }

    public MysqlJdbcResource(String envVariable)
    {
        this(envVariable, null);
    }

    public MysqlJdbcResource()
    {
        this(DEFAULT_ENV, null);
    }

    /**
     * @return  A MySQL database connection
     */
    public Connection create()
    {
        String connUri = null == System.getenv(envVariable) ? defaultValue : System.getenv(envVariable);
        if (null == connUri) {
            throw new ResourceError(String.format("No value found in environment variable %s", envVariable));
        }

        URI uri;
        try {
            uri = new URI(connUri);
        } catch (URISyntaxException e) {
            throw new ResourceError(e);
        }

        if (!hasValidScheme(uri)) {
            throw new ResourceError(String.format("Scheme should be mysql://, got %s", connUri));
        }

        if (!hasValidPath(uri)) {
            throw new ResourceError("URI path cannot be empty");
        }

        String jdbcUri = createJdbcUri(uri);
        Properties jdbcProps = createJdbcProperties(uri);

        try {
            return DriverManager.getConnection(jdbcUri, jdbcProps);
        } catch (SQLException e) {
            throw new ResourceError(e);
        }
    }

    private boolean hasValidScheme(URI uri)
    {
        return SCHEME.equals(uri.getScheme());
    }

    private boolean hasValidPath(URI uri)
    {
        String path = uri.getPath();
        if (null == path || path.equals("") || path.equals("/")) {
            return false;
        }

        if (!path.matches("^/?[a-zA-Z][a-zA-Z0-9]*")) {
            return false;
        }

        return true;
    }

    private String createJdbcUri(URI uri)
    {
        return String.format(
            "jdbc:mysql://%s/%s",
            uri.getHost(),
            uri.getPath().replaceAll("^/", "")
        );
    }

    private Properties createJdbcProperties(URI uri)
    {
        Properties props = new Properties();
        String userinfo = uri.getUserInfo();
        if (null == userinfo) {
            return props;
        }

        String[] parts = userinfo.split(":", 2);
        props.setProperty("user", parts[0]);
        if (parts.length >= 2) {
            props.setProperty("password", parts[1]);
        }

        return props;
    }
}
