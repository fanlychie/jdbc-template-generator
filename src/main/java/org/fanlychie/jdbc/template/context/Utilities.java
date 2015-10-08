package org.fanlychie.jdbc.template.context;

/**
 * 工具类
 * 
 * @author fanlychie
 */
public class Utilities {

	private static final String IS = "is";
	
	private static final String GET = "get";
	
	private static final String SET = "set";
	
	private static final String BOOLEAN = "boolean";

	public static String set(String name) {
		return SET + SpellingUtil.capitalize(name);
	}

	public static String get(String type, String name) {
		String prefix = type.equalsIgnoreCase(BOOLEAN) ? IS : GET;
		return prefix + SpellingUtil.capitalize(name);
	}

	public static boolean isNull(Object obj) {
		return obj == null;
	}

	public static boolean isNotNull(Object obj) {
		return obj != null;
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

}