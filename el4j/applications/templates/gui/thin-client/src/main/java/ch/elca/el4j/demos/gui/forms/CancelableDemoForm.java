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
package ch.elca.el4j.demos.gui.forms;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.application.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.silvermindsoftware.hitch.Binder;
import com.silvermindsoftware.hitch.BinderManager;
import com.silvermindsoftware.hitch.annotations.Form;
import com.silvermindsoftware.hitch.annotations.ModelObject;

import ch.elca.el4j.core.context.annotations.LazyInit;
import ch.elca.el4j.demos.model.DefaultPerson;
import ch.elca.el4j.demos.model.Person;
import ch.elca.el4j.services.gui.model.mixin.PropertyChangeListenerMixin;
import ch.elca.el4j.services.gui.model.mixin.SaveRestoreCapability;
import ch.elca.el4j.services.gui.swing.GUIApplication;

import net.java.dev.designgridlayout.DesignGridLayout;


/**
 * Demonstrates a form that has a cancel button to restore the
 * original value (undo of the changes made to fields).
 *
 * Adding the ability to save and restore values (simple undo) is simple:
 * Just apply a mixin using <code>addPropertyChangeMixin</code>.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @author Stefan Wismer (SWI)
 */
@Lazy
@Component("cancelableDemoForm")
@Form(autoBind = true)
public class CancelableDemoForm extends JPanel {
	/**
	 * The first name. Bound to the model (person.firstName ; prefix "" gets removed)
	 */
	private JTextField firstName;
	
	private JButton okButton;
	private JButton cancelButton;
	
	@ModelObject(isDefault = true)
	private transient Person person;
	
	/**
	 * The binder instance variable.
	 */
	private final Binder binder = BinderManager.getBinder(this);
	
	@Autowired
	public CancelableDemoForm(GUIApplication application) {
		createComponents();
		createLayout();
		
		// assign actions
		okButton.setAction(application.getAction(this, "applyChanges"));
		cancelButton.setAction(application.getAction(this, "discardChanges"));
		
		// creating model entirely programmatically:
		person = new DefaultPerson();
		person = PropertyChangeListenerMixin.addPropertyChangeMixin(person);

		// initialize model
		person.setFirstName("Nobody");
		
		// save properties
		((SaveRestoreCapability) person).save();
		
		// bind the variable "person" to "this"
		// this interprets the @ModelObject annotation (see above)
		binder.addAutoBinding(this);
		binder.bindAll();
	}
	
	@Action
	public void applyChanges() {
		((SaveRestoreCapability) person).save();
	}
	
	@Action
	public void discardChanges() {
		((SaveRestoreCapability) person).restore();
	}
	
	/**
	 * Create the form components.
	 */
	private void createComponents() {
		firstName = new JTextField();
		okButton = new JButton();
		cancelButton = new JButton();
	}
	
	/**
	 * Layout the form components.
	 */
	private void createLayout() {
		// create the form layout
		DesignGridLayout layout = new DesignGridLayout(this);
		setLayout(layout);

		// the first two rows contains a label and a text field each
		layout.row().grid(new JLabel("First Name")).add(firstName);
		layout.row().grid().add(okButton).add(cancelButton);
	}
}
