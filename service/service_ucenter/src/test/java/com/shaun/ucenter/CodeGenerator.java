package com.shaun.ucenter;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import org.junit.Test;

import java.util.Arrays;

public class CodeGenerator {

    @Test
    public void run() {
        FastAutoGenerator.create("jdbc:mysql:///guli?serverTimezone=GMT%2B8", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("Shaun") // 设置作者名称
                            .enableSwagger() // 开启 swagger 模式，生成Swagger注解
                            .fileOverride() // 覆盖已生成文件
                            .disableOpenDir() // 代码生成完成之后不会打开文件夹
                            .dateType(DateType.ONLY_DATE) //数据库时间类型 到 实体类时间类型 对应策略 Only_Date：util类的日期类
                            .outputDir(System.getProperty("user.dir") + "\\src\\main\\java"); // 指定输出到java目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.shaun") // 设置父包名
                            .moduleName("ucenter_service") // 设置父包模块名
                            .entity("entity") // 设置实体类包名称
                            .controller("controller") // 设置controller层包名称
                            .service("service") // 设置service层包名称
                            .mapper("mapper"); // 设置mapper层包名称
                            // .serviceImpl("impl") // 默认就在service包里创建一个impl，如果设置了这个就会在父包下创建一个impl包
                            // .xml("xml"); // 设置xml包名称 // 默认是在mapper包里创建一个xml目录，如果设置了这个就会在父包下创建一个xml目录
                            // .pathInfo(Collections.singletonMap(OutputFile.mapperXml, System.getProperty("user.dir") + "\\src\\main\\resources\\xml")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("ucenter_member")
                            // .addInclude("ucenter_teacher") // 设置需要生成的表名，可以为多个表名称，或者一个List集合
                            .addTablePrefix("ucenter_service_") // 设置过滤表前缀，比如ucenter_service_test，就只会生成为test的类名称
                            .serviceBuilder() // Service的策略配置
                            .formatServiceFileName("%sService") // %s为适配，根据表名进行替换
                            .formatServiceImplFileName("%sServiceImp")
                            // 实体类的策略配置
                            .entityBuilder()
                            .addTableFills()
                            .logicDeleteColumnName("is_deleted") // 设置逻辑删除主键的字段名称
                            .enableTableFieldAnnotation() //添加属性注解
                            .columnNaming(NamingStrategy.underline_to_camel) // 开启下划线转驼峰命名
                            .enableLombok() // // lombok 模型 @Accessors(chain = true) setter链式操作
                            .idType(IdType.ASSIGN_ID) // 设置主键策略为雪花算法
                            .addTableFills(Arrays.asList(new Column("gmt_create", FieldFill.INSERT),
                                    new Column("gmt_modified", FieldFill.INSERT_UPDATE)))
                            // 控制层策略
                            .controllerBuilder()
                            .formatFileName("%sController")
                            .enableRestStyle() // 开启生成@RestController控制器
                            .enableHyphenStyle() // 开启驼峰转连字符
                            // mapper层策略
                            .mapperBuilder()
                            .superClass(BaseMapper.class) //设置父类Mapper
                            .formatMapperFileName("%sMapper")
                            .enableMapperAnnotation() // 开启Mapper类的注解
                            .formatXmlFileName("%sMapper");
                })
                .templateEngine(new VelocityTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}
