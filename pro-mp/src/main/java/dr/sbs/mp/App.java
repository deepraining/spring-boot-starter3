package dr.sbs.mp;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import java.util.Collections;

public class App {
  public static void main(String[] args) {
    String url =
        "jdbc:mysql://127.0.0.1:3306/starter2?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai";
    String username = "root";
    String password = "root123456";
    String javaFilePath = "src/main/java";
    String javaFilePackage = "dr.sbs.mp";
    String xmlFilePath = "src/main/resources/dr/sbs/mp/mapper";

    FastAutoGenerator.create(url, username, password)
        .globalConfig(
            builder -> {
              builder
                  .author("deepraining") // 设置作者
                  .commentDate("") // 不显示时间
                  .disableOpenDir() // 不打开文件夹
                  .enableSwagger() // 开启 swagger 模式
                  .fileOverride() // 覆盖已生成文件
                  .outputDir(javaFilePath); // 指定输出目录
            })
        .packageConfig(
            builder -> {
              builder
                  .parent(javaFilePackage) // 设置父包名
                  .pathInfo(
                      Collections.singletonMap(OutputFile.xml, xmlFilePath)); // 设置mapperXml生成路径
            })
        .strategyConfig(
            builder -> {
              // 排除数据表
              builder
                  .addExclude("flyway_schema_history")
                  .mapperBuilder()
                  .enableBaseResultMap()
                  .enableBaseColumnList()
                  .enableFileOverride()
                  .serviceBuilder()
                  .enableFileOverride()
                  .formatServiceFileName("%sMpService")
                  .formatServiceImplFileName("%sMpServiceImpl")
                  .entityBuilder()
                  .enableLombok()
                  .enableFileOverride();
            })
        .templateConfig(
            builder -> {
              // 不生成controller
              builder
                  .entity("/tpl/entity.java")
                  .service("/tpl/service.java")
                  .serviceImpl("/tpl/serviceImpl.java")
                  .mapper("/tpl/mapper.java")
                  .xml("/tpl/mapper.xml")
                  .controller("")
                  .build();
            })
        .templateEngine(new FreemarkerTemplateEngine())
        .execute();
  }
}
