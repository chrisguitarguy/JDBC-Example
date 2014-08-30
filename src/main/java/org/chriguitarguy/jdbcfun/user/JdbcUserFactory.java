// JDBC Fun
// License: MIT http://opensource.org/licenses/MIT
// Copyright: 2014 Christopher Davis <http://christopherdavis.me>

package org.chrisguitarguy.jdbcfun.user;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUserFactory implements UserFactory<ResultSet>
{
    public User create(ResultSet source) throws UserException
    {
        Integer id;
        String username;
        String role;

        try {
            id = source.getInt("id");
            username = source.getString("username");
            role = source.getString("role");
        } catch (SQLException e) {
            throw new UserException(e);
        }

        User u = new DefaultUser(id);
        u.setUsername(username);
        u.setRole(role);

        return u;
    }
}
