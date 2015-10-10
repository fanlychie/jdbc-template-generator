## 使用帮助

**1. 获取类库**

从托管仓库中获取最新依赖包，[查看如何获取依赖包](https://github.com/fanlychie/maven-repo)。

**2. 配置生成器配置文件**

在项目类路径下新建文件 **jdbc-template-generator.xml**


```
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    
    <!-- [可选项] 引入资源文件, 可以通过 ${} 占位符引用资源文件的内容 -->
    <properties location="classpath:jdbc.properties" />
    
    <!-- [可选项] 自定义属性, 可以在模板文件(*.vm)中直接引用 -->
    <properties>
        <property name="basePackage" value="org.fanlychie" />
    </properties>

    <!-- [必须项] 数据源配置 -->
    <datasource>
        <!-- 数据库连接地址 -->
        <property name="url" value="${jdbc.url}" />
        <!-- 连接数据库的驱动 -->
        <property name="driverClass" value="${jdbc.driver}" />
        <!-- 账户名称 -->
        <property name="username" value="${jdbc.username}" />
        <!-- 账户密码 -->
        <property name="password" value="${jdbc.password}" />
    </datasource>
    
    <!-- [可选项] 数据库配置 -->
    <schema>
        <!-- 表名分隔符, 用于驼峰标识拼写 -->
        <property name="tableNameSeparator" value="_" />
        <!-- 列名分隔符, 用于驼峰标识拼写 -->
        <property name="columnNameSeparator" value="_" />
        <!-- 忽略表的模式串, 支持星配符, 被匹配表将被忽略不处理 -->
        <property name="tableIgnorePattern" value="test_*" />
        <!-- 逃逸模式匹配的表, 下列配置的表即使被上述模式匹配也不会被忽略 -->
        <property name="tableIgnoreEscapes">
            <value>test_1</value>
            <value>test_2</value>
        </property>
        <!-- 忽略的列名称, 被忽略的列将不被处理 -->
        <property name="columnIgnoreNames">
            <value>column_1</value>
            <value>column_2</value>
        </property>
        <!-- 数据类型映射处理 -->
        <property name="dataTypes">
            <!-- 数据库中的 int 类型映射成 java 中的 Integer 类型 -->
            <value jdbcType="int" javaType="Integer" />
        </property>
    </schema>
    
    <!-- [必须项] 扫描器 -->
    <scanner>
        <!-- 模板类扫描的包 -->
        <property name="templateClasses" value="org.fanlychie.jdbc.template.test" />
        <!-- 模板文件扫描的包 -->
        <property name="templateVmsPath" value="org.fanlychie.jdbc.template.test.vm" />
    </scanner>
    
    <!-- 
        约定 ：若模板文件名为 Domain.vm, 则模板类名称须为 Domain.java 或 DomainTemplate.java。
        作为模板类文件须实现 org.fanlychie.jdbc.template.Template 接口, 为模板文件提供生成参数的支持。
        模板文件(*.vm)内置变量：
        1．多文件模式, 内置变量 table(表模型)、columns(列集合), 具体参考 org.fanlychie.jdbc.template.schema.Table, org.fanlychie.jdbc.template.schema.Column
        2．单文件模式, 内置变量 tables(表集合), 具体参考 org.fanlychie.jdbc.template.schema.Table
        3．内置变量包括上述配置节点 properties 所有自定义属性值, 以及 obj、str 对象。具体参考 org.fanlychie.jdbc.template.context.Obj/Str
        模板文件(*.vm)内置指令：
        1．#fields 生成表的字段的指令
        2．#getset 生成表的字段的 getters 和 setters 方法的指令
        3．#tostring 生成 toString 方法的指令
        4．#equals 或 #equals(字符串参数) 或 #equals([字符串参数1, 字符串参数2...]) 生成 equals 和 hashCode 方法指令（按 Java 约定）
    -->
    
</configuration>
```

**3. 编写模板文件**

新建模板文件 Domain.vm


```
package ${basePackage}.model;

public class $table.name {
	
#fields #getset #tostring

#equals($table.pk)

}
```

${basePackage} 引用了 **jdbc-template-generator.xml** 中自定义的属性值。

$table.name 使用内置的 table 变量得到表名称作为类名称。

\#fields 使用内置指令生成表的字段域。

\#getset 使用内置指令生成表的字段域的 getters 和 setters 方法。

\#tostring 使用内置指令生成 toString() 方法。

\#equals($table.pk) 使用内置指令根据表的主键字段生成 equals() 和 hashCode() 方法。

**4. 编写模板文件对应的类**

新建模板类文件 DomainTemplate.java


```
package org.fanlychie.jdbc.template.test;

import java.util.Map;
import org.fanlychie.jdbc.template.Template;

public class DomainTemplate implements Template {

	@Override
	public void setContextParams(Map<String, Object> params) {
		
	}

	@Override
	public String getOutputFilePath(String tableName) {
		return "src/main/java/org/fanlychie/model/" + tableName + ".java";
	}

	@Override
	public boolean isMultiFileMode() {
		return true;
	}

	@Override
	public boolean isForceOverride() {
		return true;
	}

}
```

setContextParams 方法可以动态设置一些模板文件需要用到的参数值，如 params.put("key", value);

getOutputFilePath 方法返回该模板文件生成的具体路径。

isMultiFileMode 方法，true 表示多文件模式（多个表生成多个模板类），false 表示单文件模式（多个表生成一个模板类）。单文件和多文件模式的区分在于模板类是生成单个还是多个。

isForceOverride 方法，true 表示文件若已经存在则强制覆盖，false 表示文件若已经存在则不再生成。

**5. 执行模板生成**


```
public static void main(String[] args) {
	TemplateGenerator.generate();
}
```

附 jdbc.properties 文件内容


```
jdbc.username = root
jdbc.password = root
jdbc.driver = com.mysql.jdbc.Driver
jdbc.url = jdbc:mysql://127.0.0.1:3306/mysql
```

使用 mysql 自带的 mysql 数据库测试。其中 func 表生成的类文件效果（内容未做任何处理）


```
package org.fanlychie.model;

public class Func {
	
	/**  */
	private String name;
	
	/**  */
	private String dl;
	
	/**  */
	private Boolean ret;
	
	/**  */
	private String type;
	
 	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDl() {
		return dl;
	}
	
	public void setDl(String dl) {
		this.dl = dl;
	}
	
	public Boolean isRet() {
		return ret;
	}
	
	public void setRet(Boolean ret) {
		this.ret = ret;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
 	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("name = ").append(name).append(", ");
		builder.append("dl = ").append(dl).append(", ");
		builder.append("ret = ").append(ret).append(", ");
		builder.append("type = ").append(type);
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Func other = (Func) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

}
```
