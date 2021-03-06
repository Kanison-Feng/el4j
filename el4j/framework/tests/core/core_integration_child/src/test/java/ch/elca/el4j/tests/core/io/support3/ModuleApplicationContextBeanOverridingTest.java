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
package ch.elca.el4j.tests.core.io.support3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.context.ApplicationContext;

import ch.elca.el4j.core.context.ModuleApplicationContext;
import ch.elca.el4j.tests.core.io.support.helper.Employee;

/**
 * Tests the bean definition overriding behavior of the module application
 * context.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @author Martin Zeltner (MZE)
 */
public class ModuleApplicationContextBeanOverridingTest {
	/**
	 * Config location prefix.
	 */
	public static final String CONFIG_LOCATION_PREFIX
		= "classpath*:scenarios/core/io/support3/";
	
	/**
	 * Name of the tested bean.
	 */
	public static final String BEAN_NAME = "employee";
	
	/**
	 * Tests bean overriding by using a config location with a wildcard and
	 * not including outer resources.
	 */
	@Test
	public void testConfigLocationWithWildcardWithoutOuterResources() {
		boolean mergeWithOuterResources = false;
		ApplicationContext appContext = new ModuleApplicationContext(
			new String[] {CONFIG_LOCATION_PREFIX + "*.xml"},
			null, true, null, mergeWithOuterResources);
		Employee e = (Employee) appContext.getBean("employee");
		assertNotNull(e);
		assertEquals("Martin", e.getPrename());
		assertEquals("Zeltner", e.getLastname());
	}
	
	/**
	 * Tests bean overriding by using a config location with a wildcard and
	 * including outer resources. The outer resources are the most specific.
	 */
	@Test
	public void
	testConfigLocationWithWildcardWithOuterResourcesAsMostSpecific() {
		boolean mergeWithOuterResources = true;
		boolean mostSpecificResourceLast = true;
		boolean mostSpecificBeanDefinitionCounts = true;
		ApplicationContext appContext = new ModuleApplicationContext(
			new String[] {CONFIG_LOCATION_PREFIX + "*.xml"},
			null, true, null, mergeWithOuterResources, mostSpecificResourceLast,
			mostSpecificBeanDefinitionCounts);
		Employee e = (Employee) appContext.getBean("employee");
		assertNotNull(e);
		assertEquals("Philipp", e.getPrename());
		assertEquals("Oser", e.getLastname());
	}

	/**
	 * Tests bean overriding by using a config location with a wildcard but
	 * without outer resources. The least specific resources are the most
	 * important ones.
	 */
	@Test
	public void
	testConfigLocationWithWildcardWithoutOuterResourcesAndLeastSpecifics() {
		boolean mergeWithOuterResources = false;
		boolean mostSpecificResourceLast = true;
		boolean mostSpecificBeanDefinitionCounts = false;
		ApplicationContext appContext = new ModuleApplicationContext(
			new String[] {CONFIG_LOCATION_PREFIX + "*.xml"},
			null, true, null, mergeWithOuterResources, mostSpecificResourceLast,
			mostSpecificBeanDefinitionCounts);
		Employee e = (Employee) appContext.getBean("employee");
		assertNotNull(e);
		assertEquals("Alex", e.getPrename());
		assertEquals("Mathey", e.getLastname());
	}

	/**
	 * Tests bean overriding by using a config location with a wildcard and
	 * including outer resources. The outer resources are the least specific.
	 */
	@Test
	public void
	testConfigLocationWithWildcardWithOuterResourcesAsLeastSpecific() {
		boolean mergeWithOuterResources = true;
		boolean mostSpecificResourceLast = true;
		boolean mostSpecificBeanDefinitionCounts = false;
		ApplicationContext appContext = new ModuleApplicationContext(
			new String[] {CONFIG_LOCATION_PREFIX + "*.xml"},
			null, true, null, mergeWithOuterResources, mostSpecificResourceLast,
			mostSpecificBeanDefinitionCounts);
		Employee e = (Employee) appContext.getBean("employee");
		assertNotNull(e);
		assertEquals("Alex", e.getPrename());
		assertEquals("Mathey", e.getLastname());
	}

	/**
	 * Tests bean overriding by using a config location with a wildcard and
	 * including outer resources.
	 */
	@Test
	public void testStrictConfigLocationOrder() {
		boolean mergeWithOuterResources = true;
		ApplicationContext appContext = new ModuleApplicationContext(
			new String[] {
				CONFIG_LOCATION_PREFIX
					+ "bean-definition-overriding-test-3.xml",
				CONFIG_LOCATION_PREFIX
					+ "bean-definition-overriding-test-2.xml",
				CONFIG_LOCATION_PREFIX
					+ "bean-definition-overriding-test-1.xml"
			}, null, true, null, mergeWithOuterResources);
		Employee e = (Employee) appContext.getBean("employee");
		assertNotNull(e);
		assertEquals("Alex", e.getPrename());
		assertEquals("Mathey", e.getLastname());
	}
}
