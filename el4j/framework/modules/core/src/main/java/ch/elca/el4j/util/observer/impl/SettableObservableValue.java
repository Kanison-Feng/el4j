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
package ch.elca.el4j.util.observer.impl;

/**
 * An ObservableValue whose value can be {@link #set(Object)}.
 *
 * @param <T> see supertype.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @see ch.elca.el4j.util.observer.ObservableValue
 * @author Adrian Moos (AMS)
 */
public class SettableObservableValue<T> extends AbstractObservableValue<T> {
	/** creates a new SettableObservableValue.
	 * @param initialReference the initial reference */
	public SettableObservableValue(T initialReference) {
		super(initialReference);
	}

	// publish setter
	/** sets the reference held to {@code newRef}. */
	public void set(T newRef) {
		super.set(newRef);
	}
}