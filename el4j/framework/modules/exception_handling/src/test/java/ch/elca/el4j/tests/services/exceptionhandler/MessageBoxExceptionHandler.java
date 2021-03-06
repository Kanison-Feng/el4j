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

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.elca.el4j.services.exceptionhandler.AbstractExceptionHandlerInterceptor;
import ch.elca.el4j.services.exceptionhandler.handler.AbstractExceptionHandler;

/**
 * This simple exception handler logs exception messages in a dialogue,
 * printed to the standard out.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @author Andreas Bur (ABU)
 */
public class MessageBoxExceptionHandler extends AbstractExceptionHandler {

	/**
	 * Logger.
	 */
	private static Logger s_logger
		= LoggerFactory.getLogger(MessageBoxExceptionHandler.class);
	
	/** Number of handle calls. */
	public static int s_numberOfHandleCalls = 0;
	
	/**
	 * {@inheritDoc}
	 */
	protected Object handleException(Throwable t,
			AbstractExceptionHandlerInterceptor exceptionInvoker,
			MethodInvocation invocation, Logger logger) throws Throwable {
		
		s_numberOfHandleCalls++;
		
		String s = t.getMessage();
		StringBuffer buffer = new StringBuffer('\n');
		drawLine(s.length(), buffer);
		buffer.append("| ");
		buffer.append(s);
		buffer.append(" |\n");
		drawLine(s.length(), buffer);
		
		s_logger.info(buffer.toString());
		
		return null;
	}

	/**
	 * Draws a line consisting of hyphens.
	 *
	 * @param length
	 *      The number of hyphens to print.
	 *
	 * @param buffer
	 *      The buffer to write the hyphens to.
	 */
	private void drawLine(int length, StringBuffer buffer) {
		// Checkstyle: MagicNumber off
		for (int i = 0; i < length + 4; i++) {
			buffer.append("-");
		}
		buffer.append('\n');
		// Checkstyle: MagicNumber on
	}
}
