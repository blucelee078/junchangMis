/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package junchangmis.student;

/**
 *
 * @author developers
 */
import java.util.logging.Level;
import java.util.logging.Logger;
import publicmethod.*;
import junchangmis.codecSetting.*;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import java.sql.*;
import java.util.Vector;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.PageOrientation;
import jxl.format.VerticalAlignment;

public class newComerTableExcel {

    private WritableCellFormat timesBoldUnderline;
    private WritableCellFormat times;
    private WritableCellFormat timesOne;
    private WritableCellFormat timesBoldHead;
    private String inputFile;
    String classCode;
    String meaning;

    public newComerTableExcel(String classCode) throws WriteException, IOException {
        this.classCode = classCode;
        String codeTriple = codecMethod.getCodeTripleMeaning(classCode);//获得总编码的翻译结果
        meaning = codeTriple;
    }

    public void setOutputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public void write() throws IOException, WriteException {
        File file = new File(inputFile);
        WorkbookSettings wbSettings = new WorkbookSettings();

        wbSettings.setLocale(new Locale("en", "CN"));

        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
        workbook.createSheet("Report", 0);
        WritableSheet excelSheet = workbook.getSheet(0);
        //设置表格打印属性
        excelSheet.getSettings().setTopMargin(0.3);
        excelSheet.getSettings().setLeftMargin(0.1);
        excelSheet.getSettings().setRightMargin(0.1);
        excelSheet.getSettings().setHorizontalCentre(true);
        excelSheet.getSettings().setOrientation(PageOrientation.LANDSCAPE);

        createLabel(excelSheet);
        createContent(excelSheet);

        workbook.write();
        workbook.close();
    }

    private void createLabel(WritableSheet sheet)
            throws WriteException {
        WritableFont times16ptBoldHead = new WritableFont(WritableFont.TIMES, 16);
        timesBoldHead = new WritableCellFormat(times16ptBoldHead);
        timesBoldHead.setAlignment(Alignment.CENTRE);
        timesBoldHead.setWrap(true);

        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
        times = new WritableCellFormat(times10pt);
        times.setAlignment(Alignment.CENTRE);
        times.setVerticalAlignment(VerticalAlignment.CENTRE);
        times.setBorder(Border.ALL, BorderLineStyle.THIN);
        times.setWrap(true);

        timesOne = new WritableCellFormat(times10pt);
        timesOne.setAlignment(Alignment.CENTRE);
        timesOne.setVerticalAlignment(VerticalAlignment.CENTRE);
        timesOne.setWrap(true);


        WritableFont times10ptBoldUnderline = new WritableFont(
                WritableFont.TIMES, 10, WritableFont.BOLD, false);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        timesBoldUnderline.setAlignment(Alignment.CENTRE);
        timesBoldUnderline.setVerticalAlignment(VerticalAlignment.CENTRE);
        timesBoldUnderline.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
        timesBoldUnderline.setWrap(true);

        CellView cv = new CellView();
        cv.setFormat(times);
        cv.setFormat(timesBoldUnderline);
        cv.setAutosize(true);

        // Write a few headers

        sheet.mergeCells(0, 0, 5, 0);
        addHeader(sheet, 0, 0, "军昌文化艺术学校试听生登记表");
        sheet.setRowView(0, 500);
        sheet.mergeCells(0, 1, 5, 1);
        sheet.setRowView(1, 300);
        addLabelOne(sheet, 0, 1, "班级编码：" + classCode + "(" + meaning + ")" + "        " + "任课老师：                                     " + "试听时间：                                            " + "录入人：");

        addCaption(sheet, 0, 2, "姓名");
        addCaption(sheet, 1, 2, "性别");
        addCaption(sheet, 2, 2, "家长电话");
        addCaption(sheet, 3, 2, "报名时间");
        addCaption(sheet, 4, 2, "出生日期");
        addCaption(sheet, 5, 2, "学校/幼儿园");

        for (int i = 0; i < 6; i++) {
            sheet.setColumnView(i, 20);
        }


    }

    private void createContent(WritableSheet sheet) throws WriteException,
            RowsExceededException {
        try {
            Vector<String> name = new Vector<String>();
            Vector<String> sex = new Vector<String>();
            Vector<String> tel = new Vector<String>();
            Vector<String> enrollDate = new Vector<String>();
            Vector<String> birthday = new Vector<String>();
            Vector<String> school = new Vector<String>();
            DbOperation db = new DbOperation();
            db.DbConnect();
            String sql = "select * from newComer where classCode='" + classCode + "'";
            ResultSet rs = db.DBSqlQuery(sql);
            while (rs.next()) {
                name.add(rs.getString("name"));
                sex.add(rs.getString("sex"));
                tel.add(rs.getString("tel"));
                enrollDate.add(rs.getString("enrollDate"));
                birthday.add(rs.getString("birthday"));
                school.add(rs.getString("school"));
            }
            db.DbClose();
            for (int i = 0; i < name.size(); i++) {
                addLabel(sheet, 0, i + 3, name.get(i));
                addLabel(sheet, 1, i + 3, sex.get(i));
                addLabel(sheet, 2, i + 3, tel.get(i));
                addLabel(sheet, 3, i + 3, enrollDate.get(i));
                addLabel(sheet, 4, i + 3, birthday.get(i));
                addLabel(sheet, 5, i + 3, school.get(i));
            }
        } catch (SQLException ex) {
            Logger.getLogger(newComerTableExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addHeader(WritableSheet sheet, int column, int row, String s)
            throws RowsExceededException, WriteException {
        Label label;
        label = new Label(column, row, s, timesBoldHead);//位置 内容 格式
        sheet.addCell(label);
    }

    private void addCaption(WritableSheet sheet, int column, int row, String s)
            throws RowsExceededException, WriteException {
        Label label;
        label = new Label(column, row, s, timesBoldUnderline);//位置 内容 格式
        sheet.addCell(label);
    }

    private void addLabelOne(WritableSheet sheet, int column, int row, String s)
            throws RowsExceededException, WriteException {
        Label label;
        label = new Label(column, row, s, timesOne);//位置 内容 格式
        sheet.addCell(label);
    }

    private void addNumber(WritableSheet sheet, int column, int row,
            Integer integer) throws WriteException, RowsExceededException {
        Number number;
        number = new Number(column, row, integer, times);//位置 内容 格式
        sheet.addCell(number);
    }

    private void addLabel(WritableSheet sheet, int column, int row, String s)
            throws WriteException, RowsExceededException {
        Label label;
        label = new Label(column, row, s, times);
        sheet.addCell(label);
    }

    public static void main(String[] args) throws WriteException, IOException {
        newComerTableExcel test = new newComerTableExcel("SSY111");
        test.setOutputFile("lars.xls");
        test.write();
        System.out.println("Please check the result file under lars.xls ");
    }
}

