package org.fanlychie.jdbc.template.schema;

import java.util.Set;

/**
 * 扫描器模型
 * 
 * @author fanlychie
 */
public class Scanner {

	// 模板类文件集
	private Set<String> templateClasses;
	
	// 模板文件路径
	private String templateVmsPath;

	public Set<String> getTemplateClasses() {
		return templateClasses;
	}

	public void setTemplateClasses(Set<String> templateClasses) {
		this.templateClasses = templateClasses;
	}

	public String getTemplateVmsPath() {
		return templateVmsPath;
	}

	public void setTemplateVmsPath(String templateVmsPath) {
		this.templateVmsPath = templateVmsPath;
	}

	@Override
	public String toString() {
		return "Scanner [ templateClasses = " + templateClasses
				+ ", templateVmsPath = " + templateVmsPath + " ]";
	}
	
}