package org.fanlychie.jdbc.template.schema;

/**
 * 数据源模型
 * 
 * @author fanlychie
 */
public class DataSource {

	// 链接数据库的地址
	private String url;

	// 链接数据库的账户
	private String username;

	// 链接数据库的密码
	private String password;

	// 链接数据库的驱动
	private String driverClass;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	@Override
	public String toString() {
		return "DataSource [ url = " + url + ", username = " + username
				+ ", password = " + password + ", driverClass = " + driverClass
				+ " ]";
	}

}