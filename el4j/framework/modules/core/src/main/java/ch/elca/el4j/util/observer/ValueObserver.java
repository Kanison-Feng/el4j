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
package ch.elca.el4j.util.observer;

/**
 * An object observing (i.e. receiving change notifications about) a reference
 * of type T.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @param <T> see above
 * @see ObservableValue
 * @see InquisitiveValueObserver
 * @author Adrian Moos (AMS)
 */
public interface ValueObserver<T> {
	/** Invoked if the observed reference has changed.
	 * Implementations may not assume that other value observers
	 * have already received their notifications.
	 * @param newRef the new reference */
	void changed(T newRef);
}