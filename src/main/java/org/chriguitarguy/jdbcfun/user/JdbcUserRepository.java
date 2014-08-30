// JDBC Fun
// License: MIT http://opensource.org/licenses/MIT
// Copyright: 2014 Christopher Davis <http://christopherdavis.me>

package org.chrisguitarguy.jdbcfun.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


public class JdbcUserRepository implements UserRepository
{
    final private static String DEFAULT_TABLE = "users";

    private Connection conn;
    private UserFactory<ResultSet> factory;
    private String table;

    public JdbcUserRepository(Connection conn, UserFactory<ResultSet> factory, String table)
    {
        this.conn = conn;
        this.factory = factory;
        this.table = table;
    }

    public JdbcUserRepository(Connection conn, UserFactory<ResultSet> factory)
    {
        this(conn, factory, DEFAULT_TABLE);
    }

    public JdbcUserRepository(Connection conn)
    {
        this(conn, new JdbcUserFactory(), DEFAULT_TABLE);
    }

    public User save(User user) throws UserException
    {
        try {
            if (user.hasIdentifier()) {
                return update(user);
            } else {
                return insert(user);
            }
        } catch (SQLException e) {
            throw new UserException(e);
        }
    }

    public boolean delete(User user) throws UserException
    {
        if (!user.hasIdentifier()) {
            throw new UserException("Cannot delete user without identifier");
        }

        try {
            return doDelete(user) > 0;
        } catch (SQLException e) {
            throw new UserException(e);
        }
    }

    public User findOne(Integer id) throws UserException
    {
        try {
            return doFindOne(id);
        } catch (SQLException e) {
            throw new UserException(e);
        }
    }

    public Iterable<User> findAll() throws UserException
    {
        try {
            return doFindAll();
        } catch (SQLException e) {
            throw new UserException(e);
        }
    }

    private User update(User user) throws SQLException
    {
        PreparedStatement stm = getConnection().prepareStatement(
            "UPDATE "+getTable()+" SET username = ?, role = ? WHERE id = ?"
        );

        stm.setString(1, user.getUsername());
        stm.setString(2, user.getRole());
        stm.setInt(3, user.getIdentifier());
        stm.executeUpdate();

        return user;
    }

    private User insert(User user) throws SQLException, UserException
    {
        PreparedStatement stm = getConnection().prepareStatement(
            "INSERT INTO "+getTable()+" (username, role) VALUES (?, ?)",
            Statement.RETURN_GENERATED_KEYS
        );
        stm.setString(1, user.getUsername());
        stm.setString(2, user.getRole());
        stm.executeUpdate();

        Integer id = null;
        ResultSet rs = stm.getGeneratedKeys();
        if (rs.next()) {
            id = rs.getInt(1);
        }

        if (null == id) {
            throw new UserException("Count not fetch new primary key after insert");
        }

        User newUser = new DefaultUser(id);
        newUser.setUsername(user.getUsername());
        newUser.setRole(user.getRole());

        return newUser;
    }

    private int doDelete(User user) throws SQLException
    {
        PreparedStatement stm = getConnection().prepareStatement(
            "DELETE FROM "+getTable()+" WHERE id = ?"
        );
        stm.setInt(1, user.getIdentifier());
        return stm.executeUpdate();
    }

    private User doFindOne(Integer id) throws SQLException, UserException
    {
        PreparedStatement stm = getConnection().prepareStatement(
            "SELECT id, username, role FROM "+getTable()+" WHERE id = ? LIMIT 1"
        );
        stm.setInt(1, id);
        ResultSet rs = stm.executeQuery();

        if (!rs.next()) {
            rs.close();
            return null;
        }

        try {
            return createUser(rs);
        } finally {
            rs.close();
        }
    }

    private Iterable<User> doFindAll() throws SQLException, UserException
    {
        ResultSet rs = getConnection().createStatement().executeQuery(
            "SELECT id, username, role FROM "+getTable()
        );

        try {
            return createUsers(rs);
        } finally {
            rs.close();
        }
    }

    private Connection getConnection()
    {
        return conn;
    }

    private String getTable()
    {
        return table;
    }

    private Iterable<User> createUsers(ResultSet rs) throws UserException, SQLException
    {
        ArrayList<User> users = new ArrayList<User>();
        while (rs.next()) {
            users.add(createUser(rs));
        }

        return users;
    }

    private User createUser(ResultSet rs) throws UserException
    {
        return factory.create(rs);
    }
}
