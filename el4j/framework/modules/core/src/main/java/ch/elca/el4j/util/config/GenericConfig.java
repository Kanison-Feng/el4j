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
package ch.elca.el4j.util.config;

import java.util.HashMap;
import java.util.Map;

/**
 * The base class for generic configurations. It is a hierarchical structure of
 * configuration entries, where entries can be added, inherited and overridden.
 *
 * <script type="text/javascript">printFileStatus
 *   ("$URL$",
 *    "$Revision$",
 *    "$Date$",
 *    "$Author$"
 * );</script>
 *
 * @author Stefan Wismer (SWI)
 */
public class GenericConfig {
    /**
     * The parent {@link GenericConfig} which configuration is inherited.
     */
    protected GenericConfig m_parentConfig;
    
    /**
     * The configuration entries that are added or override configurations
     * declared in m_parentConfig.
     */
    protected Map<String, Object> m_map;
    
    /**
     * The default constructor, which creates an empty configuration.
     */
    public GenericConfig() {
        m_map = new HashMap<String, Object>();
    }
    
    /**
     * @param parent    the parent {@link GenericConfig} which configuration
     *                  is inherited
     */
    public void setParent(GenericConfig parent) {
        m_parentConfig = parent;
    }
    
    /**
     * @return    the configuration entries declared in this config (without 
     *            the configuration declared in the parent) 
     */
    public Map<String, Object> getMap() {
        return m_map;
    }
    
    /**
     * @param map    the the configuration entries to set
     */
    public void setMap(Map<String, Object> map) {
        m_map = map;
    }
    
    /**
     * @param map    the configuration entries to add to the current entries
     */
    public void setOverrideMap(Map<String, Object> map) {
        if (m_map != null) {
            m_map.putAll(map);
        } else {
            m_map = map;
        }
    }
    
    /**
     * @param key      the key of the configuration entry to add
     * @param value    the value of the configuration entry to add
     */
    public void add(String key, Object value) {
        m_map.put(key, value);
    }
    
    /**
     * @param key    the key of the configuration entry that should be returned
     * @return       the corresponding value or <code>null</code> if not found
     */
    public Object get(String key) {
        if (m_map.containsKey(key)) {
            return m_map.get(key);
        } else if (m_parentConfig != null) {
            return m_parentConfig.get(key);
        } else {
            return null;
        }
    }


}
