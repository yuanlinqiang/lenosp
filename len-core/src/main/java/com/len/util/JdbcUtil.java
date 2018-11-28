package com.len.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtil {

  public static Connection getConnection() {
    Connection con = null;

    String driverName = "com.mysql.jdbc.Driver";
//    String dbURL = "jdbc:mysql://localhost:3306/z_eas?useUnicode=true&characterEncoding=UTF-8&useSSL=false&autoReconnect=true&failOverReadOnly=false";
    String dbURL="jdbc:mysql://localhost:3306/z_eas?useUnicode=true&characterEncoding=UTF-8";
    String userName = "root";
    String userPwd = "123";
    try {
      Class.forName(driverName);
      con =  DriverManager.getConnection(dbURL, userName, userPwd);
    } catch (Exception e) {
      System.out.println("获取连接失败." + e.getMessage());
    }
    return con;
  }

  public static void main(String[] args) throws SQLException {
    Connection con= getConnection();
    Statement statement = con.createStatement();
    String sql = "select * from z_user";
   ResultSet rs = statement.executeQuery(sql);
   while(rs.next()){
     System.out.println(rs.getString("username"));
   }

  }
}