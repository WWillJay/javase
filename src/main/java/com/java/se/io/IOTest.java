package com.java.se.io;

import java.io.File;

public class IOTest {

    public static void main(String[] args) throws Exception {
//        IOTest.saveOrDelete();
        IOTest.rename();
    }

    // 优化创建父目录：静态代码块，只需判断一次
    private static File optimizedFile = new File("f:" + File.separator + "optimize" + File.separator + "optimize.txt");
    static {
        if (!optimizedFile.getParentFile().exists()){
            optimizedFile.getParentFile().mkdirs();
        }
    }

    private final static String PATH_NAME = "f:" + File.separator + "demo" + File.separator + "demo.txt";

    /**
     * 保存或者删除
     */
    public static void saveOrDelete() throws Exception {
        File file = new File(PATH_NAME);
        // 文件目录创建可以用静态代码块，这样只需要判断一次，保证在代码执行前就存在
        // 父目录是否存在，每次都要判断
        if (!file.getParentFile().exists()) {
            // 创建所有的文件目录
            System.out.println("文件父目录不存在，创建完整文件目录，result=" + file.getParentFile().mkdirs());
        }
        // 文件是否存在
        if (file.exists()) {
            // 删除文件
            System.out.println("文件已存在，进行删除操作，result=" + file.delete());
        } else {
            // 添加文件
            System.out.println("文件不存在，进行添加操作，result=" + file.createNewFile());
        }
    }

    /**
     * 更名
     */
    public static void rename(){
        File oldFile = new File("f://demo.txt");
        File newFile = new File("f://demo1.txt");
        oldFile.renameTo(newFile);
    }

    private static File logFile = new File("f:" + File.separator + "log" + File.separator + "optimize.txt");
    static {
        if (!logFile.getParentFile().exists()){
            logFile.getParentFile().mkdirs();
        }
    }

    public static void logOut(){

    }

}
