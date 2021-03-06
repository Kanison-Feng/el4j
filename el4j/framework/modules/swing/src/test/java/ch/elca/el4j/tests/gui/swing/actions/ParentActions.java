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
package ch.elca.el4j.tests.gui.swing.actions;

import org.jdesktop.application.Action;

import ch.elca.el4j.tests.gui.swing.ValueHolder;

/**
 * Parent class containing actions.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @author Stefan Wismer (SWI)
 */
public class ParentActions extends GrandparentActions {
	/**
	 * @param prefix         the prefix to write into variableHolder
	 * @param stateHolder    the variable to store which action got executed
	 */
	public ParentActions(String prefix, ValueHolder<String> stateHolder) {
		super(prefix, stateHolder);
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void doB() {
		m_stateHolder.setValue(m_prefix + "Parent.doB");
	}
	
	/**
	 * Perform action C.
	 */
	@Action
	public void doC() {
		m_stateHolder.setValue(m_prefix + "Parent.doC");
	}
}
