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
package com.silvermindsoftware.hitch.binding.components;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;

import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.swingbinding.JComboBoxBinding;
import org.jdesktop.swingbinding.SwingBindings;

import com.silvermindsoftware.hitch.binding.AbstractBindingCreator;

import ch.elca.el4j.services.gui.swing.GUIApplication;
import ch.elca.el4j.util.config.GenericConfig;

/**
 * This class creates bindings for ComboBoxes.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @author Stefan Wismer (SWI)
 */
public class ComboBoxBinding extends AbstractBindingCreator<JComboBox> {
	
	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	public AutoBinding createBinding(Object object, JComboBox formComponent) {
		List list = (List) object;
		JComboBoxBinding cb = SwingBindings.createJComboBoxBinding(
			m_updateStrategy, list, formComponent);
		return cb;
	}
	
	/** {@inheritDoc} */
	public void addValidation(JComboBox formComponent) {
		GenericConfig config = GUIApplication.getInstance().getConfig();
		formComponent.setRenderer((ListCellRenderer) config.get("cellRenderer"));
	}
}
