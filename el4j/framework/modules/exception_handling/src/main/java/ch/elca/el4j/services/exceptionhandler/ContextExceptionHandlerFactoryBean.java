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

package ch.elca.el4j.services.exceptionhandler;

import java.util.Map;

import ch.elca.el4j.services.monitoring.notification.CoreNotificationHelper;
/**
 * Convenience factory that simplifies the creation of context exception
 * handlers.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @author Andreas Bur (ABU)
 */
public class ContextExceptionHandlerFactoryBean extends
		AbstractExceptionHandlerFactoryBean {

	/** The policies. */
	private Map m_policies;
	
	/**
	 * Default Constructor. Configures the proxy to handle only those exceptions
	 * that are <b>not</b> defined in the signature (excluding unchecked
	 * exceptions, which are handled always).
	 */
	public ContextExceptionHandlerFactoryBean() {
		super();
		// change this behaviour in the ContextExceptionHandlerInterceptor too
		// (to be done manually since Java doesn't support multi inheritance).
		setForwardSignatureExceptions(true);
		setHandleRTSignatureExceptions(true);
	}

	/**
	 * @see ContextExceptionHandlerInterceptor#setPolicies(Map)
	 */
	public void setPolicies(Map policies) {
		m_policies = policies;
	}

	/**
	 * {@inheritDoc}
	 */
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		CoreNotificationHelper.notifyIfEssentialPropertyIsEmpty(
				m_policies, "policies", this);
	}

	/**
	 * {@inheritDoc}
	 */
	protected AbstractExceptionHandlerInterceptor
	createExceptionHandlerInterceptor() {
		
		ContextExceptionHandlerInterceptor interceptor
			= new ContextExceptionHandlerInterceptor();
		interceptor.setPolicies(m_policies);
		return interceptor;
	}
}
