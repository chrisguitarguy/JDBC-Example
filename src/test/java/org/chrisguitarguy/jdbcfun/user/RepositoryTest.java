// JDBC Fun
// License: MIT http://opensource.org/licenses/MIT
// Copyright: 2014 Christopher Davis <http://christopherdavis.me>

package org.chrisguitarguy.jdbcfun.user;

import java.sql.Connection;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.chrisguitarguy.jdbcfun.resource.MysqlJdbcResource;

public class RepositoryTest
{
    private UserRepository repo;
    private Connection conn;

    @Before
    public void setUp() throws Exception
    {
        String envVar = "DATABASE_URL";
        Assume.assumeTrue(System.getenv().containsKey(envVar));
        conn = new MysqlJdbcResource(envVar).create();
        repo = new JdbcUserRepository(conn);
        conn.createStatement().executeUpdate("DELETE FROM users WHERE 1");
    }

    @After
    public void tearDown() throws Exception
    {
        if (null != conn) {
            conn.close();
        }
    }

    @Test
    public void lifecyceOfAUserWorksAsExpected() throws Exception
    {
        User u1 = repo.save(new DefaultUser("chrisguitarguy", "admin"));
        System.out.println(u1.getIdentifier());

        User u2 = new DefaultUser();
        u2.setUsername("user2");
        User u3 = repo.save(u2);
        System.out.println(u3.getIdentifier());

        System.out.println("Fetching All");
        for (User u : repo.findAll()) {
            System.out.println(u.getUsername());
        }

        System.out.println("Deleting "+u3.getUsername());
        System.out.println(repo.delete(u3));

        System.out.println("Fetching One");
        User u4 = repo.findOne(u1.getIdentifier());
        System.out.println(u4.getUsername());
    }
}
