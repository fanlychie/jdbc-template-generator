package org.fanlychie.jdbc.template;

import java.util.Map;

/**
 * 模板接口
 * 
 * @author fanlychie
 */
public interface Template {

	/**
	 * 设置模板文件所需的参数
	 * 
	 * @param params
	 *            参数表
	 */
	void setContextParams(Map<String, Object> params);

	/**
	 * 模板文件输出到的路径
	 * 
	 * @param tableName
	 *            表名称
	 * @return 返回模板文件输出到的路径
	 */
	String getOutputFilePath(String tableName);

	/**
	 * 是否为多文件模式, 多文件模式会为每一个表对应生成一个模板文件
	 * 
	 * @return true | false
	 */
	boolean isMultiFileMode();

	/**
	 * 目标文件已存在时是否强制覆盖文件
	 * 
	 * @return true | false
	 */
	boolean isForceOverride();

}