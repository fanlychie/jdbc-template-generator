package org.fanlychie.jdbc.template;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.fanlychie.jdbc.template.core.TemplateConsumer;
import org.fanlychie.jdbc.template.core.TemplateProducer;
import org.fanlychie.jdbc.template.exception.RuntimeCastException;
import org.fanlychie.jdbc.template.schema.DBConnection;
import org.fanlychie.jdbc.template.schema.DataSource;
import org.fanlychie.jdbc.template.schema.Scanner;
import org.fanlychie.jdbc.template.schema.Schema;
import org.fanlychie.jdbc.template.schema.SchemaMetaData;
import org.fanlychie.jdbc.template.schema.Table;

/**
 * 模板文件生成器
 * 
 * @author fanlychie
 */
public class TemplateGenerator {

	// 私有化构造子
	private TemplateGenerator() {
		
	}
	
	/**
	 * 生成模板文件
	 */
	public static void generate() {
		ConfigXmlParser parser = ConfigXmlParser.parse();
		Schema schema = parser.getSchema();
		Scanner scanner = parser.getScanner();
		DataSource dataSource = parser.getDataSource();
		Connection conn = DBConnection.getConnection(dataSource);
		SchemaMetaData schemaMetaData = new SchemaMetaData(schema, conn);
		List<Table> tables = schemaMetaData.getTables();
		TemplateProducer producer = new TemplateProducer(scanner.getTemplateVmsPath());
		for (String templateClass : scanner.getTemplateClasses()) {
			Map<String, Object> params = getPropertiesMap(parser.getProps());
			producer.produce(tables, getTemplate(templateClass), params);
		}
		new TemplateConsumer().consume();
	}
	
	/**
	 * 获取模板类对象
	 * 
	 * @param templateClass
	 *            模板类路径
	 * @return Template
	 */
	private static Template getTemplate(String templateClass) {
		try {
			Class<?> clazz = Class.forName(templateClass);
			return (Template) clazz.newInstance();
		} catch (Throwable e) {
			throw new RuntimeCastException(e);
		}
	}
	
	/**
	 * 获取属性表对象
	 * 
	 * @param props
	 *            Properties
	 * @return Map
	 */
	private static Map<String, Object> getPropertiesMap(Properties props) {
		Map<String, Object> params = new HashMap<String, Object>();
		for (Object key : props.keySet()) {
			params.put(key.toString(), props.get(key));
		}
		return params;
	}
	
}