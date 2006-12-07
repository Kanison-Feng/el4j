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
package ch.elca.el4j.demos.statistics.detailed.jmx;

import java.util.Iterator;
import java.util.List;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import ch.elca.el4j.services.monitoring.jmx.JmxHtmlFormatter;
import ch.elca.el4j.services.statistics.detailed.MeasureItem;
import ch.elca.el4j.services.statistics.detailed.processing.DataRepository;
import ch.elca.el4j.services.statistics.detailed.processing.StatisticsOutputter;
import ch.elca.el4j.util.codingsupport.Reject;

/**
 * This class publishes the detailed statistics through JMX. 
 * 
 * <script type="text/javascript">printFileStatus
 *   ("$URL$",
 *    "$Revision$",
 *    "$Date$",
 *    "$Author$"
 * );</script>
 * 
 * @author Rashid Waraich (RWA)
 * @author David Stefan (DST)
 */
public class DetailedStatisticsReporter implements
    DetailedStatisticsReporterMBean, InitializingBean, DisposableBean {

    /** The address that points to this class. */
    public static final String NAME 
        = "Performance:key=detailedStatisticsReporter";

    /** The MBean server where this class is registered in. */
    private MBeanServer m_server;

    /** The DataAssembler from which supplies the data for the statistics. */
    private DataRepository m_dataRepository;

    /**
     * @return Retruns the MBean server instance where this instance is
     *         registered in.
     */
    public MBeanServer getServer() {
        return m_server;
    }

    /**
     * Sets the MBean server where this instance has to register.
     * 
     * @param beanServer
     *            The MBean server to set.
     */
    public void setServer(MBeanServer beanServer) {
        m_server = beanServer;
    }

    /**
     * @return Retruns the DataAssembler which supplies the data for the
     *         statistics.
     */
    public DataRepository getDataRepository() {
        return m_dataRepository;
    }

    /**
     * Sets the DataAssembler.
     * 
     * @param dataRepository
     *            The DataRepository to set
     */
    public void setDataRepository(DataRepository dataRepository) {
        m_dataRepository = dataRepository;
    }

    /**
     * {@inheritDoc}
     */
    public void afterPropertiesSet() throws Exception {
        if (m_server == null) {
            throw new IllegalStateException("m_beanServer has not been set!");
        }

        m_server.registerMBean(this, new ObjectName(NAME));
    }

    /**
     * {@inheritDoc}
     */
    public void destroy() throws Exception {
        if (m_server != null) {
            m_server.unregisterMBean(new ObjectName(NAME));
        }
    }

    /**
     * {@inheritDoc}
     */
    public String showMeasureIDTable() {
        String result = "";
        String[][] table;
        List measureIds = m_dataRepository.getFirstMeasureItems();
        Iterator iter = measureIds.iterator();
        int noOfRowsInTable = 0;
        MeasureItem tempItem;

        while (iter.hasNext()) {
            noOfRowsInTable++;
            iter.next();
        }
        iter = measureIds.iterator();
        // Checkstyle: MagicNumber off
        table = new String[noOfRowsInTable + 1][3];
        // Checkstyle: MagicNumber on

        table[0][0] = "MeasureID";
        table[0][1] = "Duration in [ms]";
        table[0][2] = "Invocation Start";

        for (int i = 1; i < noOfRowsInTable + 1; i++) {
            tempItem = (MeasureItem) iter.next();
            table[i][0] = tempItem.getID().getFormattedString();
            table[i][1] = Long.toString(tempItem.getDuration());
            table[i][2] = tempItem.getEjbName() + "."
                + tempItem.getMethodName();
        }

        result = JmxHtmlFormatter.getHtmlTable(table);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public String getSvgString(String measureId) {
        Reject.ifNull(measureId);
        StatisticsOutputter sv = new StatisticsOutputter(m_dataRepository
            .getAllMeasureItems());
        return sv.getHTMLCompatibleSVGGraph(measureId);
    }

    /**
     * 
     * {@inheritDoc}
     */
    public void createSVGFile(String filename, String measureId) {
        Reject.ifNull(filename);
        Reject.ifNull(measureId);
        StatisticsOutputter sv = new StatisticsOutputter(m_dataRepository
            .getAllMeasureItems());
        sv.createSVGFile(filename, measureId); 
    }

    /**
     * 
     * {@inheritDoc}
     */
    public void createCSVFile(String filename, String measureId) {
        Reject.ifNull(filename);
        Reject.ifNull(measureId);
        StatisticsOutputter sv = new StatisticsOutputter(m_dataRepository
            .getAllMeasureItems());
        sv.createCVSFile(filename, measureId);
    }
}