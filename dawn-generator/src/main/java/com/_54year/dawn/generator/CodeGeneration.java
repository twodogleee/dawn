package com._54year.dawn.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class CodeGeneration {


	//main函数
	public static void main(String[] args) {

		AutoGenerator autoGenerator = new AutoGenerator()
			.setGlobalConfig(
				//全局配置
				new GlobalConfig()
					.setOutputDir("E:\\workSpace\\test\\dawn\\dawn-auth\\src\\main\\java")//生成文件输出根目录
					.setOpen(false)//生成完成后不弹出文件框
					.setFileOverride(true)//文件覆盖
					.setActiveRecord(false)// 不需要ActiveRecord特性的请改为false
					.setEnableCache(false)// XML 二级缓存
					.setBaseResultMap(true)// XML ResultMap
					.setBaseColumnList(false)// XML columList
					.setAuthor("Andersen")// 作者
					// 自定义文件命名，注意 %s 会自动填充表实体属性！
					.setControllerName("%sController")
					.setServiceName("%sService")
					.setServiceImplName("%sServiceImpl")
					.setMapperName("%sMapper")
					.setXmlName("%sMapper")
			).setDataSource(
				// 数据源配置
				new DataSourceConfig()
					.setDbType(DbType.MYSQL)  //设置数据库类型
					.setDriverName("com.mysql.cj.jdbc.Driver")
					.setUsername("andersen")
					.setPassword("andersen")
					.setUrl("jdbc:mysql://192.168.1.91:3306/dawn")  //指定数据库
			).setStrategy(
				// 策略配置
				new StrategyConfig()
					// .setCapitalMode(true)// 全局大写命名
					// .setDbColumnUnderline(true)//全局下划线命名
					//.setTablePrefix(new String[]{"tbl_", "mp_"})// 此处可以修改为您的表前缀
					.setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
					.setInclude(new String[]{"dawn_user_role"})// 需要生成的表
				// .setExclude(new String[]{"test"}) // 排除生成的表
				// 自定义实体父类
				// .setSuperEntityClass("com.baomidou.demo.TestEntity")
				// 自定义实体，公共字段
				//.setSuperEntityColumns(new String[]{"test_id"})
				//.setTableFillList(tableFillList)
				// 自定义 mapper 父类
				// .setSuperMapperClass("com.baomidou.demo.TestMapper")
				// 自定义 service 父类
				// .setSuperServiceClass("com.baomidou.demo.TestService")
				// 自定义 service 实现类父类
				// .setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl")
				// 自定义 controller 父类
				// .setSuperControllerClass("com.baomidou.demo.TestController")
				// 【实体】是否生成字段常量（默认 false）
				// public static final String ID = "test_id";
				// .setEntityColumnConstant(true)
				// 【实体】是否为构建者模型（默认 false）
				// public User setName(String name) {this.name = name; return this;}
				// .setEntityBuilderModel(true)
				// 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
				// .setEntityLombokModel(true)
				// Boolean类型字段是否移除is前缀处理
				// .setEntityBooleanColumnRemoveIsPrefix(true)
				// .setRestControllerStyle(true)
				// .setControllerMappingHyphenStyle(true)
			).setPackageInfo(
				new PackageConfig()
					.setParent("com._54year.dawn.auth")
					.setController("controller")
					.setService("service")
					.setServiceImpl("service.impl")
					.setMapper("dao.mapper")
					.setXml("dao.mapper.xml")
					.setEntity("entity")
			);
			//.setTemplate(
				// 关闭默认 xml 生成，调整生成 至 根目录
				//new TemplateConfig().setXml(null)
				// 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
				// 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
				// .setController("...")
				// .setEntity("...")
				// .setMapper("...")
				// .setXml("...")
				// .setService("...")
			 //.setServiceImpl("...")
			//);
		// 执行生成
		autoGenerator.execute();
	}

}
