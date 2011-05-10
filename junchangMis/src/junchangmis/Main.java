/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package junchangmis;

import junchangmis.codecSetting.codecUI;
import java.io.File;

/**
 *
 * @author developers
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        File newComer = new File("试听生登记表");
        newComer.mkdir();
        File formalStu = new File("交费登记表");
        formalStu.mkdir();
        File idAccount = new File("学生证和收据");
        idAccount.mkdir();
        MainFrame fr = new MainFrame();
        fr.setVisible(true);
    }
}
