package org.fanlychie.jdbc.template.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

/**
 * 输入流引擎
 * 
 * @author fanlychie
 */
public class InputStreamEngine {

	private static VelocityEngine engine = new VelocityEngine();

	public static void evaluate(Context context, Writer writer, String pathname)
			throws ParseErrorException, MethodInvocationException,
			ResourceNotFoundException, IOException {
		engine.evaluate(context, writer, "", parseTemplate(pathname));
	}
	
	private static String parseTemplate(String pathname) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				ClassLoader.getSystemResourceAsStream(pathname)));
		StringBuilder builder = new StringBuilder();
		String read;
		while ((read = reader.readLine()) != null) {
			builder.append(read).append("\r\n");
		}
		reader.close();
		return builder.substring(0, builder.length() - 2);
	}

}