package com.liangsy.generate.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 将自定义的配置文件写到properties文件中
 * 自定义配置工具类
 * @author liangsy
 */
public class PropertiesUtils {
    public static Map<String,String> customMap = new HashMap<String, String>(16);
    static {
        try {
            File dir = new File("properties");
            List<File> files = FileUtils.searchAllFile(new File(dir.getAbsolutePath()));
            for (File file:
                 files) {
                if (file.getName().endsWith("properties")){
                    Properties prop = new Properties();
                    prop.load(new FileInputStream(file));
                    customMap.putAll((Map)prop);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
