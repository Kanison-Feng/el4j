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
package ch.elca.el4j.maven.plugins.database.mojo;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import ch.elca.el4j.maven.plugins.database.AbstractDBMojo;

/**
 * This class is a database mojo for the 'stop' statement.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @goal stop
 * @author David Stefan (DST)
 */
public class StopMojo extends AbstractDBMojo {

	/**
	 * {@inheritDoc}
	 */
	public void executeInternal() throws MojoExecutionException, MojoFailureException {
		try {
			getLog().info("Stopping database... ");
			getDbController().stop();
		} catch (Exception e) {
			throw new MojoFailureException(e.getMessage());
		}
	}

}
