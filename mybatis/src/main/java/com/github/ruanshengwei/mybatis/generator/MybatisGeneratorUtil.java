package com.github.ruanshengwei.mybatis.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * mybatis  mapper entity dao 自动生成
 */
public class MybatisGeneratorUtil {

    public static void main(String[] args) throws Exception {
        generator();
    }

    private static void generator() throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;

        File configFile = new File("/Users/ruanshengwei/Desktop/dev/code/code-snippets/mybatis/src/main/resources/generator/generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);

        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);

        System.out.println("代码生成完毕>>>>>>>>>>>>");
    }

}