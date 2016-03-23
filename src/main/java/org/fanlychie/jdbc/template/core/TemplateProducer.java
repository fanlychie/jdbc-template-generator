package org.fanlychie.jdbc.template.core;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import org.fanlychie.jdbc.template.Template;
import org.fanlychie.jdbc.template.TemplateFileQueue;
import org.fanlychie.jdbc.template.TemplateFileQueue.Entry;
import org.fanlychie.jdbc.template.context.Obj;
import org.fanlychie.jdbc.template.context.Str;
import org.fanlychie.jdbc.template.schema.Table;

/**
 * 模板文件生产者
 * 
 * @author fanlychie
 */
public class TemplateProducer {

	// 模板引擎
	private TemplateEngine engine;
	
	// 模板文件路径
	private String templateVmsPath;
	
	private static final Obj OBJ = new Obj();
	
	private static final Str STR = new Str();

	/**
	 * 构建模板生产者
	 * 
	 * @param templateVmsPath
	 *            模板文件路径
	 */
	public TemplateProducer(String templateVmsPath) {
		this.engine = new TemplateEngine();
		this.templateVmsPath = templateVmsPath;
	}

	/**
	 * 生产模板
	 * 
	 * @param tables
	 *            表集合
	 * @param template
	 *            模板类
	 * @param params
	 *            模板使用到的参数表
	 */
	public void produce(Collection<Table> tables, Template template, Map<String, Object> params) {
		params.put("obj", OBJ);
		params.put("str", STR);
		if (template.isMultiFileMode()) {
			produceMultiTemplateFile(tables, template, params);
		} else {
			produceSingleTemplateFile(tables, template, params);
		}
	}
	
	/**
	 * 生产多模板文件
	 * 
	 * @param tables
	 *            表集合
	 * @param template
	 *            模板类
	 * @param params
	 *            模板使用到的参数表
	 */
	private void produceMultiTemplateFile(Collection<Table> tables, Template template, Map<String, Object> params) {
		for (Table table : tables) {
			String pathname = template.getOutputFilePath(table.getName());
			File file = new File(pathname);
			if (template.isForceOverride() || !file.exists()) {
				File parent = file.getParentFile();
				if (!parent.exists()) {
					parent.mkdirs();
				}
				params.put("table", table);
				params.put("columns", table.getColumns());
				template.setContextParams(params);
				String templateSource = engine.parseTemplate(getTemplatePathname(template), params);
				TemplateFileQueue.push(new Entry(templateSource, file));
			}
		}
	}
	
	/**
	 * 生产单模板文件
	 * 
	 * @param tables
	 *            表集合
	 * @param template
	 *            模板类
	 * @param params
	 *            模板使用到的参数表
	 */
	private void produceSingleTemplateFile(Collection<Table> tables, Template template, Map<String, Object> params) {
		String pathname = template.getOutputFilePath(null);
		File file = new File(pathname);
		if (template.isForceOverride() || !file.exists()) {
			File parent = file.getParentFile();
			if (!parent.exists()) {
				parent.mkdirs();
			}
			params.put("tables", tables);
			template.setContextParams(params);
			String templateSource = engine.parseTemplate(getTemplatePathname(template), params);
			TemplateFileQueue.push(new Entry(templateSource, file));
		}
	}
	
	/**
	 * 获取模板文件路径
	 * 
	 * @param template
	 *            模板类
	 * @return
	 */
	private String getTemplatePathname(Template template) {
		String templateClass = template.getClass().getSimpleName();
		if (templateClass.endsWith("Template")) {
			templateClass = templateClass.substring(0, templateClass.indexOf("Template"));
		}
		return templateVmsPath + templateClass + ".vm";
	}

}