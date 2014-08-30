// JDBC Fun
// License: MIT http://opensource.org/licenses/MIT
// Copyright: 2014 Christopher Davis <http://christopherdavis.me>

package org.chrisguitarguy.jdbcfun.user;

public class DefaultUser implements User
{
    private Integer id;
    private String username;
    private String role;

    public DefaultUser(Integer id)
    {
        this.id = id;
    }

    public DefaultUser()
    {
        this(null);
    }

    public boolean hasIdentifier()
    {
        return null != id;
    }

    public Integer getIdentifier()
    {
        return id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }
}
