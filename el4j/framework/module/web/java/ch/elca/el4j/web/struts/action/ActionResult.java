/*
 * EL4J, the Enterprise Library for Java, complementing Spring http://el4j.sf.net
 * Copyright (C) 2005 by ELCA Informatique SA, Av. de la Harpe 22-24,
 * 1000 Lausanne, Switzerland, http://www.elca.ch
 *
 * This program is published under the GNU General Public License (GPL) license.
 * http://www.gnu.org/licenses/gpl.txt
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * For alternative licensing, please contact info@elca.ch 
 */

package ch.elca.el4j.web.struts.action;

import ch.elca.el4j.util.codingsupport.AbstractDefaultEnum;

/**
 * Enumeration for action results.
 *
 * <script type="text/javascript">printFileStatus
 *   ("$Source$",
 *    "$Revision$",
 *    "$Date$",
 *    "$Author$"
 * );</script>
 *
 * @author Martin Zeltner (MZE)
 */
public final class ActionResult extends AbstractDefaultEnum {
    /**
     * Result success.
     */
    public static final ActionResult SUCCESS 
        = new ActionResult("success", 1);

    /**
     * Result failure.
     */
    public static final ActionResult FAILURE 
        = new ActionResult("failure", 2);

    /**
     * Result invalid.
     */
    public static final ActionResult INVALID 
        = new ActionResult("invalid", 3);

    /**
     * Private constructor.
     * 
     * @param name Is the name of the result.
     * @param code Is the result code.
     */
    private ActionResult(String name, int code) {
        super(name, code);
    }

    /**
     * @param name Is the name of the result.
     * @return Returns the result with the given name.
     */
    public static ActionResult get(String name) {
        return (ActionResult) AbstractDefaultEnum.get(ActionResult.class, name);
    }
}
