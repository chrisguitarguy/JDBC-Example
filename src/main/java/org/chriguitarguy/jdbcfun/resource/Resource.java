// JDBC Fun
// License: MIT http://opensource.org/licenses/MIT
// Copyright: 2014 Christopher Davis <http://christopherdavis.me>

package org.chrisguitarguy.jdbcfun.resource;

/**
 * A resource attached to the application via a url in an environment variable.
 *
 * http://12factor.net/backing-services
 *
 * @since   0.1
 */
public interface Resource<T>
{
    /**
     * Create a new resource and return it.
     *
     * @since   0.1
     * @throws  ResourceError if the resource cannot be created for some reason.
     * @return  The resource
     */
    public T create() throws ResourceError;
}
