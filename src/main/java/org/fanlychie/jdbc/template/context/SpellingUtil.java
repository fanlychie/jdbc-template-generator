package org.fanlychie.jdbc.template.context;

/**
 * 拼写工具类
 * 
 * @author fanlychie
 */
public class SpellingUtil {

	/**
	 * 首字母大写
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String capitalize(String str) {
		char[] ch = str.toCharArray();
		ch[0] = Character.toUpperCase(ch[0]);
		return new String(ch);
	}

	/**
	 * 首字母大写, 在遇到指定的分隔符时, 去掉分隔符并将分隔符后的第一个字母大写
	 * 
	 * @param str
	 *            字符串
	 * @param separator
	 *            分隔符
	 * @return
	 */
	public static String capitalize(String str, String separator) {
		return capitalize(str, separator, true);
	}

	/**
	 * 在遇到指定的分隔符时, 去掉分隔符并将分隔符后的第一个字母大写
	 * 
	 * @param str
	 *            字符串
	 * @param separator
	 *            分隔符
	 * @param initialUpperCase
	 *            是否将字符串的首字母大写
	 * @return
	 */
	public static String capitalize(String str, String separator, boolean initialUpperCase) {
		if (separator != null && str.contains(separator)) {
			int index = 0;
			String target = "";
			String[] sources = str.split(separator);
			if (!initialUpperCase) {
				index = 1;
				target = sources[0].toLowerCase();
			}
			for (int i = index; i < sources.length; i++) {
				target += capitalize(sources[i]);
			}
			return target;
		} else {
			return initialUpperCase ? capitalize(str) : str.toLowerCase();
		}
	}
	
}