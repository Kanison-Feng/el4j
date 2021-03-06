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
package ch.elca.el4j.services.search.criterias;

import org.springframework.util.StringUtils;

import ch.elca.el4j.util.codingsupport.Reject;

/**
 * Criteria to compare fields to values.
 *
 * @svnLink $Revision$;$Date$;$Author$;$URL$
 *
 * @author Martin Zeltner (MZE)
 */
public class ComparisonCriteria extends AbstractCriteria {
	/**
	 * Is the type prefix.
	 */
	public static final String TYPE_PREFIX = "comparison";
	
	/**
	 * Is the compare operator.
	 */
	private String m_operator;
	
	/**
	 * Is the type of this criteria.
	 */
	private String m_type;
	
	/**
	 * Default constructor for remoting protocols like hessian added.
	 */
	protected ComparisonCriteria() { }
	
	/**
	 * Constructor to create new special comparison Criteria objects.
	 *
	 * @param field Is the field the criteria is made for.
	 * @param value Is the value of this criteria.
	 * @param operator Is the compare operator.
	 * @param typeSuffix is the type suffix of this criteria.
	 *    (we use the unqualified class name of the basic Java types (
	 *     Boolean,Integer, ...))
	 */
	public ComparisonCriteria(String field, Object value, String operator,
		String typeSuffix) {
		super(field, value);
		Reject.ifEmpty(field);
		m_type = TYPE_PREFIX + StringUtils.capitalize(typeSuffix);
		m_operator = operator;
	}

	/**
	 * @param field Is the field the criteria is made for.
	 * @param value Is the value of this criteria.
	 * @return Returns an equals comparison criteria.
	 */
	public static ComparisonCriteria equals(String field, boolean value) {
		return new ComparisonCriteria(
			field, Boolean.valueOf(value), "=", "Boolean");
	}

	/**
	 * @param field Is the field the criteria is made for.
	 * @param value Is the value of this criteria.
	 * @return Returns an equals comparison criteria.
	 */
	public static ComparisonCriteria equals(String field, int value) {
		return new ComparisonCriteria(
			field, Integer.valueOf(value), "=", "Integer");
	}

	/**
	 * @param field Is the field the criteria is made for.
	 * @param value Is the value of this criteria.
	 * @return Returns an equals comparison criteria.
	 */
	public static ComparisonCriteria equals(String field, long value) {
		return new ComparisonCriteria(
			field, Long.valueOf(value), "=", "Long");
	}

	/**
	 * @param field Is the field the criteria is made for.
	 * @param value Is the value of this criteria.
	 * @return Returns an equals comparison criteria.
	 */
	public static ComparisonCriteria equals(String field, short value) {
		return new ComparisonCriteria(
			field, Short.valueOf(value), "=", "Short");
	}

	/**
	 * @param field Is the field the criteria is made for.
	 * @param value Is the value of this criteria.
	 * @return Returns an equals comparison criteria.
	 */
	public static ComparisonCriteria equals(String field, byte value) {
		return new ComparisonCriteria(
			field, Byte.valueOf(value), "=", "Byte");
	}
	
	/**
	 * @param field Is the field the criteria is made for.
	 * @param value Is the value of this criteria.
	 * @return Returns an equals comparison criteria.
	 */
	public static ComparisonCriteria equals(String field, double value) {
		return new ComparisonCriteria(
			field, new Double(value), "=", "Double");
	}

	/**
	 * @param field Is the field the criteria is made for.
	 * @param value Is the value of this criteria.
	 * @return Returns an equals comparison criteria.
	 */
	public static ComparisonCriteria equals(String field, Enum<?> value) {
		return new ComparisonCriteria(
			field, value, "=", "Enum");
	}
	
	/**
	 * @param field Is the field the criteria is made for.
	 * @param value Is the value of this criteria.
	 * @return Returns an equals comparison criteria.
	 */
	public static ComparisonCriteria equals(String field, float value) {
		return new ComparisonCriteria(
			field, new Float(value), "=", "Float");
	}

	/**
	 * @param field Is the field the criteria is made for.
	 * @param value Is the value of this criteria.
	 * @return Returns an equals comparison criteria.
	 */
	public static ComparisonCriteria equals(String field, String value) {
		return new ComparisonCriteria(
			field, value, "=", "String");
	}
	
	/**
	 * @param field Is the field the criteria is made for.
	 * @param value Is the value of this criteria.
	 * @return Returns an equals comparison criteria.
	 */
	public static ComparisonCriteria equalsObject(String field, Object value) {
		return new ComparisonCriteria(
			field, value, "=", "Object");
	}
	
	/**
	 * @return Returns the compare operator.
	 */
	public String getOperator() {
		return m_operator;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getType() {
		return m_type;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return getSqlWhereCondition();
	}

	/**
	 * {@inheritDoc}
	 */
	public String getSqlWhereCondition() {
		return " ( "+getField()+" "+getOperator()+" "+getValue()+ " ) ";
	}
	
}
