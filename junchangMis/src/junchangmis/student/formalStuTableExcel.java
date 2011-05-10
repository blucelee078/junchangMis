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

import java.util.StringTokenizer;
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

public class formalStuTableExcel {

    private WritableCellFormat timesBoldUnderline;
    private WritableCellFormat times;
    private WritableCellFormat timesOne;
    private WritableCellFormat timesBoldHead;
    private String inputFile;
    String classCode;
    String meaning;

    public formalStuTableExcel(String classCode) throws WriteException, IOException {
        this.classCode = classCode;       
        String codeTriple = codecMethod.getCodeTripleMeaning(classCode);//获得总编码的翻译结果
        meaning = codeTriple ;
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

        sheet.mergeCells(0, 0, 12, 0);
        addHeader(sheet, 0, 0, "军昌文化艺术学校交费登记表");
        sheet.setRowView(0, 500);
        sheet.mergeCells(0, 1, 12, 1);
        sheet.setRowView(1, 300);
        addLabelOne(sheet, 0, 1, "班级编码：" + classCode + "(" + meaning + ")" + "        " + "开学时间：                  " + "上课时间：                     " + "每次课收费：                    " + "教师：");

        addCaption(sheet, 0, 2, "序号");
        addCaption(sheet, 1, 2, "学生姓名");
        addCaption(sheet, 2, 2, "学生编码");
        addCaption(sheet, 3, 2, "学生联系电话");
        addCaption(sheet, 4, 2, "交费起点");
        addCaption(sheet, 5, 2, "截止时间");
        addCaption(sheet, 6, 2, "课次");
        addCaption(sheet, 7, 2, "学费");
        addCaption(sheet, 8, 2, "服装费");
        addCaption(sheet, 9, 2, "书费");
        addCaption(sheet, 10, 2, "其他费");
        addCaption(sheet, 11, 2, "总收费");
        addCaption(sheet, 12, 2, "备注");

        sheet.setColumnView(0, 5);
        sheet.setColumnView(1, 10);
        sheet.setColumnView(2, 10);
        sheet.setColumnView(3, 13);
        for (int i = 4; i < 12; i++) {
            sheet.setColumnView(i, 10);
        }
        sheet.setColumnView(12, 20);


    }

    private void createContent(WritableSheet sheet) throws WriteException,
            RowsExceededException {
        try {
            Vector<String> name = new Vector<String>();
            Vector<String> id = new Vector<String>();
            Vector<String> tel = new Vector<String>();
            Vector<String> dateStart = new Vector<String>();
            Vector<String> weekCount = new Vector<String>();
            Vector<String> coursePerWeek = new Vector<String>();
            Vector<String> tuition = new Vector<String>();
            Vector<String> costumeFee = new Vector<String>();
            Vector<String> bookFee = new Vector<String>();
            Vector<String> otherFee = new Vector<String>();
            Vector<String> sumFee = new Vector<String>();

            DbOperation db = new DbOperation();
            db.DbConnect();
            String sql = "select * from formalStu where classCode='" + classCode + "'";
            ResultSet rs = db.DBSqlQuery(sql);
            while (rs.next()) {
                name.add(rs.getString("name"));
                id.add(rs.getString("id"));
                tel.add(rs.getString("tel"));
                dateStart.add(rs.getString("dateStart"));
                weekCount.add(rs.getString("weekCount"));
                coursePerWeek.add(rs.getString("coursePerWeek"));
                tuition.add(rs.getString("tuition"));
                costumeFee.add(rs.getString("costumeFee"));
                bookFee.add(rs.getString("bookFee"));
                otherFee.add(rs.getString("otherFee"));
                sumFee.add(rs.getString("sumFee"));
            }
            db.DbClose();
            for (int i = 0; i < name.size(); i++) {
                addLabel(sheet, 0, i + 3, Integer.toString(i + 1));
                addLabel(sheet, 1, i + 3, name.get(i));
                addLabel(sheet, 2, i + 3, id.get(i));
                addLabel(sheet, 3, i + 3, tel.get(i));
                addLabel(sheet, 4, i + 3, dateStart.get(i));

                int gap = 7 * Integer.valueOf(weekCount.get(i));
                StringTokenizer st = new StringTokenizer(dateStart.get(i), ".");
                Vector<String> temp = new Vector<String>();
                while (st.hasMoreTokens()) {
                    //System.out.println(st.nextToken());
                    temp.add(st.nextToken());
                }
                int year = Integer.valueOf(temp.get(0));
                int month = Integer.valueOf(temp.get(1));
                int day = Integer.valueOf(temp.get(2));
                addLabel(sheet, 5, i + 3, formalStuInputMethod.getEndDate(year, month, day, gap));

                int count = Integer.valueOf(weekCount.get(i)) * Integer.valueOf(coursePerWeek.get(i));
                addLabel(sheet, 6, i + 3, Integer.toString(count));

                addLabel(sheet, 7, i + 3, tuition.get(i));
                addLabel(sheet, 8, i + 3, costumeFee.get(i));
                addLabel(sheet, 9, i + 3, bookFee.get(i));
                addLabel(sheet, 10, i + 3, otherFee.get(i));
                addLabel(sheet, 11, i + 3, sumFee.get(i));
                addLabel(sheet, 12, i + 3, "");
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

