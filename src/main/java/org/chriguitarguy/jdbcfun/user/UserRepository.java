// JDBC Fun
// License: MIT http://opensource.org/licenses/MIT
// Copyright: 2014 Christopher Davis <http://christopherdavis.me>

package org.chrisguitarguy.jdbcfun.user;

// this should probably be a generic interface
interface UserRepository
{
    /**
     * Save a user.
     *
     * If the user has an identifier, it will be updated.
     *
     * @param   user The user to save
     * @throws  UserException if something goes wrong with the database insert/update
     * @return  A new user object if the user was inserted that has its ID set
     *          otherwise the same user that was passed in
     */
    public User save(User u) throws UserException;

    /**
     * Remove a user from the database.
     *
     * @param   user The user to remove
     * @throws  UserException if something goes wrong with the delete
     * @return  True if the user was deleted
     */
    public boolean delete(User u) throws UserException;

    /**
     * Get a single user by its primary key.
     *
     * @param   id The users primary key
     * @throws  UserException if something goes wrong with the fetch
     * @return  The user object if found or null
     */
    public User findOne(Integer id) throws UserException;

    /**
     * Get all the users.
     *
     * @throws  UserException if something goes wrong with the fetch
     * @return  An iterable of all the users
     */
    public Iterable<User> findAll() throws UserException;
}
