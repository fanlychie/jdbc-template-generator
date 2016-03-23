package org.fanlychie.jdbc.template.exception;

/**
 * ConfigXmlParserCastException
 * 
 * @author fanlychie
 */
public class ConfigXmlParserCastException extends RuntimeException {

	private static final long serialVersionUID = -2129960440385424588L;

	public ConfigXmlParserCastException(String message) {
		super(message);
	}

	public ConfigXmlParserCastException(Throwable e) {
		super(e);
	}

	public ConfigXmlParserCastException(String message, Throwable e) {
		super(message, e);
	}

}