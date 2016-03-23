package org.fanlychie.jdbc.template.schema;

import java.sql.Connection;
import java.sql.DriverManager;

import org.fanlychie.jdbc.template.exception.RuntimeCastException;

/**
 * 数据库连接
 * 
 * @author fanlychie
 */
public class DBConnection {

	public static Connection getConnection(DataSource dataSource) {
		try {
			Class.forName(dataSource.getDriverClass());
			return DriverManager.getConnection(dataSource.getUrl(),
					dataSource.getUsername(), dataSource.getPassword());
		} catch (Throwable e) {
			throw new RuntimeCastException(e);
		}
	}

}