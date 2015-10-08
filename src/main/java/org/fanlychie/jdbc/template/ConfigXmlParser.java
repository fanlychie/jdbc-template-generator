package org.fanlychie.jdbc.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.fanlychie.jdbc.template.exception.ConfigXmlParserCastException;
import org.fanlychie.jdbc.template.schema.DataSource;
import org.fanlychie.jdbc.template.schema.Scanner;
import org.fanlychie.jdbc.template.schema.Schema;

/**
 * XML 配置文件解析器
 * 
 * @author fanlychie
 */
public class ConfigXmlParser {
	
	private Schema schema;

	private Scanner scanner;

	private Properties props;

	private DataSource dataSource;

	private static final String CONF_XML_FILE = "classpath:jdbc-template-generator.xml";

	private ConfigXmlParser() {

	}

	/**
	 * 解析配置文件
	 * 
	 * @return ConfigXmlParser
	 */
	public static ConfigXmlParser parse() {
		ConfigXmlParser configXmlParser = new ConfigXmlParser();
		InputStream inputStream = configXmlParser.getInputStream(CONF_XML_FILE);
		Document document = null;
		try {
			document = new SAXReader().read(inputStream);
		} catch (DocumentException e) {
			throw new ConfigXmlParserCastException(e);
		}
		try {
			configXmlParser.parseProperties(document);
		} catch (IOException e) {
			throw new ConfigXmlParserCastException(e);
		}
		configXmlParser.parseDataSource(document);
		configXmlParser.parseSchema(document);
		configXmlParser.parseScanner(document);
		return configXmlParser;
	}

	/**
	 * 解析 properties 节点
	 * 
	 * @param document
	 *            Document
	 * @throws IOException
	 */
	private void parseProperties(Document document) throws IOException {
		// 解析 <properties location="xxx"> 节点
		Iterator<?> nodes = document.selectNodes("//properties[@location]")
				.iterator();
		props = new Properties();
		// 迭代节点
		while (nodes.hasNext()) {
			Element e = (Element) nodes.next();
			props.load(getInputStream(getAttribute(e, "location")));
		}
		// 解析 <properties> 节点的 <property> 子节点
		nodes = document.selectNodes("//properties/property").iterator();
		// 迭代节点
		while (nodes.hasNext()) {
			Element e = (Element) nodes.next();
			props.put(getAttribute(e, "name"), getAttribute(e, "value"));
		}
	}

	/**
	 * 解析 datasource 节点
	 * 
	 * @param document
	 *            Document
	 */
	private void parseDataSource(Document document) {
		// 解析 <datasource> 节点下的所有 <property> 子节点
		Iterator<?> nodes = document.selectNodes("//datasource/property")
				.iterator();
		dataSource = new DataSource();
		// 迭代节点
		while (nodes.hasNext()) {
			Element e = (Element) nodes.next();
			String name = getAttribute(e, "name");
			String value = parsePlaceholder(getAttribute(e, "value"));
			if ("url".equals(name)) {
				dataSource.setUrl(value);
			} else if ("username".equals(name)) {
				dataSource.setUsername(value);
			} else if ("password".equals(name)) {
				dataSource.setPassword(value);
			} else if ("driverClass".equals(name)) {
				dataSource.setDriverClass(value);
			} else {
				throw new ConfigXmlParserCastException(
						"unknown property name=\"" + name + "\"");
			}
		}
	}

	/**
	 * 解析 schema 节点
	 * 
	 * @param document
	 *            Document
	 */
	private void parseSchema(Document document) {
		// 解析 <schema> 节点下的所有 <property> 子节点
		Iterator<?> nodes = document.selectNodes("//schema/property")
				.iterator();
		schema = new Schema();
		// 迭代节点
		while (nodes.hasNext()) {
			Element e = (Element) nodes.next();
			String name = getAttribute(e, "name");
			if ("tableNameSeparator".equals(name)) {
				schema.setTableNameSeparator(getAttribute(e, "value"));
			} else if ("columnNameSeparator".equals(name)) {
				schema.setColumnNameSeparator(getAttribute(e, "value"));
			} else if ("tableIgnorePattern".equals(name)) {
				schema.setTableIgnorePattern(replacePlaceholder(getAttribute(e,
						"value")));
			} else if ("tableIgnoreEscapes".equals(name)) {
				schema.setTableIgnoreEscapes(parseValues(e));
			} else if ("columnIgnoreNames".equals(name)) {
				schema.setColumnIgnoreNames(parseValues(e));
			} else if ("dataTypes".equals(name)) {
				schema.setDataTypes(parseMapValues(e));
			} else {
				throw new ConfigXmlParserCastException(
						"unknown property name=\"" + name + "\"");
			}
		}
	}
	
	/**
	 * 解析 scanner 节点
	 * 
	 * @param document
	 *            Document
	 */
	private void parseScanner(Document document) {
		// 解析 <scanner> 节点
		Iterator<?> nodes = document.selectNodes("//scanner/property")
				.iterator();
		scanner = new Scanner();
		// 迭代节点
		while (nodes.hasNext()) {
			Element e = (Element) nodes.next();
			String name = getAttribute(e, "name");
			String value = getAttribute(e, "value");
			if ("templateClasses".equals(name)) {
				scanner.setTemplateClasses(scanFiles(value));
			} else if ("templateVmsPath".equals(name)) {
				scanner.setTemplateVmsPath("/" + value.replace(".", "/") + "/");
			}
		}
	}

	/**
	 * 解析 value 节点
	 * 
	 * @param element
	 *            Element
	 * @return Set
	 */
	private Set<String> parseValues(Element element) {
		Set<String> set = new HashSet<String>();
		Iterator<?> nodes = element.selectNodes("./value").iterator();
		// 迭代节点
		while (nodes.hasNext()) {
			Element e = (Element) nodes.next();
			set.add(e.getStringValue());
		}
		return set;
	}

	/**
	 * 解析 value 节点
	 * 
	 * @param element
	 *            Element
	 * @return Map
	 */
	private Map<String, String> parseMapValues(Element element) {
		Map<String, String> map = new HashMap<String, String>();
		Iterator<?> nodes = element.selectNodes("./value").iterator();
		// 迭代节点
		while (nodes.hasNext()) {
			Element e = (Element) nodes.next();
			map.put(getAttribute(e, "jdbcType").toLowerCase(), getAttribute(e, "javaType"));
		}
		return map;
	}

	/**
	 * 解析 ${} 占位符
	 * 
	 * @param value
	 * @return
	 */
	private String parsePlaceholder(String value) {
		if (value.matches("\\$\\{.*?\\}")) {
			// 处理 ${} 占位符
			Pattern pattern = Pattern.compile("[a-z_A-Z.1-9]+");
			Matcher matcher = pattern.matcher(value);
			if (matcher.find()) {
				String placeholder = matcher.group();
				String placeholderValue = props.getProperty(placeholder);
				if (placeholderValue == null) {
					throw new ConfigXmlParserCastException(
							"can not found property " + value);
				}
				return placeholderValue;
			} else {
				throw new ConfigXmlParserCastException(
						"can not parse property " + value);
			}
		}
		return value;
	}

	/**
	 * 替换处理星配符
	 * 
	 * @param value
	 * @return
	 */
	private String replacePlaceholder(String value) {
		if (value.contains("*")) {
			value = value.replaceAll("\\*", ".*?");
		}
		return value;
	}

	/**
	 * 获取节点属性的值
	 * 
	 * @param e
	 *            节点对象
	 * @param name
	 *            节点属性
	 * @return
	 */
	private String getAttribute(Element e, String name) {
		try {
			return e.attribute(name).getStringValue();
		} catch (NullPointerException ex) {
			throw new ConfigXmlParserCastException("can not found attribute \""
					+ name + "\" in <" + e.getName() + ">");
		}
	}
	
	/**
	 * 扫描类文件
	 * 
	 * @param packageName
	 *            包名
	 * @return Set
	 */
	private Set<String> scanFiles(String packageName) {
		String packagePath = packageName.replace(".", "/");
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource(packagePath);
		if (url == null) {
			throw new ConfigXmlParserCastException("can not found path : " + packagePath);
		}
		File packageDir = new File(url.getPath());
		if (!packageDir.isDirectory()) {
			throw new ConfigXmlParserCastException(packagePath + " is not a directory");
		}
		Set<String> fileSet = new HashSet<String>();
		File[] files = packageDir.listFiles();
		if (files == null || files.length == 0) {
			throw new ConfigXmlParserCastException("the directory " + packagePath + " is empty");
		}
		for (File file : files) {
			if (!file.isDirectory()) {
				String fileName = file.getName();
				fileName = fileName.substring(0, fileName.lastIndexOf("."));
				fileSet.add(packageName + "." + fileName);
			}
		}
		return fileSet;
	}

	/**
	 * 获取输入流
	 * 
	 * @param filename
	 *            文件名称
	 * @return InputStream
	 */
	private InputStream getInputStream(String filename) {
		InputStream inputStream = null;
		if (filename.contains("classpath:")) {
			filename = filename.substring("classpath:".length());
			try {
				inputStream = ClassLoader.getSystemResourceAsStream(filename);
			} catch (Throwable e) {
				throw new ConfigXmlParserCastException("can not found "
						+ filename + " file in the classpath");
			}
		} else {
			try {
				return new FileInputStream(new File(filename));
			} catch (FileNotFoundException e) {
				throw new ConfigXmlParserCastException("can not found "
						+ filename + " file");
			}
		}
		return inputStream;
	}

	/**
	 * 获取数据库模型
	 * 
	 * @return Schema
	 */
	public Schema getSchema() {
		return schema;
	}

	/**
	 * 获取扫描器模型
	 * 
	 * @return Scanner
	 */
	public Scanner getScanner() {
		return scanner;
	}

	/**
	 * 获取属性对象
	 * 
	 * @return Properties
	 */
	public Properties getProps() {
		return props;
	}

	/**
	 * 获取数据源模型
	 * 
	 * @return DataSource
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

}