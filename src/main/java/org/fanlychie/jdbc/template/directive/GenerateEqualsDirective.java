package org.fanlychie.jdbc.template.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.fanlychie.jdbc.template.core.InputStreamEngine;

/**
 * 生成 equals 方法指令, 按 Java 约定, 重写 equals 时, 必须重写 hashCode。
 * 
 * 指令使用：#equals 或 #equals(字符串参数) 或 #equals([字符串参数1, 字符串参数2...])
 * 
 * @author fanlychie
 */
public class GenerateEqualsDirective extends Directive {

	@Override
	public String getName() {
		return "equals";
	}

	@Override
	public int getType() {
		return LINE;
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer,
			Node node) throws IOException, ResourceNotFoundException,
			ParseErrorException, MethodInvocationException {
		Object arg = null;
		try {
			arg = node.jjtGetChild(0).value(context);
			// #equals 没有参数时此处会抛 NullPointerException, 忽略该异常
		} catch (NullPointerException e) {}
		// 参数不为 null 时, 根据传入的参数生成; 否则根据所有的列字段生成
		if (arg != null) {
			Object param = null;
			if (arg instanceof String) {
				List<Object> list = new ArrayList<Object>();
				list.add(arg);
				param = list;
			} else if (arg instanceof List) {
				param = arg;
			} else {
				throw new IllegalArgumentException(
						"#equals 如果设置参数, 则参数必须是字符串或数组类型");
			}
			context.put("_params", param);
		}
		InputStreamEngine.evaluate(context, writer,
				"org/fanlychie/jdbc/template/directive/content/equals.vm");
		return false;
	}

}