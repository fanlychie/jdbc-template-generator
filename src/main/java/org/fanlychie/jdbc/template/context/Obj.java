package org.fanlychie.jdbc.template.context;

public class Obj {

	public boolean isNull(Object obj) {
		return obj == null;
	}

	public boolean isNotNull(Object obj) {
		return obj != null;
	}
	
}