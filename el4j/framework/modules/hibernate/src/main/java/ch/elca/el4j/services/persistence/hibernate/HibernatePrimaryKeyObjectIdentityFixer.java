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
package ch.elca.el4j.services.persistence.hibernate;

import ch.elca.el4j.services.persistence.generic.dto.PrimaryKeyObject;

/**
 *  An identity fixer for objects loaded by hibernate and implementing the <code>PrimaryKeyObject</code> interface.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @see HibernateProxyAwareIdentityFixer
 * 
 * @author Andreas Rueedlinger (ARR)
 */
public class HibernatePrimaryKeyObjectIdentityFixer
		extends HibernateProxyAwareIdentityFixer {
	
	/** {@inheritDoc} */
	@Override
	protected Object id(Object o) {
		if (o instanceof PrimaryKeyObject) {
			
			PrimaryKeyObject obj = (PrimaryKeyObject) o;
			
			if (obj.isKeyNew()) {
				return null;
			}
		}
		return super.id(o);
	}
}
