// JDBC Fun
// License: MIT http://opensource.org/licenses/MIT
// Copyright: 2014 Christopher Davis <http://christopherdavis.me>

package org.chrisguitarguy.jdbcfun.resource;

/**
 * Thrown when a resource cannot be created.
 *
 * @since   0.1
 */
public class ResourceError extends Error
{
    public ResourceError(String msg)
    {
        super(msg);
    }

    public ResourceError(Throwable e)
    {
        super(e);
    }
}
