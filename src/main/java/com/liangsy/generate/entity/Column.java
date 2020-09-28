package com.liangsy.generate.entity;

import lombok.Data;

/**
 * 列(字段)对象
 * @author liangsy
 */
@Data
public class Column {

    /**
     * 数据库字段列名
     */
    private String columnName;
    /**
     * java属性列名
     */
    private String columnName2;
    /**
     * java列类型
     */
    private String columnType;
    /**
     * 数据库字段类型
     */
    private String columnDbType;
    /**
     * 列注释
     */
    private String columnComment;
    /**
     * 是否是主键
     */
    private String columnKey;
}
