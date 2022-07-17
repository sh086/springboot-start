package com.shooter.springboot.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * MyBatis Plus 代码生成器
 */
@Slf4j
@SpringBootTest
public class CodeGeneratorUtils {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    /**
     * 交互式生成
     */
    @Test
    public void codeGeneratorTurnTest() {
        val userDir = System.getProperty("user.dir").replace("\\","/");
        val javaPath = userDir + "/src/main/java";
        val resourcePath = userDir + "/src/main/resources";
        val freemarkerPath = resourcePath + "/templates/freemarker";

        // 1 配置数据源
        FastAutoGenerator.create(createDataSource(url,username,password))
                // 2 全局配置
                .globalConfig((scanner, builder) ->
                        builder.author(scanner.apply("请输入作者名称？"))
                                .enableSwagger() // 是否开启 swagger 模式
                                .fileOverride() // 覆盖已生成文件
                                .commentDate("yyyy-MM-dd hh:mm:ss") //注释日期
                                // 定义生成的实体类中日期的类型 TIME_PACK=LocalDateTime; ONLY_DATE=Date;
                                .dateType(DateType.TIME_PACK)
                                .outputDir(javaPath) // 指定输出目录
                                .disableOpenDir() // 禁止打开输出目录，默认打开
                                .fileOverride() // 覆盖之前的文件
                )
                // 3 包配置
                .packageConfig(builder -> {
                    builder.parent("com.shooter.springboot") // 设置父包名
                            .moduleName("module") // 设置模块包名
                            .entity("domain")   //pojo 实体类包名
                            .service("service") //Service 包名
                            .serviceImpl("service.impl") // ServiceImpl 包名
                            .mapper("mapper")   //Mapper 包名
                            .xml("mapper")  //Mapper XML 包名
                            .controller("controller") //Controller 包名
                            //.other("common") //自定义文件包名
                            // 设置mapperXml生成路径
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml,resourcePath+"/mapper"));
                })
                // 4 策略配置
                .strategyConfig((scanner, builder) ->
                        builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
                                .addTablePrefix("tc_","tb_") // 设置过滤表前缀
                                //Mapper 策略配置
                                .mapperBuilder()
                                .superClass(BaseMapper.class)   //设置父类
                                .formatMapperFileName("%sMapper")   //格式化 mapper 文件名称
                                .enableMapperAnnotation()       //开启 @Mapper 注解
                                .formatXmlFileName("%sXml") //格式化 Xml 文件名称

                                //service 策略配置
                                .serviceBuilder()
                                .formatServiceFileName("%sService") //格式化 service 接口文件名称，%s进行匹配表名，如 UserService
                                .formatServiceImplFileName("%sServiceImpl") //格式化 service 实现类文件名称，%s进行匹配表名，如 UserServiceImpl

                                //实体类 策略配置
                                .entityBuilder()
                                .enableLombok() //开启 Lombok
                                .idType(IdType.AUTO) //设置IdType注解
                                .disableSerialVersionUID()  //不实现 Serializable 接口，不生产 SerialVersionUID
                                .logicDeleteColumnName("deleted")   //逻辑删除字段名
                                .naming(NamingStrategy.underline_to_camel)  //数据库表映射到实体的命名策略：下划线转驼峰命
                                .columnNaming(NamingStrategy.underline_to_camel)    //数据库表字段映射到实体的命名策略：下划线转驼峰命
                                .addTableFills(
                                        new Column("create_time", FieldFill.INSERT),
                                        new Column("add_time", FieldFill.INSERT),
                                        new Column("modify_time", FieldFill.INSERT_UPDATE),
                                        new Column("update_time", FieldFill.INSERT_UPDATE)
                                )   //添加表字段填充，"create_time"字段自动填充为插入时间，"modify_time"字段自动填充为插入修改时间
                                //.enableTableFieldAnnotation()       // 开启生成实体时生成字段注解

                                // Controller策略配置
                                .controllerBuilder()
                                .enableHyphenStyle() // 开启驼峰转连字符
                                .formatFileName("%sController") //格式化 Controller 类文件名称，%s进行匹配表名，如 UserController
                                .enableRestStyle()  //开启生成 @RestController 控制器
                )

                // 5 使用Velocity引擎模板，可选模板引擎 Beetl 或 Freemarker
                .templateEngine(new FreemarkerTemplateEngine())
                // .templateEngine(new BeetlTemplateEngine())

                // 6 执行
                .execute();
    }

    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }

    /**
     * 自定义配置 DataSource
     */
    public DataSourceConfig.Builder createDataSource(String url, String username, String password) {
        return new DataSourceConfig.Builder(url, username, password)
                // 重写数据类型转换配置 MySqlTypeConvert
                .typeConvert(new MySqlTypeConvert(){
                    // 重写数据类型转换配置 MySqlTypeConvert
                    @Override
                    public IColumnType processTypeConvert(GlobalConfig config, String fieldType) {
                        // 若数据库字段类型是double，则返回 DbColumnType.DOUBLE
                        if(fieldType.equals("double")){
                            return DbColumnType.BIG_DECIMAL;
                        }
                        // 将数据库中tinyint转换成Boolean
                        if (fieldType.equals( "tinyint" ) ) {
                            return DbColumnType.BOOLEAN;
                        }
                        // 将数据库中datetime转换成date
                        if (fieldType.equals( "data" )) {
                            return DbColumnType.LOCAL_DATE_TIME;
                        }
                        return super.processTypeConvert(config, fieldType);
                    }
                });
    }
}