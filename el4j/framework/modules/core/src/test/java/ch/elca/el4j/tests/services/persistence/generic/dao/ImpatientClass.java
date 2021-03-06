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
package ch.elca.el4j.tests.services.persistence.generic.dao;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import ch.elca.el4j.core.context.ModuleApplicationContext;
import ch.elca.el4j.core.context.ModuleApplicationListener;
import ch.elca.el4j.services.persistence.generic.dao.DaoRegistry;
import ch.elca.el4j.services.persistence.generic.dao.GenericDao;

/**
 * This impatient class tries to access the daoRegistry before the Spring context is initialized.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @author Stefan Wismer (SWI)
 */
public class ImpatientClass implements ApplicationContextAware, ModuleApplicationListener {
	/**
	 * The Spring application context.
	 */
	private ModuleApplicationContext m_applicationContext;
	
	/**
	 * The dao registry.
	 */
	private DaoRegistry m_daoRegistry;
	
	/**
	 * The thread that tries to access the daoRegistry too early.
	 */
	private Thread m_impatientThread;
	
	/**
	 * Is context ready?
	 */
	private volatile boolean m_contextIsReady = false;
	
	/**
	 * Was test successful?
	 */
	private boolean m_successful = false;
	
	/** {@inheritDoc} */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		m_applicationContext = (ModuleApplicationContext) applicationContext;
	}
	
	/** {@inheritDoc} */
	public void onContextRefreshed() {
		m_contextIsReady = true;
	}
	/**
	 * @return   the DAO registry
	 */
	public DaoRegistry getDaoRegistry() {
		return m_daoRegistry;
	}
	
	/**
	 * @param daoRegistry   the DAO registry
	 */
	public void setDaoRegistry(final DaoRegistry daoRegistry) {
		m_daoRegistry = daoRegistry;
		
		// access the dao registry before Spring context is completely initialized
		m_impatientThread = new Thread(new Runnable() {
			public void run() {
				GenericDao<?> dao = daoRegistry.getFor(String.class);
				dao.getClass();
				
				// if daoRegistry access is done before context is ready -> waiting for completely
				// initialized Spring context didn't work!!!
				
				if (m_contextIsReady || m_applicationContext.isRefreshed()) {
					m_successful = true;
				}
			}
		});
		m_impatientThread.start();
		
		// delay the Spring context initialization (100ms, see thread)
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) { }
	}
	
	/**
	 * Wait until impatient thread finally has accessed the DAO registry.
	 * 
	 * @return    <code>true</code> if test was successful.
	 */
	public boolean join() {
		try {
			m_impatientThread.join();
		} catch (InterruptedException e) { }
		
		return m_successful;
	}
}
