package com.liangsy.generate.core;

import com.liangsy.generate.utils.FileUtils;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.Data;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * 核心处理类
 * @author liangsy
 */
@Data
public class Generator {
    /**
     * 模板路径（目录）
     */
    private String templatePath;
    /**
     * 代码生成路径（目录）
     */
    private String outPath;
    /**
     * FreeMarker配置对象
     */
    private Configuration cfg;

    public Generator(String templatePath,String outPath) throws Exception {
        this.templatePath = templatePath;
        this.outPath = outPath;
        //1.实例化Configuration对象
        cfg = new Configuration();
        //2.指定模板加载器 -- 文件模板加载器
        FileTemplateLoader ft1 = new FileTemplateLoader(new File(templatePath));
        cfg.setTemplateLoader(ft1);
    }

    /**
     * 代码生成 扫描目录下所有模板并生成文件
     */
    public void scanAndGenerator(Map<String,Object> dataModel) throws Exception {
        //1.扫描模板路径下的所有模板
        List<File> fileList = FileUtils.searchAllFile(new File(templatePath));
        // 2.对每个模板进行文件生成（数据模型）
        for (File file : fileList){
            executeGenerator(dataModel,file);
        }
    }


    /**
     * 对模板进行文件生成
     * @param dataModel 数据模型
     * @param file 模板文件
     */
    public void executeGenerator(Map<String,Object> dataModel,File file) throws Exception {
        /**
         * 1.文件路径处理
         *    例如
         *      templatePath    E:\模板\${path1}\${path2}\${path3}\
         *      file    E:\模板\${path1}\${path2}\${path3}\${ClassName}.java
         *    处理为：
         *          templateFileName \${ClassName}.java
         *          outFilePath  \User.java
         *
         */
        String templateFileName = file.getAbsolutePath().replace(this.templatePath, "");
        //处理字符串模板
        String outFilePath = processTemplateString(templateFileName, dataModel);
        //2.读取文件模板  相对路径
        Template template = cfg.getTemplate(templateFileName);
        template.setOutputEncoding("utf-8");
        //3.创建文件
        File file1 = FileUtils.mkdir(outPath, outFilePath);
        //4.模板处理（处理文件模板  文件生成）
        FileWriter fw = new FileWriter(file1);
        template.process(dataModel,fw);
        fw.close();
    }

    /**
     * 通过 字符串模板加载器 创建模板
     */
    public String processTemplateString(String templateString, Map dataModel) throws Exception {
        StringWriter out = new StringWriter();
        //通过字符串创建模板 name可以自定义 类似于文件模板里面的占位符
        Template template = new Template("ts", new StringReader(templateString), cfg);
        //处理模板
        template.process(dataModel,out);
        return out.toString();
    }

}
