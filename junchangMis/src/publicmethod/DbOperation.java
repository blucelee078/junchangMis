/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package publicmethod;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class DbOperation {

    Connection connection = null;
    Statement stmt = null;
    ResultSet rs = null;
    //String url = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};PWD=123456;DBQ=db.mdb;useUnicode=false;characterEncoding=gbk";
    String url = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=db.mdb;useUnicode=false;characterEncoding=gbk";
    Properties prop = new Properties();
    public PreparedStatement prestmt = null;

    public DbOperation() {
    }

    public void DbConnect() {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("" + e);
        }
        try {
            prop.put("charSet", "gb2312");
            connection = DriverManager.getConnection(url, prop);
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        } catch (SQLException e) {
            //  e.printStackTrace();
            System.out.println("" + e);
        }
    }

    public void setPreStme(String str) {
        try {
            prestmt = connection.prepareCall(str);
        } catch (SQLException e) {
            //  e.printStackTrace();
            System.out.println("" + e);
        }
    }

    public void setPreStmeUpdateable(String str) {
        try {
            prestmt = connection.prepareCall(str, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException e) {
            //  e.printStackTrace();
            System.out.println("" + e);
        }
    }

    public void DbClose() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (prestmt != null) {
                prestmt.close();
            }
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet DBSqlQuery(String sql) {
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            // e.printStackTrace();
            System.out.println("" + e);
        }
        return rs;
    }

    public boolean DbSqlUpdate(String sql) {
        boolean ok = true;
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            ok = false;
            //e.printStackTrace();
            System.out.println("" + e);
        }
        return ok;
    }

    public void DBSqlExe(String sql) throws SQLException {
        boolean r;
        r = stmt.execute(sql);
    }

    public void setStmt() {
        stmt = null;
        try {
            stmt = connection.createStatement();
        } catch (Exception e) {
        }
    }
}
