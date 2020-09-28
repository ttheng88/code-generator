package com.liangsy.generate.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 工程设置实体类
 * @author liangsy
 */
@Data
public class Settings {

    /**
     * 工程名
     */
    private String project;
    /**
     * 完整包名
     */
    private String pPackage;

    /**
     * 工程描述
     */
    private String projectComment;

    /**
     * 作者
     */
    private String author;

    /**
     * 日期时间
     */
    private String dateTime;

    /**
     * 一级路径（包名1）
     */
    private String path1;
    /**
     * 二级路径（包名2）
     */
    private String path2;
    /**
     * 三级路径（包名3）
     */
    private String path3;
    /**
     * 四级路径（包名4）
     */
    private String path4;
    /**
     * 全路径
     */
    private String pathAll;

    /**
     * 生成的文件名
     */
    private String fileName;

    public Settings(){
        this.fileName =  "java";
        this.dateTime = DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDateTime.now());
    }

    public Map<String,Object> getSettingMap(){
        HashMap<String, Object> map = new HashMap<>(7);
        Field[] declaredFields = Settings.class.getDeclaredFields();
        for (Field field :
                declaredFields) {
            try {
                field.setAccessible(true);
                map.put(field.getName(),field.get(this));
            } catch (IllegalAccessException e) {
            }
        }
        return map;
    }

}
