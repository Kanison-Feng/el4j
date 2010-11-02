/*
 * EL4J, the Extension Library for the J2EE, adds incremental enhancements to
 * the spring framework, http://el4j.sf.net
 * Copyright (C) 2010 by ELCA Informatique SA, Av. de la Harpe 22-24,
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
package ch.elca.el4j.tests.aspects.dao.impl;

import ch.elca.el4j.services.persistence.generic.dao.AutocollectedGenericDao;
import ch.elca.el4j.services.persistence.hibernate.dao.GenericHibernateDao;
import ch.elca.el4j.tests.aspects.dao.AspectsTestsDao;
import ch.elca.el4j.tests.aspects.model.AspectsTests;

/**
 * Dao hibernate impl for domain object {@link AspectsTests}.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @author Martin Zeltner (MZE)
 */
@AutocollectedGenericDao("aspectsTestsDao")
public class AspectsTestsDaoImpl extends GenericHibernateDao<AspectsTests, Integer>
	implements AspectsTestsDao { }
