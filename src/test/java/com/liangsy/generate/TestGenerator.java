package com.liangsy.generate;

import com.liangsy.generate.core.Generator;
import com.liangsy.generate.core.GeneratorFacade;
import com.liangsy.generate.entity.DataBase;
import com.liangsy.generate.entity.Settings;
import com.liangsy.generate.entity.Table;
import com.liangsy.generate.utils.DataBaseUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

class TestGenerator {

    DataBase dataBase = null;

    /**
     * 数据库连接
     */
    @BeforeEach
    void getDataBase(){
        String driver = "com.mysql.cj.jdbc.Driver";
        String ip = "10.120.22.40";
        String port = "8306";
        String catalog = "bd_bis2";
        String userName = "root";
        String passWord = "zhdgps_123456";
        dataBase = new DataBase(driver,ip,port,userName,passWord,catalog);
    }

    /**
     * 获取数据库列表
     */
    @Test
    void testGetSchemas() throws Exception {
        List<String> list = DataBaseUtils.getSchemas(dataBase);
        System.out.println(list);
    }

    /**
     * 获取所有表和表的字段
     */
    @Test
    void testGetDbInfo() throws Exception {
        List<Table> dbInfo = DataBaseUtils.getDbInfo(dataBase);
        dbInfo.forEach(System.out::println);
    }

    /**
     * 扫描目录下所有模板并生成文件
     */
    @Test
    void testScanAndGenerator() throws Exception {
        String templatePath = "C:\\Users\\liangsy\\Desktop\\temp\\template";
        String outPath = "C:\\Users\\liangsy\\Desktop\\temp\\outPath";
        Generator generator = new Generator(templatePath, outPath);
        HashMap<String, Object> dataModel = new HashMap<>(1);
        dataModel.put("username","哈哈哈3");
        generator.scanAndGenerator(dataModel);
    }

    /**
     * 调用入口类 生成代码
     */
    @Test
    void testGeneratorFacade() throws Exception {
        String templatePath = "D:\\my_work\\myProject1\\code-generator\\src\\main\\resources\\templates";
        String outPath = "C:\\Users\\liangsy\\Desktop\\temp\\outPath";
        //使用Builder不会调用无参构造函数
        Settings settings = new Settings();
        settings.setAuthor("liangsy");
        settings.setPath1("com");
        settings.setPath2("sgcc");
        settings.setPath3("richsoft");
        settings.setPath4("proxy");
        settings.setPPackage("com.sgcc.richsoft.proxy");
        settings.setProject("sgcc-mysql-proxy");
        settings.setProjectComment("数据库代理服务");
        GeneratorFacade generatorFacade = new GeneratorFacade(templatePath,outPath,settings,dataBase);
        generatorFacade.generatorByDataBase();
    }
}
