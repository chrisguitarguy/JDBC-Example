// JDBC Fun
// License: MIT http://opensource.org/licenses/MIT
// Copyright: 2014 Christopher Davis <http://christopherdavis.me>

package org.chrisguitarguy.jdbcfun.user;

public interface User
{
    public Integer getIdentifier();

    public boolean hasIdentifier();

    public String getUsername();

    public void setUsername(String username);

    public String getRole();

    public void setRole(String role);
}
