package org.fanlychie.jdbc.template.context;

public class Str {

	public boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public boolean isNotEmpty(String str) {
		return str != null && str.length() > 0;
	}
	
	public String toCapitalize(String str) {
		if (isNotEmpty(str)) {
			char[] ch = str.toCharArray();
			// 首字母大写
			ch[0] = Character.toUpperCase(ch[0]);
			return new String(ch);
		}
		return null;
	}
	
	public String getOrIs(String type) {
		return type.equalsIgnoreCase("boolean") ? "is" : "get";
	}
	
}