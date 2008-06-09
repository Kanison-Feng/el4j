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
package ch.elca.el4j.tests.remoting.jaxws;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import ch.elca.el4j.core.context.ModuleApplicationContext;

import com.xmlme.webservices.ShakespeareSoap;

/**
 * This class is a test for JAX-WS using classes generated by wsimport.
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
public class ShakespeareTest {
	/**
	 * ApplicationContext.
	 */
	private ConfigurableApplicationContext m_appContext;
	
	/**
	 * {@inheritDoc}
	 */
	@Before
	public void setUp() {
		m_appContext = new ModuleApplicationContext(
			new String[] {"classpath*:mandatory/*.xml",
				"scenarios/client/remotingtests-jaxws-shakespeare-config.xml"},
				false);
	}
	
	/**
	 *
	 * {@inheritDoc}
	 */
	@After
	public void tearDown() {
		m_appContext.close();
	}
	
	/**
	 * Test ShakeSpeareService.
	 */
	@Test
	public void testShakeSpeareService() {
		ShakespeareSoap s = getShakespeare();
		String speech = s.getSpeech("To be, or not to be");
		assertTrue(speech.startsWith("<SPEECH><PLAY>HAMLET</PLAY><SPEAKER>HAMLET</SPEAKER>To be, or not to be: that is the question: Whether 'tis nobler"));
		assertTrue(speech.endsWith("in thy orisons Be all my sins remember'd.</SPEECH>"));
	}
	
	/**
	 * Get the shakespeare to use.
	 * @return Shakespeare to use
	 */
	public ShakespeareSoap getShakespeare() {
		return (ShakespeareSoap) getApplicationContext().getBean("shakespeare");
	}
	
	/**
	 * Get the Applicationcontext.
	 * @return The ApplicationContext
	 */
	protected ApplicationContext getApplicationContext() {
		return m_appContext;
	}
}
