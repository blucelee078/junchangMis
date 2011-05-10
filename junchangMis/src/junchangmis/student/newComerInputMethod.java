/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package junchangmis.student;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import publicmethod.*;

/**
 *
 * @author developers
 */
public class newComerInputMethod {

    public static void deleteNewComer(formalStu student) {//删除试听生

        try {
            //删除试听生
            DbOperation db = new DbOperation();
            db.DbConnect();
            String sql = "delete * from newComer where name='" + student.name + "' and tel='" + student.tel + "'";
            db.DBSqlExe(sql);
            db.DbClose();
        } catch (SQLException ex) {
            Logger.getLogger(newComerInputMethod.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void addNewComer(newComer student) {//添加试听生
        try {
            DbOperation db = new DbOperation();
            db.DbConnect();
            String sql = "insert into newComer values('" + student.name + "','" + student.sex + "','" + student.birthday + "','" + student.tel + "','" + student.school + "','" + student.classCode + "','" + DateCompute.getCurrentDate() + "')";
            db.DBSqlExe(sql);
            db.DbClose();
        } catch (SQLException ex) {
            Logger.getLogger(newComerInputMethod.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
