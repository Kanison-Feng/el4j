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

package ch.elca.el4j.core.io.support;

/**
 * This interface is used to transform the order of modules given as
 * dependencies into a list of modules that respects the partial order.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @author Andreas Bur (ABU)
 */
public interface ModuleSorter {

	/**
	 * Sorts a list of unordered modules using their dependency information into
	 * a list that respects the partial order.
	 *
	 * @param modules
	 *      The modules to sort.
	 *
	 * @return Returns a list of modules that preserves the partial order
	 *      defined by dependency information.
	 */
	public Module[] sortModules(Module[] modules);
}
