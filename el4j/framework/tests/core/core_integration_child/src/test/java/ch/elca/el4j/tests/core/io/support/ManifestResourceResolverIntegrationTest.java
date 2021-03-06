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

package ch.elca.el4j.tests.core.io.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import ch.elca.el4j.core.io.support.ConfigLocationProvider;
import ch.elca.el4j.core.io.support.ListResourcePatternResolverDecorator;
import ch.elca.el4j.core.io.support.ManifestOrderedConfigLocationProvider;

/**
 * This integration test checks correctness of the Manifest resource resolver.
 * It works only if the <code>MANIFEST.MF</code> files with the special
 * configuration location section are available.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @author Andreas Bur (ABU)
 */
public class ManifestResourceResolverIntegrationTest
	extends AbstractOrderTestCase {

	/** A configuration file from module core. */
	public static final String CONFIG_CORE_1
		= "optional/interception/methodTracing.xml";
	/** A configuration file from module core. */
	public static final String CONFIG_CORE_2
		= "optional/interception/transactionCommonsAttributes.xml";
	/** A configuration file from module core. */
	public static final String CONFIG_CORE_4 = "sql-error-codes.xml";
	
	/** A configuration file from module core-tests. */
	public static final String CONFIG_CORE_TESTS_1
		= "scenarios/core/aop/testBeansForBeanTypeAutoProxyCreator.xml";
	/** A configuration file from module core-tests. */
	public static final String CONFIG_CORE_TESTS_2
		= "scenarios/core/config/beansAddUndefinedBean.xml";
	/** A configuration file from module core-tests. */
	public static final String CONFIG_CORE_TESTS_3
		= "scenarios/core/io/support/a.xml";
	/** A configuration file from module core-tests. */
	public static final String CONFIG_CORE_TESTS_4
		= "scenarios/core/io/support/ab.xml";
	/** A configuration file from module core-tests. */
	public static final String CONFIG_CORE_TESTS_5
		= "scenarios/core/io/support/b.xml";
	/** A configuration file from module core-tests. */
	public static final String CONFIG_CORE_TESTS_6
		= "scenarios/core/io/support/optional/a.xml";
	
	/** A configuration file from module core_integration-tests. */
	public static final String CONFIG_CORE_INTEGRATION_TESTS_1
		= "scenarios/core/io/integration/a.xml";
	/** A configuration file from module core_integration-tests. */
	public static final String CONFIG_CORE_INTEGRATION_TESTS_2
		= "scenarios/core/io/integration/ab.xml";
	/** A configuration file from module core_integration-tests. */
	public static final String CONFIG_CORE_INTEGRATION_TESTS_3
		= "scenarios/core/io/integration/b.xml";
	/** A configuration file from module core_integration-tests. */
	public static final String CONFIG_CORE_INTEGRATION_TESTS_4
		= "scenarios/core/io/integration/c.xml";
	
	/** Configuration files form the module core. */
	public static final String[] CONFIG_CORE = {
		CONFIG_CORE_1, CONFIG_CORE_2, CONFIG_CORE_4
	};
	
	/** Configuration files form the module core-tests. */
	public static final String[] CONFIG_CORE_TESTS = {
		CONFIG_CORE_TESTS_1, CONFIG_CORE_TESTS_2, CONFIG_CORE_TESTS_3,
		CONFIG_CORE_TESTS_4, CONFIG_CORE_TESTS_5, CONFIG_CORE_TESTS_6
	};
	
	/** Configuration files from the module core_integration-tests. */
	public static final String[] CONFIG_CORE_INTEGRATION_TESTS = {
		CONFIG_CORE_INTEGRATION_TESTS_1, CONFIG_CORE_INTEGRATION_TESTS_2,
		CONFIG_CORE_INTEGRATION_TESTS_3, CONFIG_CORE_INTEGRATION_TESTS_4,
	};

	/**
	 * Logger.
	 */
	private static Logger s_logger
		= LoggerFactory.getLogger(ManifestResourceResolverIntegrationTest.class);
	
	/** The resource pattern resover. */
	private ResourcePatternResolver m_resolver;
	
	/**
	 * The configuration locations found by a configuration location provider.
	 */
	private String[] m_locations;
	
	/**
	 * Creates a new instance of this test.
	 */
	public ManifestResourceResolverIntegrationTest() {
		ConfigLocationProvider provider
			= new ManifestOrderedConfigLocationProvider();
		
		m_locations = provider.getConfigLocations();
		
		ListResourcePatternResolverDecorator resolver
			= new ListResourcePatternResolverDecorator(provider);
		
		resolver.setMergeWithOuterResources(true);
		m_resolver = resolver;
	}
	
	/**
	 * Tests whether the configuration files are arranged in a correct order
	 * (checks partial order only).
	 */
	@Test
	public void testOrder() {
		assertAllBefore(CONFIG_CORE_TESTS, CONFIG_CORE, m_locations);
		assertAllBefore(CONFIG_CORE_INTEGRATION_TESTS, CONFIG_CORE, m_locations);
		assertAllBefore(CONFIG_CORE_INTEGRATION_TESTS, CONFIG_CORE_TESTS, m_locations);
	}

	/**
	 * Tests whether a single resource is resolved correctly using
	 * <code>getResource()</code>.
	 */
	@Test
	public void testGetSingleResource() throws IOException {
		String resourceLocation
			= "optional/interception/transactionJava5Annotations.xml";
		Resource[] r = m_resolver.getResources(
			"classpath*:" + resourceLocation);
		assertEquals(r.length, 2);
		s_logger.info(r[0].getURL().toString());
		s_logger.info(r[1].getURL().toString());
		Resource r2 = m_resolver.getResource(
			"classpath:" + resourceLocation);
		assertNotNull(r2);
		assertEquals(r[0].getURL(), r2.getURL());
		s_logger.info(r2.getURL().toString());
	}
	
	/**
	 * Tests the question mark semantic.
	 *
	 * @throws IOException
	 *      If an I/O error occurs.
	 */
	@Test
	public void testQuestionMarkPattern() throws IOException {
		Resource[] r = m_resolver.getResources(
				"classpath:scenarios/core/io/support/a?.xml");
		assertEquals("Returned wrong number of resources", 1, r.length);
		s_logger.info(r[0].getFilename());
	}
	
	/**
	 * Tests whether the right number of configuration files are resolved, that
	 * reside in the mandatory folder.
	 *
	 * @throws IOException
	 *      If an I/O error occurs.
	 */
	@Test
	public void testMandatory() throws IOException {
		Resource[] r = m_resolver.getResources("classpath*:mandatory/*.xml");
		assertEquals(0, r.length);
	}
	
	/**
	 * Tests whether local resources are found.
	 *
	 * @throws IOException
	 *      If an I/O error occurs.
	 */
	@Test
	public void testLocalResource() throws IOException {
		Resource[] r = m_resolver.getResources(
				"classpath*:scenarios/core/io/integration/*.xml");
		// Checkstyle: MagicNumber off
		assertEquals(4, r.length);
		// Checkstyle: MagicNumber on
	}
	
	/**
	 * Tests whether all resources in the <code>core/io/integration</code>
	 * folder are found.
	 *
	 * @throws IOException
	 *      If an I/O error occurs.
	 */
	@Test
	public void testGetAllIntegrationResources() throws IOException {
		checkResources("classpath*:scenarios/core/io/integration/*.xml",
				CONFIG_CORE_INTEGRATION_TESTS);
	}
	
	/**
	 * Tests whether all resources ending with <code>a.xml</code> are found.
	 *
	 * @throws IOException
	 *      If an I/O error occurs.
	 */
	@Test
	public void testGetAllResourcesEndingWithA() throws IOException {
		checkResources("classpath*:scenarios/core/**/*a.xml",
				new String[] {CONFIG_CORE_INTEGRATION_TESTS_1, CONFIG_CORE_TESTS_6, CONFIG_CORE_TESTS_3});
	}
	
	/**
	 * Checks whether the given configuration location pattern is resolved
	 * to resources that match the given array of file names.
	 *
	 * @param configLocation
	 *      The configuration location pattern to match against.
	 *
	 * @param expeted
	 *      The array with the expected file names.
	 *
	 * @throws IOException
	 *      If an I/O error occurs.
	 */
	private void checkResources(String configLocation, String[] expeted)
		throws IOException {
		
		String[] s = getResoucreNames(configLocation);
		assertEquals("Didn't found as many resources as expected.",
				expeted.length, s.length);
		
		for (int i = 0; i < expeted.length; i++) {
			assertTrue("'" + expeted[i] + "' was not found using location "
					+ "pattern '" + configLocation + "'",
					containsStringEndingWith(expeted[i], s));
		}
	}
	
	/**
	 * Queries the list of resources that match the given configuration
	 * location pattern and returns their file names.
	 *
	 * @param configLocation
	 *      The configuration location pattern to match against.
	 *
	 * @return Returns the file names of all resources that match the given
	 *      configuration location pattern.
	 *
	 * @throws IOException
	 *      If an I/O error occurs.
	 */
	private String[] getResoucreNames(String configLocation)
		throws IOException {
		
		Resource[] r = m_resolver.getResources(configLocation);
		String[] s = new String[r.length];
		for (int i = 0; i < r.length; i++) {
			s[i] = r[i].getURL().toString();
		}
		return s;
	}
}
