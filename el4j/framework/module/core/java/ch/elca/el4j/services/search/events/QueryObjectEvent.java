/*
 * EL4J, the Extension Library for the J2EE, adds incremental enhancements to
 * the spring framework, http://el4j.sf.net
 * Copyright (C) 2005 by ELCA Informatique SA, Av. de la Harpe 22-24,
 * 1000 Lausanne, Switzerland, http://www.elca.ch
 *
 * EL4J is published under the GNU General Public License (GPL) Version 2.0.
 * http://www.gnu.org/licenses/
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * For alternative licensing, please contact info@elca.ch
 */
package ch.elca.el4j.services.search.events;

import org.springframework.context.ApplicationEvent;

import ch.elca.el4j.services.search.QueryObject;

/**
 * Event for query objects.
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
public class QueryObjectEvent extends ApplicationEvent {
    /**
     * Is the query object of this event.
     */
    private final QueryObject m_queryObject;

    /**
     * Constructor.
     * 
     * @param source Is the place this event has been created.
     * @param queryObject Is the query object of this event.
     */
    public QueryObjectEvent(Object source, QueryObject queryObject) {
        super(source);
        m_queryObject = queryObject;
    }
    
    /**
     * @return Returns the query object of this event.
     */
    public QueryObject getQueryObject() {
        return m_queryObject;
    }
}
