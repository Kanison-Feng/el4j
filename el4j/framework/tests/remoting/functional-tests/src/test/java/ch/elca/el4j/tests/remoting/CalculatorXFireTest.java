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

// Checkstyle: EmptyBlock off
// Checkstyle: MagicNumber off

package ch.elca.el4j.tests.remoting;

import java.util.Arrays;

import org.springframework.context.ApplicationContext;

import ch.elca.el4j.core.context.ModuleApplicationContext;
import ch.elca.el4j.tests.remoting.service.Calculator;
import ch.elca.el4j.tests.remoting.service.CalculatorException;
import ch.elca.el4j.tests.remoting.service.CalculatorValueObject;

import junit.framework.TestCase;

/**
 * This class is a test for the calculator.
 * It uses the XFire Protocol with its default binding
 *
 * <script type="text/javascript">printFileStatus
 *   ("$URL$",
 *    "$Revision$",
 *    "$Date$",
 *    "$Author$"
 * );</script>
 *
 * @author Rashid Waraich (RWA)
 * @author Philippe Jacot (PJA)
 */
public class CalculatorXFireTest extends TestCase {
    /**
     * Is the delta to doubles can have to be equal.
     */
    private static final double DOUBLE_TOLERANCE = 0.000000001;
    
    /**
     * Instance of the calculator proxy.
     */
    private Calculator m_calc;

    /**
     * {@inheritDoc}
     */
    public void setUp() {
        ApplicationContext appContext 
            = new ModuleApplicationContext(
                new String[] {"classpath*:mandatory/*.xml",
                    "scenarios/client/remotingtests-xfire-client-config.xml"}, 
                    false);
        setCalc((Calculator) appContext.getBean("calculator"));
        
        // TODO: Control me regularly
        // Remove the proxy manually, until 
        // http://jira.codehaus.org/browse/XFIRE-401 really is resolved
        System.clearProperty("http.proxyHost");
    }

    /**
     * This test tests the area calulation method.
     */
    public void testAreaCalculation() {
        final double VALUE_A = 2.3;
        final double VALUE_B = 5.7;
        final double FAULT_DELTA = 0.00000001;
        double result = getCalc().getArea(VALUE_A, VALUE_B);
        assertEquals("The area is not correctly calculated.", result, 
            VALUE_A * VALUE_B, FAULT_DELTA);
    }
    
    /**
     * This test tests the exception handling.
     */
    public void testExceptionBehaviour() {
        try {
            getCalc().throwMeAnException();
            fail("No exception was thrown.");
        } catch (CalculatorException e) {
            
        }
    }
    
    /**
     * This test tests the counting of uppercase letters.
     */
    public void testNumberOfUppercaseCharacters() {
        String message = "Hans M�ller likes to pay with Euro.";
        int numberOfUppercaseLetters = 3;
        int result = getCalc().countNumberOfUppercaseLetters(message);
        assertEquals("The number of uppercase letter was not " 
            + "counted correctly.", result, numberOfUppercaseLetters);
    }
    
    /**
     * This test is used to test if a value object will be serialized and
     * deserialized correctly.
     */
    public void testEchoOfValueObject() {
        final int MY_INT = 449312154;
        final long MY_LONG = 3121846575454654L;
        final double MY_DOUBLE = 6994.641368469;
        final String MY_STRING 
            = "I can not find any �, �, �, �, � or � character on my keyboard.";
        final byte[] MY_BYTE_ARRAY = MY_STRING.getBytes();
        
        CalculatorValueObject o = new CalculatorValueObject();
        o.setMyInt(MY_INT);
        o.setMyLong(MY_LONG);
        o.setMyDouble(MY_DOUBLE);
        o.setMyString(MY_STRING);
        o.setMyByteArray(MY_BYTE_ARRAY);
        
        CalculatorValueObject echo = getCalc().echoValueObject(o);
        assertEquals("Int values are not equals.", 
            o.getMyInt(), echo.getMyInt());
        assertEquals("Long values are not equals.", 
            o.getMyLong(), echo.getMyLong());
        assertEquals("Double values are not equals.", 
            o.getMyDouble(), echo.getMyDouble(), DOUBLE_TOLERANCE);
        assertEquals("Strings are not equals.",
            o.getMyString(), echo.getMyString());
        assertTrue("Byte arrays are not equals.",
            Arrays.equals(o.getMyByteArray(), echo.getMyByteArray()));
    }
    
    /**
     * Get the calculator to use.
     * @return Calculator to use
     */
    public Calculator getCalc() {
        return m_calc;
    }
    
    /**
     * Set the calculator to use.
     * @param calc The calculator to use
     */
    public void setCalc(Calculator calc) {
        m_calc = calc;
    }
}
