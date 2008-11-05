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
package ch.elca.el4j.demos.gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.application.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.silvermindsoftware.hitch.Binder;
import com.silvermindsoftware.hitch.BinderManager;
import com.silvermindsoftware.hitch.annotations.Form;
import com.silvermindsoftware.hitch.annotations.ModelObject;

import ch.elca.el4j.demos.model.DefaultPerson;
import ch.elca.el4j.demos.model.Person;
import ch.elca.el4j.gui.swing.GUIApplication;
import ch.elca.el4j.model.mixin.PropertyChangeListenerMixin;
import ch.elca.el4j.model.mixin.SaveRestoreCapability;

import net.java.dev.designgridlayout.DesignGridLayout;


/**
 * Demonstrates a form that has a cancel button to restore the
 * original value (undo of the changes made to fields).
 *
 * Adding the ability to save and restore values (simple undo) is simple:
 * Just apply a mixin using <code>addPropertyChangeMixin</code>.
 *
 * <script type="text/javascript">printFileStatus
 *   ("$URL$",
 *    "$Revision$",
 *    "$Date$",
 *    "$Author$"
 * );</script>
 *
 * @author Stefan Wismer (SWI)
 */
@Form(autoBind = true)
public class CancelableDemoForm extends JPanel {
	/**
	 * The first name. Bound to the model (m_person.firstName ; prefix "m_" gets removed)
	 */
	private JTextField m_firstName;
	
	private JButton m_okButton;
	private JButton m_cancelButton;
	
	@ModelObject(isDefault = true)
	private Person m_person;
	
	/**
	 * The binder instance variable.
	 */
	private final Binder m_binder = BinderManager.getBinder(this);
	
	@Autowired
	public CancelableDemoForm(GUIApplication application) {
		createComponents();
		createLayout();
		
		// assign actions
		m_okButton.setAction(application.getAction(this, "applyChanges"));
		m_cancelButton.setAction(application.getAction(this, "discardChanges"));
		
		// creating model entirely programmatically:
		m_person = new DefaultPerson();
		m_person = PropertyChangeListenerMixin.addPropertyChangeMixin(m_person);

		// initialize model
		m_person.setFirstName("Nobody");
		
		// save properties
		((SaveRestoreCapability) m_person).save();
		
		// bind the variable "person" to "this"
		// this interprets the @ModelObject annotation (see above)
		m_binder.addAutoBinding(this);
		m_binder.bindAll();
	}
	
	@Action
	public void applyChanges() {
		((SaveRestoreCapability) m_person).save();
	}
	
	@Action
	public void discardChanges() {
		((SaveRestoreCapability) m_person).restore();
	}
	
	/**
	 * Create the form components.
	 */
	private void createComponents() {
		m_firstName = new JTextField();
		m_okButton = new JButton();
		m_cancelButton = new JButton();
	}
	
	/**
	 * Layout the form components.
	 */
	private void createLayout() {
		// create the form layout
		DesignGridLayout layout = new DesignGridLayout(this);
		setLayout(layout);

		// the first two rows contains a label and a text field each
		layout.row().label(new JLabel("First Name")).add(m_firstName);
		layout.row().add(m_okButton).add(m_cancelButton);
	}
}
