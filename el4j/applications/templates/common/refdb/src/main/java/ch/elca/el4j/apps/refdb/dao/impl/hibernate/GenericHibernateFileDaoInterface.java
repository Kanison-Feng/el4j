/*
 * EL4J, the Extension Library for the J2EE, adds incremental enhancements to
 * the spring framework, http://el4j.sf.net
 * Copyright (C) 2008 by ELCA Informatique SA, Av. de la Harpe 22-24,
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
package ch.elca.el4j.apps.refdb.dao.impl.hibernate;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;

import ch.elca.el4j.apps.refdb.dao.FileDao;
import ch.elca.el4j.apps.refdb.dom.File;
import ch.elca.el4j.services.persistence.hibernate.dao.ConvenienceGenericHibernateDao;
import ch.elca.el4j.services.persistence.hibernate.dao.extent.DataExtent;

/**
 * The HibernateDao Interface to be used to both access 
 * {@link FileDao} and {@link ConvenienceGenericHibernateDao} methods. 
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @author Andreas Rueedlinger (ARR)
 */
public interface GenericHibernateFileDaoInterface 
	extends ConvenienceGenericHibernateDao<File, Integer>, FileDao {

	/**
	 * Get all the files or file descriptors with the same name.
	 * Loads at least the given extent.
	 *
	 * @param name
	 *            Is the name of a file or file descriptor.
	 * @param extent
	 * 			  the extent in which objects get loaded.
	 * @return Returns the desired file or file descriptor.
	 * @throws DataAccessException
	 *             If general data access problem occurred.
	 * @throws DataRetrievalFailureException
	 *             If file or file descriptor could not be retrieved.
	 */
	public List<File> getByName(String name, DataExtent extent)
		throws DataAccessException, DataRetrievalFailureException;
}
