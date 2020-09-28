package com.liangsy.generate.entity;

import lombok.Data;

import java.util.List;

/**
 * 表实体类
 * @author liangsy
 */
@Data
public class Table {
    /**
     * 数据库表的名称
     */
    private String name;
    /**
     * java表的名称
     */
    private String name2;
    /**
     * 表的注释
     */
    private String comment;
    /**
     * 表的主键
     */
    private String key;
    /**
     * 表的所有列集合
     */
    private List<Column> columns;
}
