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

package ch.elca.el4j.tests.util.metadata.annotations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Checkstyle: MagicNumber off
/**
 * This class contains a method called test(int) with annotation declarations.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @author Raphael Boog (RBO)
 * @author Martin Zeltner (MZE)
 */
public class FooImpl implements Foo {
	
	/**
	 * Inner class for testing interception.
	 */
	public static class Bar {
		/**
		 * Private logger of this inner class.
		 */
		private static Logger s_innerLogger = LoggerFactory.getLogger(Bar.class);

		/**
		 * This method does a log.
		 */
		public void doLog() {
			s_innerLogger.info("Hello, I do log something.");
		}
	}

	/**
	 * The BASE constant.
	 */
	public static final int BASE = 3;

	/**
	 * Private logger of this class.
	 */
	private static Logger s_logger = LoggerFactory.getLogger(FooImpl.class);

	/**
	 * The base member variable.
	 */
	private int m_base = BASE;

	/**
	 * {@inheritDoc}
	 */
	@ExampleAnnotationOne(factor = 5)
	@ExampleAnnotationTwo(factor = 9)
	public int test(int number) {
		s_logger.info("Multiplication of base (=" + m_base + ") and number (="
			+ number + ").");
		m_base = m_base * number;
		return m_base;
	}

	/**
	 * {@inheritDoc}
	 */
	@ExampleAnnotationOne(factor = 5)
	@ExampleAnnotationTwo(factor = 9)
	public int test(int number, FooImpl.Bar innerClass) {
		innerClass.doLog();
		s_logger.info("Multiplication of base (=" + m_base + ") and number (="
			+ number + ") in method with inner class as parameter.");
		m_base = m_base * number;
		return m_base;
	}
	
	/**
	 * The getter method for the base member.
	 *
	 * @return the base
	 */
	public int getBase() {
		return m_base;
	}

	/**
	 * The setter method for the base member.
	 *
	 * @param base
	 *            the base to set
	 */
	public void setBase(int base) {
		m_base = base;
	}
}
//Checkstyle: MagicNumber on
