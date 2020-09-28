package com.liangsy.generate.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据库连接信息实体类
 * @author liangsy
 */
@Data
public class DataBase {

    private String ip;
    private String port;
    private String driver;
    private String url;
    private String userName;
    private String passWord;
    /**
     * 数据库名  不指定默认所有
     */
    private String catalog;

    public DataBase(String driver, String ip, String port,String userName,String passWord,String catalog){
        this.ip = ip;
        this.port = port;
        this.driver = driver;
        this.userName = userName;
        this.passWord = passWord;
        this.catalog = catalog;
        this.url = "jdbc:mysql://"+ip+":"+port+"/"+catalog+"?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
    }


}
