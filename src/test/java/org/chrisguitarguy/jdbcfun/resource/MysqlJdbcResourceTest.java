// JDBC Fun
// License: MIT http://opensource.org/licenses/MIT
// Copyright: 2014 Christopher Davis <http://christopherdavis.me>

package org.chrisguitarguy.jdbcfun.resource;

import java.sql.Connection;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Assume;

public class MysqlJdbcResourceTest
{
    @Test(expected=ResourceError.class)
    public void emptyEnvironmentVariableErrorsWhenConnectionIsCreated() throws Exception
    {
        MysqlJdbcResource r = new MysqlJdbcResource("THIS_DOES_NOT_EXIST_AT_ALL");
        r.create();
    }

    @Test(expected=ResourceError.class)
    public void invalidUriForConnectionCausesError() throws Exception
    {
        MysqlJdbcResource r = new MysqlJdbcResource("IGNORE_ENV_VAR", "://nopenope");
        r.create();
    }

    @Test(expected=ResourceError.class)
    public void uriWithoutMysqlProtocolCausesError() throws Exception
    {
        MysqlJdbcResource r = new MysqlJdbcResource("IGNORE_ENV_VAR", "postgres://localhost/dbname");
        r.create();
    }

    @Test(expected=ResourceError.class)
    public void uriWithEmptyPathCausesError() throws Exception
    {
        MysqlJdbcResource r = new MysqlJdbcResource("IGNORE_ENV_VAR", "mysql://localhost");
        r.create();
    }

    @Test(expected=ResourceError.class)
    public void uriWithInvalidCharactersInPathCausesError() throws Exception
    {
        MysqlJdbcResource r = new MysqlJdbcResource("IGNORE_ENV_VAR", "mysql://localhost/path/to/whatever");
        r.create();
    }

    @Test
    public void validUriCreatesValidDatabaseConnection() throws Exception
    {
        final String envVar = "DATABASE_URL";
        Assume.assumeTrue(System.getenv().containsKey(envVar));
        MysqlJdbcResource r = new MysqlJdbcResource(envVar);
        Connection conn = r.create();
        conn.close();
    }
}
