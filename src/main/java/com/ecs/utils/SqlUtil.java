package com.ecs.utils;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlUtil {

    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/edge_computing_service?serverTimezone=UTC";
    private static final String username = "root";
    private static final String password = "a128263";
    private static final File file = new File("D:\\sql\\userinfo.sql");


    /*public static void main(String[] args) throws SQLException, ClassNotFoundException {
        mybatisExec();
    }*/

    private static void mybatisExec() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, username, password);
        ScriptRunner runner = new ScriptRunner(conn);
        try {
            runner.setStopOnError(true);
            runner.runScript(new FileReader(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        conn.close();
    }

}
