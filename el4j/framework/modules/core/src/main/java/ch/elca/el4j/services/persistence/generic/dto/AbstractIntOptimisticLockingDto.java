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

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * This is an abstract class for optimistic locking. The used version type is an
 * integer.
 * In java code the update count must be checked to know if the version number
 * on database has been increased. If yes, the version number of dto must be
 * increased too.<br>
 * <br>
 * <b>Java code:</b>
 * <pre><code>
 * ...
 *     MyTableDto myDto = ...
 *     SqlMapClientTemplate smc = ...
 *     int count = smc.update("updateMyTable", myDto);
 *     if (count == 1) {
 *         myDto.increaseOptimisticLockingVersion();
 *     }
 * ...
 * </code></pre>
 *
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @author Martin Zeltner (MZE)
 */
@MappedSuperclass
public abstract class AbstractIntOptimisticLockingDto
	implements Serializable, OptimisticLockingObject {
	/**
	 * Is the optimistic locking version number. Initial optimistic locking
	 * version number is zero.
	 */
	private int m_optimisticLockingVersion = 0;

	/**
	 * @return Returns the optimistic locking version.
	 */
	@Version
	@Column(name = "OPTIMISTICLOCKINGVERSION")
	public int getOptimisticLockingVersion() {
		return m_optimisticLockingVersion;
	}

	/**
	 * @param optimisticLockingVersion
	 *            The optimistic locking version to set.
	 */
	public void setOptimisticLockingVersion(
		int optimisticLockingVersion) {
		m_optimisticLockingVersion = optimisticLockingVersion;
	}
	
	/**
	 * Method to increase the int optimistic locking version number.
	 */
	public void increaseOptimisticLockingVersion() {
		m_optimisticLockingVersion++;
	}
}
