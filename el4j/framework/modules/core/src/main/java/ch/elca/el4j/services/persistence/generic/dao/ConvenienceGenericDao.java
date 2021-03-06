/*
 * EL4J, the Extension Library for the J2EE, adds incremental enhancements to
 * the spring framework, http://el4j.sf.net
 * Copyright (C) 2006 by ELCA Informatique SA, Av. de la Harpe 22-24,
 * 1000 Lausanne, Switzerland, http://www.elca.ch
 *
 * EL4J is published under the GNU Lesser General Public License (LGPL)
 * Version 2.1. See http://www.gnu.org/licenses/
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * For alternative licensing, please contact info@elca.ch
 */
package ch.elca.el4j.services.persistence.generic.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.OptimisticLockingFailureException;

/**
 * Extends the GenericDao with a few convenience methods.
 * As EL4J only supports Hibernate as persistence framework it's more convenient to use
 * ConvenienceGenericHibernateDao directly.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @param <T>
 *            The generic type of the domain class the DAO is responsible for
 * @param <ID>
 *            The generic type of the domain class' identifier
 *
 * @author Philipp Oser (POS)
 * @author Alex Mathey (AMA)
 * @author Martin Zeltner (MZE)
 */
public interface ConvenienceGenericDao<T, ID extends Serializable>
	extends GenericDao<T> {
	
	/**
	 * Retrieves a domain object by identifier. This method gets the object from
	 * the hibernate cache. It might be that you don't get the actual version
	 * that is in the database. If you want the actual version do a refresh()
	 * after this method call.
	 *
	 * @param id
	 *            The id of the domain object to find
	 * @return Returns the found domain object.
	 * @throws DataRetrievalFailureException
	 *             If no domain object could be found with given id.
	 * @throws DataAccessException
	 *             If general data access problem occurred
	 */
	T findById(ID id) throws DataRetrievalFailureException, DataAccessException;
	
	/**
	 * Retrieves a domain object by identifier lazily. It uses Hibernate lazy loading, namely
	 * the method load() instead of get(). For detailed information about fetching strategies see
	 * http://www.hibernate.org/hib_docs/v3/reference/en/html/performance.html.
	 * 
	 * @see findById
	 */
	T findByIdLazy(ID id) throws DataRetrievalFailureException, DataAccessException;
	
	/**
	 * Deletes the domain object with the given id, disregarding any
	 * concurrent modifications that may have occurred.
	 *
	 * @param id
	 *             The id of the domain object to delete
	 * @throws OptimisticLockingFailureException
	 *             If domain object has been deleted in the meantime
	 * @throws DataAccessException
	 *             If general data access problem occurred
	 * @deprecated Renamed to deleteById as of el4j 1.6
	 */
	@Deprecated
	void delete(ID id)
		throws OptimisticLockingFailureException, DataAccessException;
	
	/**
	 * Deletes the domain object with the given id, disregarding any
	 * concurrent modifications that may have occurred.
	 *
	 * @param id
	 *             The id of the domain object to delete
	 * @throws OptimisticLockingFailureException
	 *             If domain object has been deleted in the meantime
	 * @throws DataAccessException
	 *             If general data access problem occurred
	 */
	void deleteById(ID id)
		throws OptimisticLockingFailureException, DataAccessException;
	
	/**
	 * Retrieves all the domain objects of type T.
	 *
	 * @return The list containing all the domain objects of type T; if no such
	 *         domain objects exist, an empty list will be returned
	 * @throws DataAccessException
	 *             If general data access problem occurred
	 */
	List<T> getAll() throws DataAccessException;
	
	/**
	 * Deletes the given domain object.
	 *
	 * @param entity
	 *             The domain object to delete
	 * @throws OptimisticLockingFailureException
	 *             If domain object has been modified/deleted in the meantime
	 * @throws DataAccessException
	 *             If general data access problem occurred
	 */
	void delete(T entity)
		throws OptimisticLockingFailureException, DataAccessException;
	
	/**
	 * Deletes all available <code>T</code>.
	 *
	 * @throws OptimisticLockingFailureException
	 *             If domain object has been modified/deleted in the meantime
	 * @throws DataAccessException
	 *             If general data access problem occurred
	 */
	public void deleteAll()
		throws OptimisticLockingFailureException, DataAccessException;
	
	/**
	 * Sometimes, the way Hibernate handles all the actions in a session is
	 * very unbelievable. For example, we call
	 * <code>
	 *  delete(project);
	 *  project.setId(null) <= to insert new one
	 *  insert(project);
	 * </code>
	 *
	 * It could cause java.sql.BatchUpdateException:
	 * ORA-00001: unique constraint BECAUSE Hibernate doesn't flush
	 * the previous action first.
	 *
	 * This method provides a way to flush manually some action.
	 * Note that this method is only used in an extremely rare case.
	 */
	void flush();
}