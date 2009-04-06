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

package ch.elca.el4j.services.persistence.generic.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ch.elca.el4j.util.codingsupport.Reject;


/**
 * This class is an abstract dto which uses an integer as key value and also
 * an integer for optimistic locking version controlling.
 *
 * <script type="text/javascript">printFileStatus
 *   ("$URL$",
 *    "$Revision$",
 *    "$Date$",
 *    "$Author$"
 * );</script>
 *
 * @author Martin Zeltner (MZE)
 */
@MappedSuperclass
public abstract class AbstractIntKeyIntOptimisticLockingDto
	extends AbstractIntOptimisticLockingDto
	implements PrimaryKeyOptimisticLockingObject {
	
	/**
	 * The logger.
	 */
	private static Log s_logger = LogFactory.getLog(AbstractIntKeyIntOptimisticLockingDto.class);

	/**
	 * Primary key.
	 */
	private int m_key;
	
	/**
	 * Flag to indicate if the key is new.
	 */
	private boolean m_keyNew = true;
	
	/**
	 * Has the hashCode value been leaked while being in transient state?
	 */
	private boolean m_transientHashCodeLeaked = false;

	/**
	 * @return Returns the key.
	 */
	@Id @GeneratedValue(strategy = GenerationType.AUTO,
		generator = "keyid_generator")
	@Column(name = "KEYID")
	public int getKey() {
		return m_key;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transient
	public Object getKeyAsObject() {
		return isKeyNew() ? null : Integer.valueOf(getKey());
	}

	/**
	 * @param key The key to set.
	 */
	public void setKey(int key) {
		m_key = key;
		m_keyNew = false;
	}
	
	/**
	 * Convenience method to set the key. The given parameter must be an
	 * <code>Number</code> otherwise it will be rejected.
	 *
	 * @param keyObject Is the key as object to set.
	 */
	public void setKey(Object keyObject) {
		Reject.ifNull(keyObject);
		Reject.ifNotAssignableTo(keyObject, Number.class);
		int key = ((Number) keyObject).intValue();
		setKey(key);
	}
	
	/**
	 * @return Returns <code>true</code> if the key has never be set by using
	 *         method <code>setKey</code>.
	 */
	@Transient
	public boolean isKeyNew() {
		return m_keyNew;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		if (m_keyNew) {
			m_transientHashCodeLeaked = true;
			return super.hashCode();
		} else {
			if (m_transientHashCodeLeaked) {
				s_logger.error("hashCode() has be called once on transient state and once on persistent state  "
					+ "of object '" + this.toString() + "' (" + getClass().toString() + "). "
					+ "This can happen if you insert a transient object into a collection and persist them afterwards. "
					+ "Save the objects before you insert them into a collection!");
			}
			return m_key;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		/* The following is a solution that works for hibernate lazy loading proxies.
		if (getClass() != HibernateProxyHelper.getClassWithoutInitializingProxy(obj)) {
			return false;
		}*/
		if (obj instanceof AbstractIntKeyIntOptimisticLockingDto) {
			final AbstractIntKeyIntOptimisticLockingDto other = (AbstractIntKeyIntOptimisticLockingDto) obj;
			if (!isKeyNew() && !other.isKeyNew()) {
				return getKey() == other.getKey() && getClass() == other.getClass();
			}
		}
		return false;
	}

	/**
	 * Reset the key state to "new". This is required in some circumstances (offlining), otherwise
	 * it should NOT be used.
	 */
	public void resetNew() {
		m_keyNew = true;
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * Does nothing, because primary key will be generated by database.
	 */
	public void useGeneratedKey() { }
}
