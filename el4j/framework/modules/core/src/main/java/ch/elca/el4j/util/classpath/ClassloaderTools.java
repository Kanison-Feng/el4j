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
package ch.elca.el4j.util.classpath;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Tool for analyzing the classpath (e.g. in maven projects).
 * This tool allows the list of URLs to be easily retrieved:
 * Call <code>getClassPath(YourMainClass.class)</code>.
 * 
 * Remark: '<code>mvn exec:java</code>' uses an URLClassloader for the main class and
 * passes all project dependencies plus the target directories as URLs to that.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @author David Bernhard (DBD)
 */
public final class ClassloaderTools {

	/**
	 * Cannot instantiate.
	 */
	private ClassloaderTools() { }
	
	/**
	 * Checks whether a class is loaded via an URLClassLoader,
	 * in which case a call to <code>getClassPath</code> will work.
	 * @param mainClass The class to check.
	 * @return Whether this class was loaded via an URLClassLoader.
	 */
	public static boolean isURLLoaded(Class < ? > mainClass) {
		ClassLoader cl = mainClass.getClassLoader();
		return (cl instanceof URLClassLoader);
	}

	/**
	 * @param mainClass A class from which to obtain the classloader and its
	 * classpath.
	 * @return A comma-separated list of the modules on the current maven
	 * classpath.
	 * @throws RuntimeException - if the class does not
	 * come from an URLClassLoader
	 */
	public static String getClassPath(Class < ? > mainClass)
		throws RuntimeException {
		ClassLoader cl = mainClass.getClassLoader();
		
		if (!isURLLoaded(mainClass)) {
			throw new RuntimeException(
				"The class " + mainClass
				+ " was not loaded via an URLClassLoader.");
		}
		URL[] urls = ((URLClassLoader) cl).getURLs();
		StringBuffer classPathBuffer =  new StringBuffer();
		for (URL u : urls) {
			classPathBuffer.append(u.toExternalForm()).append(", ");
		}
		// remove last ", "
		classPathBuffer.delete(classPathBuffer.length() - 2, classPathBuffer.length());

		return classPathBuffer.toString();
	}
	
	/**
	 * @param mainClass A class from which to obtain the classloader and its
	 * classpath.
	 * @return A String[] of the modules on the current maven
	 * classpath.
	 * @throws RuntimeException - if the class does not
	 * come from an URLClassLoader
	 */
	public static String[] getURLs(Class < ? > mainClass)
		throws RuntimeException {
		ClassLoader cl = mainClass.getClassLoader();
		
		if (!isURLLoaded(mainClass)) {
			throw new RuntimeException(
				"The class " + mainClass
				+ " was not loaded via an URLClassLoader.");
		}
		URL[] urls = ((URLClassLoader) cl).getURLs();
		String[] urlStrings = new String[urls.length];
		for (int i = 0; i < urls.length; i++) {
			urlStrings[i] = urls[i].toString();
		}
		return urlStrings;
	}
}
