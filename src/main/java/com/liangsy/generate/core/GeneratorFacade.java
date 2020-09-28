package com.liangsy.generate.core;

import com.liangsy.generate.entity.DataBase;
import com.liangsy.generate.entity.Settings;
import com.liangsy.generate.entity.Table;
import com.liangsy.generate.utils.DataBaseUtils;
import com.liangsy.generate.utils.PropertiesUtils;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 入口类
 * 使用外观模式
 * @author liangsy
 */
@Data
public class GeneratorFacade {
    private String templatePath;
    private String outPath;
    private Settings settings;
    private DataBase db;
    private Generator generator;

    public GeneratorFacade(String templatePath, String outPath, Settings settings, DataBase db) throws Exception {
        this.templatePath = templatePath;
        this.outPath = outPath;
        this.settings = settings;
        this.db = db;
        this.generator = new Generator(templatePath,outPath);
    }

    /**
     * 入口方法
     * 1.准备数据模型
     * 2.调用核心处理类生成代码
     */
    public void generatorByDataBase() throws Exception {
        List<Table> tables = DataBaseUtils.getDbInfo(db);
        //对每一个table对象进行代码生成
        for(Table table : tables){
            //获取数据模型
            Map<String, Object> dataModel = getDataModel(table);
            //调用核心处理类 生成代码
            generator.scanAndGenerator(dataModel);
        }
    }

    /**
     * 根据table对象获取数据模型
     */
    private Map<String,Object> getDataModel(Table table){
        HashMap<String, Object> dataModel = new HashMap<>(16);
        //1.自定义配置
        dataModel.putAll(PropertiesUtils.customMap);
        //2.元数据
        dataModel.put("table",table);
        //3.项目setting
        dataModel.putAll(this.settings.getSettingMap());
        //4.单独抽取类型出来 避免使用table.name2形式麻烦
        dataModel.put("ClassName",table.getName2());
        dataModel.put("LowerClassName",table.getName2().toLowerCase());
        return dataModel;
    }
}
