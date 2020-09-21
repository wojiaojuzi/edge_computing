package com.ecs.utils;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlUtil {

    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/edge_computing_service??useUnicode=true&characterEncoding=utf-8&autoReconnect=true&useSSL=false";
    private static final String username = "root";
    private static final String password = "a128263";
    private static final File file1 = new File("/media/guoxidong/TEST/edge_computing_service.sql");
    private static final File file2 = new File("/media/guoxidong/TEST/first.sql");

    /*public static void main(String[] args) throws SQLException, ClassNotFoundException {
        mybatisExec();
    }*/

    public static void mybatisExec1() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, username, password);
        ScriptRunner runner = new ScriptRunner(conn);
        try {
            runner.setStopOnError(true);
            runner.runScript(new FileReader(file1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        conn.close();
    }
    public static void mybatisExec2() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", username, password);
        ScriptRunner runner = new ScriptRunner(conn);
        try {
            runner.setStopOnError(true);
            runner.runScript(new FileReader(file2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        conn.close();
    }

}
