/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package junchangmis.student;

import java.util.logging.Level;
import java.util.logging.Logger;
import publicmethod.*;
import java.sql.*;
import java.util.Vector;

/**
 *
 * @author developers
 */
public class formalStuInputMethod {

    public static int getFormalStuCount(String classCode) {//获取某一班级的正式生人数
        try {
            DbOperation db = new DbOperation();
            db.DbConnect();
            String sql = "select count(*) from formalStu where classCode='" + classCode + "'";
            ResultSet rs = db.DBSqlQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(formalStuInputMethod.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public static void deleteFormalStu(String id) {
        try {
            DbOperation db = new DbOperation();
            db.DbConnect();
            String sql = "delete * from formalStu where id='" + id + "'";
            db.DBSqlExe(sql);
            db.DbClose();
        } catch (SQLException ex) {
            Logger.getLogger(formalStuInputMethod.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addFormalStu(formalStu student) {//添加一个正式生
        try {
            //添加一个正式生
            DbOperation db = new DbOperation();
            db.DbConnect();
            String sql = "insert into formalStu values('" + student.id + "','" + student.name + "','" + student.sex + "','" + student.birthday + "','" + student.tel + "','" + student.school + "','" + student.weekCount + "','" + student.dateStart + "','" + student.coursePerWeek + "','" + student.pricePerCourse + "','" + student.tuition + "','" + student.costumeFee + "','" + student.bookFee + "','" + student.otherFee + "','" + student.sumFee + "','" + student.classCode + "')";
            db.DBSqlExe(sql);
            db.DbClose();
        } catch (SQLException ex) {
            Logger.getLogger(formalStuInputMethod.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getEndDate(int year, int month, int day, int gap) {//生成交费截止时间
        int a[] = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (day + gap <= a[month - 1]) {
            day += gap;
        } else {
            while (gap - a[month - 1] >= 0) {
                gap = gap - a[month - 1];
                if ((((year % 100 == 0 && year % 400 == 0) || (year % 100 != 0 && year % 4 == 0)) && month == 2)) {
                    gap--;
                }
                month++;
                if (month == 13) {
                    month = 1;
                    year++;
                }
            }
            day += gap;
            if (day > a[month - 1]) {
                day -= a[month - 1];
                month++;
                if (month == 13) {
                    month = 1;
                    year++;
                }
            }
        }
        return year + "." + month + "." + day;
    }

    public static String getMaxId(String classCode) {//获取一个班级中已有学号的最大值
        try {
            DbOperation db = new DbOperation();
            db.DbConnect();
            Vector<String> temp = new Vector<String>();
            String sql = "select id from formalStu where classCode='" + classCode + "'";
            ResultSet rs = db.DBSqlQuery(sql);
            while (rs.next()) {
                temp.add(rs.getString(1));
            }
            if (temp.size() > 0) {
                return temp.get(temp.size() - 1);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(formalStuInputMethod.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public static void main(String[] args) {
        System.out.println(getEndDate(2011, 2, 1, 70));
    }
}
