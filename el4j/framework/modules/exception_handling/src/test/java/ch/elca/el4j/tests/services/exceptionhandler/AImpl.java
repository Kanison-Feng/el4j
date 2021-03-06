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

/**
 * Sample bean class used for testing.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @author Andreas Bur (ABU)
 */
public class AImpl implements A {

	/** Static int to count number of method invocations. */
	public static int s_numberOfConcatCalls = 0;
	
	/** Static field to set if the call should fail. */
	public static boolean s_concatFails = true;
	
	/** The adder to delegate calls to. */
	private Adder m_adder;
	
	/** Number of retires. */
	// Checkstyle: MagicNumber off
	private int m_retries = 4;
	// Checkstyle: MagicNumber on
	
	/** Resets the static counter. */
	public static void reset() {
		s_numberOfConcatCalls = 0;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setRetries(int retries) {
		this.m_retries = retries;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setAdder(Adder adder) {
		m_adder = adder;
		s_concatFails = true;
	}

	/**
	 * {@inheritDoc}
	 */
	public int div(int a, int b) {
		return a / b;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void throwException() throws ApplicationException {
		throw new ApplicationException("Exception");
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void throwRTException() {
		throw new RuntimeException();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String concat(String a, String b) {
		s_numberOfConcatCalls++;
		if (s_concatFails) {
			throw new UnsupportedOperationException();
		} else {
			return a.concat(b);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int add(int a, int b) {
		return m_adder.add(a, b);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int sub(int a, int b) {
		if (m_retries > 0) {
			m_retries--;
			throw new IllegalArgumentException();
		}
		return a - b;
	}
}
