## 使用帮助

**1. 获取类库**

从托管仓库中获取最新依赖包，[查看如何获取依赖包](https://github.com/fanlychie/maven-repo)。

**2. 获取类库**

在项目类路径下新建文件 **jdbc-template-generator.xml**。


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
        约定 ：模板文件名为 Domain.vm, 则模板类名称须为 Domain.java 或 DomainTemplate.java。
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
