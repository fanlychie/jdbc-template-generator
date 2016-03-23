package org.fanlychie.jdbc.template.schema;

import java.util.List;
import java.util.Map;

/**
 * 表模型
 * 
 * @author fanlychie
 */
public class Table implements Comparable<Table> {
	
	// 表主键
	private String pk;
	
	// 源主键
	private String opk;

	// 表名称
	private String name;
	
	// 源名称
	private String origin;
	
	// 表的列集
	private List<Column> columns;
	
	// 表的列名对照表, <列名称, 源列名称>
	Map<String, String> columnOrigin;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getOpk() {
		return opk;
	}

	public void setOpk(String opk) {
		this.opk = opk;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrigin() {
		return origin;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	@Override
	public int compareTo(Table o) {
		return this.name.compareTo(o.name);
	}

	@Override
	public String toString() {
		return "Table [ pk = " + pk + ", name = " + name + ", origin = "
				+ origin + ", columns = " + columns + " ] ";
	}
	
}