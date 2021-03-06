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
package ch.elca.el4j.services.search.criterias;

/**
 * A Criteria that negates the Criteria it wraps.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @author Philipp Oser (POS)
 */
public class NotCriteria implements Criteria {

	protected Criteria m_criteria;
	
	public NotCriteria (Criteria c) {
		m_criteria = c;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getType() {
		return "";
	}

	/**
	 * {@inheritDoc}
	 */
	public String getSqlWhereCondition() {
		return "( NOT "+ m_criteria.getSqlWhereCondition() + " ) ";
	}

	public Criteria getCriteria() {
		return m_criteria;
	}
	
}
