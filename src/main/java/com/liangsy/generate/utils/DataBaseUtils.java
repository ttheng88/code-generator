package com.liangsy.generate.utils;

import com.liangsy.generate.entity.Column;
import com.liangsy.generate.entity.DataBase;
import com.liangsy.generate.entity.Table;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.*;

/**
 * 数据库操作工具类
 * @author liangsy
 */
public class DataBaseUtils {

    /**
     * 获取数据库连接
     */
    public static Connection getConnection(DataBase db) throws Exception {
        Properties props = new Properties();
        props.put("user",db.getUserName());
        props.put("password",db.getPassWord());
        //获取数据库备注信息
        props.put("remarksReporting","true");
        Class.forName(db.getDriver());
        return DriverManager.getConnection(db.getUrl(),props);
    }

    /**
     * 获取数据库列表
     */
    public static List<String> getSchemas(DataBase db) throws Exception {
        Connection connection = getConnection(db);
        //获取元数据
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet rs = metaData.getCatalogs();
        ArrayList<String> list = new ArrayList<String>();
        while (rs.next()){
            list.add(rs.getString(1));
        }
        rs.close();
        connection.close();
        return list;
    }

    /**
     * 获取数据库表和字段构建实体类
     */
    public static List<Table> getDbInfo(DataBase db) throws Exception {
        //1.获取连接
        Connection connection = getConnection(db);
        //2.获取元数据
        DatabaseMetaData metaData = connection.getMetaData();
        /**
         * 3.获取当前数据库中的所有表
         * catalog 数据库(null代表所有数据库)
         * schema 用户名(oracle) null(mysql)
         * tableName 表(null表示所有表)
         * type[] 类型(表TABLE/视图VIEW)
         */
        ResultSet tables = metaData.getTables(db.getCatalog(), null, null, new String[]{"TABLE"});

        List<Table> list = new ArrayList<>();
        while (tables.next()){
            //表
            Table table = new Table();
            //i.表名
            String tableName = tables.getString("TABLE_NAME");
            //只输出配置文件里面指定的表
            String tableNames = PropertiesUtils.customMap.get("TABLE_NAME");
            if(!StringUtils.isBlank(tableNames)){
                String[] split = tableNames.split(",");
                if(!Arrays.asList(split).contains(tableName)){
                    continue;
                }
            }

            //ii.类名
            String className = removePrefix(tableName);
            //iii.描述
            String remarks = tables.getString("REMARKS");
            //iiii.主键 要考虑联合主键的情况
            ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName);
            String keys = "";
            while (primaryKeys.next()){
                String keyName = primaryKeys.getString("COLUMN_NAME");
                keys += keyName + ",";
            }
            table.setName(tableName);
            table.setName2(className);
            table.setComment(remarks);
            table.setKey(keys);

            //字段
            ResultSet columns = metaData.getColumns(db.getCatalog(), null, tableName, null);

            List<Column> columnList = new ArrayList<>();
            while (columns.next()){
                Column column = new Column();
                //i.数据库列名称
                String columnName = columns.getString("COLUMN_NAME");
                //java属性名
                String attName = StringUtils.underlineToCamelCase(columnName);
                //ii.Type类型
                String dbTypeName = columns.getString("TYPE_NAME");
                //注意可能返回的类型 如：INT 和 INT UNSIGNED，通过" "来截取
                String javaTypeName = PropertiesUtils.customMap.get(dbTypeName.split(" ")[0]);
                //iii.备注
                String columnsRemark = columns.getString("REMARKS");
                //iiii.是否主键
                String pri = null;
                if(keys.contains(columnName)){
                    pri = "PRI";
                }
                column.setColumnName(columnName);
                if("true".equals(PropertiesUtils.customMap.get("IS_SAME_FIELD"))){
                    column.setColumnName2(columnName);
                }else {
                    column.setColumnName2(attName);
                }
                column.setColumnDbType(dbTypeName);
                column.setColumnType(javaTypeName);
                column.setColumnComment(columnsRemark);
                column.setColumnKey(pri);
                columnList.add(column);
            }
            columns.close();
            table.setColumns(columnList);
            list.add(table);
        }
        tables.close();
        connection.close();
        return list;
    }

    /**
     * 删除表名前缀
     */
    public static String removePrefix(String tableName){
        String prefixes = PropertiesUtils.customMap.get("tableRemovePrefixes");
        for (String fp : prefixes.split(",")) {
            if(tableName.startsWith(fp)){
                tableName = tableName.split(fp)[1];
                break;
            }
        }
        return StringUtils.upperCaseFirst(StringUtils.underlineToCamelCase(tableName));
    }

}
