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
package ch.elca.el4j.tests.services.remoting.loadbalancing.client.random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;

import ch.elca.el4j.core.context.ModuleApplicationContext;
import ch.elca.el4j.tests.services.remoting.loadbalancing.common.BusinessObject;

/**
 * This class tests the idempotent invocation interceptor that handles retrials
 * by itself.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @author Stefan Pleisch (SPL)
 */
public class LbRandomNoContextPassingTest {

	public LbRandomNoContextPassingTest() {
		super();
		m_applicationContext = new ModuleApplicationContext(
			getInclusiveConfigLocations(),
			(String[]) null,
			false,
			null);
	} // <init>

	
	/**
	 * Tests whether the server/DB properly abort the transaction upon reception
	 * of a special flag.
	 *
	 * @see #THROW_IDEMPOTENTINVOCATION_EXCEPTION
	 */
	@Test
	public void testNextProtocol() {
		getLog().debug("Starting test 'testIdempotence'....");
		try {
			s_logger.debug("-- Calling with hello 1") ;
			String result = getTestObj().call("hello1") ;
			assertEquals("Server 1 should have executed this code.",
				"localhost:8094",
				result);
			s_logger.debug("-- Calling with hello 2") ;
			result = getTestObj().call("hello2") ;
			assertEquals("Server 1 should have executed this code.",
				"localhost:8094",
				result);
			s_logger.debug("-- Calling with hello 3") ;
			result = getTestObj().call("hello3") ;
			assertEquals("Server 1 should have executed this code.",
				"localhost:8094",
				result);
			s_logger.debug("-- Calling with hello 4") ;
			result = getTestObj().call("hello4") ;
			assertEquals("Server 1 should have executed this code.",
				"localhost:8094",
				result);
			s_logger.debug("-- Calling with hello 5") ;
			result = getTestObj().call("hello5") ;
			assertEquals("Server 1 should have executed this code.",
				"localhost:8094",
				result);

		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception occurred: " + e.getMessage());
		} // catch
	} // testSecondAccess()

	/** {@inheritDoc} */
	protected void runTest() {
		testNextProtocol();
	} // runTest()

	/** {@inheritDoc} */
	protected String[] getInclusiveConfigLocations() {
		return new String[] {
			"classpath*:mandatory/*.xml",
			"classpath:loadbalancing/client/random/startup-nocontextpassing.xml",
			"classpath:loadbalancing/remoting/loadbalancing-generic-nocontextpassing-protocol-config.xml",
			"classpath:loadbalancing/remoting/rmi-nocontext-protocol-config.xml",
			"classpath:loadbalancing/remoting/random/loadbalancing-protocol-config.xml",
			"classpath*:remoting/loadbalancing/policy/random-policy-config.xml"};
	} // getInclusiveConfigLocations()

	protected Logger getLog() {
		return s_logger;
	} // getLog()

	protected BusinessObject getTestObj() {
		if (m_obj == null) {
			BeanFactory factory = (BeanFactory) m_applicationContext;

			m_obj = (BusinessObject) factory.getBean("rmiBusinessObj");

		} // if
		return m_obj;
	} // getTestObj

	/**
	 * Private logger.
	 */
	private static Logger s_logger = LoggerFactory
		.getLogger(LbRandomNoContextPassingTest.class);

	private ApplicationContext m_applicationContext ;
	
	private BusinessObject m_obj ;
	
} // CLASS ClientMultipleServerTestCase
