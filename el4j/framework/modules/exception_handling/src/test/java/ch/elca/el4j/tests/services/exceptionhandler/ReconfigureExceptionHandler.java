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

package ch.elca.el4j.tests.services.exceptionhandler;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;

import ch.elca.el4j.services.exceptionhandler.InappropriateHandlerException;
import ch.elca.el4j.services.exceptionhandler.handler.AbstractReconfigureExceptionHandler;

/**
 * This exception handler reconfigures the A bean, exchanging the concrete
 * {@link ch.elca.el4j.tests.services.exceptionhandler.Adder} implementation.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @author Andreas Bur (ABU)
 */
public class ReconfigureExceptionHandler extends
		AbstractReconfigureExceptionHandler {

	/** The replacement object. */
	private C m_c;
	
	/**
	 * Sets the replacement object.
	 *
	 * @param c
	 *      The instance of C that is used as the replacement.
	 */
	public void setC(C c) {
		m_c = c;
	}

	/**
	 * {@inheritDoc}
	 */
	protected void reconfigure(Throwable t, MethodInvocation invocation, Logger logger) {
		
		if (!"add".equals(invocation.getMethod().getName())) {
			throw new InappropriateHandlerException();
		}
		
		((A) invocation.getThis()).setAdder(m_c);
	}
}
