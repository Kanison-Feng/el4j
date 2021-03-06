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

package ch.elca.el4j.services.persistence.ibatis.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ch.elca.el4j.services.persistence.generic.dao.ConvenienceGenericDao;
import ch.elca.el4j.services.persistence.generic.dao.annotations.ReturnsUnchangedParameter;
import ch.elca.el4j.services.persistence.generic.dto.PrimaryKeyOptimisticLockingObject;
import ch.elca.el4j.services.search.QueryObject;
import ch.elca.el4j.util.codingsupport.CollectionUtils;
import ch.elca.el4j.util.codingsupport.Reject;

/**
 *
 * This class is an iBatis-specific implementation of the ConvenienceGenericDao
 * interface.
 *
 * Note that when using this DAO, a special ibatis xml configuration file using
 * is necessary for every domain object.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @param <T>
 *            The domain class the DAO is responsible for
 * @param <ID>
 *            The type of the domain class' identifier
 *
 * @author Alex Mathey (AMA)
 */
public class GenericSqlMapDao<T extends PrimaryKeyOptimisticLockingObject,ID extends Serializable>
		extends ConvenienceSqlMapClientDaoSupport implements ConvenienceGenericDao<T, ID>, InitializingBean {

	@SuppressWarnings("unchecked")
	public GenericSqlMapDao() {
		try {
			this.m_persistentClass = (Class<T>) ((ParameterizedType)
					getClass().getGenericSuperclass()).
					getActualTypeArguments()[0];
		} catch (Exception e){
			// ignore issues (e.g. when the subclass is not a parametrized type)
			//  in that case, one needs to set the persistencClass otherwise.
		}
	}

	/**
	 * The domain class this DAO is responsible for.
	 */
	private Class<T> m_persistentClass;

	/**
	 * This call is no longer required
	 *
	 * @param c
	 *            Mandatory. The domain class this DAO is responsible for.
	 */
	public void setPersistentClass(Class<T> c) {
		Reject.ifNull(c);
		m_persistentClass = c;
	}

	/**
	 * @return Returns the domain class this DAO is responsible for.
	 */
	public Class<T> getPersistentClass() {
		assert m_persistentClass != null;
		return m_persistentClass;
	}

	/**
	 * {@inheritDoc}
	 *
	 * If OBJECTNAME denotes the name of the domain class this DAO is
	 * responsible for, the statement in the ibatis xml config file
	 * which is associated with this operation must be called
	 * getOBJECTNAMEByKey.
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public T findById(ID id)
		throws DataAccessException, DataRetrievalFailureException {
		return (T) getConvenienceSqlMapClientTemplate()
		.queryForObjectStrong("get" + getPersistentClassName() + "ByKey",
				id, getPersistentClassName());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public T findByIdLazy(ID id)
		throws DataAccessException, DataRetrievalFailureException {
		return (T) findById(id);
	}

	/**
	 * {@inheritDoc}
	 *
	 * If OBJECTNAME denotes the name of the domain class this DAO is
	 * responsible for, the statement in the ibatis xml config file
	 * which is associated with this operation must be called
	 * getAllOBJECTNAMEs.
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<T> getAll() throws DataAccessException {
		List<T> result = getConvenienceSqlMapClientTemplate().queryForList(
				"getAll" + getPersistentClassName() + "s", null);
		return CollectionUtils.asList(result);
	}

	/**
	 * {@inheritDoc}
	 *
	 * If OBJECTNAME denotes the name of the domain class this DAO is
	 * responsible for, the statement in the ibatis xml config file
	 * which is associated with this operation must be called
	 * searchOBJECTNAMEs.
	 *
	 * It is very complex to write the ibatis configuration for such
	 *  queries. We only implement it partially in EL4J.
	 *
	 *  In addition, the paging support needs to be added by hand.
	 *
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<T> findByQuery(QueryObject q) throws DataAccessException {
		Reject.ifNull(q);
		List<T> result = getConvenienceSqlMapClientTemplate().queryForList(
				"search" + getPersistentClassName() + "s", q.getCriteriaList());
		return CollectionUtils.asList(result);
	}

	/**
	 * {@inheritDoc}
	 *
	 * If OBJECTNAME denotes the name of the domain class this DAO is
	 * responsible for, the statements in the ibatis xml config file
	 * which are associated with this operation must be called
	 * insertOBJECTNAME and updateOBJECTNAME.
	 */
	@ReturnsUnchangedParameter
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public T saveOrUpdate(T entity) throws DataAccessException,
			DataIntegrityViolationException, OptimisticLockingFailureException {
		Reject.ifNull(entity);
		getConvenienceSqlMapClientTemplate().insertOrUpdate(entity,
				getPersistentClassName());
		return entity;
	}

	/**
	 * {@inheritDoc}
	 *
	 * If OBJECTNAME denotes the name of the domain class this DAO is
	 * responsible for, the statement in the ibatis xml config file
	 * which is associated with this operation must be called
	 * deleteOBJECTNAME.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(T entity) throws DataAccessException,
			DataIntegrityViolationException, OptimisticLockingFailureException {
		getConvenienceSqlMapClientTemplate().deleteStrong(
				"delete" + getPersistentClassName(), entity,
				getPersistentClassName());
	}

	/**
	 * {@inheritDoc}
	 *
	 * If OBJECTNAME denotes the name of the domain class this DAO is
	 * responsible for, the statement in the ibatis xml config file
	 * which is associated with this operation must be called
	 * refreshOBJECTNAME.
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public T refresh(T entity) throws DataAccessException, DataRetrievalFailureException {
		return (T) getConvenienceSqlMapClientTemplate()
		.queryForObjectStrong("refresh" + getPersistentClassName(),
				entity, getPersistentClassName());
	}

	/**
	 * {@inheritDoc}
	 *
	 * If OBJECTNAME denotes the name of the domain class this DAO is
	 * responsible for, the statement in the ibatis xml config file
	 * which is associated with this operation must be called
	 * deleteOBJECTNAMEById.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(ID id) throws OptimisticLockingFailureException, DataAccessException {
		getConvenienceSqlMapClientTemplate().deleteStrong(
				"delete" + getPersistentClassName() + "ById", id,
				getPersistentClassName());
	}

	/** {@inheritDoc} */
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Collection<T> entities)
		throws OptimisticLockingFailureException, DataAccessException {
		for (T entity : entities) {
			getConvenienceSqlMapClientTemplate().deleteStrong("delete"
					+ getPersistentClassName(), entity.getKeyAsObject(),
					getPersistentClassName());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteAll()
		throws OptimisticLockingFailureException, DataAccessException {
		List<T> list = getAll();
		if (list.size() > 0) {
			delete(list);
		}
	}
	
	/** {@inheritDoc} */
	@Transactional(propagation = Propagation.REQUIRED)
	public void flush() {
		getConvenienceSqlMapClientTemplate().getSqlMapClient().flushDataCache();
	}

	/**
	 * Returns the simple name of the persistent class this DAO is responsible
	 * for.
	 *
	 * @return The simple name of the persistent class this DAO is responsible
	 *         for.
	 */
	protected String getPersistentClassName() {
		return getPersistentClass().getSimpleName();
	}

	/**
	 * {@inheritDoc}
	 */
	public int findCountByQuery(QueryObject query) throws DataAccessException {
		throw new NotImplementedException("findCountByQuery is not implemented "
				+" by default");
	}
}