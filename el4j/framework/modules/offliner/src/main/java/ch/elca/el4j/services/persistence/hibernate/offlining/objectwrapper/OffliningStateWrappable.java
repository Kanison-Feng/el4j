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
package ch.elca.el4j.services.persistence.hibernate.offlining.objectwrapper;

import ch.elca.el4j.services.persistence.hibernate.offlining.util.OffliningState;
import ch.elca.el4j.util.objectwrapper.Wrappable;


/**
 * Wrappable of an object being in one of several states for synchronization.
 *
 * <script type="text/javascript">printFileStatus
 *   ("$URL$",
 *    "$Revision$",
 *    "$Date$",
 *    "$Author$"
 * );</script>
 *
 * @author David Bernhard (DBD)
 */
public interface OffliningStateWrappable extends Wrappable {
	
	/**
	 * Get the state. <i>This requires both the object and the mapping entry so it is here.</i>
	 * @return The  offlining state.
	 */
	OffliningState getState();
	
	/**
	 * Set the state. This is available to mark objects as processed or conflicted.
	 * @param state The state to set.
	 */
	void setState(OffliningState state);
}
