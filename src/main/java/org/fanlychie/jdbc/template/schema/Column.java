package org.fanlychie.jdbc.template.schema;

/**
 * 列模型
 * 
 * @author fanlychie
 */
public class Column implements Comparable<Column> {

	// 所属的表
	private Table table;
	
	// 列名称
	private String name;
	
	// 列类型
	private String type;
	
	// 列注释
	private String remark;
	
	public Column(Table table) {
		this.table = table;
	}
	
	public boolean isPrimaryKey() {
		return getOrigin().equals(table.getPk());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrigin() {
		return table.columnOrigin.get(name);
	}

	@Override
	public int compareTo(Column o) {
		if (o.isPrimaryKey()) {
			return 1;
		}
		return this.name.compareTo(o.name);
	}

	@Override
	public String toString() {
		return "Column [ name = " + name + ", type = " + type + ", remark = "
				+ remark + " ]";
	}
	
}