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
package ch.elca.el4j.demos.statistics.detailed.impl;

import ch.elca.el4j.demos.statistics.detailed.DemoA;
import ch.elca.el4j.util.codingsupport.annotations.FindBugsSuppressWarnings;

/**
 * This class is a dummy implementation for presentation purposes for the
 * detailed statistics demo.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @author David Stefan (DST)
 */

@FindBugsSuppressWarnings(value="UWF_FIELD_NOT_INITIALIZED_IN_CONSTRUCTOR",
	justification="Fields not initialized because this is only a dummy class")
public class DemoAImpl implements DemoA {

	/** nonsense demoB object. */
	private DemoB m_demoB;
	
	/** nonsense demoB object. */
	private DemoC m_demoC;

	/**
	 * {@inheritDoc}
	 */
	public void computeA(int number) {

		// Checkstyle: MagicNumber off
		try {
			Thread.sleep(3L * number);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		m_demoB.computeB(3);
		
		try {
			Thread.sleep(3L * number);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		m_demoC.print();
	}

	/**
	 * Setter for DemoB.
	 *
	 * @param demo
	 *            DemoB object
	 */
	public void setDemoB(DemoB demo) {
		this.m_demoB = demo;
	}
	
	/**
	 * Setter for DemoC.
	 *
	 * @param demo
	 *            DemoB object
	 */
	public void setDemoC(DemoC demo) {
		this.m_demoC = demo;
	}
	
}
// Checkstyle: MagicNumber on
