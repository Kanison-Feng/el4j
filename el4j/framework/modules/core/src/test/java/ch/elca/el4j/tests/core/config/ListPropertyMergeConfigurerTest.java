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

package ch.elca.el4j.tests.core.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.ApplicationContext;

import ch.elca.el4j.core.context.ModuleApplicationContext;

/**
 * JUnit test class for the ListPropertyMergeConfigurer.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @author Raphael Boog (RBO)
 */
public class ListPropertyMergeConfigurerTest {

	/**
	 * This test adds a value from one location to a list property which is
	 * empty and checks if the value is set correctly.
	 */
	@Test
	public void testAddValuesFromOneLocationToEmptyList() {
		ApplicationContext ac = new ModuleApplicationContext(
				"classpath:scenarios/core/config/"
				+ "beansWithOneLocationAndEmptyList.xml", false);

		ListClass listTest = (ListClass) ac.getBean("ListTest");

		assertEquals("The list in 'ListTest' does not contain 1 entry.", 1,
				listTest.getListTest().size());

		assertEquals("The first entry in the list is not 'item 1'.", "item 1",
				listTest.getListTest().get(0));

	}

	/**
	 * This test adds a value from one location to a list property which already
	 * contains one value and checks if the values are set correctly.
	 *
	 */
	@Test
	public void testAddValuesFromOneLocationToNonEmptyList() {
		ApplicationContext ac = new ModuleApplicationContext(
				"classpath:scenarios/core/config/"
				+ "beansWithOneLocationAndNonEmptyList.xml", false);

		ListClass listTest = (ListClass) ac.getBean("ListTest");

		assertEquals("The list in 'ListTest' does not contain 2 entries.", 2,
				listTest.getListTest().size());

		// HACKY: change in spring 2.0.3, probably a mistake?
		assertEquals("The first entry in the list is not 'item 0'.", "item 0",
				listTest.getListTest().get(0));

		assertEquals("The second entry in the list is not 'item 1'.", "item 1",
				listTest.getListTest().get(1));

	}

	/**
	 * This test adds two values from one location to a list property which
	 * already contains a value and checks if the values are set correctly.
	 *
	 */
	@Test
	public void testAddValuesFromOneLocationWithTwoListValuesToNonEmptyList() {
		ApplicationContext ac = new ModuleApplicationContext(
				"classpath:scenarios/core/config/"
				+ "beansWithOneLocationWithTwoValuesList.xml", false);

		ListClass listTest = (ListClass) ac.getBean("ListTest");

		// Checkstyle: MagicNumber off
		assertEquals("The list in 'ListTest' does not contain 3 entries.", 3,
				listTest.getListTest().size());

		// HACKY: change in spring 2.0.3, probably a mistake?
		assertEquals("The first entry in the list is not 'item 0'.", "item 0",
				listTest.getListTest().get(0));
		
		assertEquals("The second entry in the list is not 'item 2'.", "item 2",
				listTest.getListTest().get(1));

		assertEquals("The third entry in the list is not 'item 3'.", "item 3",
				listTest.getListTest().get(2));
		// Checkstyle: MagicNumber on
	}

	/**
	 * This test adds three values from two locations to a list property which
	 * already contains a value and checks if the values are set correctly and
	 * the order of processing the locations is the same as defined in the xml
	 * file.
	 *
	 */
	@Test
	public void testAddValuesFromTwoLocationsToNonEmptyList() {
		ApplicationContext ac = new ModuleApplicationContext(
				"classpath:scenarios/core/config/"
				+ "beansWithTwoLocationsAndNonEmptyList.xml", false);

		ListClass listTest = (ListClass) ac.getBean("ListTest");

		// Checkstyle: MagicNumber off
		assertEquals("The list in 'ListTest' does not contain 4 entries.", 4,
				listTest.getListTest().size());

		// HACKY: change in spring 2.0.3, probably a mistake?
		assertEquals("The first entry in the list is not 'item 0'.", "item 0",
				listTest.getListTest().get(0));

		assertEquals("The second entry in the list is not 'item 1'.", "item 1",
				listTest.getListTest().get(1));

		assertEquals("The third entry in the list is not 'item 2'.", "item 2",
				listTest.getListTest().get(2));

		assertEquals("The four entry in the list is not 'item 3'.", "item 3",
				listTest.getListTest().get(3));
		// Checkstyle: MagicNumber on
	}

	/**
	 * This test adds two values from one location to two lists (one value to
	 * each list) and checks if the values are set correctly.
	 *
	 */
	@Test
	public void testAddValuesFromOneLocationToTwoLists() {
		ApplicationContext ac = new ModuleApplicationContext(
				"classpath:scenarios/core/config/"
				+ "beansTwoListsWithOneLocation.xml", false);

		ListClass listTest = (ListClass) ac.getBean("ListTest");
		ListClass listTest2 = (ListClass) ac.getBean("ListTest2");

		assertEquals("The list in 'ListTest' does not contain 1 entry.", 1,
				listTest.getListTest().size());
	
		assertEquals("The first entry in the list is not 'item 4'.", "item 4",
				listTest.getListTest().get(0));

		assertEquals("The list in 'ListTest2' does not contain 2 entries.", 2,
				listTest2.getListTest().size());

		// HACKY: change in spring 2.0.3, probably a mistake?
		assertEquals("The first entry in the list is not 'item 0'.", "item 0",
				listTest2.getListTest().get(0));

		assertEquals("The second entry in the list is not 'item 5'.", "item 5",
				listTest2.getListTest().get(1));
	}

	/**
	 * This test adds a value to a list property, then overrides this list
	 * property, then adds a value to this list property and finally checks if
	 * the values are set correctly. Note that the order of processing the
	 * postProcessBeanFactory implementations can be set via the 'order'
	 * property. Lower values are processed first.
	 *
	 */
	@Test
	public void testAddValuesThenOverrideThenAddValue() {
		ApplicationContext ac = new ModuleApplicationContext(
				"classpath:scenarios/core/config/"
				+ "beansWithOverriding.xml", false);

		ListClass listTest = (ListClass) ac.getBean("ListTest");

		assertEquals("The list in 'ListTest' does not contain 2 entries.", 2,
				listTest.getListTest().size());

		assertEquals("The first entry in the list is not 'override item'.",
				"override item", listTest.getListTest().get(0));

		assertEquals("The second entry in the list is not 'item 1'.", "item 1",
				listTest.getListTest().get(1));

	}

	/**
	 * This test takes an empty list.properties file and checks if the values
	 * are set correctly and no errors are thrown.
	 *
	 */
	@Test
	public void testAddValuesFromEmptyPropertyFile() {
		ApplicationContext ac = new ModuleApplicationContext(
				"classpath:scenarios/core/config/"
				+ "beansWithEmptyLocation.xml", false);

		ListClass listTest = (ListClass) ac.getBean("ListTest");

		assertEquals("The list in 'ListTest' does not contain 1 entry.", 1,
				listTest.getListTest().size());

		assertEquals("The first entry in the list is not 'item 0'.", "item 0",
				listTest.getListTest().get(0));
	}

	/**
	 * This test adds values to an undefined bean and checks if a
	 * BeanInitializationException is thrown.
	 *
	 */
	@Test
	public void testAddValuesToUndefinedBean() {
		// Checkstyle: EmptyBlock off
		try {
			new ModuleApplicationContext(
					"classpath:scenarios/core/config/"
					+ "beansAddUndefinedBean.xml", false);
			fail("Should raise an exception.");
		} catch (BeanInitializationException e) { }
		// Checkstyle: EmptyBlock on
	}

}