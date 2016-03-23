package org.fanlychie.jdbc.template.schema;

import java.util.Map;
import java.util.Set;

/**
 * 数据库模型
 * 
 * @author fanlychie
 */
public class Schema {

	// 表名分隔符
	private String tableNameSeparator;

	// 列名分隔符
	private String columnNameSeparator;

	// 忽略模式匹配的表名
	private String tableIgnorePattern;

	// 逃逸模式匹配的表名
	private Set<String> tableIgnoreEscapes;

	// 忽略指定的列名
	private Set<String> columnIgnoreNames;

	// 数据类型映射表
	private Map<String, String> dataTypes;

	public String getTableNameSeparator() {
		return tableNameSeparator;
	}

	public void setTableNameSeparator(String tableNameSeparator) {
		this.tableNameSeparator = tableNameSeparator;
	}

	public String getColumnNameSeparator() {
		return columnNameSeparator;
	}

	public void setColumnNameSeparator(String columnNameSeparator) {
		this.columnNameSeparator = columnNameSeparator;
	}

	public String getTableIgnorePattern() {
		return tableIgnorePattern;
	}

	public void setTableIgnorePattern(String tableIgnorePattern) {
		this.tableIgnorePattern = tableIgnorePattern;
	}

	public Set<String> getTableIgnoreEscapes() {
		return tableIgnoreEscapes;
	}

	public void setTableIgnoreEscapes(Set<String> tableIgnoreEscapes) {
		this.tableIgnoreEscapes = tableIgnoreEscapes;
	}

	public Set<String> getColumnIgnoreNames() {
		return columnIgnoreNames;
	}

	public void setColumnIgnoreNames(Set<String> columnIgnoreNames) {
		this.columnIgnoreNames = columnIgnoreNames;
	}

	public Map<String, String> getDataTypes() {
		return dataTypes;
	}

	public void setDataTypes(Map<String, String> dataTypes) {
		this.dataTypes = dataTypes;
	}

	@Override
	public String toString() {
		return "Schema [ tableNameSeparator = " + tableNameSeparator
				+ ", columnNameSeparator = " + columnNameSeparator
				+ ", tableIgnorePattern = " + tableIgnorePattern
				+ ", tableIgnoreEscapes = " + tableIgnoreEscapes
				+ ", columnIgnoreNames = " + columnIgnoreNames
				+ ", dataTypes = " + dataTypes + " ]";
	}

}