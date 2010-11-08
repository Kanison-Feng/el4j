/*
 * EL4J, the Extension Library for the J2EE, adds incremental enhancements to
 * the spring framework, http://el4j.sf.net
 * Copyright (C) 2005 by ELCA Informatique SA, Av. de la Harpe 22-24,
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
package ch.elca.el4j.services.persistence.jpa.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.OptimisticLockingFailureException;

import ch.elca.el4j.services.persistence.generic.dao.ConvenienceGenericDao;
import ch.elca.el4j.services.persistence.hibernate.dao.extent.DataExtent;
import ch.elca.el4j.services.search.QueryObject;

/**
 * This interface extends {@link ConvenienceGenericDao} with query methods using
 * {@link CriteriaQuery}s.
 *
 * @svnLink $Revision: 4028 $;$Date: 2009-12-11 09:38:50 +0100 (Fr, 11 Dez 2009) $;$Author: swisswheel $;$URL: https://el4j.svn.sourceforge.net/svnroot/el4j/trunk/el4j/framework/modules/hibernate/src/main/java/ch/elca/el4j/services/persistence/hibernate/dao/ConvenienceGenericHibernateDao.java $
 *
 * @param <T>     the domain object type
 * @param <ID>    the id of the domain object to find
 *
 * @author Simon Stelling (SST)
 */
public interface ConvenienceGenericJpaDao<T, ID extends Serializable>
	extends ConvenienceGenericDao<T, ID> {
	
	/**
	 * Convenience method: Executes saveOrUpdate() and flush() on that entity.
	 * 
	 * @param entity    The domain object to save or update
	 * @return          The saved or updated object
	 * @throws DataAccessException
	 * @throws DataIntegrityViolationException
	 * @throws OptimisticLockingFailureException
	 */
	@Deprecated
	public T saveOrUpdateAndFlush(T entity) throws DataAccessException,
		DataIntegrityViolationException, OptimisticLockingFailureException;
	
	/**
	 * merge the given entity.
	 * @param entity the entity to merge.
	 * @return the merged entity
	 * @throws DataAccessException
	 * @throws DataIntegrityViolationException
	 * @throws OptimisticLockingFailureException
	 */
	public T merge(T entity) throws DataAccessException,
		DataIntegrityViolationException, OptimisticLockingFailureException;
	
	/**
	 * persist the given entity.
	 * @param entity the entity to persist.
	 * @return the persisted entity
	 * @throws DataAccessException
	 * @throws DataIntegrityViolationException
	 * @throws OptimisticLockingFailureException
	 */
	public T persist(T entity) throws DataAccessException,
		DataIntegrityViolationException, OptimisticLockingFailureException;
	
	/** 
	 * Deletes all available <code>T</code> using a JPQL query.
	 * 
	 * This has the benefit of a significant performance improvement
	 * in comparison to {@link deleteAll}. The tradeoff is that this
	 * method does no cascade deletion. 
	 *
	 * @throws OptimisticLockingFailureException
	 *             If domain object has been modified/deleted in the meantime
	 * @throws DataAccessException
	 *             If general data access problem occurred
	 * */
//	public void deleteAllNoCascade()
//		throws OptimisticLockingFailureException, DataAccessException;
	
	/**
	 * Deletes the given domain objects using a JPQL query. 
	 * 
	 * This has the benefit of a significant performance improvement
	 * in comparison to {@link delete}. The tradeoff is that this
	 * method does no cascade deletion. 
	 * 
	 * @param entities The domain objects to delete.
	 * @throws OptimisticLockingFailureException
	 *             If domain object has been modified/deleted in the meantime
	 * @throws DataAccessException
	 *             If general data access problem occurred
	 */
//	public void deleteNoCascade(Collection<T> entities) throws DataAccessException,
//		DataIntegrityViolationException, OptimisticLockingFailureException;

	/**
	 * Retrieves all the domain objects matching the JPA criteria.
	 * 
	 * @param criteria             the criteria that the result has to fulfill
	 * @return                     all object that fulfill the criteria
	 * @throws DataAccessException
	 *
	 * @see ConvenienceJpaTemplate#findByCriteria(DetachedCriteria)
	 */
	public List<T> findByCriteria(CriteriaQuery<T> criteria)
		throws DataAccessException;
	
	
	/**
	 * Retrieves all the domain objects matching the JPA criteria.
	 * Loads at least the given extent.
	 * 
	 * @param criteria             the criteria that the result has to fulfill
	 * @param extent               the extent in which objects get loaded.
	 * @return                     all object that fulfill the criteria
	 * @throws DataAccessException
	 *
	 * @see ConvenienceJpaTemplate#findByCriteria(DetachedCriteria)
	 */
	public List<T> findByCriteria(CriteriaQuery<T> criteria,
		DataExtent extent) throws DataAccessException;
	
	/**
	 * Retrieves a range of domain objects matching the JPA criteria.
	 * 
	 * @param criteria             the criteria that the result has to fulfill
	 * @param firstResult          the index of the first result to return
	 * @param maxResults           the maximum number of results to return
	 * @return                     the specified subset of object that fulfill
	 *                             the criteria
	 * @throws DataAccessException
	 *
	 * @see ConvenienceJpaTemplate#findByCriteria(DetachedCriteria, int, int)
	 */
	public List<T> findByCriteria(CriteriaQuery<T> criteria,
		int firstResult, int maxResults) throws DataAccessException;
	
	/**
	 * Retrieves a range of domain objects matching the JPA criteria.
	 * Loads at least the given extent.
	 * 
	 * @param criteria             the criteria that the result has to fulfill
	 * @param firstResult          the index of the first result to return
	 * @param maxResults           the maximum number of results to return
	 * @param extent               the extent in which objects get loaded.
	 * @return                     the specified subset of object that fulfill
	 *                             the criteria
	 * @throws DataAccessException
	 *
	 * @see ConvenienceJpaTemplate#findByCriteria(DetachedCriteria, int, int)
	 */
	public List<T> findByCriteria(CriteriaQuery<T> criteria, int firstResult,
		int maxResults, DataExtent extent) throws DataAccessException;
	
	/**
	 * Retrieves the number of domain objects matching the JPA criteria.
	 * 
	 * @param criteria             the criteria that the result has to fulfill
	 * @return                     the number of objects that fulfill
	 *                             the criteria
	 * @throws DataAccessException
	 *
	 * @see ConvenienceJpaTemplate#findCountByCriteria(DetachedCriteria)
	 */
	public int findCountByCriteria(CriteriaQuery<T> criteria)
		throws DataAccessException;
	
	/**
	 * Retrieves a domain object by identifier. This method gets the object from
	 * the hibernate cache. It might be that you don't get the actual version
	 * that is in the database. If you want the actual version do a refresh()
	 * after this method call.
	 * Loads at least the given extent.
	 *
	 * @param id        The id of the domain object to find
	 * @param extent    the extent in which objects get loaded.
	 * @return Returns the found domain object.
	 * @throws DataRetrievalFailureException
	 *             If no domain object could be found with given id.
	 * @throws DataAccessException
	 *             If general data access problem occurred
	 */
	public T findById(ID id, DataExtent extent) 
		throws DataRetrievalFailureException, DataAccessException;
	
	/**
	 * Retrieves all the domain objects of type T.
	 * Loads at least the given extent.
	 *
	 * @param extent    the extent in which objects get loaded.
	 * 
	 * @return The list containing all the domain objects of type T; if no such
	 *         domain objects exist, an empty list will be returned
	 * @throws DataAccessException
	 *             If general data access problem occurred
	 */
	List<T> getAll(DataExtent extent) throws DataAccessException;
	
	/**
	 * Executes a query based on a given query object.
	 *  This method may also support paging (see javadoc
	 *   of implementing class).
	 * Loads at least the given extent.
	 *
	 * @param q         The search query object
	 * @param extent    the extent in which objects get loaded.
	 * @throws  DataAccessException
	 *             If general data access problem occurred
	 * @return A list containing 0 or more domain objects
	 */
	List<T> findByQuery(QueryObject q, DataExtent extent) throws DataAccessException;

	/**
	 * Re-reads the state of the given domain object from the underlying
	 * store.
	 * Loads at least the given extent.
	 *
	 * @param entity
	 *            The domain object to re-read the state of
	 * @param extent
	 *            the extent in which objects get loaded.
	 * @throws DataAccessException
	 *             If general data access problem occurred
	 * @throws DataRetrievalFailureException
	 *             If domain object could not be re-read
	 * @return The refreshed entity
	 */
	T refresh(T entity, DataExtent extent) throws DataAccessException,
		DataRetrievalFailureException;
	
	/**
	 * Re-reads the state of the given domain object from the undermost
	 * store (eg. the database).
	 * Loads at least the given extent.
	 *
	 * @param entity
	 *            The domain object to re-read the state of
	 * @param extent
	 *            the extent in which objects get loaded.
	 * @throws DataAccessException
	 *             If general data access problem occurred
	 * @throws DataRetrievalFailureException
	 *             If domain object could not be re-read
	 * @return The refreshed entity
	 */
	T reload(T entity, DataExtent extent) throws DataAccessException,
		DataRetrievalFailureException;
	
	/**
	 * @return    the default {@link Order} to order the results
	 */
	public Order[] getDefaultOrder();

	/**
	 * Set default order of results returned by getAll and findByQuery (not findByCriteria!).
	 * If defaultOrder is <code>null</code> then default ordering is deactivated.
	 * 
	 * @param defaultOrder    the default {@link Order} to order the results
	 */
	public void setDefaultOrder(Order... defaultOrder);
	
	/**
	 * Create a {@link DetachedCriteria} that contains default ordering and distinct constraints.
	 * 
	 * @return    a {@link DetachedCriteria}
	 */
	public CriteriaQuery<T> getOrderedCriteria();
	
}
