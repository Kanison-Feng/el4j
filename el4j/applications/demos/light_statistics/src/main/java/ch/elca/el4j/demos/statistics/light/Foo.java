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

package ch.elca.el4j.demos.statistics.light;

/**
 * This class is just a demo bean.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @author Andreas Bur (ABU)
 */
public class Foo {

	/**
	 * Computes the ith fibonacci number.
	 * @param i The fibonacci number index.
	 * @return Returns the ith fibonacci number.
	 */
	public int fibonacci(int i) {
		int fib;
		if (i <= 0) {
			fib = 0;
		} else if (i <= 2) {
			fib = 1;
		} else {
			fib = fibonacci(i - 1) + fibonacci(i - 2);
		}
		return fib;
	}
	
	// Checkstyle: EmptyBlock off
	// Checkstyle: MagicNumber off
	/**
	 * This methods sleeps one second.
	 */
	public void sleepOneSecond() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) { }
	}
	// Checkstyle: MagicNumber on
	// Checkstyle: EmptyBlock on
}
