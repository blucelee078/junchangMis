/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * newComerInputUI.java
 *
 * Created on 2011-5-7, 15:58:54
 */
package junchangmis.student;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import publicmethod.*;
import junchangmis.*;
import junchangmis.codecSetting.codecMethod;
import javax.swing.JOptionPane;
import java.awt.event.*;
import javax.swing.*;
import java.util.StringTokenizer;
import java.util.Vector;
import java.sql.ResultSet;

/**
 *
 * @author developers
 */
public class newComerInputUI extends javax.swing.JFrame implements WindowListener {

    //JFrame parent;//可以接受不同的父窗口类型
    newToFormalUI parent1 = null;
    MainFrame parent2 = null;
    newComer newcomerTemp;

    /** Creates new form newComerInputUI */
    public newComerInputUI(newToFormalUI parent, newComer newcomerTemp) {//查询和修改时的构造函数
        this.parent1 = parent;
        this.newcomerTemp = newcomerTemp;
        initComponents();
        this.jButtonAdd.setText("修改而关闭");
        this.jButtonAdd.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInquireAndEditActionPerformed(evt);
            }
        });
        this.jButtonJustExit.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonJustExitActionPerformed(evt);
            }
        });
        infoInquire();
        this.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(this);
        this.setLocationRelativeTo(null);
        this.setTitle("试听生信息查询");
    }

    void infoInquire() {
        this.jLabelTitle.setText("试听生信息查询");
        this.jTextFieldName.setText(this.newcomerTemp.name);
        this.jComboBoxSex.setSelectedItem(this.newcomerTemp.sex);
        StringTokenizer st = new StringTokenizer(this.newcomerTemp.birthday, ".");
        Vector<String> temp = new Vector<String>();
        while (st.hasMoreTokens()) {
            //System.out.println(st.nextToken());
            temp.add(st.nextToken());
        }
        this.jComboBoxYear.setSelectedItem(temp.get(0));
        this.jComboBoxMonth.setSelectedItem(temp.get(1));
        this.jComboBoxDay.setSelectedItem(temp.get(2));

        this.jTextFieldTel.setText(this.newcomerTemp.tel);
        this.jTextFieldSchool.setText(this.newcomerTemp.school);
        this.jTextFieldClassCode.setText(this.newcomerTemp.classCode);


    }

    public newComerInputUI(MainFrame parent) {//添加时的构造函数
        this.parent2 = parent;
        initComponents();
        this.jButtonJustExit.setVisible(false);
        this.jButtonAdd.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });
        this.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(this);
        this.setLocationRelativeTo(null);
        this.setTitle("试听生信息录入");
    }

    private void jButtonInquireAndEditActionPerformed(java.awt.event.ActionEvent evt) {//查询--修改时的按钮消息
        //根据newcomerTemp查询数据库，然后由控件上的各种值进行修改
        String classCode = this.jTextFieldClassCode.getText();
        if (classCode == null || classCode.length() != 6) {
            JOptionPane.showConfirmDialog(null, "专业班级的编码格式错误！", "提示", JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
            this.jLabelCodeShow.setText("");
            this.jTextFieldClassCode.setSelectionStart(0);
        } else {
            String codeTriple = codecMethod.getCodeTripleMeaning(this.jTextFieldClassCode.getText());//获得总编码的翻译结果
            if (codeTriple == null) {
                JOptionPane.showConfirmDialog(null, "不存在该专业班级编码！", "提示", JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
                this.jTextFieldClassCode.setSelectionStart(0);
            } else {
                try {
                    //查询--修改时的按钮消息
                    //根据newcomerTemp查询数据库，然后由控件上的各种值进行修改
                    DbOperation db = new DbOperation();
                    db.DbConnect();
                    String sql = "select * from newComer where name='" + this.newcomerTemp.name + "' and tel='" + this.newcomerTemp.tel + "'";
                    ResultSet rs = db.DBSqlQuery(sql);
                    if (rs.next()) {
                        rs.updateString(1, this.jTextFieldName.getText());
                        rs.updateString(2, this.jComboBoxSex.getSelectedItem().toString());
                        rs.updateString(3, this.jComboBoxYear.getSelectedItem().toString() + "." +
                                this.jComboBoxMonth.getSelectedItem().toString() + "." +
                                this.jComboBoxDay.getSelectedItem().toString());
                        rs.updateString(4, this.jTextFieldTel.getText());
                        rs.updateString(5, this.jTextFieldSchool.getText());
                        rs.updateString(6, this.jTextFieldClassCode.getText());
                        rs.updateRow();//注意，有这一句才能修改成功
                        JOptionPane.showConfirmDialog(null, "修改成功！", "提示", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    }
                    db.DbClose();
                    //更新左右列表
                    parent1.updateLeft();
                    parent1.updateRight();

                    dispose(); //关闭自己
                    parent1.setVisible(true); //显示父窗口
                } catch (SQLException ex) {
                    Logger.getLogger(newComerInputUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void jButtonJustExitActionPerformed(java.awt.event.ActionEvent evt) {//查询--不修改时的按钮消息
        dispose();//关闭自己
        parent1.setVisible(true);//显示父窗口
    }

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//添加时的按钮消息
        //获取录入信息
        newComer student = new newComer();
        student.name = this.jTextFieldName.getText();
        student.sex = this.jComboBoxSex.getSelectedItem().toString();
        student.birthday = this.jComboBoxYear.getSelectedItem().toString() + "." + this.jComboBoxMonth.getSelectedItem().toString() + "." + this.jComboBoxDay.getSelectedItem().toString();
        student.tel = this.jTextFieldTel.getText();
        student.school = this.jTextFieldSchool.getText();
        student.classCode = this.jTextFieldClassCode.getText();
        //添加试听生信息
        newComerInputMethod.addNewComer(student);
        JOptionPane.showConfirmDialog(null, "添加成功！", "提示", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
        //this.setVisible(true);
        dispose();//关闭自己
        parent2.setVisible(true);//显示父窗口
    }

    public void windowActivated(WindowEvent arg0) { //设置为活动窗口时触发
        //System.out.println("windowActivated -->窗口被选中。");
    }

    public void windowClosed(WindowEvent arg0) { //窗口被关闭时触发
        //System.out.println("windowClosed -->窗口被关闭。");
        //parent.setVisible(true);
    }

    public void windowClosing(WindowEvent arg0) { //窗口关闭时触发,按下关闭按钮
        System.out.println("windowClosing -->窗口关闭。");
        if (parent1 != null) {
            parent1.setVisible(true);
        }
        if (parent2 != null) {
            parent2.setVisible(true);
        }
    }

    public void windowDeactivated(WindowEvent arg0) { //设置为非活动窗口时触发
        //System.out.println("windowDeactivated -->取消窗口选中。");
    }

    public void windowDeiconified(WindowEvent arg0) { //窗口从最小化还原时触发
        //System.out.println("windowDeiconified -->窗口从最小化恢复。");
    }

    public void windowIconified(WindowEvent arg0) { //窗口最小化时触发
        //System.out.println("windowIconified -->窗口最小化。");
    }

    public void windowOpened(WindowEvent arg0) { //窗口最小化时触发
        //System.out.println("windowv -->窗口被打开。");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelCenter = new javax.swing.JPanel();
        jPanelBottom = new javax.swing.JPanel();
        jButtonAdd = new javax.swing.JButton();
        jButtonJustExit = new javax.swing.JButton();
        jLabelName = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jLabelSex = new javax.swing.JLabel();
        jComboBoxSex = new javax.swing.JComboBox();
        jLabelBirthday = new javax.swing.JLabel();
        jComboBoxYear = new javax.swing.JComboBox();
        jLabelYear = new javax.swing.JLabel();
        jComboBoxMonth = new javax.swing.JComboBox();
        jLabelMonth = new javax.swing.JLabel();
        jComboBoxDay = new javax.swing.JComboBox();
        jLabelDay = new javax.swing.JLabel();
        jLabelTel = new javax.swing.JLabel();
        jTextFieldTel = new javax.swing.JTextField();
        jLabelSchool = new javax.swing.JLabel();
        jTextFieldSchool = new javax.swing.JTextField();
        jLabelClassCode = new javax.swing.JLabel();
        jTextFieldClassCode = new javax.swing.JTextField();
        jLabelCodeShow = new javax.swing.JLabel();
        jLabelTitle = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButtonAdd.setText("确定");

        jButtonJustExit.setText("不修改而关闭");

        javax.swing.GroupLayout jPanelBottomLayout = new javax.swing.GroupLayout(jPanelBottom);
        jPanelBottom.setLayout(jPanelBottomLayout);
        jPanelBottomLayout.setHorizontalGroup(
            jPanelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBottomLayout.createSequentialGroup()
                .addContainerGap(131, Short.MAX_VALUE)
                .addComponent(jButtonAdd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonJustExit)
                .addGap(39, 39, 39))
        );
        jPanelBottomLayout.setVerticalGroup(
            jPanelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBottomLayout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(jPanelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAdd)
                    .addComponent(jButtonJustExit))
                .addContainerGap())
        );

        jLabelName.setText("姓名");

        jLabelSex.setText("性别");

        jComboBoxSex.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "男", "女" }));

        jLabelBirthday.setText("出生日期");

        jComboBoxYear.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016" }));

        jLabelYear.setText("年");

        jComboBoxMonth.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        jLabelMonth.setText("月");

        jComboBoxDay.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

        jLabelDay.setText("日");

        jLabelTel.setText("家长电话");

        jLabelSchool.setText("学校/幼儿园");

        jLabelClassCode.setText("班级编码");

        jTextFieldClassCode.setToolTipText("输入完六位专业班级编码后，按下回车键，即可看到专业班级名称");
        jTextFieldClassCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldClassCodeActionPerformed(evt);
            }
        });

        jLabelCodeShow.setText("--");

        jLabelTitle.setText("试听生信息录入");

        javax.swing.GroupLayout jPanelCenterLayout = new javax.swing.GroupLayout(jPanelCenter);
        jPanelCenter.setLayout(jPanelCenterLayout);
        jPanelCenterLayout.setHorizontalGroup(
            jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCenterLayout.createSequentialGroup()
                .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCenterLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanelBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelCenterLayout.createSequentialGroup()
                                .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelName)
                                    .addComponent(jLabelBirthday)
                                    .addComponent(jLabelTel)
                                    .addComponent(jLabelClassCode))
                                .addGap(18, 18, 18)
                                .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldClassCode, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextFieldTel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                                    .addGroup(jPanelCenterLayout.createSequentialGroup()
                                        .addComponent(jComboBoxYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabelYear))
                                    .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelCenterLayout.createSequentialGroup()
                                        .addComponent(jComboBoxMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanelCenterLayout.createSequentialGroup()
                                                .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabelSex)
                                                    .addComponent(jLabelMonth))
                                                .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanelCenterLayout.createSequentialGroup()
                                                        .addGap(4, 4, 4)
                                                        .addComponent(jComboBoxSex, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(jPanelCenterLayout.createSequentialGroup()
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jComboBoxDay, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jLabelDay))))
                                            .addGroup(jPanelCenterLayout.createSequentialGroup()
                                                .addComponent(jLabelSchool)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextFieldSchool, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))))
                                    .addComponent(jLabelCodeShow)))))
                    .addGroup(jPanelCenterLayout.createSequentialGroup()
                        .addGap(164, 164, 164)
                        .addComponent(jLabelTitle)))
                .addContainerGap())
        );
        jPanelCenterLayout.setVerticalGroup(
            jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCenterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelName)
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxSex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelSex))
                .addGap(18, 18, 18)
                .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelBirthday)
                    .addComponent(jComboBoxYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelMonth)
                    .addComponent(jComboBoxMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelYear)
                    .addComponent(jComboBoxDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDay))
                .addGap(18, 18, 18)
                .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTel)
                    .addComponent(jTextFieldTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelSchool)
                    .addComponent(jTextFieldSchool, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelClassCode)
                    .addComponent(jTextFieldClassCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCodeShow))
                .addGap(10, 10, 10)
                .addComponent(jPanelBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jPanelCenter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelCenter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldClassCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldClassCodeActionPerformed
        // TODO add your handling code here:
        String classCode = this.jTextFieldClassCode.getText();
        if (classCode == null || classCode.length() != 6) {
            JOptionPane.showConfirmDialog(null, "专业班级的编码格式错误！", "提示", JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
            this.jLabelCodeShow.setText("");
            this.jTextFieldClassCode.setSelectionStart(0);
        } else {
            String codeTriple = codecMethod.getCodeTripleMeaning(this.jTextFieldClassCode.getText());//获得总编码的翻译结果
            if (codeTriple == null) {
                JOptionPane.showConfirmDialog(null, "不存在该专业班级编码！", "提示", JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
                this.jTextFieldClassCode.setSelectionStart(0);
                return;
            }
            this.jLabelCodeShow.setText("：" + codeTriple);
        }
    }//GEN-LAST:event_jTextFieldClassCodeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* java.awt.EventQueue.invokeLater(new Runnable() {

        public void run() {
        new newComerInputUI().setVisible(true);
        }
        });*/
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonJustExit;
    private javax.swing.JComboBox jComboBoxDay;
    private javax.swing.JComboBox jComboBoxMonth;
    private javax.swing.JComboBox jComboBoxSex;
    private javax.swing.JComboBox jComboBoxYear;
    private javax.swing.JLabel jLabelBirthday;
    private javax.swing.JLabel jLabelClassCode;
    private javax.swing.JLabel jLabelCodeShow;
    private javax.swing.JLabel jLabelDay;
    private javax.swing.JLabel jLabelMonth;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelSchool;
    private javax.swing.JLabel jLabelSex;
    private javax.swing.JLabel jLabelTel;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JLabel jLabelYear;
    private javax.swing.JPanel jPanelBottom;
    private javax.swing.JPanel jPanelCenter;
    private javax.swing.JTextField jTextFieldClassCode;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextField jTextFieldSchool;
    private javax.swing.JTextField jTextFieldTel;
    // End of variables declaration//GEN-END:variables
}
