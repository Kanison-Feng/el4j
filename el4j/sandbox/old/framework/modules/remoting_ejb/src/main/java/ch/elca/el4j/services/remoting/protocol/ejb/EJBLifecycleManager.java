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

package ch.elca.el4j.services.remoting.protocol.ejb;

import javax.ejb.EJBObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class allows to control the life cycle of an EJB Object.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @author Andreas Bur (ABU)
 */
public final class EJBLifecycleManager {

    /** The static logger. */
    private static Log s_logger = LogFactory.getLog(EJBLifecycleManager.class);
    
    /**
     * Hides default constructor.
     */
    private EJBLifecycleManager() { }
    
    /**
     * Calls <code>remove</code> on the EJB container.
     * 
     * @param springBean
     *      The bean to remove that has been enriched to a EJBObject.
     * 
     * @return Returns <code>true</code> if the remove call on the EJBObject
     *      passed successfully.
     */
    public static boolean removeEjb(Object springBean) {
        try {
            ((EJBObject) springBean).remove();
            return true;
            
        } catch (Exception e) {
            s_logger.warn("Error while calling 'remove' on the EJBObject "
                    + springBean, e);
            return false;
        }
    }
}
