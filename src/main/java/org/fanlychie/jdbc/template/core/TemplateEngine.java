package org.fanlychie.jdbc.template.core;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.fanlychie.jdbc.template.exception.RuntimeCastException;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * 模板引擎
 * 
 * @author fanlychie
 */
public class TemplateEngine {

	/**
	 * 解析模板
	 * 
	 * @param template
	 *            模板文件名称
	 * @param contextParams
	 *            模板上下文所需的参数
	 * @return
	 */
	public String parseTemplate(String template, Map<String, Object> contextParams) {
		Thread thread = Thread.currentThread();
		ClassLoader loader = thread.getContextClassLoader();
		thread.setContextClassLoader(this.getClass().getClassLoader());
		StringWriter writer = new StringWriter();
		try {
			VelocityEngine engine = getVelocityEngine();
			engine.getTemplate(template).merge(buildContext(contextParams), writer);
			return writer.toString();
		} catch (Throwable e) {
			throw new RuntimeCastException(e);
		} finally {
			thread.setContextClassLoader(loader);
			try {
				writer.close();
			} catch (Throwable ex) {}
		}
	}

	/**
	 * 构建模板上下文
	 * 
	 * @param contextParams
	 *            模板上下文所需的参数
	 * @return VelocityContext
	 */
	private VelocityContext buildContext(Map<String, Object> contextParams) {
		VelocityContext context = new VelocityContext();
		for (String param : contextParams.keySet()) {
			context.put(param, contextParams.get(param));
		}
		return context;
	}
	
	/**
	 * 获取 Velocity 引擎对象
	 * 
	 * @return
	 * @throws Throwable
	 */
	private VelocityEngine getVelocityEngine() throws Throwable {
		Properties prop = new Properties();
		prop.load(ClassLoader.getSystemResourceAsStream("velocity.properites"));
		prop.put("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		prop.put(RuntimeConstants.RESOURCE_LOADER, "classpath");
		VelocityEngine engine = new VelocityEngine();
		engine.init(prop);
		return engine;
	}
	
}