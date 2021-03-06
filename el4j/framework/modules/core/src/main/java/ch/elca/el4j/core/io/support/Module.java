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

package ch.elca.el4j.core.io.support;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import ch.elca.el4j.core.io.ExplicitClassPathResource;

/**
 * This class represents a simplified view of a module (EL4Ant build system
 * unit). It contains its name, the configuration files and the dependencies.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @author Andreas Bur (ABU)
 */
public class Module {
	
	/** The delimiter used to separate configuration files and dependencies. */
	public static final String DELIMITER = ",";
	
	/** Private logger. */
	private static Logger s_logger = LoggerFactory.getLogger(Module.class);
	
	/**
	 * Is the used class loader.
	 */
	protected final ClassLoader m_classLoader;
	
	/**
	 * The module's name.
	 */
	private final String m_name;
	
	/**
	 * The module's location.
	 */
	private final String m_moduleLocation;
	
	/**
	 * The module's configuration files.
	 */
	private List<String> m_configFiles;
	
	/**
	 * The module's config file resources.
	 * Like {@link #m_configFiles} but with resolved resources.
	 */
	private List<Resource> m_configFileResources;
	
	/**
	 * The module's dependencies.
	 */
	private List<String> m_dependencies;
	
	/**
	 * Creates a new module with the given name.
	 *
	 * @param name
	 *      The module's name.
	 */
	public Module(String name) {
		this(name, null, null);
	}
	
	/**
	 * Creates a new module with the given name and the module's location.
	 *
	 * @param name The module's name.
	 * @param moduleLocation The module's location.
	 */
	public Module(String name, String moduleLocation) {
		this(name, moduleLocation, null);
	}
	
	/**
	 * Creates a new module with the given name, the module's location
	 * and resolves resources by using the given class loader.
	 *
	 * @param name The module's name.
	 * @param moduleLocation The module's location.
	 * @param classLoader The class loader to resolve resources.
	 */
	public Module(String name, String moduleLocation, ClassLoader classLoader) {
		m_name = name;
		m_moduleLocation = StringUtils.hasText(moduleLocation)
			? moduleLocation : null;
		m_configFiles = new ArrayList<String>();
		m_configFileResources = new ArrayList<Resource>();
		m_dependencies = new ArrayList<String>();
		m_classLoader = classLoader != null
			? classLoader : ClassUtils.getDefaultClassLoader();
	}
	
	/**
	 * Adds all items -- separated by {@link #DELIMITER} -- to the configuration
	 * file list.
	 *
	 * @param configFiles
	 *      The list of configuration files to add.
	 */
	public void addAllConfigFiles(String configFiles) {
		StringTokenizer tokenizer = new StringTokenizer(configFiles, DELIMITER);
		while (tokenizer.hasMoreTokens()) {
			addConfigFile(tokenizer.nextToken());
		}
	}
	
	/**
	 * Adds the given configuration file to the module.
	 *
	 * @param configFile
	 *      The configuration file to add.
	 */
	public void addConfigFile(String configFile) {
		m_configFiles.add(configFile);
		m_configFileResources.add(getConfigFileAsResource(configFile));
	}
	
	/**
	 * Adds all item -- separated by {@link #DELIMITER} -- to the dependency
	 * list.
	 *
	 * @param dependencies
	 *      The list of dependencies to add.
	 */
	public void addAllDependencies(String dependencies) {
		StringTokenizer tokenizer = new StringTokenizer(
				dependencies, DELIMITER);
		while (tokenizer.hasMoreTokens()) {
			addDependency(tokenizer.nextToken());
		}
	}
	
	/**
	 * Adds the given dependency to the module.
	 *
	 * @param dependency
	 *      The dependency to add.
	 */
	public void addDependency(String dependency) {
		m_dependencies.add(dependency);
	}
	
	/**
	 * @return Returns all configuration files.
	 */
	public String[] getConfigFiles() {
		return (String[]) m_configFiles.toArray(
				new String[m_configFiles.size()]);
	}
	
	/**
	 * @return Returns all dependencies.
	 */
	public String[] getDependencies() {
		return (String[]) m_dependencies.toArray(
				new String[m_dependencies.size()]);
	}
	
	/**
	 * @return Returns the configuration file list.
	 */
	public List<String> getConfigFilesAsList() {
		return new ArrayList<String>(m_configFiles);
	}
	
	/**
	 * @return Returns the dependency list.
	 */
	public List<String> getDependenciesAsList() {
		return new ArrayList<String>(m_dependencies);
	}
	
	/**
	 * @return Returns the module's name.
	 */
	public String getName() {
		return m_name;
	}
	
	/**
	 * @return Returns the module's location.
	 */
	public String getModuleLocation() {
		return m_moduleLocation;
	}
	
	/**
	 * @return Returns the configuration files resolved as resources in a list.
	 */
	public List<Resource> getConfigFileResourcesAsList() {
		return new ArrayList<Resource>(m_configFileResources);
	}
	
	/**
	 * Converts the given config file in a resource that takes place in
	 * the current module.
	 *
	 * @param configFile The config file to convert.
	 * @return Returns the converted resource.
	 */
	public Resource getConfigFileAsResource(String configFile) {
		Resource resource = null;
		if (m_moduleLocation == null) {
			resource = new ClassPathResource(configFile);
		} else {
			try {
				Enumeration<URL> resources
					= m_classLoader.getResources(configFile);
				while (resources.hasMoreElements() && resource == null) {
					URL url = resources.nextElement();
					String urlString = url.getFile();
					if (urlString.startsWith(m_moduleLocation)) {
						resource = new ExplicitClassPathResource(
							url, configFile, m_classLoader);
					}
				}
			} catch (IOException e) {
				resource = null;
			}
			if (resource == null) {
				s_logger.warn("Config file '" + configFile
					+ "' could not be resolved with current class loader.");
				resource = new ClassPathResource(configFile);
			}
		}
		return resource;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		if (m_moduleLocation != null) {
			return "Module '" + m_name + "' at '" + m_moduleLocation + "'";
		} else {
			return "Module '" + m_name + "'";
		}
	}
}
