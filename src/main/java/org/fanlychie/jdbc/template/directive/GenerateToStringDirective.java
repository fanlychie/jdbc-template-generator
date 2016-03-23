package org.fanlychie.jdbc.template.directive;

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.fanlychie.jdbc.template.core.InputStreamEngine;

/**
 * 生成 toString 方法的指令。
 * 
 * 指令使用：#tostring
 * 
 * @author fanlychie
 */
public class GenerateToStringDirective extends Directive {

	@Override
	public String getName() {
		return "tostring";
	}

	@Override
	public int getType() {
		return LINE;
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer,
			Node node) throws IOException, ResourceNotFoundException,
			ParseErrorException, MethodInvocationException {
		InputStreamEngine.evaluate(context, writer,
				"org/fanlychie/jdbc/template/directive/content/tostring.vm");
		return false;
	}

}