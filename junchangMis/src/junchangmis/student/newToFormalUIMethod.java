/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package junchangmis.student;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import publicmethod.*;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author developers
 */
public class newToFormalUIMethod {

    public static newComer getSelectedNewComer(String name, String tel) {
        try {
            DbOperation db = new DbOperation();
            db.DbConnect();
            String sql = "select * from newComer where name='" + name + "' and tel='" + tel + "'";
            ResultSet rs = db.DBSqlQuery(sql);
            if (rs.next()) {
                return new newComer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(newToFormalUIMethod.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static void getNewComer(String classCode, Vector<newComer> newcomer) {//获取某个专业班级的所有试听生
        try {
            newcomer.clear();
            DbOperation db = new DbOperation();
            db.DbConnect();
            String sql = "select * from newComer where classCode='" + classCode + "'";
            ResultSet rs = db.DBSqlQuery(sql);
            while (rs.next()) {
                newcomer.add(new newComer(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6)));
            }
            db.DbClose();
        } catch (SQLException ex) {
            Logger.getLogger(newToFormalUIMethod.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void getFormalStu(String classCode, Vector<formalStu> formalstu) {
        try {
            formalstu.clear();
            DbOperation db = new DbOperation();
            db.DbConnect();
            String sql = "select * from formalStu where classCode='" + classCode + "'";
            ResultSet rs = db.DBSqlQuery(sql);
            while (rs.next()) {
                formalstu.add(new formalStu(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6)));
            }
            db.DbClose();
        } catch (SQLException ex) {
            Logger.getLogger(newToFormalUIMethod.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
