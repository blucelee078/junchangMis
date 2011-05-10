/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package junchangmis.codecSetting;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import publicmethod.DbOperation;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author developers
 */
public class codecMethod {

    public static void addSpecialty(String code, String meaning) {//添加专业
        try {
            DbOperation db = new DbOperation();
            db.DbConnect();
            String sql = "insert into specialty values('" + code + "','" + meaning + "')";
            db.DBSqlExe(sql);
            db.DbClose();
        } catch (SQLException ex) {
            Logger.getLogger(codecMethod.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addSubSpecialty(String code, String meaning, String father) {//添加小专业
        try {
            DbOperation db = new DbOperation();
            db.DbConnect();
            String sql = "insert into subSpec values('" + code + "','" + meaning + "','" + father + "')";
            db.DBSqlExe(sql);
            db.DbClose();
        } catch (SQLException ex) {
            Logger.getLogger(codecMethod.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean hasSpecialtyExisted(String code, String meaning) {//是否存在该专业
        try {
            DbOperation db = new DbOperation();
            db.DbConnect();
            String sql = "select * from specialty where code='" + code + "' and meaning='" + meaning + "'";
            ResultSet rs = db.DBSqlQuery(sql);
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(codecMethod.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static boolean hasSpecialtyCodeExisted(String code) {//是否存在该专业码
        try {
            DbOperation db = new DbOperation();
            db.DbConnect();
            String sql = "select * from specialty where code='" + code + "'";
            ResultSet rs = db.DBSqlQuery(sql);
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(codecMethod.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static boolean hasCodeSumExisted(String code) {//是否存在该完整的专业班级码
        try {
            DbOperation db = new DbOperation();
            db.DbConnect();
            String sql = "select * from codeSum where code='" + code + "'";
            ResultSet rs = db.DBSqlQuery(sql);
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(codecMethod.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static String getSpecialyMeaning(String code) {//根据专业码获得专业名字
        try {
            DbOperation db = new DbOperation();
            db.DbConnect();
            String sql = "select * from specialty where code='" + code + "'";
            ResultSet rs = db.DBSqlQuery(sql);
            if (rs.next()) {
                return rs.getString(2);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(codecMethod.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static boolean hasSubSpecExisted(String code, String meaning) {//是否存在该小专业
        try {
            DbOperation db = new DbOperation();
            db.DbConnect();
            String sql = "select * from subSpec where code='" + code + "' and meaning='" + meaning + "'";
            ResultSet rs = db.DBSqlQuery(sql);
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(codecMethod.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static boolean hasSubSpecCodeExisted(String code) {//是否存在该小专业码
        try {
            DbOperation db = new DbOperation();
            db.DbConnect();
            String sql = "select * from subSpec where code='" + code + "'";
            ResultSet rs = db.DBSqlQuery(sql);
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(codecMethod.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static boolean hasSubSpecFatherExisted(String father) {//小专业表中是否存在某个父专业
        try {
            DbOperation db = new DbOperation();
            db.DbConnect();
            String sql = "select * from subSpec where father='" + father + "'";
            ResultSet rs = db.DBSqlQuery(sql);
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(codecMethod.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static boolean hasCodeSumFatherExisted(String father) {
        try {
            DbOperation db = new DbOperation();
            db.DbConnect();
            String sql = "select * from codeSum where father='" + father + "'";
            ResultSet rs = db.DBSqlQuery(sql);
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(codecMethod.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static String getSubSpecMeaning(String code) {//根据小专业码获得其小专业名字
        try {
            DbOperation db = new DbOperation();
            db.DbConnect();
            String sql = "select * from subSpec where code='" + code + "'";
            ResultSet rs = db.DBSqlQuery(sql);
            if (rs.next()) {
                return rs.getString(2);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(codecMethod.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /*  public static void initSubSpecCode(Vector<String> subSpecCode) {//初始化小专业码数组 所有的小专业码
    try {
    subSpecCode.clear();
    DbOperation db = new DbOperation();
    db.DbConnect();
    String sql = "select * from subSpec";
    ResultSet rs = db.DBSqlQuery(sql);
    while (rs.next()) {
    subSpecCode.add(rs.getString(1));
    }
    } catch (SQLException ex) {
    Logger.getLogger(codecMethod.class.getName()).log(Level.SEVERE, null, ex);
    }
    }*/
    public static void initSubSpecCodeSons(Vector<String> subSpecCode, Vector<String> subSpecMeaning, String father) {//根据某个父专业码找到其附属的所有小专业码
        try {
            subSpecCode.clear();
            subSpecMeaning.clear();
            DbOperation db = new DbOperation();
            db.DbConnect();
            String sql = "select * from subSpec where father='" + father + "'";
            ResultSet rs = db.DBSqlQuery(sql);
            while (rs.next()) {
                subSpecCode.add(rs.getString(1));
                subSpecMeaning.add(rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(codecMethod.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void iniYearClassNumSons(Vector<String> YearClassNum, String father) {
        try {
            YearClassNum.clear();
            DbOperation db = new DbOperation();
            db.DbConnect();
            String sql = "select * from codeSum where father='" + father + "'";
            ResultSet rs = db.DBSqlQuery(sql);
            while (rs.next()) {
                YearClassNum.add(rs.getString(1).substring(3, 6));
            }
        } catch (SQLException ex) {
            Logger.getLogger(codecMethod.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void initSpecialtyCode(Vector<String> specialtyCode, Vector<String> specialtyMeaning) {//初始化专业码数组 所有专业码 还有专业名称数组
        try {
            specialtyCode.clear();
            specialtyMeaning.clear();
            DbOperation db = new DbOperation();
            db.DbConnect();
            String sql = "select * from specialty";
            ResultSet rs = db.DBSqlQuery(sql);
            while (rs.next()) {
                specialtyCode.add(rs.getString(1));
                specialtyMeaning.add(rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(codecMethod.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void deleteSubSpec(String code, String meaning) {//从小专业表中删除一项
        try {
            DbOperation db = new DbOperation();
            db.DbConnect();
            String sql = "delete * from subSpec where code='" + code + "'and meaning='" + meaning + "'";
            db.DBSqlExe(sql);
            db.DbClose();
        } catch (SQLException ex) {
            Logger.getLogger(codecMethod.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateSpecialty() {//更新专业码表
        try {
            DbOperation db = new DbOperation();
            db.DbConnect();
            Vector<String> toBeDelete = new Vector<String>();
            String sql = "select * from specialty";
            ResultSet rs = db.DBSqlQuery(sql);
            while (rs.next()) {
                String father = rs.getString(1) + "(" + rs.getString(2) + ")";
                if (hasSubSpecFatherExisted(father) == false) {//如果已经没有了附属小专业，则从专业码表中删除该专业码
                    toBeDelete.add(father);
                }
            }
            for (int i = 0; i < toBeDelete.size(); i++) {
                int start = toBeDelete.get(i).indexOf("(") + 1;
                int end = toBeDelete.get(i).indexOf(")");
                String meaning = toBeDelete.get(i).substring(start, end);
                sql = "delete * from specialty where code='" + toBeDelete.get(i).substring(0, 1) + "' and meaning='" + meaning + "'";
                db.DBSqlExe(sql);
            }
            db.DbClose();
        } catch (SQLException ex) {
            Logger.getLogger(codecMethod.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateSubSpec() {//更新小专业码表
        try {
            DbOperation db = new DbOperation();
            db.DbConnect();
            Vector<String> toBeDelete = new Vector<String>();
            Vector<String> meaning = new Vector<String>();
            String sql = "select * from subSpec";
            ResultSet rs = db.DBSqlQuery(sql);
            while (rs.next()) {
                String father = rs.getString(3).substring(0, 1) + rs.getString(1);
                if (hasCodeSumFatherExisted(father) == false) {
                    toBeDelete.add(father);
                    meaning.add(rs.getString(2));
                }
            }
            for (int i = 0; i < toBeDelete.size(); i++) {
                sql = "delete * from subSpec where code='" + toBeDelete.get(i).substring(1, 3) + "' and meaning='" + meaning.get(i) + "'";
                db.DBSqlExe(sql);
            }
            db.DbClose();
        } catch (SQLException ex) {
            Logger.getLogger(codecMethod.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addCodeSum(String code, String meaning, String father) {//在总编码表中添加
        try {
            DbOperation db = new DbOperation();
            db.DbConnect();
            String sql = "insert into codeSum values('" + code + "','" + meaning + "','" + father + "')";
            db.DBSqlExe(sql);
            db.DbClose();
        } catch (SQLException ex) {
            Logger.getLogger(codecMethod.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void deleteCodeSum(String code) {//在总编码表中删除
        try {
            DbOperation db = new DbOperation();
            db.DbConnect();
            String sql = "delete * from codeSum where code='" + code + "'";
            db.DBSqlExe(sql);
            db.DbClose();
        } catch (SQLException ex) {
            Logger.getLogger(codecMethod.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getCodeTripleMeaning(String code) {//获得总编码的翻译结果
        try {
            DbOperation db = new DbOperation();
            db.DbConnect();
            String sql = "select * from codeSum where code='" + code + "'";
            ResultSet rs = db.DBSqlQuery(sql);
            if (rs.next()) {
                return rs.getString(2);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(codecMethod.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
