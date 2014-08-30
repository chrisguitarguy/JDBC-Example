// JDBC Fun
// License: MIT http://opensource.org/licenses/MIT
// Copyright: 2014 Christopher Davis <http://christopherdavis.me>

package org.chrisguitarguy.jdbcfun.user;

public class UserException extends Exception
{
    public UserException(String msg)
    {
        super(msg);
    }

    public UserException(Throwable e)
    {
        super(e);
    }
}
