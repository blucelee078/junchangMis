/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package publicmethod;

/**
 *
 * @author developers
 */
public class formalStu {

    public String id;
    public String name;
    public String sex;
    public String birthday;
    public String tel;
    public String school;
    public String weekCount;
    public String dateStart;
    public String coursePerWeek;
    public String pricePerCourse;
    public String tuition;
    public String costumeFee;
    public String bookFee;
    public String otherFee;
    public String sumFee;
    public String classCode;

    public formalStu() {
    }

    public formalStu(String id, String name, String sex, String birthday, String tel, String school) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.tel = tel;
        this.school = school;
    }
}
