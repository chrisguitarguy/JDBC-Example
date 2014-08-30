// JDBC Fun
// License: MIT http://opensource.org/licenses/MIT
// Copyright: 2014 Christopher Davis <http://christopherdavis.me>

package org.chrisguitarguy.jdbcfun.user;

public interface UserFactory<T>
{
    /**
     * Create a new user from T.
     *
     * @param   source The source for the user information
     * @return  The newly created user
     */
    public User create(T source) throws UserException;
}
