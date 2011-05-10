/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package publicmethod;

/**
 *
 * @author developers
 */
public class newComer {

    public String name;
    public String sex;
    public String birthday;
    public String tel;
    public String school;
    public String classCode;
    //这里没有加入报名时间这一项 报名时间只在录入时写入数据库 或者在正式生转化为试听生的时候

    public newComer() {
    }

    public newComer(String name, String sex, String birthday, String tel, String school, String classCode) {
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.tel = tel;
        this.school = school;
        this.classCode = classCode;
    }
}
