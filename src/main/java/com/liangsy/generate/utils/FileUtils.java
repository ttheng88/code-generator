package com.liangsy.generate.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件操作工具类
 * @author liangsy
 */
public class FileUtils {

    /**
     * 获取相对路径
     * @param baseDir
     * @param file
     * @return
     */
    public static String getRelativePath(File baseDir,File file){
        if (baseDir.equals(file)){
            return "";
        }
        if (baseDir.getParentFile() == null){
            return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length());
        }
        return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length()+1);
    }

    /**
     * 获取某个目录下的所有文件
     * @param dir
     * @return
     */
    public static List<File> searchAllFile(File dir){
        ArrayList<File> arrayList = new ArrayList<>();
        searchFiles(dir,arrayList);
        return arrayList;
    }

    /**
     * 递归获取某个目录下的所有文件
     * @param dir
     * @param collector
     */
    public static void searchFiles(File dir, List<File> collector){
        if(dir.isDirectory()){
            File[] subFiles = dir.listFiles();
            for (int i = 0; i < subFiles.length; i++) {
                searchFiles(subFiles[i],collector);
            }
        }else {
            collector.add(dir);
        }
    }

    /**
     * 创建文件
     */
    public static File mkdir(String dir,String file){
        if (dir == null){
            throw new IllegalArgumentException("dir must be not null");
        }
        File result = new File(dir,file);
        if (result.getParentFile() != null){
            result.getParentFile().mkdirs();
        }
        return result;
    }
}
